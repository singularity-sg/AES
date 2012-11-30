package jobs;

import play.Logger;
import play.Play;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentPool;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

public class ODB {

	private static ODB instance;
	
	private String url;
	private String username;
	private String password;
	private ODatabaseDocumentTx db;
	
	private ODB() {
		this.url = Play.configuration.getProperty("odb.url");
		this.username = Play.configuration.getProperty("odb.user");
		this.password = Play.configuration.getProperty("odb.password");
		
		db = ODatabaseDocumentPool.global().acquire(url, username, password);
		db.countClass("Iteration");
		
		Logger.info("Initialised Orient DB");
	}
	
	public void startup() {
		db = ODatabaseDocumentPool.global().acquire(url, username, password);
	}
	
	public void shutdown() {
		db.close();
	}
	
	public static ODB getInstance() {
		if(instance == null) {
			instance = new ODB();
		}
		
		return instance;
	}
	
	
	
}
