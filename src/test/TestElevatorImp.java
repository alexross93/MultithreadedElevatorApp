package test;

import static org.junit.Assert.*;

import org.junit.Test;

import elevator.Elevator;
import elevator.ElevatorImp;
import elevator.MovingState;
import elevatorsystem.ElevatorPanel;
import elevatorsystem.ElevatorSystem;
import elevatorsystem.ElevatorSystemImp;

/**
* This class is to test the elevator implementation methods
*
* @author  Alexander Ross
* @version 1.9.0_4
* @see assignment2
* @since   1.8.0_144
*/
public class TestElevatorImp {

	/** This variable represents the elevator system*/
	private ElevatorSystem system = new ElevatorSystemImp(0, 20);
	/** This variable represents the elevator*/
	ElevatorImp e = new ElevatorImp( 10, (ElevatorPanel) system,1);
	
	/**
	 * Test method for {@link elevator.ElevatorImp#ElevatorImp(double,elevatorsystem.ElevatorPanel))}.
	 * testing error check for when negative capacity entered into ElevatorImp constructor 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testElevatorImpConstructor() {
		ElevatorImp e = new ElevatorImp(-1,(ElevatorPanel) system,1);
	}
	
	/**
	 * Test method for {@link elevator.ElevatorImp#moveTo(int)}.
	 * testing error check for when negative number inputed to moveTo() method
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testMoveToNegativeFloor() {	
		system.addElevator(e);
		e.moveTo(-1);
	}

	/**
	 * Test method for {@link elevator.ElevatorImp#requestStop(int)}.
	 * testing error check for when number over max floor inputed to requestStop() method
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRequestOverMaxStop() {
		system.addElevator(e);
		e.requestStop(21);
	}
	
	/**
	 * Test method for {@link elevator.ElevatorImp#requestStop(int)}.
	 * testing error check for when negative number inputed to moveTo() method
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRequestNegativeStop() {
		system.addElevator(e);
		e.requestStop(-1);
	}
	
	/**
	 * Test method for {@link elevator.ElevatorImp#requestStop(int)}.
	 * testing error check for when negative number inputed to moveTo() method
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRequestNegativeStops() {
		system.addElevator(e);
		e.requestStops(-10,-5);
	}

	/**
	 * Test method for {@link elevator.ElevatorImp#getFloorCount()}.
	 * testing getter
	 */
	@Test
	public void testGetFloor() {
		system.addElevator(e);
		e.moveTo(5);
		assertEquals(system.getFloorCount(),20);
	}

	/**
	 * Test method for {@link elevator.ElevatorImp#isFull()}.
	 */
	@Test
	public void testIsFull() {
		e.addPersons(10);
		assertEquals(e.isFull(),true);
	}

	/**
	 * Test method for {@link elevator.ElevatorImp#isEmpty()}.
	 */
	@Test
	public void testIsEmpty() {
		system.addElevator(e);
		assertEquals(e.isEmpty(),true);
	}

	/**
	 * Test method for {@link elevator.ElevatorImp#getPowerConsumed()}.
	 */
//	@Test
//	public void testGetPowerConsumed() {
//		system.addElevator(e);
//		Elevator elevator = system.callUp(0);
//		elevator.requestStop(2);
//		assertEquals(e.getPowerConsumed(),12.0,0.01);
//	}

	/**
	 * Test method for {@link elevator.ElevatorImp#addPersons(int)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testMaxAddPersons() {
		system.addElevator(e);
		Elevator elevator = system.callUp(0);
		elevator.addPersons(11);
	}
	
	/**
	 * Test method for {@link elevator.ElevatorImp#addPersons(int)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeAddPersons() {
		system.addElevator(e);
		Elevator elevator = system.callUp(0);
		elevator.addPersons(11);
	}

	/**
	 * Test method for {@link elevator.ElevatorImp#getState()}.
	 */
	@Test
	public void testGetState() {
		system.addElevator(e);
		Elevator elevator = system.callUp(0);
		elevator.addPersons(1);
		assertEquals(elevator.getState(),MovingState.Idle);
	}

	/**
	 * Test method for {@link elevator.ElevatorImp#getCapacity()}.
	 */
	@Test
	public void testGetCapacity() {
		system.addElevator(e);
		Elevator elevator = system.callUp(0);
		elevator.addPersons(9);
		assertEquals(e.getCapacity(),1);
	}
	
	/**
	 * Test method for {@link elevator.ElevatorImp#isIdle()}.
	 */
	@Test
	public void testIsIdle() {
		system.addElevator(e);
		Elevator elevator = system.callUp(0);
		assertEquals(elevator.isIdle(),true);
	}
	
	/**
	 * Test method for {@link elevator.ElevatorImp#id()}.
	 */
	@Test
	public void testId() {
		system.addElevator(e);
		Elevator elevator = system.callUp(0);
		assertEquals(elevator.id(),1);
	}
}
