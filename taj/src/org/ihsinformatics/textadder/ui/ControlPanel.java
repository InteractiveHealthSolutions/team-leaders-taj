package org.ihsinformatics.textadder.ui;

/*
 @author Ali Habib
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.ihsinformatics.textadder.Constants;
import org.ihsinformatics.textadder.FileFinder;

public class ControlPanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = 7965523667046214649L;
    private static final Color bgColour = new Color(255, 255, 255);// lightBlue(187,193,253)
    private static final Color borderColour = new Color(128, 128, 255); // darkBlue(128,128,255)

    protected static final String pathString = "Path:";
    protected static final String textString = "Text";
    protected static final String yearPlaceholderString = "Year Placeholder";

    protected static final String startString = "Start";

    protected JLabel actionLabel;
    protected JTextArea textArea;
    protected JFileChooser chooser;
    protected JTextField yearPlaceholderField;

    protected JButton startButton;

    public static Properties props;

    public ControlPanel() {

	setLayout(new BorderLayout());

	// Create a regular text field.
	textArea = new JTextArea();
	textArea.setText(Constants.DEFAULT_TEXT);
	// textArea.setBounds(0, 0, 15, 10);
	textArea.setRows(2);
	textArea.setColumns(15);
	textArea.setBackground(Color.WHITE);

	yearPlaceholderField = new JTextField(16);
	yearPlaceholderField.setActionCommand(yearPlaceholderString);
	yearPlaceholderField.setText(Constants.DEFAULT_YEAR_PLACE_HOLDER);
	yearPlaceholderField.addActionListener(this);

	chooser = new JFileChooser();
	chooser.addActionListener(this);
	chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

	// Create some labels for the fields.
	JLabel textAreaLabel = new JLabel(textString + ": ");
	textAreaLabel.setLabelFor(textArea);
	JLabel yearPlaceholderLabel = new JLabel(yearPlaceholderString + ": ");
	yearPlaceholderLabel.setLabelFor(yearPlaceholderField);
	JLabel pathLabel = new JLabel(pathString + ": ");
	pathLabel.setLabelFor(chooser);

	// Lay out the text controls and the labels.
	JPanel textControlsPane = new JPanel();
	textControlsPane.setBackground(bgColour);
	GridBagLayout gridbag = new GridBagLayout();
	GridBagConstraints c = new GridBagConstraints();

	textControlsPane.setLayout(gridbag);

	/*
	 * JLabel[] labels = {mtbCodeFieldLabel, rifCodeFieldLabel,
	 * qcCodeFieldLabel,localPortFieldLabel}; JTextField[] textFields =
	 * {mtbCodeField, rifCodeField,qcCodeField,localPortField};
	 * addLabelTextRows(labels, textFields, gridbag, textControlsPane);
	 */

	c.anchor = GridBagConstraints.EAST;

	c.gridwidth = GridBagConstraints.RELATIVE; // end row
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0.0;
	textControlsPane.add(textAreaLabel, c);

	textArea.setCursor(Cursor.getDefaultCursor());
	textArea.setBackground(bgColour);
	c.gridwidth = GridBagConstraints.REMAINDER; // end row
	c.fill = GridBagConstraints.HORIZONTAL;
	textControlsPane.add(textArea, c);

	c.anchor = GridBagConstraints.EAST;

	c.gridwidth = GridBagConstraints.RELATIVE;
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0.0;
	textControlsPane.add(yearPlaceholderLabel, c);

	c.gridwidth = GridBagConstraints.REMAINDER;
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 1.0;
	textControlsPane.add(yearPlaceholderField, c);

	c.gridwidth = GridBagConstraints.RELATIVE; // next-to-last
	c.fill = GridBagConstraints.NONE; // reset to default
	c.weightx = 0.0; // reset to default
	textControlsPane.add(pathLabel, c);

	c.gridwidth = GridBagConstraints.REMAINDER; // end row
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 1.0;
	textControlsPane.add(chooser, c);

	startButton = new JButton();

	startButton.setText(startString);
	startButton.setCursor(Cursor.getDefaultCursor());
	startButton.setMargin(new Insets(0, 0, 0, 0));
	startButton.setActionCommand(startString);
	startButton.addActionListener(this);
	textControlsPane.add(startButton, c);

	c.gridwidth = GridBagConstraints.REMAINDER; // last
	c.anchor = GridBagConstraints.WEST;
	c.weightx = 1.0;

	textControlsPane.setBorder(BorderFactory.createCompoundBorder(
		BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(borderColour), ""),
		BorderFactory.createEmptyBorder(5, 5, 5, 5)));

	JPanel leftPane = new JPanel(new BorderLayout());
	leftPane.add(textControlsPane, BorderLayout.PAGE_START);

	add(leftPane, BorderLayout.LINE_START);
	// add(rightPane, BorderLayout.LINE_END);
	leftPane.setBackground(bgColour);

    }

    // //////////////////////ACTION PERFORMED/////////////////////////////////

    public void actionPerformed(ActionEvent e) {
	if (startString.equals(e.getActionCommand())) {
	    FileFinder ff = new FileFinder();
	    ff.setText(textArea.getText());
	    ff.setYearPlaceholder(yearPlaceholderField.getText());
	    try {
		ff.search(chooser.getSelectedFile());
		JOptionPane.showMessageDialog(null,
			"Done!\n" + ff.getTotCounter() + " files checked!\n"
				+ ff.getCounter() + " updated!");

	    } catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }
	}

    }

}
