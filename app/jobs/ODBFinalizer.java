package jobs;

import play.jobs.Job;
import play.jobs.OnApplicationStop;

@OnApplicationStop
public class ODBFinalizer extends Job {

	@Override
	public void doJob() throws Exception {
		ODB.getInstance().shutdown();
	}
	
}
