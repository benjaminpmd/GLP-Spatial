package gui.elements;

import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import process.repositories.SpaceCenterRepository;

/**
 * A panel with pre-made buttons. Allows the user to choose the launch site.
 * Used in @see MainGUI
 * 
 * @author Alice Mabille
 */
public class SpaceCentersPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private SpaceCenterRepository repo = SpaceCenterRepository.getInstance();
	private Set<String> centersSet = repo.getKeys();
	private JList<String> list;

	public SpaceCentersPanel() {
		JLabel centersLabel = new JLabel("Launch sites");
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
	 * Used in @see MainGUI
	 * 
	 * @return the currently selected Center
	 */
	public String getSelectedCenter() {
		return list.getSelectedValue();
	}
}
