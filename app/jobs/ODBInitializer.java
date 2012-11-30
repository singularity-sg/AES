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
		ODB.getInstance().startup();
	}
	
}
