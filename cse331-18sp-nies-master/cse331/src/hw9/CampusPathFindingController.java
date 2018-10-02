package hw9;

import hw8.CampusPathModel;

import java.awt.*;

import java.awt.event.*;

import javax.swing.*;

import java.util.Set;

import java.util.TreeSet;

/**
 * CampusPathFindingController is a GUI controller for the
 * campus route finding tool
 */
public class CampusPathFindingController extends JPanel {
	private static final long serialVersionUID = 1L;
	
	// model of campus route finding tool
	private CampusPathModel m;
	
	// GUI view of campus route finding tool
	private CampusPathFindingView v;
	
	// Set which stores abbreviate names of all buildings
	private Set<String> buildings;
	
	// description for the startSelectionBox
	private JLabel startBuilding;
	
	// drop down box for client to select start building of the route
	private JComboBox<String> startSelectionBox;
	
	// description for the endSelectionBox
	private JLabel endBuilding;
	
	// drop down box for client to select end building of the route
	private JComboBox<String> endSelectionBox;
	
	// description of the distance of the shortest route
	private JLabel distance;
	
	/**
	 * Construct a new GUI controller for the campus route finding tool
	 * @param m the model of campus route finding tool
	 * @param v the view of campus route finding tool
	 * @effects create a new CampusPathFindingController 
	 */
	public CampusPathFindingController(CampusPathModel m, CampusPathFindingView v) {
		if (m == null) {
			throw new IllegalArgumentException("Model passed in cannot be null");
		}
		if (v == null) {
			throw new IllegalArgumentException("View passed in cannot be null");
		}
		
		this.m = m;
		this.v = v;
		
		// get full name of all the buildings on campus and sort in 
		// alphabetical order
		buildings = new TreeSet<String>(m.getBuildingNames().values());
		
		// create a panel to hold all the labels and combo boxes
		JPanel selection_panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// create a panel to hold all the buttons
		JPanel button_panel = new JPanel(new GridLayout(2, 1));
		
		// create all the labels and combo boxes
		startBuilding = new JLabel("Starting building (blue dot): ");
		startSelectionBox = new JComboBox<String>(buildings.toArray(new String[0]));
		endBuilding = new JLabel("Ending building (yellow dot): ");
		endSelectionBox = new JComboBox<String>(buildings.toArray(new String[0]));
		
		// create all the buttons and then add ActionListener 
		// in order to update the view when the user click
		// the buttons
		JButton getRoute = new JButton("Get route");
		getRoute.addActionListener(new UpdateListener());
		JButton reset = new JButton("Reset");
		reset.addActionListener(new UpdateListener());
		
		// add components to the selection_panel
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 3;
		selection_panel.add(startBuilding, c);
		c.gridx = 4;
		c.gridy = 1;
		c.gridwidth = 5;
		selection_panel.add(startSelectionBox, c);
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 3;
		selection_panel.add(endBuilding, c);
		c.gridx = 4;
		c.gridy = 2;
		c.gridwidth = 5;
		selection_panel.add(endSelectionBox, c);
		
		// add components to the button_panel
		button_panel.add(getRoute);
		button_panel.add(reset);
		
		// add selection_panel and button_panel to controller panel
		this.add(selection_panel);
		this.add(button_panel);
		
		//create a panel to hold the labels that displays 
		// the distance of the route
		JPanel distance_panel = new JPanel(new GridLayout(2,1));
		JLabel distanceDisplay = new JLabel("  Total distance:");
		distance = new JLabel();
		distance_panel.add(distanceDisplay);
		distance_panel.add(distance);
		
		// add distance_panel to the controller panel
		this.add(distance_panel);
	}
	
	/**
	 * An action listener which either updates the new shortest route
	 * between two buildings currently selected by the user or sets 
	 * the view to its initial state.
	 */
	private class UpdateListener implements ActionListener {
		/**
		 * Invoked when an action occurs.
		 * @param e an event
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// get the string of the action
			String command = e.getActionCommand();
			if (command.equals("Get route")) {
				// when client click "Get route", get the names of the start and
				// end building the client choose and display the shortest route
				// between them
				String startBuilding = startSelectionBox.getSelectedItem().toString();
				String endBuilding = endSelectionBox.getSelectedItem().toString();
				double d = v.findShortestRoute(startBuilding, endBuilding);
				distance.setText(String.format("  %.0f", d) + "feet");
			} else {
				// when client click "Reset", set view to initial state
				v.reset();
				distance.setText("");
			}
		}
	}
}
