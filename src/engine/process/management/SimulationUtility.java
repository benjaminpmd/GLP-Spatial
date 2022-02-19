package engine.process.management;

import config.SimConfig;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SimulationUtility {
	
	/**
	 * Reads a image from an image file.
	 * 
	 * @param filePath the path (from "src") of the image file
	 * @return the read file
	 */
	public static Image readImage(String filePath) {
		try {
			return ImageIO.read(new File(filePath));
		} catch (IOException e) {
			System.err.println("-- Can not read the image file ! --");
			return null;
		}
	}

	/**
	 * Simulates the unit time (for an iteration) defined for the simulation. 
	 */
	public static void unitTime() {
		try {
			Thread.sleep(SimConfig.SIMULATION_SPEED);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Simulated the necessary refresh time for avoiding printing issue.
	 */
	public static void windowRefreshTime() {
		try {
			Thread.sleep(SimConfig.SIMULATION_SPEED * 10);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}
}
