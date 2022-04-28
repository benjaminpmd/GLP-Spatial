package gui.elements;

import java.awt.Color;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import process.repositories.SpaceCenterRepository;

/**
 * Class containing a panel with pre-made buttons. Allows the user to choose the launch site.
 * Used in {@link MainGUI}.
 *
 * @see gui.MainGUI
 * @author Alice Mabille
 * @version 22.04.28 (1.0.0)
 * @since 22.02.11
 */
public class SpaceCentersPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	// Datas
	private SpaceCenterRepository repo = SpaceCenterRepository.getInstance();
	private Set<String> centersSet = repo.getKeys();
	private JList<String> list;
	
	private JLabel centersLabel = new JLabel("Launch sites");

	/**
	 * Builds a JPanel that displays every registered launch sites to be selected by the user.
	 */
	public SpaceCentersPanel() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(centersLabel);
		addCenters();
	}

	/**
	 * Gets the names of all the centers from CenterRepository and adds them to a JList. Then adds the JList to the panel.
	 */
	public void addCenters() {
		String[] centerNames = new String[centersSet.size()];
		centerNames = centersSet.toArray(centerNames);
		list = new JList<String>(centerNames);
		add(list);
	}

	/**
	 * Used in {@link MainGUI}
	 *
	 * @see gui.MainGUI
	 * @return the currently selected Center
	 */
	public String getSelectedCenter() {
		return list.getSelectedValue();
	}
	
	/**
	 * Sets up the background color of the elements.
	 * @param c the color of the elements
	 */
	public void setElementsBackground(Color c) {
		centersLabel.setBackground(c);
		list.setBackground(c);

	}

	/**
	 * Sets up the foreground color of the elements.
	 * @param c the color of the elements
	 */
	public void setElementsForeground(Color c) {
		centersLabel.setForeground(c);
		list.setForeground(c);
	}
}