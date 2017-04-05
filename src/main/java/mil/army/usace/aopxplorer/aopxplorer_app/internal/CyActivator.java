package mil.army.usace.aopxplorer.aopxplorer_app.internal;

import java.io.File;
import java.net.URL;
import java.util.Properties;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.task.read.OpenSessionTaskFactory;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskIterator;
import org.osgi.framework.BundleContext;
import org.apache.commons.io.FileUtils;


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
		final OpenSessionTaskFactory openSessionTaskFactory = getService(bc, OpenSessionTaskFactory.class);
		
		//File sessionFile = new File("/Users/burgoonl/Documents/test.cys");
		
		try {
			File sessionFile = File.createTempFile("temp_cytoscape", ".cys");
			URL url = new URL("https://raw.githubusercontent.com/DataSciBurgoon/aop_networks/master/epilepsy.cys");
			FileUtils.copyURLToFile(url, sessionFile);
			TaskIterator itr = openSessionTaskFactory.createTaskIterator(sessionFile);
			while(itr.hasNext()) {
				final Task task = itr.next();
				task.run(new HeadlessTaskMonitor());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}