import org.junit.Test;

import play.modules.orientdb.Model;
import play.test.UnitTest;

import com.orientechnologies.orient.core.iterator.OObjectIteratorMultiCluster;

public class BasicTest extends UnitTest {

    @Test
    public void aVeryImportantThingToTest() {
        assertEquals(2, 1 + 1);
    }

    @Test
    public void deleteAllRecords() {
    	
    	OObjectIteratorMultiCluster<Model> it = models.Story.all();
    	while(it.hasNext()) {
    		it.next().delete();
    	}
    }
}
