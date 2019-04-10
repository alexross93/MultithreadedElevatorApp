package elevator;
/*
 * File name: MovingState.java
 * Name: Alexander Ross, 040873561
 * Course: CST8288
 * Assignment: 1
 * Date: Feb.15, 2018
 * Professor: Leanne Seaward
 * Purpose: Enumeration of different elevator states
 */


/**
* This is the MovingState enumeration
*
* @author  Alexander Ross
* @version 1.9.0_4
* @see assignment2
* @since   1.8.0_144
*/
public enum MovingState {
	Up, Down, SlowUp, SlowDown,
    Idle, Off; 
	
	
	/**
	 * Return the slow version of {@link MovingState#Up} or {@link MovingState#Down}, if already slow return the same.
	 * @return slow version of {@link MovingState#Up} or {@link MovingState#Down}.
	 * @throws IllegalStateException - if calling slow on wrong state.
	 */
	public MovingState slow(){
		switch( this){
			case Down:
				return SlowDown;
			case Up:
				return SlowUp;
			case SlowUp:
			case SlowDown:
				return this;
			default:
				throw new IllegalStateException( "ERROR - " + this.name() + " has no slow");
		}
	}

	/**
	 * Return the normal version of {@link MovingState#SlowUp} or {@link MovingState#SlowUp}, if already normal return the same.
	 * @return normal version of {@link MovingState#SlowUp} or {@link MovingState#SlowUp}.
	 * @throws IllegalStateException - if calling slow on wrong state.
	 */
	public MovingState normal(){
		switch( this){
			case SlowDown:
				return Down;
			case SlowUp:
				return Up;
			case Up:
			case Down:
				return this;
			default:
				throw new IllegalStateException( "ERROR - " + this.name() + " has no normal");
		}
	}

	/**
	 * return opposite of each state if any.</br>
	 * {@link MovingState#SlowUp} to {@link MovingState#SlowDown}</br>
	 * {@link MovingState#SlowDown} to {@link MovingState#SlowUp}</br>
	 * {@link MovingState#Up} to {@link MovingState#Down}</br>
	 * {@link MovingState#Down} to {@link MovingState#Up}</br>
	 * @return opposite of state if any
	 * @throws IllegalStateException - if state is has no opposite
	 */
	public MovingState opposite(){
		switch( this){
			case Down:
				return Up;
			case Up:
				return Down;
			case SlowDown:
				return SlowUp;
			case SlowUp:
				return SlowDown;
			default:
				throw new IllegalStateException( "ERROR - " + this.name() + " has no opposite");
		}
	}

	/**
	 * return true if state is {@link MovingState#Down} or {@link MovingState#SlowDown}
	 * @return true if state is {@link MovingState#Down} or {@link MovingState#SlowDown}
	 */
	public boolean isGoingDown(){
		return this == Down || this == SlowDown;
	}

	/**
	 * return true if state is {@link MovingState#Up} or {@link MovingState#SlowUp}
	 * @return true if state is {@link MovingState#Up} or {@link MovingState#SlowUp}
	 */
	public boolean isGoingUp(){
		return this == Up || this == SlowUp;
	}

	/**
	 * return true if state is {@link MovingState#SlowUp} or {@link MovingState#SlowDown}
	 * @return true if state is {@link MovingState#SlowUp} or {@link MovingState#SlowDown}
	 */
	public boolean isGoingSlow(){
		return this == SlowDown || this == SlowUp;
	}

	/**
	 * return true if state is {@link MovingState#Idle}
	 * @return true if state is {@link MovingState#Idle}
	 */
	public boolean isIdle(){
		return this == Idle;
	}

	/**
	 * return true if state is {@link MovingState#Off}
	 * @return true if state is {@link MovingState#Off}
	 */
	public boolean isOff(){
		return this == Off;
	}
}
