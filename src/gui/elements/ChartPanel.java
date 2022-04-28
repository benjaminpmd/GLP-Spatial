package gui.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import config.SimConfig;
import process.management.TelemetryRecord;

/**
 * Class containing methods allowing both the acceleration and velocity data charts of the rocket to be drawn.
 * Used in {@link SimulationGUI}.
 * 
 * @see process.management.TelemetryRecord
 * @see gui.SimulationGUI
 * @author Alice Mabille
 * @version 22.04.28 (1.0.0)
 * @since 22.02.22
 */
public class ChartPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private TelemetryRecord telemetryRecord;
	private ArrayList<Integer> dataArray = new ArrayList<Integer>();
	private org.jfree.chart.ChartPanel chartPanel;

	private final Color BACKGROUND_COLOR = new Color(60,61,64);
	private final Color TEXT_COLOR = new Color(240, 240, 240);
	private final Color LINES_COLOR = new Color(255, 10, 10);

	private String title;
	
	/**
	 * Draws a graph using an array of data from {@link SimulationManager} at t=0, so it will probably be empty until you call the update() and repaint() methods.
	 *
	 * @see gui.SimulationGUI, process.management.SimulationManager
	 * @param title should be either "Speed", "Acceleration", or "Altitude"
	 * 
	 */
	public ChartPanel(String title, TelemetryRecord telemetryRecord) {
		super();
		this.telemetryRecord = telemetryRecord;
		setPreferredSize(SimConfig.IDEAL_GRAPH_DIMENSION);
		setBackground(Color.DARK_GRAY);
		this.title = title;

		chartPanel = new org.jfree.chart.ChartPanel(getEvolutionChart());
		update();

		chartPanel.setPreferredSize(SimConfig.IDEAL_GRAPH_DIMENSION);
		add(chartPanel);

	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

	}

	/**
	 * Generates the curve chart for evolution of the chosen data.
	 * 
	 * @return the curve chart
	 */
	public JFreeChart getEvolutionChart() {
		XYSeries serie = new XYSeries("");
		for (int index = 0; index < dataArray.size(); index++) {
			serie.add(index, dataArray.get(index));
		}

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(serie);

		return ChartFactory.createXYLineChart(title, "time", "", dataset, PlotOrientation.VERTICAL, false, false, false);
	}

	/**
	 * Gets the latest data from the {@link TelemetryRecord}.
	 * 
	 * @see process.management.TelemetryRecord
	 */
	public void update() {
		switch(title) {
		case "Speed" :
			dataArray = telemetryRecord.getVelocities();
			break;
		
		case "Acceleration" :
			dataArray = telemetryRecord.getAccelerations();
			break;

		default :
			throw new IllegalArgumentException("Cannot draw a graph about "+title);
		}
		chartPanel.setChart(getEvolutionChart());
	}
}