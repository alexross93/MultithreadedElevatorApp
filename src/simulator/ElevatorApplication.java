package simulator;

import java.util.Observable;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.util.Observer;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import elevator.*;
import elevatorsystem.*;
import simulator.*;
import elevator.Elevator;
import elevator.ElevatorImp;
import elevatorsystem.ElevatorPanel;
import elevatorsystem.ElevatorSystem;
import elevatorsystem.ElevatorSystemImp;
/*
 * File name: ElevatorApplication.java
 * Name: Alexander Ross, 040873561
 * Course: CST8288
 * Assignment: 1
 * Date: Feb.15, 2018
 * Professor: Leanne Seaward
 * Purpose: Launches application
 */

/**
* This class is where we set the four elevator animations into the scene
*
* @author  Alexander Ross
* @version 1.9.0_4
* @see assignment2
* @since   1.8.0_144
*/
public class ElevatorApplication extends Application implements Observer{

	/**the elevator animation array of size 4 who's start method will be called*/
	private ElevatorAnimation[] animator = new ElevatorAnimation[4];
	/**floor count for creating the arrays of labels*/
	private static final int FLOOR_COUNT = 21;
	/** Instantiate the simulator*/
	Simulator s = new Simulator(this);

	/**
	 * This method shuts down the simulator  
	 */
	@Override
	public void stop() {
		s.shutdown();
	}
	
