package elevator;
/*
 * File name: Elevator.java
 * Name: Alexander Ross, 040873561
 * Course: CST8288
 * Assignment: 1
 * Date: Feb.15, 2018
 * Professor: Leanne Seaward
 * Purpose: Elevator interface
 */

import java.util.Observer;

/**
* This is the Elevator interface
*
* @author  Alexander Ross
* @version 1.9.0_4
* @see assignment2
* @since   1.8.0_144
*/
public interface Elevator {

	/**
	* Method that processes each floor and notifies the observer till destination is reached
	* 
	* @param floor  the target floor
	*/
	void moveTo(final int floor);
	
	/**
	* Method that adds person top elevator 
	* 
	* @param person  the person to be added
	*/
	void addPersons(final int person);
	
	/**
	* Method that requests stop via the panel
	* 
	* @param floor  the target floor
	*/
	void requestStop(final int floor);
	
	/**
	 * represents the request made by multiple passengers inside of an {@link Elevator} object
	 * 
	 * @param floors - target floors
	 */
	void requestStops( final int...floors);
	
	/**
	* Method that gets capacity of the elevator 
	* 
	* @return capacity
	*/
	int getCapacity();

	/**
	* Method that returns the current floor
	* 
	* @return int  the current floor
	*/
	int getFloor();

	/**
	* Method that gives us the energy used
	* 
	* @return powerUsed  the energy used (slow start/stop=2 or continuous=1)
	*/
	double getPowerConsumed();

	/**
	* Method that returns the moving state (Up, Down, SlowUp, SlowDown, Idle or Off)
	* 
	* @return MovingState  the state of the elevator
	*/
	MovingState getState();
	
	/**
	* Method that checks if the elevator is at max capacity and thus full
	* 
	* @param boolean  true is capacity is at max capacity of persons
	*/
	boolean isFull();
	
	/**
	* Method that checks if elevator is empty
	* 
	* @param boolean  true if capacity is zero
	*/
	boolean isEmpty();
	
	/**
	 * check if elevator is in MovingState.Idle
	 * 
	 * @return true if current elevator state is MovingState.Idle
	 */
	boolean isIdle();
	
	/**
	 * Unique integer that identifies this {@link Elevator} object
	 * 
	 * @return unique identifier integer
	 */
	int id();
	
	/**
	 * add an {@link Observer} to this {@link Elevator}
	 * 
	 * @param observer - add to this {@link Elevator}, cannot be null
	 */
	void addObserver( Observer observer);
	
	/**
	 * call direction
	 * 
	 * @param state - store call direction from call up or call down
	 */
	void callDirection(MovingState state);
	
	/**
	 * get call direction
	 * 
	 * @return MovingState of call direction
	 */
	MovingState getCallDirection();
	
	
}
