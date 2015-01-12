package org.ihsinformatics.textadder.ui;

/*
 @author Ali Habib
 */

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class TextAdderMain {

    private static ControlPanel settingsPanel;

    // private static JTextPane monitorPanel;

    public TextAdderMain() {

    }

    private static void createAndShowGUI() {
	JFrame mainFrame = new JFrame("Taj");

	// ///////////////////////////

	// set Frame size and location
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	mainFrame.setLocation(d.width / 2 - 400, d.height / 2 - 300);
	mainFrame.setSize(800, 600);
	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	// set frame's layout
	Container mainPane = mainFrame.getContentPane();
	GridBagLayout gridbag = new GridBagLayout();
	GridBagConstraints c = new GridBagConstraints();

	mainPane.setLayout(gridbag);

	// add button panel
	/*
	 * c.gridwidth = GridBagConstraints.REMAINDER; //last c.anchor =
	 * GridBagConstraints.PAGE_START; c.weightx = 0.5; c.weighty = 0.5;
	 * c.gridx = 0; c.gridy = 0; // c.fill = GridBagConstraints.BOTH;
	 * oldControlPanel = new OldControlPanel(); ///
	 * mainPane.add(oldControlPanel, c);
	 */// //////////////////////////////////////////////////

	// add settings panel
	// monitorPanel = new JTextPane();
	c.fill = GridBagConstraints.BOTH;
	c.weightx = 1;
	c.weighty = 1;

	c.gridx = 1;
	c.gridy = 1;
	settingsPanel = new ControlPanel();
	mainPane.add(settingsPanel, c);

	// add text panel

	/*
	 * monitorPanel.setEditable(false); monitorPanel.setPreferredSize(new
	 * Dimension(300,300)); c.gridx = 1; c.gridy = 0; //c.fill =
	 * GridBagConstraints.BOTH; mainFrame.add(new JScrollPane( monitorPanel
	 * ), c);
	 */

	// monitorFrame.setResizable(true);
	// monitorFrame.pack();
	// monitorFrame.setVisible(true);
	mainFrame.pack();
	mainFrame.setResizable(false);
	mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
	SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		// Turn off metal's use of bold fonts
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		createAndShowGUI();
	    }
	});

    }
}