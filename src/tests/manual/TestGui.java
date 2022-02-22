package tests.manual;

import gui.MainGUI;

public class TestGui {
	public static void main(String[] args) {
		MainGUI mainGUI = new MainGUI("GLP - Spatial");
		Thread simulationThread = new Thread(mainGUI);
		simulationThread.start();
	}
}
