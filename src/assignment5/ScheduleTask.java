package assignment5;

import java.util.TimerTask;
import java.util.Date;

public class ScheduleTask extends TimerTask{
	
	public void run(){
		Critter.displayWorld();
		Critter.worldTimeStep();
		System.out.println("working");
	}
}
