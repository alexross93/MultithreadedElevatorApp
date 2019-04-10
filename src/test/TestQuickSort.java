package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import elevator.Elevator;
import elevator.ElevatorImp;
import elevator.MovingState;
import elevatorsystem.ElevatorPanel;
import elevatorsystem.ElevatorSystem;
import elevatorsystem.ElevatorSystemImp;
import elevatorsystem.QuickSort;


/**
* This class is to test quick sort ascending and descending
*
* @author  Alexander Ross
* @version 1.9.0_4
* @see assignment2
* @since   1.8.0_144
*/
public class TestQuickSort {

	/** This variable represents the elevator system*/
	private ElevatorSystem system = new ElevatorSystemImp(0, 20);
	/** This variable represents the elevator*/
	ElevatorImp e = new ElevatorImp( 10, (ElevatorPanel) system,1);
	
	/**
	 * check if elevator is in {@link MovingState#Idle}
	 * @return true if current elevator state is {@link MovingState#Idle}
	 */
	@Test
	public void testSort() {
		
		int[] ints = {5,9,4,10,7,2,12};
		List<Integer> list = new ArrayList<>();
		for (int i : ints)
		    list.add(i);

		QuickSort.sortAscen(list);
		
		assertEquals("[2, 4, 5, 7, 9, 10, 12]",list.toString());
	}
	
	/**
	 * check if elevator is in {@link MovingState#Idle}
	 * @return true if current elevator state is {@link MovingState#Idle}
	 */
	@Test
	public void testSortDesc() {
		int[] ints = {5,9,4,10,7,2,12};
		List<Integer> list = new ArrayList<>();
		for (int i : ints)
		    list.add(i);

		QuickSort.sortDesc(list);
		
		assertEquals("[12, 10, 9, 7, 5, 4, 2]",list.toString());
	}
	
	/**
	 * check if elevator is in {@link MovingState#Idle}
	 * @return true if current elevator state is {@link MovingState#Idle}
	 */
	@Test
	public void testSortNegatives() {
		int[] ints = {-12,9,4,-5,7,-1,12};
		List<Integer> list = new ArrayList<>();
		for (int i : ints)
		    list.add(i);

		QuickSort.sortAscen(list);
		
		assertEquals("[-12, -5, -1, 4, 7, 9, 12]",list.toString());
	}
	
	/**
	 * check if elevator is in {@link MovingState#Idle}
	 * @return true if current elevator state is {@link MovingState#Idle}
	 */
	@Test
	public void testSortDescNegatives() {
		int[] ints = {-12,9,4,-5,7,-1,12};
		List<Integer> list = new ArrayList<>();
		for (int i : ints)
		    list.add(i);

		QuickSort.sortDesc(list);
		
		assertEquals("[12, 9, 7, 4, -1, -5, -12]",list.toString());
	}
	
	

}
