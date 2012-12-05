package controllers;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import models.Iteration;
import play.mvc.Controller;
import au.com.bytecode.opencsv.CSVReader;

public class Application extends Controller {

    public static void index() {
    	List<Iteration> iterations = Iteration.find("select * from Iteration");
    	flash.clear();
        render(iterations);
    }
    
    public static void importCsv(File csv_file) throws IOException {
    	CSVReader reader = new CSVReader(new FileReader(csv_file));
	    String [] nextLine;
	    
    	int cnt = 0;
    	while ((nextLine = reader.readNext()) != null) {
    		if(++cnt > 1) {
    			Iteration iteration = deserializeToObject(nextLine);
    			iteration.save();
    		}
	    }
    	
    	index();
    }

	private static Iteration deserializeToObject(String[] fields) {
		
		String story_name = fields[0];
		String story_description = fields[1];
		String iteration_name = "Unplanned - Add this story to an iteration";
		if(fields.length > 2 && !"".equals(fields[2])) {
			iteration_name = fields[2];
		}
		
		List<Iteration> iterations = Iteration.find("select * from Iteration where name like ?", iteration_name);
		Iteration iteration = null;
		if(iterations.size() > 0) {
			iteration = iterations.get(0);
		} else {
			iteration = new Iteration();
			iteration.name = iteration_name;
		}
		
		models.Story story = new models.Story();
		story.name = story_name;
		story.description = story_description;
		
		iteration.stories.add(story);
		
		return iteration;
	}

}