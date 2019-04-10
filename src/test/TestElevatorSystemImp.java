/**
 * 
 */
package test;

import static org.junit.Assert.*;

import org.junit.Test;

import elevator.Elevator;
import elevator.ElevatorImp;
import elevatorsystem.ElevatorPanel;
import elevatorsystem.ElevatorSystem;
import elevatorsystem.ElevatorSystemImp;

/**
* This class is to test the elevator system implementation methods
*
* @author  Alexander Ross
* @version 1.9.0_4
* @see assignment2
* @since   1.8.0_144
*/
public class TestElevatorSystemImp {

	/** This variable represents the elevator system*/
	private ElevatorSystem system = new ElevatorSystemImp(0, 20);
	/** This variable represents an elevator*/
	ElevatorImp e = new ElevatorImp( 10, (ElevatorPanel) system,1);
	/** This variable represents another elevator*/
	ElevatorImp e2 = new ElevatorImp( 0, (ElevatorPanel) system,1);
	
	/**
	 * Test method for {@link elevatorsystem.ElevatorSystemImp#ElevatorSystemImp(int, int)}.
	 * testing error check for when negative number entered as min floor in ElevatorImp constructor 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testElevatorSystemImpMinNegative() {
		system = new ElevatorSystemImp(-1, 20);
	}

	/**
	 * Test method for {@link elevatorsystem.ElevatorSystemImp#ElevatorSystemImp(int, int)}.
	 * testing error check for when negative number entered as max floor in ElevatorImp constructor
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testElevatorSystemImpMaxNegative() {
		system = new ElevatorSystemImp(0, -20);
	}
	
	/**
	 * Test method for {@link elevatorsystem.ElevatorSystemImp#requestStop(int, elevator.Elevator)}.
	 * testing error check for when negative number inputed to moveTo() method
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRequestNegativeStop() {

		system.addElevator(e);
		e.requestStop(-1);
	}
	
	/**
	 * Test method for {@link elevatorsystem.ElevatorSystemImp#requestStop(int, elevator.Elevator)}.
	 * testing error check for when negative number inputed to moveTo() method
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRequestNegativeStops() {

		system.addElevator(e);
		e.requestStops(-1,-2,-5);
	}
	
	/**
	 * Test method for {@link elevatorsystem.ElevatorSystemImp#requestStop(int, elevator.Elevator)}.
	 * testing error check for when number over max floor inputed to requestStop() method
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testRequestOverMaxStop() {

		system.addElevator(e);
		e.requestStop(21);
	}

	/**
	 * Test method for {@link elevatorsystem.ElevatorSystemImp#callUp(int)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void callUpOverMax() {

		system.addElevator(e);
		Elevator elevator = system.callUp(21);
	}
	
	/**
	 * Test method for {@link elevatorsystem.ElevatorSystemImp#callUp(int)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void callUpNegative() {

		system.addElevator(e);
		Elevator elevator = system.callUp(-1);
	}

	/**
	 * Test method for {@link elevatorsystem.ElevatorSystemImp#callUp(int)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void callDownOverMax() {

		system.addElevator(e);
		Elevator elevator = system.callDown(21);
	}
	
	/**
	 * Test method for {@link elevatorsystem.ElevatorSystemImp#callUp(int)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void callDownNegative() {

		system.addElevator(e);
		Elevator elevator = system.callDown(-1);
	}

	/**
	 * Test method for {@link elevatorsystem.ElevatorSystemImp#getPowerConsumed()}.
	 */
	@Test
	public void testGetPowerConsumed() {
		system.addElevator(e2);
		Elevator elevator = system.callUp(0);
		elevator.requestStop(2);
		
		assertEquals(system.getPowerConsumed(),12.0,0.01);
	}

	/**
	 * Test method for {@link elevatorsystem.ElevatorSystemImp#getMaxFloor()}.
	 */
	@Test
	public void testGetMaxFloor() {
	
		system.addElevator(e);
		e.moveTo(3);
		assertEquals(system.getMaxFloor(),20);
	}

	/**
	 * Test method for {@link elevatorsystem.ElevatorSystemImp#getMinFloor()}.
	 */
	@Test
	public void testGetMinFloor() {

		system.addElevator(e);
		e.moveTo(3);
		assertEquals(system.getMinFloor(),0);
	}
	
	/**
	 * Test method for {@link elevator.ElevatorImp#getFloor()}.
	 * testing getter
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testAddNullElevator() {
		system.addElevator(null);
		e.moveTo(5);
	}

}
