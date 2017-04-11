package mil.army.usace.aopxplorer.aopxplorer_app.internal;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import org.cytoscape.app.CyAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.swing.CytoPanel;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.application.swing.CytoPanelState;
import org.cytoscape.application.swing.events.CytoPanelComponentSelectedEvent;
import org.cytoscape.application.swing.events.CytoPanelComponentSelectedListener;
import org.cytoscape.session.CySession;
import org.cytoscape.session.CySessionManager;


public class ControlPanelAction implements CytoPanelComponentSelectedListener{
	
	private MyControlPanel controlPanel;
	private CyAppAdapter adapter;
	private CySwingApplication cytoscapeDesktopService;

	public ControlPanelAction(CySwingApplication cytoscapeDesktopService, MyControlPanel controlPanel, CyAppAdapter adapter) {
		this.controlPanel = controlPanel;
		this.adapter = adapter;
		this.cytoscapeDesktopService = cytoscapeDesktopService;
	}


	@Override
	public void handleEvent(CytoPanelComponentSelectedEvent e) {
		System.out.println("I'm in ControlPanelAction");
		CySessionManager mySessionManager = adapter.getCySessionManager();
		CySession session = mySessionManager.getCurrentSession();
		session = mySessionManager.getCurrentSession();
		
	}}
