package assignment5;

import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.Response;

import java.util.*;
import javafx.application.Application;
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
	static double animationSpeed = 1.0;
	static boolean amRunning = false;
	static Toolkit toolkit;
	static ArrayList<Class> valid_critters = new ArrayList<Class>();
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.

    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }
	
	
	
	
	public static void main(String[] args) throws ClassNotFoundException, InvalidCritterException { 
		//System.out.println(Critter.class.getPackage().getName());
		//List<Class<?>> cls = ClassFinder.find("assignment5");		
		//System.out.println(cls);

		
		List<String> results = new ArrayList<String>();
		List<Class> class_results = new ArrayList<Class>();

		File[] files = new File("src/assignment5").listFiles();
		//If this pathname does not denote a directory, then listFiles() returns null. 
		System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));

		for (File file : files) {
		    if (file.isFile() && file.toString().endsWith(".java")) {
		        results.add(file.getName().substring(0, file.getName().indexOf(".")));
		        //System.out.println(file.getName().substring(0, file.getName().indexOf(".")));
		        
		        //something is wrong with my class path name
		        Class<?> c = Class.forName(myPackage + "." + file.getName());
		        System.out.println(c.getSuperclass());
		        System.out.println(c);
		    }
		}
		System.out.println(results);
		
		
		//launch(args);
	}
	
	
	@Override
	public void start(Stage primaryStage) {
		Scene scene = new Scene(grid, Params.world_width * Painter.size, Params.world_height * Painter.size);
		
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
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Grid");
		primaryStage.show();
		
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
    
		final ComboBox<String> critterComboBox = new ComboBox<String>();
		critterComboBox.getItems().addAll(
			"Craig",
			"Algaephogic"
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
            		System.out.println("Create Critter");
            		int amount = Integer.parseInt(critterAmount.getText());
            		System.out.println(amount);
        		}
        		catch (NumberFormatException e){
    				System.out.println("error processing " + "'" + critterAmount.getText() + "'" + " in setting amount of Critters");
    			}
        	}
        });
        grid2.add(critterButton, 3, 1);
        
        grid2.add(new Label("Time Step:"), 0, 7);
        Button singleTimeStepButton = new Button();
        singleTimeStepButton.setText("Single");
        singleTimeStepButton.setOnAction(new EventHandler<ActionEvent>(){
        	@Override
        	public void handle(ActionEvent event) {
        		System.out.println("Single");
        	}
        });
        grid2.add(singleTimeStepButton, 0, 8);
        grid2.add(new Label("Amount"), 1, 8);
        TextField timeStepAmount = new TextField();
        grid2.add(timeStepAmount, 2, 8);
        Button multipleButton = new Button();
        multipleButton.setText("Step");
        multipleButton.setOnAction(new EventHandler<ActionEvent>(){
        	@Override
        	public void handle(ActionEvent event) {
        		try{
        			System.out.println("Multiple");
            		int amount = Integer.parseInt(timeStepAmount.getText());
            		System.out.println(amount);	
        		}
        		catch (NumberFormatException e){
    				System.out.println("error processing " + "'" + timeStepAmount.getText() + "'" + " in setting amount of Time Steps");
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
        			System.out.println("Seed");
            		int amount = Integer.parseInt(seedAmount.getText());
            		System.out.println(amount);
            		Critter.setSeed(amount);
        		}
        		catch (NumberFormatException e){
    				System.out.println("error processing " +  "'" + seedAmount.getText() + "'" + " in setting amount for Seed");
    			}
        	}
        });
        grid2.add(seedButton, 2, 16);
       
        grid2.add(new Label("Animation:"), 0, 22);
        grid2.add(new Label("Speed"), 0, 23);
        final ComboBox<String> animationSpeedBox = new ComboBox<String>();
        animationSpeedBox.getItems().addAll(
			"1x",
			"2x"
		);
        grid2.add(animationSpeedBox, 1, 23);
        Button startButton = new Button();
        startButton.setText("Start");
        startButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		System.out.println("Start");
        	}
        });
        grid2.add(startButton, 2, 23);
        Button stopButton = new Button();
        stopButton.setText("Stop");
        stopButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		System.out.println("Stop");
        	}
        });
        grid2.add(stopButton, 3, 23);
        
        primaryStage.setScene(new Scene(grid2, 600, 450));
        primaryStage.show();

		
	}

}
