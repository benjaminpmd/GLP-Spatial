package gui.elements;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A panel with pre-made buttons. Allows the user to choose the launch site.
 * Used in @see PreLaunchGUI
 *
 * @author Alice Mabille
 */
public class LaunchSitesPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public LaunchSitesPanel() {
        JLabel launchSitesLabel = new JLabel("Launch sites");
        add(launchSitesLabel);

    }

}