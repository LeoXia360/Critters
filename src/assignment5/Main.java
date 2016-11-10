package assignment5;

import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.Response;

import java.util.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.AnimationTimer;

public class Main extends Application{

	static GridPane grid = new GridPane();
	//static GridPane grid2 = new GridPane();
	static Timer time = new Timer();
	static int animationSpeed;
	static boolean amRunning = false;
	static Toolkit toolkit;
	static ArrayList<Class> valid_critters = new ArrayList<Class>();
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
	static List<String> critter_string = new ArrayList<String>();
	static int size = 20;
	Thread animationOfGrid;
	Button clearButton;
	Button stopButton;


    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }
	
    
    
	public static void main(String[] args) throws ClassNotFoundException{ 
		get_critter_list();
		launch(args);
	}
	
	
	/**
	 * Returns the list of critters in the working directory
	 * 
	 */
	public static void get_critter_list(){
		
		List<String> results = new ArrayList<String>();
		List<Class> class_results = new ArrayList<Class>();

		File[] files = new File("src/assignment5").listFiles();
		//If this pathname does not denote a directory, then listFiles() returns null. 
//		System.out.println("Working Directory = " +
//	              System.getProperty("user.dir"));

		for (File file : files) {
		    if (file.isFile() && file.toString().endsWith(".java")) {
		        results.add(file.getName().substring(0, file.getName().indexOf(".")));
		        String cls = file.getName().substring(0,file.getName().indexOf('.'));
		        
		        try{
		        	Class<?> c = Class.forName(myPackage + "." +  cls);
			        String super_class = c.getSuperclass().getName().substring(c.getSuperclass().getName().indexOf('.') + 1);
			        if(!cls.equals("Header") && super_class.equals("Critter")){
			        	class_results.add(c);
			        	critter_string.add(c.getName().substring(c.getName().indexOf('.') + 1));
			        }
		        }
		        catch (ClassNotFoundException e){
		        	//do nothing
		        }
		        
		    }
		}
	}
	
	
	@Override
	public void start(Stage primaryStage) {
		Scene scene = new Scene(grid, Params.world_width * size, Params.world_height * size);
		
		try {
			for(int i = 0; i < 2; i++){
	        	Critter.makeCritter("Craig");
	        }
	        for(int j = 0; j < 2; j++){
	        	Critter.makeCritter("Algae");
	        }
		}catch (Exception e) {
			e.printStackTrace();
		}
        grid.setPadding(new Insets(10));
		Critter.displayWorld();
		primaryStage.setScene(scene);
		primaryStage.setTitle("Grid");
		primaryStage.show();
		
//		Timer time = new Timer();
//		ScheduleTask st = new ScheduleTask();
//		time.schedule(st, 0, 1000);
		
		
//		new AnimationTimer(){
//			@Override
//			public void handle(long now){
//				if (now%100 == 0){
//					System.out.println(now);
//					Critter.displayWorld();
//					Critter.worldTimeStep();
//				}
//			}
//		}.start();
		
        GridPane grid2 = new GridPane();
        grid2.setHgap(20);
        grid2.setVgap(10);
        grid2.setPadding(new Insets(10));
//        ColumnConstraints c1 = new ColumnConstraints();
//        c1.setPercentWidth(25);
//        ColumnConstraints c2 = new ColumnConstraints();
//        c2.setPercentWidth(25);
//        ColumnConstraints c3 = new ColumnConstraints();
//        c3.setPercentWidth(25);
//        ColumnConstraints c4 = new ColumnConstraints();
//        c4.setPercentWidth(25);
//        grid2.getColumnConstraints().addAll(c1, c2, c3, c4);
    
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(25);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(25);
        ColumnConstraints c3 = new ColumnConstraints();
        c3.setPercentWidth(25);
        ColumnConstraints c4 = new ColumnConstraints();
        c4.setPercentWidth(25);
        grid2.getColumnConstraints().addAll(c1, c2, c3, c4);
		final ComboBox critterComboBox = new ComboBox();
		critterComboBox.getItems().addAll(
			critter_string
		);
        grid2.add(new Label("Make Critter: "), 0, 0);
        grid2.add(critterComboBox, 0, 1);
        grid2.add(new Label("Amount"), 1, 1);
        TextField critterAmount = new TextField();
        grid2.add(critterAmount, 2, 1);
        Button critterButton = new Button();
        critterButton.setText("Create");
        critterButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		try{
            		String critter = (String)critterComboBox.getValue();
            		if (critter == null){
            			StackPane pane1 = new StackPane();
            			Label critterError = new Label("A critter has not been selected to add to the World.");
            			pane1.getChildren().add(critterError);
            			Stage critterErrorStage = new Stage();
            			critterErrorStage.setTitle("Critter Error");
            			Scene scene2 = new Scene(pane1, 300, 50);
            			critterErrorStage.setScene(scene2);
            			critterErrorStage.show();
            			int amount = Integer.parseInt(critterAmount.getText());
            		}
            		else{
            			/*
            			 * Need to add functionanlity to this part to actually add critters.
            			 */
            			int amount = Integer.parseInt(critterAmount.getText());
            			for (int i = 0; i < amount; i++){
            				try{
            					Critter.makeCritter((String)critterComboBox.getValue());
            				}	catch(InvalidCritterException e){
        						System.out.println("error processing: ");
        					}
        					catch (Exception e){
        						System.out.println("error processing: ");
        					}
            			}
                		Critter.displayWorld();
//                		primaryStage.setScene(scene);
//                		primaryStage.setTitle("Grid");
                		primaryStage.show();
            		}
        		}
        		catch (NumberFormatException e){
        			StackPane pane2 = new StackPane();
        			Label NumberError = new Label("Error processing " + "'" + critterAmount.getText() + "'" + " in setting amount of Critters");
        			pane2.getChildren().add(NumberError);
        			Stage NumberErrorStage = new Stage();
        			NumberErrorStage.setTitle("Critter Amount Error");
        			Scene scene3 = new Scene(pane2, 300, 50);
        			NumberErrorStage.setScene(scene3);
        			NumberErrorStage.show();
    			}
        	}
        });
        grid2.add(critterButton, 3, 1);
        
        grid2.add(new Label("Time Step:"), 0, 7);
        Button singleButton = new Button();
        singleButton.setText("Single");
        singleButton.setOnAction(new EventHandler<ActionEvent>(){
        	@Override
        	public void handle(ActionEvent event) {
        		Critter.worldTimeStep();
        		Critter.displayWorld();
//        		primaryStage.setScene(scene);
//        		primaryStage.setTitle("Grid");
        		primaryStage.show();
        	}
        });
        grid2.add(singleButton, 0, 8);
        grid2.add(new Label("Amount"), 1, 8);
        TextField timeStepAmount = new TextField();
        grid2.add(timeStepAmount, 2, 8);
        Button multipleButton = new Button();
        multipleButton.setText("Step");
        multipleButton.setOnAction(new EventHandler<ActionEvent>(){
        	@Override
        	public void handle(ActionEvent event) {
        		try{
            		int amount = Integer.parseInt(timeStepAmount.getText());
            		for (int i = 0; i < amount; i++){
            			Critter.worldTimeStep();
            		}
            		Critter.displayWorld();
//            		primaryStage.setScene(scene);
//            		primaryStage.setTitle("Grid");
            		primaryStage.show();
        		}
        		catch (NumberFormatException e){
        			StackPane pane4 = new StackPane();
        			Label NumberError = new Label("Error processing " + "'" + timeStepAmount.getText() + "'" + " in setting amount of Time Steps");
        			pane4.getChildren().add(NumberError);
        			Stage NumberErrorStage = new Stage();
        			NumberErrorStage.setTitle("Time Step Amount Error");
        			Scene scene4 = new Scene(pane4, 300, 50);
        			NumberErrorStage.setScene(scene4);
        			NumberErrorStage.show();
    			}
        	}
        });
        grid2.add(multipleButton, 3, 8);
        
        
        grid2.add(new Label("Set Seed:"), 0, 15);
        grid2.add(new Label("Amount"), 0, 16);
        TextField seedAmount = new TextField();
        grid2.add(seedAmount, 1, 16);
        Button seedButton = new Button();
        seedButton.setText("Set");
        seedButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		try{
            		int amount = Integer.parseInt(seedAmount.getText());
            		Critter.setSeed(amount);
        		}
        		catch (NumberFormatException e){
    				StackPane pane5 = new StackPane();
        			Label NumberError = new Label("Error processing " +  "'" + seedAmount.getText() + "'" + " in setting amount for Seed");
        			pane5.getChildren().add(NumberError);
        			Stage NumberErrorStage = new Stage();
        			NumberErrorStage.setTitle("Seed Amount Error");
        			Scene scene4 = new Scene(pane5, 300, 50);
        			NumberErrorStage.setScene(scene4);
        			NumberErrorStage.show();
    			}
        	}
        });
        grid2.add(seedButton, 2, 16);
       
        grid2.add(new Label("Animation:"), 0, 22);
        grid2.add(new Label("Speed"), 0, 23);
        final ComboBox animationSpeedBox = new ComboBox();
        animationSpeedBox.getItems().addAll("1x", "2x", "5x", "10x", "20x", "50x", "100x");
        grid2.add(animationSpeedBox, 1, 23);
        Button startButton = new Button();
        startButton.setText("Start");
        startButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		String animation = (String)animationSpeedBox.getValue();
        		if (animation == null){
        			StackPane pane6 = new StackPane();
        			Label animationError = new Label("Animation speed has not been selected");
        			pane6.getChildren().add(animationError);
        			Stage animationErrorStage = new Stage();
        			animationErrorStage.setTitle("Critter Error");
        			Scene scene5 = new Scene(pane6, 300, 50);
        			animationErrorStage.setScene(scene5);
        			animationErrorStage.show();
        		}
        		else{
        			animationOfGrid = new Thread(new Runnable() { 
        				@Override 
        				public void run() { 
        					try { 
        						while (true) { 
        							Platform.runLater(new Runnable() { // Run from JavaFX GUI 
        								@Override 
        								public void run() { 
                							Critter.worldTimeStep();
        									Critter.displayWorld(); 
        								} 
        							}); 

        							Thread.sleep(1000 / animationSpeed);
        						}
        					}
        					catch (InterruptedException ex) {
        					}
        				}
        			});
        			animation = animation.replace("x", "");
        			animationSpeed = Integer.parseInt(animation);
//        			critterButton.setDisable(true);
//        			critterComboBox.setDisable(true);
//        			animationSpeedBox.setDisable(true);
//        			singleButton.setDisable(true);
//        			multipleButton.setDisable(true);
//        			seedButton.setDisable(true);
//        			stopButton.setDisable(true);
//        			startButton.setDisable(true);
//        			clearButton.setDisable(true);
        			animationOfGrid.start();
        		}
        	}
        });
        grid2.add(startButton, 2, 23);
        Button stopButton = new Button();
        stopButton.setText("Stop");
        stopButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		animationOfGrid.stop();
        	}
        });
        grid2.add(stopButton, 3, 23);
        
        grid2.add(new Label("Clear Board:"), 0, 30);
        clearButton = new Button();
        clearButton.setText("Clear");
        clearButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		Critter.clearWorld();
        		Critter.displayWorld();
//    			critterButton.setDisable(false);
//    			critterComboBox.setDisable(false);
//    			animationSpeedBox.setDisable(false);
//    			singleButton.setDisable(false);
//    			multipleButton.setDisable(false);
//    			seedButton.setDisable(false);
//        		primaryStage.setScene(scene);
//        		primaryStage.setTitle("Grid");
        		primaryStage.show();
        	}
        });
        grid2.add(clearButton, 0, 31);
//        stopButton.setDisable(true);
        
        Stage secondStage = new Stage();
		secondStage.setTitle("Second Stage");
		secondStage.setScene(new Scene(grid2, 600, 550));
		secondStage.show();
		
	}
}
