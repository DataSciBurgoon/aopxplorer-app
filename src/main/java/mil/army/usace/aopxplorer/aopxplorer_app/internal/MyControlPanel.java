package mil.army.usace.aopxplorer.aopxplorer_app.internal;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.apache.commons.io.FileUtils;
import org.cytoscape.app.CyAppAdapter;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.session.CySession;
import org.cytoscape.session.CySessionManager;
import org.cytoscape.task.read.OpenSessionTaskFactory;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskIterator;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.JLabel;

public class MyControlPanel extends JPanel implements CytoPanelComponent {
	
	private static final long serialVersionUID = 8292806967891823933L;
	//private CyAppAdapter adapter;


	public MyControlPanel(final CyAppAdapter adapter, final OpenSessionTaskFactory openSessionTaskFactory) throws IOException{
		//this.adapter = adapter;
		
		JLabel lbXYZ = new JLabel("AOPXplorer v1.0.0");
		
		URL url = new URL("https://api.github.com/repos/DataSciBurgoon/aop_networks/contents/");
		
		InputStream is = url.openStream();
		JSONObject json;
		final HashMap<String, String> hm_aopn_url = new HashMap();
	    try {
	    	BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	    	String jsonText = readAll(rd);
	    	JSONArray json_array = new JSONArray(jsonText);
	    	for(int i = 0; i < json_array.length(); i++) {
	    		JSONObject json_object = json_array.getJSONObject(i);
	    		String name = json_object.getString("name");
	    		Pattern pattern = Pattern.compile("\\.cys");
	    		Matcher match = pattern.matcher(name);
	    		if (match.find()){
	    			String download_url = json_object.getString("download_url");
	    			hm_aopn_url.put(name, download_url);
	    		}
	    	}
	    } 
	    finally {
	    	is.close();
	    }
	    Set<String> keys = hm_aopn_url.keySet();
	    String[] aopn_names = keys.toArray(new String[keys.size()]);
		final JComboBox jcb = new JComboBox(aopn_names);
		
		jcb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					CySessionManager mySessionManager = adapter.getCySessionManager();
					CySession session = mySessionManager.getCurrentSession();
					session = mySessionManager.getCurrentSession();
					File sessionFile = File.createTempFile("temp_cytoscape", ".cys");
					
					String aopn_name = jcb.getSelectedItem().toString();
					String url_string = hm_aopn_url.get(aopn_name);
					
//					URL url = new URL("https://raw.githubusercontent.com/DataSciBurgoon/aop_networks/master/epilepsy.cys");
					URL url = new URL(url_string);
					FileUtils.copyURLToFile(url, sessionFile);
					TaskIterator itr = openSessionTaskFactory.createTaskIterator(sessionFile);
					while(itr.hasNext()) {
						final Task task = itr.next();
						task.run(new HeadlessTaskMonitor());
					}
				}
				catch(Exception e2){
					e2.printStackTrace();
				}
				
			}
		});
		
		this.add(lbXYZ);
		this.add(jcb);
		this.setVisible(true);
	}
	
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }


	public Component getComponent() {
		return this;
	}


	public CytoPanelName getCytoPanelName() {
		return CytoPanelName.WEST;
	}


	public String getTitle() {
		return "AOPXplorer";
	}


	public Icon getIcon() {
		return null;
	}
}
