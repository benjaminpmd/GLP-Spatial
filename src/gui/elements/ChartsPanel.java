package gui.elements;

import config.SimConfig;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import process.management.TelemetryRecord;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Class containing methods allowing both the acceleration and velocity data charts of the rocket to be drawn.
 * Used in {@link gui.SimulationGUI}.
 *
 * @author Alice M
 * @version 22.04.28 (1.0.0)
 * @see process.management.TelemetryRecord
 * @see gui.SimulationGUI
 * @since 22.02.22
 */
public class ChartsPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private final TelemetryRecord telemetryRecord;
    private final ChartPanel chartPanel;
    private final String title;
    private ArrayList<Integer> dataArray = new ArrayList<Integer>();

    /**
     * Draws a graph using an array of data from {@link process.management.SimulationManager} at t=0, so it will probably be empty until you call the update() and repaint() methods.
     *
     * @param title should be either "Speed", "Acceleration", or "Altitude"
     * @see gui.SimulationGUI, process.management.SimulationManager
     */
    public ChartsPanel(String title, TelemetryRecord telemetryRecord) {
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
        switch (title) {
            case "Speed":
                dataArray = telemetryRecord.getVelocities();
                break;

            case "Acceleration":
                dataArray = telemetryRecord.getAccelerations();
                break;

            default:
                throw new IllegalArgumentException("Cannot draw a graph about " + title);
        }
        chartPanel.setChart(getEvolutionChart());
    }
}