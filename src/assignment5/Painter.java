/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2015
 */
package assignment5;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import javafx.application.Application;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class Painter {
	
	public static int size = 50;
	
	/*
	 * Returns a square or a circle, according to shapeIndex
	 */
	static Shape getIcon(int shapeIndex) {
		Shape s = null;
		int size = 20;
		
		switch(shapeIndex) {
		case 0: s = new Rectangle(size, size); 
			s.setFill(javafx.scene.paint.Color.RED); break;
		case 1: s = new Circle(size/2); 
			s.setFill(javafx.scene.paint.Color.GREEN); break;
		}
		// set the outline of the shape
		s.setStroke(javafx.scene.paint.Color.BLUE); // outline
		return s;
	}
	
	/*
	 * Paints the shape on a grid.
	 */
	public static void paint() {
		Main.grid.getChildren().clear(); // clean up grid.
		
		//creates the grid
		for (int i = 0; i < Params.world_width; i++){
			for (int j = 0; j < Params.world_height; j++){
				Shape s = new Rectangle(size, size);
				s.setFill(javafx.scene.paint.Color.TRANSPARENT);
				s.setStroke(javafx.scene.paint.Color.BLACK);
				Main.grid.add(s, i, j);
			}
		}
		
	}
	
	public static void triangle(int x, int y, javafx.scene.paint.Color color){
		Polygon triangle = new Polygon();
		triangle.getPoints().addAll(new Double[]{
				(double)(size/2), 0.0,
				0.0, (double)size,
				(double)size, (double)size
				});
		triangle.setFill(color);
		Main.grid.add(triangle, x, y);	
	}
	
	public static void diamond(int x, int y, javafx.scene.paint.Color color){
		Polygon diamond = new Polygon();
		diamond.getPoints().addAll(new Double[]{
				(double)(size/2), 0.0,
				0.0, (double)(size/2),
				(double)(size/2), (double)size,
				(double)size, (double)size/2 });
		diamond.setFill(color);
		Main.grid.add(diamond, x, y);	
	}
	
	public static void star(int x, int y, javafx.scene.paint.Color color){
		Polygon star = new Polygon();
		star.getPoints().addAll(new Double[]{
				(double)(size/2), 0.0,
				(double)(size/4), (double)(size/5),
//				0.0, (double)(size/5),
				(double)(size/4), (double)(size/5)*2,
//				0.0, (double)size,
//				(double)(size/2), (double)(size/5)*3,
//				(double)size, (double)size,
				(double)(size/4)*2, (double)(size/5)*2,
//				(double)size, (double)(size/5),
				(double)(size/4)*2, (double)(size/5)});
		star.setFill(color);
		Main.grid.add(star, x, y);	
	}
	
}
