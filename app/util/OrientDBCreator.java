package util;

import java.io.File;

import org.apache.log4j.Logger;

import com.orientechnologies.orient.core.config.OGlobalConfiguration;
import com.orientechnologies.orient.core.db.object.ODatabaseObjectPool;
import com.orientechnologies.orient.core.db.object.ODatabaseObjectTx;

public class OrientDBCreator {
	
	private static Logger logger = Logger.getLogger(OrientDBCreator.class);
	
	public static void main(String[] args) throws Exception {
		new OrientDBCreator().createDB("local:D:/sandbox/lab/AES/db", "admin", "admin");
	}
	
	public void createDB(String url, String user, String password) {
		
		OGlobalConfiguration.STORAGE_KEEP_OPEN.setValue(false);
		
		String path = new StringBuilder(System.getProperty("user.home")).append(File.separator).append(".AES").toString();
		
		ODatabaseObjectTx db = null;
		
		if(url != null && !"".equals(url) && url.indexOf(":") >= 0) {
			path = url.substring(url.indexOf(":") + 1);
		}

		File folder = new File(path);
		if(folder.exists()) {
			try {
				db = ODatabaseObjectPool.global().acquire(url, user, password);
				long cnt = db.countClass("Iteration");
				logger.debug(cnt);
				return;
			} catch (Exception e) {
				logger.error("Unable to open database...", e);
				db = new ODatabaseObjectTx(url).create();
				logger.info("Creating new database...");
			} finally {
                logger.info("Closing DB");
				db.close();
			}
		}else {
            logger.error("Unable to find specified path");
        }
	}

}
