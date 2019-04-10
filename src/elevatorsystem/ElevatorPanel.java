package elevatorsystem;

import elevator.*;
/*
 * File name: ElevatorPanel.java
 * Name: Alexander Ross, 040873561
 * Course: CST8288
 * Assignment: 1
 * Date: Feb.15, 2018
 * Professor: Leanne Seaward
 * Purpose: ElevatorPanel interface
 */

/**
* This class is the Elevator Panel interface
*
* @author  Alexander Ross
* @version 1.9.0_4
* @see assignment2
* @since   1.8.0_144
*/
public interface ElevatorPanel {
	
	/**
	* Method that calls requestStop in order to get from current floor to target floor 
	* 
	* @param floor  the target floor
	* @param elevator  the elevator that will call its moveTo method with the target floor as its parameter
	*/
	void requestStop(final Elevator elevator, int floor);
	
	/**
	* Method that calls requestStops in order to get from current floor to multiple floors 
	* 
	* @param floor  the target floors
	* @param elevator  the elevator that will call its moveTo method with the target floors as its parameter
	*/
	void requestStops(final Elevator elevator,final int...floors);

}