	/**
	 * Starts the application, creates the GUI and calls the simulators and animators start methods 
	 * 
	 * @param primaryStage  the stage that will call its setScene method in order to set the scene
	 * and then its show method is called
	 */
	@Override
	public void start(Stage primaryStage)  {

		HBox root = new HBox();
		root.setPrefWidth(1275);
		root.setPrefHeight(800);

		//create each elevator animation, put everything in each root node by calling initialize, start each animation
		for(int n=0;n<4;n++) {
			 animator[n] = new ElevatorAnimation();
			 animator[n].initalize(root);
			 animator[n].start();
		}
	    
		
	    Scene scene = new Scene(root, 1275,600, Color.LIGHTGRAY);
	    scene.getStylesheets().add(ElevatorApplication.class.getResource("elevator.css").toExternalForm());
	    primaryStage.setScene(scene);
	    primaryStage.setTitle("Alex's Elevator Application");

	    // close on escape
	    primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
	        if (KeyCode.ESCAPE == event.getCode()) {
	           for(int m=0;m<4;m++) {
	        	   animator[m].stop();
	            
	           }
	           primaryStage.hide();
	        }
	    });

	    s.start();
	    primaryStage.show();
	}
	
	
	/**
	 * Updates the view from the model(observable)
	 * 
	 * @param o  observable that will be cast to Elevator
	 * @param arg  object that is returned from notifyObservers
	 */
	@Override
	public void update(Observable o, Object arg) {
		ElevatorData data = new ElevatorData();
		Elevator e = (Elevator) o;
		data.ID = e.id();
		data.currentFloor = e.getFloor();
		data.targetFloor = (int) arg;
		data.energy = e.getPowerConsumed();
		data.speed = e.getState()==MovingState.SlowUp || e.getState()==MovingState.SlowDown;
		animator[data.ID].addData(data);	
	}

	/**
	 * This class is where we create the elevator animations
	 */
	private class ElevatorAnimation extends AnimationTimer{
	
		/** array of labels that will show the drawing of each floor*/
		private Label floors[];
		/** array of labels that will show the floor numbers*/
		private Label floorNumbers[];
		/** The three textfields that will display current floor, target floor and power consumed*/
		TextField tfcf, tftf, tfpu, tlid;
		
		private Queue<ElevatorData> queue = new LinkedList<ElevatorData>();

		private static final long SECOND = 1000000001;
		private static final long NORMAL_SPEED = SECOND/7;
		private static final long SLOW_SPEED = SECOND/2;
		
		private int targetFloor;
		private int currentFloor = 0;
		private long timeCheck=0;
		private long prevFrame=0;
		int id;
		
		/**
		 * This method initializes the elevator animations
		 * 
		 * @param hbox the node is the root node that holds the four elevator animations
		 */
		public void initalize(HBox hbox) {
			
			GridPane rootPane = new GridPane();
			GridPane gridPane = new GridPane();
			gridPane.setPadding(new Insets(3, 3, 3, 3));
			
		    floors = new Label[FLOOR_COUNT];
		    floorNumbers = new Label[FLOOR_COUNT];
		    for (int i = 0; i < FLOOR_COUNT; i++) {
		        floors[i] = new Label();
		        floors[i].setId("empty");
		        floorNumbers[i] = new Label(Integer.toString(i));
		    }
		    floors[0].setId("elevator"); 
		    
		    
		    for (int i = FLOOR_COUNT - 1; i >= 0; i--) {
		        gridPane.add(floorNumbers[i], 0, (FLOOR_COUNT - 1) - i);
		    }
		
		    for (int i = FLOOR_COUNT - 1; i >= 0; i--) {
		        gridPane.add(floors[i], 1, (FLOOR_COUNT - 1) - i);
		    }
		    
		    Label lcf = new Label("Current Floor:");
		    Label ltf = new Label("Target Floor:");
		    Label lpu = new Label("Power Consumed:");
		    Label lid = new Label("Elevator ID:");
		    lcf.setStyle("-fx-font: 12 arial;");
		    ltf.setStyle("-fx-font: 12 arial;");
		    lpu.setStyle("-fx-font: 12 arial;");
		    lid.setStyle("-fx-font: 12 arial;");
		
		    tfcf = new TextField();
		    tfcf.setEditable(false);
		    tfcf.setStyle("-fx-font: 12 arial;");
		    tfcf.setPrefWidth(50);
		    tftf = new TextField();
		    tftf.setEditable(false);
		    tftf.setStyle("-fx-font: 12 arial;");
		    tftf.setPrefWidth(50);
		    tfpu = new TextField();
		    tfpu.setEditable(false);
		    tfpu.setStyle("-fx-font: 12 arial;");
		    tfpu.setPrefWidth(50);
		    tlid = new TextField();
		    tlid.setEditable(false);
		    tlid.setStyle("-fx-font: 12 arial;");
		    tlid.setPrefWidth(50);
		
		    GridPane infoPane = new GridPane();
		    infoPane.addColumn(0, lid, lcf, ltf, lpu );
		    infoPane.addColumn(1, tlid, tfcf, tftf, tfpu );
		    infoPane.setAlignment(Pos.CENTER);
		    
		    rootPane.add(gridPane, 0, 0);
		    rootPane.add(infoPane, 1,0);
		    
		    hbox.getChildren().add(rootPane);
		}
		
	
		/**
		 * This method creates the view of the movement of floors in the elevators
		 * 
		 */
		@Override
		public void handle(long now) {
			
			if(now-prevFrame<timeCheck) {
				return;
			}
			prevFrame=now;
			
			if(queue.isEmpty())
				return;
		
			ElevatorData data = queue.poll();
				
			timeCheck=data.speed?SLOW_SPEED:NORMAL_SPEED;
					
			targetFloor = data.targetFloor;
			floors[currentFloor].setId("empty");
			currentFloor = data.currentFloor;
			id = data.ID;
					
			tfcf.setText(Integer.toString(currentFloor));
			tftf.setText(Integer.toString(targetFloor));
			tfpu.setText(Double.toString(data.energy));	
			tlid.setText(Double.toString(id));	
					
			floors[targetFloor].setId("target");
			floors[currentFloor].setId("elevator");	
		}
	
		public void addData(ElevatorData data) {
			queue.add(data);
		}
	}


	/**
	 * Elevator Data class that holds important information with regards to the elevator
	 */
	public class ElevatorData {
		/** The id of the elevator*/
		public int ID;
		/** The currentFloor that updates the view*/
		public int currentFloor;
		/** The currentFloor that updates the view*/
		public int targetFloor;
		/** This is the power consumed*/
		public double energy;
		/** This boolean is true if elevator is in slow state and false if it is in normal state*/
		public boolean speed;
	}

	/**
	 * Calls applications launch method
	 * 
	 * @param args  string array to be passed arguments to applications launch method
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}

}

