package elevatorsystem;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import elevator.*;
/*
 * File name: ElevatorSystemImp.java
 * Name: Alexander Ross, 040873561
 * Course: CST8288
 * Assignment: 1
 * Date: Feb.15, 2018
 * Professor: Leanne Seaward
 * Purpose: Implements the elevator system
 */

/**
* This class is to implement the ElevatorSystem
*
* @author  Alexander Ross
* @version 1.9.0_4
* @see assignment2
* @since   1.8.0_144
*/
public class ElevatorSystemImp implements ElevatorPanel, ElevatorSystem  {
	
	/** This variable represents the request lock*/
	private final Object REQUEST_LOCK = new Object();
	/** This variable represents the maximum amount of floors the elevator system can have*/
	private final int MAX_FLOOR;
	/** This variable represents the minimum amount of floors the elevator system can have*/
	private final int MIN_FLOOR;
	/** This variable represents the map*/
	private Map<Elevator,List<Integer>> stops;
	/** This variable represents the executor service*/
	private ExecutorService service;
	/** This variable represents whether elevator is shutdown or not*/
	private boolean shutdown = false;
	/** This variable help keeps track of the order of elevators*/
	
	
	/** This variable represents Runnable which loops infinitely with a shutdown boolean as condition*/
	private Runnable run = ()-> {
		//array of atomic integers of size 4, assign one for each elevator
		AtomicInteger counters[] = new AtomicInteger[stops.size()];
		for(int i=0;i<counters.length;i++) {
			counters[i] = new AtomicInteger();
		}
		
		//loop while shutdown is false
		while(!shutdown) {
			
			synchronized(REQUEST_LOCK) {
			for(Elevator e : stops.keySet()) {
				//if elevator not idle or elevator atomic int not zero
				if(!e.isIdle() || counters[e.id()].get() != 0) {
					continue;
				}
				
				//only one thread at a time
				
					
					//get list of stops and continue if empty
					List<Integer> list = stops.get(e);
					//System.out.println("stops " + stops.ge);
					if(list.isEmpty()) {
						continue;
					}
				
					//System.out.println("\nelevator " +e.id()+" move to: "+floor+"\n");
					//remove first item from list and assign it to floor, atomically increment integer by one, return updated value
					int floor = list.remove(0);
					counters[e.id()].incrementAndGet();
					
					//execute runnable, call elevator moveTo and atomically decrement by one, return updated value
					service.execute( ()->{
						System.out.println("moo: "+floor);
						e.moveTo(floor);
						counters[e.id()].decrementAndGet();
					});
				}	
			}
		}
	};
	
	/**
	 * Constructor for the elevator system that sets its minimum and maximum floors
	 * 
	 * @param MIN_FLOOR max floor of elevator system
	 * @param MAX_FLOOR min floor of elevator system
	 */
	public ElevatorSystemImp(int MIN_FLOOR,int MAX_FLOOR) {
		if(MIN_FLOOR<0 || MAX_FLOOR<0)
			throw new IllegalArgumentException();
		this.MAX_FLOOR = MAX_FLOOR;
	    this.MIN_FLOOR = MIN_FLOOR;
	    this.service = Executors.newCachedThreadPool();
	    this.stops = new HashMap<>(4);
	}
	
	/**
	 * start the main thread controlling {@link ElevatorSystem}
	 */
	@Override
	public void start() {
		service.execute(run);
	}
	
	/**
	 * shutdown {@link ExecutorService} which handles our threads
	 */
	@Override
	public void shutdown() {
		shutdown=true;
		service.shutdown();	
	}
	
	/**
	 * Method that calls requestStop in order to get from current floor to target floor 
	 * 
	 * @param floor  the target floor
	 * @param elevator  the elevator that will call its moveTo method with the target floor as its parameter
	 */
	public void requestStop(Elevator elevator, int floor) {
		if(floor>getMaxFloor() || floor<getMinFloor())
			throw new IllegalArgumentException("out of bounds");
		requestStops(elevator,floor); 
	}
	
	/**
	 * called from {@link Elevator} to inform {@link ElevatorSystem} of multiple stop requests.
	 * 
	 * @param elevator - reference to the calling elevator.
	 * @param floors - new stops to which {@link Elevator} will travel.
	 * 
	 */
	public void requestStops(final Elevator elevator,final int...floors) {
		for(int floor : floors) {
			if(floor>getMaxFloor() || floor<getMinFloor())
				throw new IllegalArgumentException("out of bounds");
		}

		System.out.println("req elev id: "+elevator.id());
		
	
		//only one thread can get the list of floors at a time and sort according to call direction
		synchronized(REQUEST_LOCK) {
			//get list from elevator
			List<Integer> list = stops.get(elevator);
			
			//add floors to list
			IntStream.of(floors).forEach (floor -> list.add(floor));
			//sort list according to call direction
			if(elevator.getCallDirection() == MovingState.Up) {
				System.out.println("up in sync id: "+elevator.id());
				QuickSort.sortAscen(list);
			}else {
				System.out.println("down in sync id: "+elevator.id());
				QuickSort.sortDesc(list);
			}
		}	
	}
	
