package mil.army.usace.aopxplorer.aopxplorer_app.internal;

import java.awt.Component;
import java.io.BufferedReader;
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

import javax.swing.JComboBox;

import org.json.JSONArray;
import org.json.JSONObject;

public class ComboxControl extends Component{
	
	public ComboxControl() throws IOException{
		URL url = new URL("https://api.github.com/repos/DataSciBurgoon/aop_networks/contents/");
		
		InputStream is = url.openStream();
		JSONObject json;
		HashMap<String, String> hm_aopn_url = new HashMap();
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
		JComboBox jcb = new JComboBox(aopn_names);
		
	}
	
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }
	
	

}
