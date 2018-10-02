package hw9;

import java.awt.*;

import javax.swing.*;

import hw8.CampusPathModel;

/**
 * CampusPathFindingGUI is a combination of the GUI view
 * and controller of the campus route finding tool
 */
public class CampusPathFindingGUI {
	private static final String TITLE = "Campus Route Finding Tool";
	
	// model of the campus route finding tool
	private CampusPathModel m;
	
	// the frame of the GUI
	private JFrame frame;
	
	/**
	 * Construct a new view/controller for the campus route finding tool
	 * @param m the model of campus route finding tool
	 * @effects create a new CampusPathFindingGUI
	 */
	public CampusPathFindingGUI(CampusPathModel m) {
		if (m == null) {
			throw new IllegalArgumentException("model passed in cannot be null");
		}
		// store the model
		this.m = m;
		
		// initialize the frame that will hold other components
		frame = new JFrame(TITLE);
		frame.setPreferredSize(new Dimension(1024, 1024));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// set layout manager for the frame
		frame.setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));
		
		// create map view for the GUI
		CampusPathFindingView view = new CampusPathFindingView(m);
		
		// create controller panel
		CampusPathFindingController control = new CampusPathFindingController(m, view);
		control.setPreferredSize(new Dimension(1024, 60));
		
		// add map view and controller to the frame
		frame.add(control);
		frame.add(view);
		
		// display the frame
		frame.pack();
		frame.setVisible(true);
	}
}
		