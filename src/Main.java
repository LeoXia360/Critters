package assignment5;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class Main extends Application{

	static GridPane grid = new GridPane();
	
	public static void main(String[] args) {
		// launch(args);
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Scene scene = new Scene(grid, Params.world_width * Painter.size, Params.world_height * Painter.size);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Grid");
			primaryStage.show();
			// paints the icons
			
	        for(int i = 0; i < 2; i++){
	        	Critter.makeCritter("Craig");
	        }
	        for(int j = 0; j < 2; j++){
	        	Critter.makeCritter("Algae");
	        }

			Critter.displayWorld();
			
			Stage secondStage = new Stage();
			secondStage.setTitle("Second Stage");
						
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

}
