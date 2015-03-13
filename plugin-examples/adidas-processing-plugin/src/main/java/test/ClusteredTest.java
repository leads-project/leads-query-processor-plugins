package test;

import eu.leads.crawler.PersistentCrawl;
import eu.leads.processor.AdidasProcessingPlugin;
import eu.leads.processor.common.StringConstants;
import eu.leads.processor.common.infinispan.CacheManagerFactory;
import eu.leads.processor.common.infinispan.InfinispanClusterSingleton;
import eu.leads.processor.common.infinispan.InfinispanManager;
import eu.leads.processor.common.utils.PrintUtilities;
import eu.leads.processor.conf.LQPConfiguration;
import eu.leads.processor.plugins.EventType;
import eu.leads.processor.plugins.PluginManager;
import eu.leads.processor.plugins.PluginPackage;

import org.infinispan.Cache;
import org.infinispan.commons.util.CloseableIterable;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public class ClusteredTest {
  public static void main(String[] args) {
	  String seed = args.length>2 ? args[3] : "http://www.bbc.com/news/uk-31545744";
	  int initPeriod 		= args.length>0 ? Integer.parseInt(args[0]) : 60;
	  int crawlingPeriod 	= args.length>0 ? Integer.parseInt(args[1]) : 15;
	  int processingPeriod 	= args.length>1 ? Integer.parseInt(args[2]) : 50;
	  
     LQPConfiguration.initialize();
     ArrayList<InfinispanManager> cluster = new ArrayList<InfinispanManager>();
     cluster.add(InfinispanClusterSingleton.getInstance().getManager());  //must add because it is used from the rest of the system
     //Crucial for joining infinispan cluster
     for ( InfinispanManager manager : cluster ) {
        manager.getPersisentCache("clustered");
     }
     //Create plugin package for upload (id,class name, jar file path, xml configuration)
        /*PluginPackage plugin = new PluginPackage();*/
     PluginPackage plugin = new PluginPackage(AdidasProcessingPlugin.class.getCanonicalName(), AdidasProcessingPlugin.class.getCanonicalName(),
                                                     "/tmp/leads/plugins/adidas-plugin.jar",
                                                     "/data/workspace/leads-query-processor-plugins/plugin-examples/adidas-processing-plugin/adidas-processing-plugin-conf.xml");


     //upload plugin
     boolean uploaded = PluginManager.uploadPlugin(plugin);
     
     System.out.print("neu stuff: ");
     System.out.println(uploaded ? "###plugin uploaded!" : "###plugin not uploaded!!");

     //distributed deployment  ( plugin id, cache to install, events)
     //PluginManager.deployPlugin();
     PluginManager.deployPlugin(AdidasProcessingPlugin.class.getCanonicalName(), "webpages", EventType.CREATEANDMODIFY);

        /*Start putting values to the cache */
     
     //Sleep for an amount of time to initialize everything
     try {
        Thread.sleep(initPeriod * 1000);
     } catch ( InterruptedException e ) {
        e.printStackTrace();
     }

     //Put some configuration properties for crawler
     
//     LQPConfiguration.getConf().setProperty("crawler.seed", seed); //For some reason it is ignored news.yahoo.com is used by default
//     LQPConfiguration.getConf().setProperty("crawler.depth", 1);
//     //Set desired target cache
//     LQPConfiguration.getConf().setProperty(StringConstants.CRAWLER_DEFAULT_CACHE, "webpages");
//     
//     //start crawler
//     PersistentCrawl.main(null);
     InfinispanManager menago = InfinispanClusterSingleton.getInstance().getManager();
     ConcurrentMap map = menago.getPersisentCache("webpages");
     
     System.out.println("Putting values to cache...");
     
     map.put("earga", "syf");
     map.put("werg", "kibel");
     
     Set set = map.keySet();
     System.out.println(set);
     
     
//     //Sleep for an amount of time to have the crawling running
//     try {
//        Thread.sleep(crawlingPeriod * 1000);
//     } catch ( InterruptedException e ) {
//        e.printStackTrace();
//     }
//	 /*Stop crawling*/
//     PersistentCrawl.stop();
     
     //Sleep for an amount of time to let processing run
     try {
        Thread.sleep(processingPeriod * 1000);
     } catch ( InterruptedException e ) {
        e.printStackTrace();
     }     
     InfinispanClusterSingleton.getInstance().getManager().stopManager();
     System.exit(0);

  }
}
