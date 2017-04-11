package mil.army.usace.aopxplorer.aopxplorer_app.internal;

import java.io.File;
import java.net.URL;
import java.util.Properties;

import org.cytoscape.app.CyAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.events.CytoPanelComponentSelectedListener;
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
		try {
			CySwingApplication cytoscapeDesktopService = getService(bc,CySwingApplication.class);
			
			CyAppAdapter adapter = getService(bc,CyAppAdapter.class);
			
			final OpenSessionTaskFactory openSessionTaskFactory = getService(bc, OpenSessionTaskFactory.class);
			MyControlPanel myControlPanel = new MyControlPanel(adapter, openSessionTaskFactory);
			ControlPanelAction controlPanelAction = new ControlPanelAction(cytoscapeDesktopService, myControlPanel, adapter);
			registerService(bc,controlPanelAction,CytoPanelComponentSelectedListener.class, new Properties());

			registerService(bc,myControlPanel,CytoPanelComponent.class, new Properties());
			
			//File sessionFile = new File("/Users/burgoonl/Documents/test.cys");
//			File sessionFile = File.createTempFile("temp_cytoscape", ".cys");
//			URL url = new URL("https://raw.githubusercontent.com/DataSciBurgoon/aop_networks/master/epilepsy.cys");
//			FileUtils.copyURLToFile(url, sessionFile);
//			TaskIterator itr = openSessionTaskFactory.createTaskIterator(sessionFile);
//			while(itr.hasNext()) {
//				final Task task = itr.next();
//				task.run(new HeadlessTaskMonitor());
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}