package test;

import java.io.IOException;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import eu.leads.PropertiesSingleton;
import eu.leads.datastore.DataStoreSingleton;

public class EnvironmentInit {
	
	public static void initiateEnv() throws ConfigurationException, IOException {
		Configuration config = 
				new XMLConfiguration("/data/workspace/leads-query-processor-plugins/plugin-examples/adidas-processing-plugin/adidas-processing-plugin-conf.xml"); 
		DataStoreSingleton.configureDataStore(config);	
	    PropertiesSingleton.setResourcesDir(config.getString("resources_path"));
	    PropertiesSingleton.setConfig(config);
	    
	    // Start Python ZeroMQ Server processes!
		List<String> endpoints = config.getList("pzsEndpoints");
		String pythonPath = "PYTHONPATH="+config.getString("pythonPath");
		String commandBase = "/usr/bin/python -m eu.leads.infext.python.CLAPI.pzs ";
		String[] envp = {pythonPath};
		for(int i=0; i<endpoints.size(); i++) {
			String endpoint = endpoints.get(i);
			String command  = commandBase+endpoint;
			Runtime.getRuntime().exec(command, envp);
		}
	}
	
}
