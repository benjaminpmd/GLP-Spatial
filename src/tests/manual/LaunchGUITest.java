package tests.manual;

import engine.process.configuration.LaunchConfigurator;
import gui.LaunchGUI;

/**
 * 
 * @author alice
 *
 */
public class LaunchGUITest {
	public static void main(String[] args) {
		LaunchConfigurator launchConfigurator = new LaunchConfigurator();
		launchConfigurator.createLaunch("src/tests/launch.csv");
		LaunchGUI launchGUI = new LaunchGUI("LaunchGUI");

	}
	
}
