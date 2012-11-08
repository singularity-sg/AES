package jobs;

import play.Logger;
import play.Play;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentPool;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

@OnApplicationStart
public class ODBInitializer extends Job {

	@Override
	public void doJob() throws Exception {
		String url = Play.configuration.getProperty("odb.url");
		String username = Play.configuration.getProperty("odb.user");
		String password = Play.configuration.getProperty("odb.password");
		ODatabaseDocumentTx db = ODatabaseDocumentPool.global().acquire(url, username, password);
		db.countClass("Iteration");
		
		Logger.info("Initialised Orient DB");
	}
	
}
