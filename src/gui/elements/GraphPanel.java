package gui.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import config.SimConfig;
import process.management.TelemetryRecord;

/**
 * This class draws a graph of one out of three possible types : Speed, Acceleration, and Altitude.
 * Used in {@link gui.SimulationGUI}.
 * 
 * @see gui.SimulationGUI
 * @author Alice Mabille
 */
public class GraphPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private TelemetryRecord telemetryRecord;
	private ArrayList<Integer> dataArray = new ArrayList<Integer>();
	private ChartPanel chartPanel;
	
	private String title;
	
	/**
	 * Draws a graph using an array of data from {@link MissionManager} at t=0, so it will probably be empty until you call the update() and repaint() methods.
	 *
	 * @param title should be either "Speed", "Acceleration", or "Altitude".
	 * @see MissionManager
	 */
	public GraphPanel(String title, TelemetryRecord telemetryRecord) {
		super();
		this.telemetryRecord = telemetryRecord;
		setPreferredSize(SimConfig.IDEAL_GRAPH_DIMENSION);
		setBackground(Color.DARK_GRAY);
		this.title = title;

		chartPanel = new ChartPanel(getEvolutionChart());
		update();

		chartPanel.setPreferredSize(SimConfig.IDEAL_GRAPH_DIMENSION);
		add(chartPanel);

	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	/**
	 * Generates the curve chart for evolution of the chosen data
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
	 * Gets the latest data corresponding to the title of the GraphPanel from the class {@link MissionManager}
	 */
	public void update() {
		switch(title) {
		case "Speed" :
			dataArray = telemetryRecord.getVelocities();
			break;
		
		case "Acceleration" :
			dataArray = telemetryRecord.getAccelerations();
			break;
		
		case "Altitude" :
			dataArray = telemetryRecord.getAltitudes();
			break;

		default :
			throw new IllegalArgumentException("Cannot draw a graph about "+title);
		}
		chartPanel.setChart(getEvolutionChart());
	}

	public void setElementsBackground(Color color) {
		chartPanel.getChart().getPlot().setBackgroundPaint(color);
		chartPanel.getChart().setBackgroundPaint(color);
	}

	public void setElementsForeground(Color color) {
		chartPanel.getChart().getTitle().setPaint(color);
	}
}