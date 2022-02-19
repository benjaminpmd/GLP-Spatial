package tests;

import gui.MainGUI;

public class TestGui {
	public static void main(String[] args) {
		MainGUI mainGUI = new MainGUI();
		Thread simulationThread = new Thread(mainGUI);
		simulationThread.start();
	}
}
