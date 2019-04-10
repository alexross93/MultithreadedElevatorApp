package elevatorsystem;
import java.util.Observer;
import java.util.concurrent.ExecutorService;

import elevator.*;
/*
 * File name: ElevatorSystem.java
 * Name: Alexander Ross, 040873561
 * Course: CST8288
 * Assignment: 1
 * Date: Feb.15, 2018
 * Professor: Leanne Seaward
 * Purpose: ElevatorSystem interface
 */

/**
* This is the ElevatorSystem interface
*
* @author  Alexander Ross
* @version 1.9.0_4
* @see assignment2
* @since   1.8.0_144
*/
public interface ElevatorSystem {


	
	/**
	* Method that calls the elevator up to the specified target floor and returns it
	* 
	* @param floor  the target floor
	* @return the elevator that is called up
	*/
	Elevator callUp(int floor);
	
	/**
	* Method that calls the elevator down to the specified floor and returns it
	* 
	* @param floor  the target floor
	* @return the elevator that is called down
	*/
	Elevator callDown(int floor);
	
	/**
	* Method that adds elevator to the elevator system 
	* 
	* @param elevator  the elevator that is to be added to the elevator system
	*/
	void addElevator(Elevator elevator);
	
	/**
	 * total number of elevators regardless of their states
	 * @return total number of elevators
	 */
	int getElevatorCount();
	
	/**
	 * get total floors to which {@link ElevatorSystem} can send an {@link Elevator}.
	 * behavior and definition of this method will likely change when more elevators are introduced.
	 * 
	 * @return total floors
	 */
	int getFloorCount();
	
	/**
	* Method that calls the elevators getFloor method in order to get the current floor
	* 
	* @return int  the current floor
	*/
	int getCurrentFloor();
	
	/**
	* Method that returns the max floor 
	* 
	* @return int  max floor
	*/
	int getMaxFloor();
	
	/**
	* Method that returns the min floor 
	* 
	* @return int  min floor
	*/
	int getMinFloor();
	
	/**
	* Method that calls the elevator getPowerConsumed method and returns a double 
	* 
	* @return double  which is the power consumed
	*/
	double getPowerConsumed();
	
	/**
	 * add an {@link Observer} to be attached to all {@link Elevator} objects
	 * 
	 * @param observer - to be added to all {@link Elevator}, cannot be null
	 */
	void addObserver( Observer observer);
	
	/**
	 * shutdown {@link ExecutorService} which handles are threads
	 */
	void shutdown();
	
	/**
	 * start the main thread controlling {@link ElevatorSystem}
	 */
	void start();
}
