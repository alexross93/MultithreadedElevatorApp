package elevator;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import elevatorsystem.*;
/*
 * File name: ElevatorImp.java
 * Name: Alexander Ross, 040873561
 * Course: CST8288
 * Assignment: 1
 * Date: Feb.15, 2018
 * Professor: Leanne Seaward
 * Purpose: Implements the Elevator
 */


/**
* This class is to implement the Elevator
*
* @author  Alexander Ross
* @version 1.9.0_4
* @see assignment2
* @since   1.8.0_144
*/

public class ElevatorImp extends Observable implements Elevator  {
	
	/** This variable represents the amount of power the elevator requires to start and stop */
	final public int POWER_START_STOP = 2;
	/** This variable represents the amount of power the elevator requires to move up or down continuously*/
	final public  int POWER_CONTINUOUS = 1;
	/** This variable represents the amount of time to sleep for start or stop */
	final public long SLEEP_START_STOP = 250;
	/** This variable represents the amount of time to sleep for continuous */
	final public  long SLEEP_CONTINUOUS = 125;
	/** This variable represents the maximum amount of people that the elevator can hold*/
	final private  double MAX_CAPACITY_PERSONS;
	/** This variable represents the delay*/
    private boolean delay = true;
	/** This variable represents the unique ID for each elevator*/
	private int ID = 0;
	/** This variable represents the amount of power used by the elevator*/
	private double powerUsed = 0;
	/** This variable represents the current floor of the elevator*/
	private int currentFloor = 0;
	/** This variable represents the amount of people that the elevator can hold*/
	private int capacity = 0;
	/** This variable represents the elevator panel inside of the elevator*/
	private ElevatorPanel panel;
	/** This variable represents the current state of the elevator (Up, Down, SlowUp, SlowDown, Idle or Off)*/
	private MovingState state = MovingState.Idle;
	/** This variable is a placeholder for the floor that was requested by the user*/
	private int targetFloor;
	/** This variable is a placeholder for the call direction of call up or call down*/
	private MovingState callDirection;
	
	/**
	 * Constructor for the elevator, sets capacity and sets the panel that is passed 
	 * 
	 * @param CAPACITY_PERSONS  the capacity of the elevator
	 * @param panel the elevator panel
	 * @param id id of elevator
	 */
	public ElevatorImp(double CAPACITY_PERSONS, ElevatorPanel panel,int id) {
		if(CAPACITY_PERSONS<0)
			throw new IllegalArgumentException("negative capacity");
		MAX_CAPACITY_PERSONS=CAPACITY_PERSONS;
		this.panel = panel;
		this.ID=id;
	}
	
	/**
	 * Constructor for the elevator, sets capacity and sets the panel that is passed 
	 * 
	 * @param CAPACITY_PERSONS  the capacity of the elevator
	 * @param panel the elevator panel
	 * @param id id of elevator
	 * @param delay boolean
	 */
	public ElevatorImp(double CAPACITY_PERSONS, ElevatorPanel panel,int id, boolean delay) {
		if(CAPACITY_PERSONS<0)
			throw new IllegalArgumentException();
		MAX_CAPACITY_PERSONS=CAPACITY_PERSONS;
		this.panel = panel;
		this.ID=id;
		this.delay=delay;
	}
	
	/**
	 * Method that processes each floor and notifies the observer till destination is reached
	 * 
	 * @param floor  the target floor
	 */
	public void moveTo(int floor) {
		if(floor<0)
			throw new IllegalArgumentException("out of bounds");
		targetFloor = floor;
		
		//process each floor and notify observer till destination is reached and is thus idle
		while(processMovingState(floor)) {
			setChanged();
			notifyObservers(targetFloor);
		}	
	}
	
