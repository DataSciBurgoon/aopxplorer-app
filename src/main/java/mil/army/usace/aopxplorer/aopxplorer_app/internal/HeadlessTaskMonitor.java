package mil.army.usace.aopxplorer.aopxplorer_app.internal;

import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;

/**
 * Task monitor for non-interactive task execution.
 *
 * TODO: print out more meaningful message.
 */
public class HeadlessTaskMonitor implements TaskMonitor {

	private String taskName = "";

	public void setTask(final Task task) {
		//this.taskName = "Task (" + task.toString() + ")";
	}

	
	@Override
	public void setTitle(final String title) {
	}

	
	@Override
	public void setStatusMessage(final String statusMessage) {
	}

	
	@Override
	public void setProgress(final double progress) {
	}


	@Override
	public void showMessage(Level level, String message) {
	}
}