	/**
	 * Method that gets available elevator which is called form the call method
	 * 
	 * @param floor  the target floors
	 * @return the elevator that is available
	 */
	private synchronized Elevator getAvailableElevatorIndex(final int floors) {
		Elevator availableElevator = null;
		synchronized(REQUEST_LOCK) {
		for(Elevator e: stops.keySet()) {
			
			if(e.getState()==MovingState.Idle && stops.get(e).size()==0  )
				availableElevator = e;
		}
		System.out.println("i: "+availableElevator.id());
		return availableElevator;
		}
		
	}
	
	/**
	 * Method to call elevator according to floor and direction
	 * 
	 * @param floor  the floor to check
	 * @return elevator that is available
	 */
	public Elevator call(final int floor,final MovingState direction) {
		try {
			//check if floor is in bounds and if there is an elevator
			floorCheck(floor);
			checkForElevator();
			//Callable task to ExecutorService using submit() and return object of type Future(this locks thread)
			Future<Elevator> result = service.submit( () ->{
				
				Elevator availableElevator = getAvailableElevatorIndex(floor);
				
				//if elevator not null move to floor and set call direction
				if(availableElevator!=null) {
					availableElevator.moveTo(floor);
					availableElevator.callDirection(direction);
				}
				System.out.println("elevator: "+availableElevator.id() + " move to "+ floor );
				return availableElevator;
			});
			
			return result.get();
			
		}catch(InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Method that checks floor making sure it is in bounds
	 * 
	 * @param floor  the floor to check
	 */
	public void floorCheck(int floor) {
		if (floor < MIN_FLOOR || floor > MAX_FLOOR) {
			throw new IllegalArgumentException("out of bounds");
		}
	}
	
	/**
	 * Method that calls the elevator up to the specified target floor and returns it
	 * 
	 * @param floor  the target floor
	 * @return the elevator that is called up
	 */
	public Elevator callUp(final int floor) {
		if(floor>getMaxFloor()|| floor<getMinFloor())
			throw new IllegalArgumentException("out of bounds");
		return call(floor,MovingState.Up);
	}
	
	/**
	 * Method that calls the elevator down to the specified floor and returns it
	 * 
	 * @param floor  the target floor
	 * @return the elevator that is called down
	 */
	public Elevator callDown(final int floor) {
		if(floor>getMaxFloor()|| floor<getMinFloor())
			throw new IllegalArgumentException("out of bounds");
		return call(floor, MovingState.Down);
	}
	
	/**
	 * Method that adds elevator to the elevator system 
	 * 
	 * @param elevator  the elevator that is to be added to the elevator system with new linked list
	 */
	public void addElevator(final Elevator elevator) {
		if(elevator==null)
			throw new IllegalArgumentException("elevator is null");
		stops.put(elevator, new LinkedList<>());
	}
	
	
	/**
	 * add an {@link Observer} to be attached to all {@link Elevator} objects
	 * 
	 * @param observer - to be added to all {@link Elevator}, cannot be null
	 */
	@Override
	public void addObserver( final Observer observer) {
		if(observer==null)
			throw new IllegalArgumentException("observer is null");
		stops.keySet().stream().forEach( e->e.addObserver(observer));
	}

	
	/**
	 * Method that checks to make sure there are elevators in stops
	 * 
	 */
	private void checkForElevator() {
		if(stops.isEmpty())
			throw new IllegalStateException("none exist");
	}
	
	/**
	 * total number of elevators regardless of their states
	 * 
	 * @return total number of elevators
	 */
	public int getElevatorCount() {
		return stops.size();
	}

	/**
	 * Method that calls the elevator getPowerConsumed method and returns a double 
	 * 
	 * @return double  which is the power consumed
	 */
	public double getPowerConsumed() {
		checkForElevator();
		return stops.keySet().stream().mapToDouble( Elevator::getPowerConsumed ).sum();
	}
	
	/**
	 * return current floor of {@link Elevator} in {@link ElevatorSystem}.
	 * since there is only 1 {@link Elevator} no need for any arguments.
	 * this method will likely change inn future designs.
	 * @return current floor of only {@link Elevator}
	 */
	@Deprecated
	public int getCurrentFloor() {
		throw new UnsupportedOperationException("since assignment 2, should not be used anymore since there are more than one elevator");
	}
	
	/**
	 * Method that counts the floors 
	 * 
	 * @return int  the number of floors
	 */
	@Override
	public int getFloorCount() {
		return Math.abs(MAX_FLOOR-MIN_FLOOR);
	}
	
	/**
	 * Method that returns the max floor 
	 * 
	 * @return int  max floor
	 */
	public int getMaxFloor() {
		return MAX_FLOOR;	
	}
	
	/**
	 * Method that returns the min floor 
	 * 
	 * @return int  min floor
	 */
	public int getMinFloor() {
		return MIN_FLOOR;	
	}
	
}
