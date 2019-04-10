package elevatorsystem;

import java.util.ArrayList;
import java.util.List;

/**
* This class is to implement the ElevatorSystem
*
* @author  Alexander Ross
* @version 1.9.0_4
* @see assignment2
* @since   1.8.0_144
*/
public class QuickSort {

	/** This variable will be the list to sort*/
	private static List<Integer> list;
	/** This variable will be the length of list*/
    private static int length;
 
    /**
	 * Sorts the list in ascending order by calling the quick sort method for ascending
	 * 
	 * @param inputArr the list to be sorted in ascending order
	 */
    public static void sortAscen(List<Integer> inputArr) {
         
        if (inputArr == null || inputArr.size()-1 == 0) {
            return;
        }
        list = inputArr;
        length = inputArr.size();
        quickSortAscen(0, length - 1);
        
        for(Integer i:list)
        	System.out.println("q:"+i);
    }
    
    
    /**
	 * Sorts the list in descending order by calling the quick sort method for ascending
	 * 
	 * @param inputArr the list to be sorted in descending order
	 */
    public static void sortDesc(List<Integer> inputArr) {
        
        if (inputArr == null || inputArr.size()-1 == 0) {
            return;
        }
        list = inputArr;
        length = inputArr.size();
        quickSortDesc(0, length - 1);
    }
 
    
    /**
	 * Updates the view from the model(observable)
	 * 
	 * @param o  observable that will be cast to Elevator
	 * @param arg  object that is returned from notifyObservers
	 */
    private static void quickSortAscen(int lowerIndex, int higherIndex) {
         
        int i = lowerIndex;
        int j = higherIndex;
        //calculate pivot as middle index number
        int pivot = list.get(lowerIndex+(higherIndex-lowerIndex)/2);

        //divide into two arrays
        while (i <= j) {
            /**
             * In each iteration, we will identify a number from left side which 
             * is greater then the pivot value, and also we will identify a number 
             * from right side which is less then the pivot value. Once the search 
             * is done, then we exchange both numbers.
             */
        	//identify a number from left side which is greater then the pivot value
            while (list.get(i) < pivot) {
                i++;
            }
            //identify a number from right side which is less then the pivot value
            while (list.get(j) > pivot) {
                j--;
            }
            //once the search is done then exchange both numbers
            if (i <= j) {
                exchangeNumbers(i, j);
                //move index to next position on both sides
                i++;
                j--;
            }
        }
        // call quickSort() method recursively for both sides of pivot
        if (lowerIndex < j)
            quickSortAscen(lowerIndex, j);
        if (i < higherIndex)
            quickSortAscen(i, higherIndex);
    }
    
    /**
	 * Updates the view from the model(observable)
	 * 
	 * @param o  observable that will be cast to Elevator
	 * @param arg  object that is returned from notifyObservers
	 */
    private static void quickSortDesc(int lowerIndex, int higherIndex) {
        
        int i = lowerIndex;
        int j = higherIndex;
        // calculate pivot as middle index number
        int pivot = list.get(lowerIndex+(higherIndex-lowerIndex)/2);

        //divide into two arrays
        while (i <= j) {
            /**
             * In each iteration, we will identify a number from left side which 
             * is greater then the pivot value, and also we will identify a number 
             * from right side which is less then the pivot value. Once the search 
             * is done, then we exchange both numbers.
             */
        	//identify a number from right side which is greater then the pivot value
            while (list.get(i) > pivot) {
                i++;
            }
            //identify a number from left side which is less then the pivot value
            while (list.get(j) < pivot) {
                j--;
            }
            
            //once the search is done then exchange both numbers
            if (i <= j) {
                exchangeNumbers(i, j);
                //move index to next position on both sides
                i++;
                j--;
            }
        }
        
        // call quickSortDesc() method recursively for both sides of pivot
        if (lowerIndex < j)
            quickSortDesc(lowerIndex, j);
        if (i < higherIndex)
            quickSortDesc(i, higherIndex);
    }
 
    /**
	 * This method swaps the the two values in the list accordingly
	 * 
	 * @param i  lower index to swap
	 * @param j  upper index to swap
	 */
    private static void exchangeNumbers(int i, int j) {
        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

}