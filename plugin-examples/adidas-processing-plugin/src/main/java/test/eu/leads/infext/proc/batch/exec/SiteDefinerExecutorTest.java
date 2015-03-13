package test.eu.leads.infext.proc.batch.exec;

import test.EnvironmentInit;
import eu.leads.infext.proc.batch.exec.SiteDefinerExecutor;

public class SiteDefinerExecutorTest {

	public static void main(String[] args) throws Exception {
		EnvironmentInit.initiateEnv();
		SiteDefinerExecutor.main(new String [] {"uk.co.zalando.www:http/"});
	}
	
}