	/**
	 * Method that calls processingMoveState in order to get form current floor to target floor 
	 * 
	 * @param floor  the target floor
	 * @throws InterruptedException 
	 */
	private boolean processMovingState(int floor) {
		//below target floor
		try {
			//target floor above current floor
			if(getFloor()<floor) {
				//calculate difference between current and target floor
				int delta = floor-getFloor();
				//if difference greater than 2, state is normal
				if(delta>2) {
					state = MovingState.Up;
					powerUsed+=POWER_CONTINUOUS;
					Thread.sleep(SLEEP_CONTINUOUS);
				//if difference is less than 2, state is slow
				}else {
					state = MovingState.SlowUp;
					powerUsed+=POWER_START_STOP;
					Thread.sleep(SLEEP_START_STOP);
				}
				currentFloor++;
				return true;
			//current floor above target floor
			}else if(getFloor()>floor) {
				int delta=getFloor()-floor;
				//if difference greater than 2, state is normal
				if(delta>2) {
					state = MovingState.Down;
					powerUsed+=POWER_CONTINUOUS;
					Thread.sleep(SLEEP_CONTINUOUS);
				//if difference is less than 2, state is slow
				}else {
					state = MovingState.SlowDown;
					powerUsed+=POWER_START_STOP;
					Thread.sleep(SLEEP_START_STOP);
				}
				currentFloor--;
				return true;
			//in idle state, returns false to break out of loop
			}else {
				state = MovingState.Idle;
				Thread.sleep(SLEEP_START_STOP);
				return false;
			}
		}catch (InterruptedException e) {
				e.printStackTrace();
		}
		return true;
	}

	/**
	 * Method that requests stop via the panel
	 * 
	 * @param floor  the target floor
	 */
	public void requestStop(int floor) {
		 panel.requestStop(this, floor);
	}
	
	/**
	 * Method that requests multiple stops via the panel
	 * 
	 * @param floor  the target floor
	 */
	public void requestStops(int...floor) {
		 panel.requestStops(this, floor);
	}
	
	/**
	 * Method that returns the current floor
	 * 
	 * @return int  the current floor
	 */
	public int getFloor() {
		return currentFloor;
	}
	
	/**
	 * Method that checks if the elevator is at max capacity and thus full
	 * 
	 * @param boolean  true is capacity is at max capacity of persons
	 */
	public boolean isFull() {
		return capacity>=MAX_CAPACITY_PERSONS;
	}
	
	/**
	 * Method that checks if elevator is empty
	 * 
	 * @param boolean  true if capacity is zero
	 */
	public boolean isEmpty() {
		return capacity==0;
	}
	
	/**
	 * check if elevator is in {@link MovingState#Idle}
	 * 
	 * @return true if current elevator state is {@link MovingState#Idle}
	 */
	public boolean isIdle() {
		return (state==MovingState.Idle);
	}
	
	
	/**
	 * Method that gives us the energy used
	 * 
	 * @return powerUsed  the energy used (slow start/stop=2 or continuous=1)
	 */
	public double getPowerConsumed() {
		return powerUsed;
	}
	
	/**
	 * Method that adds person top elevator 
	 * 
	 * @param person  the person to bea added
	 */
	public void addPersons(int person) {
		int total = capacity+person;
		if(total>MAX_CAPACITY_PERSONS || total<0)
			throw new IllegalArgumentException("out of bounds");
		capacity=total;
	}
	
	/**
	 * Method that returns the moving state (Up, Down, SlowUp, SlowDown, Idle or Off)
	 * 
	 * @return MovingState  the state of the elevator
	 */
	public MovingState getState() {
		return state; 
	}
	
	/**
	 * Method that gets capacity of the elevator 
	 * 
	 * @return capacity
	 */
	public int getCapacity() {
		return (int) MAX_CAPACITY_PERSONS-capacity;
	}
	
	/**
	 * Unique integer that identifies this {@link Elevator} object
	 * 
	 * @return unique identifier integer
	 */
	public int id() {
		return ID;
	}
	
	/**
	 * hashcode that takes id creates unique number
	 * 
	 * @return int hash
	 */
	public int hashCode() {
		int result = 37*ID;
		return result;
	}
	
	/**
	 * check if objects are equal
	 * 
	 * @return boolean whether they are equal
	 */
	public boolean equals(Object obj) 
	{
		return (this == obj);
	}
	
	/**
	 * Method that sets initial direction form call up or call down
	 * 
	 * @param state state returned from call up or call down
	 */
	public void callDirection(MovingState state) {
		callDirection = state;
	}
	
	/**
	 * Method that gets the call direction
	 * 
	 * @return call direction
	 */
	public MovingState getCallDirection() {
		return callDirection;
	}
	

}
