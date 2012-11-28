package controllers;

import java.util.ArrayList;

import models.Fibonacci;
import play.mvc.Before;
import play.mvc.Controller;

import com.orientechnologies.orient.core.id.ORecordId;

/**
 * Created with IntelliJ IDEA.
 * User: han
 * Date: 11/7/12
 * Time: 11:09 PM
 * To change this template use File | Settings | File Templates.
 */

public class Story extends Controller {
	
	@Before(only={"save","create"})
	static void validate(){
		validation.required("name", params.get("name"));
		validation.match("storyPoints", params.get("storyPoints"), "(0|1|2|3|5|8|13|21|40|100)").message("Story points must be a valid Fibonacci number");
		validation.match("actualHours", params.get("actualHours"), "[\\d]*").message("Actual Hours must be a number");
		
		if(validation.hasErrors()) {
			params.flash();
		}
	}
	
	public static void add(ORecordId id) {
        models.Iteration iteration = models.Iteration.findById(id);
        if(iteration == null) {
        	iteration = new models.Iteration();
        }
        
        models.Story story = new models.Story();
        
		CRUD mode = CRUD.CREATE;
		renderTemplate("Story/story.html", mode, iteration, story);
	}
	
    public static void create(ORecordId iteration_id) {
    	CRUD mode = CRUD.UPDATE;
    	
    	models.Iteration iteration = models.Iteration.findById(iteration_id);
    	if(iteration == null) {
        	iteration = new models.Iteration();
        	iteration.stories = new ArrayList<models.Story>();
        }
    	
    	if(validation.hasErrors()) {
    		mode = CRUD.CREATE;
    		renderTemplate("Story/story.html", mode, iteration);
    		return;
    	}
    	
    	models.Story story = new models.Story();
		story.name = params.get("name");
		story.description = params.get("description");
		story.storyPoints = Fibonacci.valueOf(Integer.parseInt(params.get("storyPoints")));
		story.actualHours = Integer.parseInt(params.get("actualHours"));
		
        iteration.stories.add(story);
        iteration.save();
        
        renderTemplate("Iteration/iteration.html", mode, iteration);
    }

    public static void save(ORecordId id, ORecordId iteration_id) {
    	CRUD mode = CRUD.UPDATE;
    	
    	models.Iteration iteration = models.Iteration.findById(iteration_id);
 
    	models.Story story = models.Story.findById(id);
    	story.name = params.get("name");
        story.description = params.get("description");
        story.actualHours = Integer.parseInt(params.get("actualHours"));
        story.storyPoints = Fibonacci.valueOf(Integer.parseInt(params.get("storyPoints")));
    	
    	if(validation.hasErrors()) {
    		renderTemplate("Story/story.html", mode, iteration, story);
    		return;
    	}
        
        story.save();
        
        renderTemplate("Iteration/iteration.html", mode, iteration);
    }

    public static void edit(ORecordId id, ORecordId iteration_id) {
    	
    	CRUD mode = CRUD.UPDATE;

        models.Iteration iteration = models.Iteration.findById(iteration_id);
        flash.put("iteration_id", iteration.getIdentity());
        
        models.Story story = models.Story.findById(id);
        flash.put("name",story.name);
        flash.put("description",story.description);
        flash.put("storyPoints", story.storyPoints);
        flash.put("actualHours", story.actualHours);
        
        renderTemplate("Story/story.html", mode, iteration, story);
    }

    public static void delete(ORecordId id) {
        models.Story story = models.Story.findById(id);
        story.delete();

        redirect("Application.index");
    }
    


}
