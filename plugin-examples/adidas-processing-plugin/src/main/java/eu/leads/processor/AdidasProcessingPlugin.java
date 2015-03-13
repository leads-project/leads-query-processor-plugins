package eu.leads.processor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import eu.leads.PropertiesSingleton;
import eu.leads.infext.proc.realtime.env.pojo.PageProcessingPojo;
import eu.leads.processor.common.infinispan.InfinispanManager;
import eu.leads.processor.core.Tuple;
import eu.leads.processor.plugins.PluginInterface;

import org.apache.commons.configuration.Configuration;
import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by vagvaz on 10/14/14.
 */
public class AdidasProcessingPlugin implements PluginInterface {
   private final String className = AdidasProcessingPlugin.class.getCanonicalName();
   private Configuration configuration;
   private InfinispanManager manager;
   private PageProcessingPojo pageProcessingPojo = null;
   private boolean isPZSStarted = false;
   @Override
   public String getId() {
      return className;
   }

   @Override
   public void setId(String id) {
      System.err.println("Cannot set ID default is the name of the class");
   }

   @Override
   public String getClassName() {
      return className;
   }

   @Override
   public void initialize(Configuration config, InfinispanManager manager) {
//	  try {
//	      this.configuration = config;
//	      this.manager = manager;
//	      
	      System.out.println("%%%%% Initializing the plugin");
	      
//	      // KEEP config
//	      PropertiesSingleton.setConfig(config);
//	      
//	      // READ Configuration for Cassandra
//	//      DataStoreSingleton.configureDataStore(config);
//	      
//	      // READ Configuration for the plugin
//	      PropertiesSingleton.setResourcesDir(config.getString("resources_path"));
//	      // TODO something more ??
//	      
//	//      try {
//	      if(pageProcessingPojo == null)
//	    	  pageProcessingPojo = new PageProcessingPojo();
//	//	  } catch (Exception e) {
//	//		  e.printStackTrace();
//			  // TODO
//	//	  }
////	      try {
////	      System.setOut(outputFile("/data/leads.out"));
////	      System.setErr(outputFile("/data/leads.err"));
//	      System.out.println("Let's start the party!");
////	      } catch (java.io.FileNotFoundException e) {
////	         e.printStackTrace();
////	      }
//	      
//	      if(!isPZSStarted) {
//		      // Start Python ZeroMQ Server processes!
//		      List<String> endpoints = config.getList("pzsEndpoints");
//		      String pythonPath = "PYTHONPATH="+config.getString("pythonPath");
//		      String commandBase = "/usr/bin/python -m eu.leads.infext.python.CLAPI.pzs ";
//		      String[] envp = {pythonPath};
//			  for(int i=0; i<endpoints.size(); i++) {
//		    	  String endpoint = endpoints.get(i);
//		    	  String command  = commandBase+endpoint;
//		    	  Runtime.getRuntime().exec(command, envp);
//			  }
//			  isPZSStarted = true;
//	      }
//      } 
//      catch (Exception e) {
//    	  System.out.println("Exception during initializing the plugin!");
//    	  e.printStackTrace();
//      }
   }

   protected java.io.PrintStream outputFile(String name) throws java.io.FileNotFoundException {
       return new java.io.PrintStream(new java.io.BufferedOutputStream(new java.io.FileOutputStream(name)));
   }

   @Override
   public void cleanup() {

   }

   @Override
   public void modified(Object key, Object value, Cache<Object, Object> cache) {
	   System.out.println("XXXmodified()");
      //Have one method for processing both events
      processTuple(key,value);
   }

   @Override
   public void created(Object key, Object value, Cache<Object, Object> cache) {
	   System.out.println("XXXcreated()");
      processTuple(key,value);
   }

   /**
 * @param key
 * @param value
 */
   private void processTuple(Object key, Object value) {
//	   try {
		   System.out.println("######## processTuple");
//		   String uri = (String) key;
//			String webpageJson = (String)value;
//			Tuple webpage = new Tuple(webpageJson);
//				      
//			String content = webpage.getAttribute("content");
//			String timestamp = webpage.getNumberAttribute("timestamp").toString();
//			HashMap<String,String> cacheColumns = new HashMap<>();
//			cacheColumns.put("content", content);
//			cacheColumns.put("fetchTime", timestamp);
//				
//			// Here Do the heavy processing stuff
//			System.out.println("########:"+getClassName().toString() + " calls a processing POJO on a key " + key);
//			pageProcessingPojo.execute(uri, timestamp, "webpages", cacheColumns);
			
/*			// ZEROMQ PYTHON CALL CHECK
 * 			PythonQueueCall pythonCall = new PythonQueueCall();
 *			pythonCall.call("eu.leads.infext.python.CLAPI.helloworld_clinterface","hello","world");
 *          System.out.println("Python called, no exceptions.");
 */
			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
   }

   @Override
   public void removed(Object key, Object value, Cache<Object, Object> cache) {
      // Do Nothing probably never called.

   }

   @Override
   public Configuration getConfiguration() {
      return configuration;
   }

   @Override
   public void setConfiguration(Configuration config) {
      this.configuration = config;
   }
//	
//	  private String id;
//	  private Cache targetCache;
//	  private List<String> attributes;
//	  private Logger log = LoggerFactory.getLogger(AdidasProcessingPlugin.class);
//
//	  @Override
//	  public String getId() {
//	    return id;
//	  }
//
//	  @Override
//	  public void setId(String s) {
//	    this.id = s;
//	  }
//
//	  @Override
//	  public String getClassName() {
//	    return AdidasProcessingPlugin.class.getCanonicalName();
//	  }
//
//	  @Override
//	  public void initialize(Configuration configuration, InfinispanManager infinispanManager) {
//	    String targetCacheName = configuration.getString("cache");
//	    if ( targetCacheName != null || !targetCacheName.equals("") ) {
//	      targetCache = (Cache) infinispanManager.getPersisentCache(targetCacheName);
//	    } else {
//	      System.out.println("TargetCache is not defined using default for not breaking");
//	      targetCache = (Cache) infinispanManager.getPersisentCache("default");
//	    }
//	    attributes = configuration.getList("attributes");
//	  }
//
//	  @Override
//	  public void cleanup() {
//
//	  }
//
//	  @Override
//	  public void modified(Object key, Object value, Cache<Object, Object> objectObjectCache) {
//		  System.out.println("YXY");
//	    Tuple t = new Tuple(value.toString());
//	    processTuple(key.toString(), t);
//	  }
//
//	  protected void processTuple(String key, Tuple tuple) {
//	    tuple.keepOnly(attributes);
//	    targetCache.put(key, tuple.asString());
//	  }
//
//	  @Override
//	  public void created(Object key, Object value, Cache<Object, Object> objectObjectCache) {
//		  System.out.println("YXX");
//	    Tuple t = new Tuple(value.toString());
//	    processTuple(key.toString(), t);
//	  }
//
//	  @Override
//	  public void removed(Object o, Object o2, Cache<Object, Object> objectObjectCache) {
//
//	  }
//
//	  @Override
//	  public Configuration getConfiguration() {
//	    return null;
//	  }
//
//	  @Override
//	  public void setConfiguration(Configuration configuration) {
//
//	  }
}
