package mil.army.usace.aopxplorer.aopxplorer_app.internal;

import java.util.Properties;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.swing.CytoPanelComponent;
import mil.army.usace.aopxplorer.aopxplorer_app.internal.ControlPanelAction;
import mil.army.usace.aopxplorer.aopxplorer_app.internal.MyControlPanel;
import org.cytoscape.service.util.AbstractCyActivator;
import org.osgi.framework.BundleContext;

public class CyActivator extends AbstractCyActivator {
	public CyActivator() {
		super();
	}


	public void start(BundleContext bc) {
		CySwingApplication cytoscapeDesktopService = getService(bc,CySwingApplication.class);
		
		MyControlPanel myControlPanel = new MyControlPanel();
		ControlPanelAction controlPanelAction = new ControlPanelAction(cytoscapeDesktopService,myControlPanel);
		
		registerService(bc,myControlPanel,CytoPanelComponent.class, new Properties());
		registerService(bc,controlPanelAction,CyAction.class, new Properties());
	}
}