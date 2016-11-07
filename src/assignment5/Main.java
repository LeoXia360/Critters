package assignment5;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.AnimationTimer;

public class Main extends Application{

	static GridPane grid = new GridPane();
	static Timer time = new Timer();
	static double animationSpeed = 1.0;
	static boolean amRunning = false;
	static Toolkit toolkit;
	
	public static void main(String[] args) {
		// launch(args);
		launch(args);
	}
	
//	public void Reminder(){
//		toolkit = Toolkit.getDefaultToolkit();
//		time.schedule(new ScheduleTask(), (double)(1000/animationSpeed));
//	}
	
	class ScheduleTask extends TimerTask{
		public void run(){
			System.out.println("working");
			Critter.worldTimeStep();
			Critter.displayWorld();
			System.exit(0);
		}
	}
	
	@Override
	public void start(Stage primaryStage) {
		Scene scene = new Scene(grid, Params.world_width * Painter.size, Params.world_height * Painter.size);
//		primaryStage.setScene(scene);
//		primaryStage.setTitle("Grid");
//		primaryStage.show();
		// paints the icons
        
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
		Critter.displayWorld();	        
	    Critter.worldTimeStep();
	    primaryStage.show();
	    Critter.displayWorld();
	    Stage secondStage = new Stage();
		secondStage.setTitle("Second Stage");

		System.out.println("done");
		
	}

}
