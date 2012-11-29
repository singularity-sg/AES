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
		validation.required("story.name", params.get("story.name"));
		validation.match("story.storyPoints", params.get("story.storyPoints"), "(0|1|2|3|5|8|13|21|40|100)").message("Story points must be a valid Fibonacci number");
		validation.match("story.actualHours", params.get("story.actualHours"), "[\\d]*").message("Actual Hours must be a number");
		
		if(validation.hasErrors()) {
			params.flash();
		}
	}
	
	public static void add(ORecordId id) {
        models.Iteration iteration = models.Iteration.findById(id);
        if(iteration == null) {
        	iteration = new models.Iteration();
        }
        iteration.name = params.get("iteration.name");
        iteration.description = params.get("iteration.description");
        
        models.Story story = new models.Story();
        
		CRUD mode = CRUD.CREATE;
		renderTemplate("Story/story.html", mode, iteration, story);
	}
	
    public static void create(ORecordId iteration_id) {
    	CRUD mode = CRUD.UPDATE;
    	
    	models.Iteration iteration = models.Iteration.findById(iteration_id);
    	if(iteration == null) {
        	iteration = new models.Iteration();
        }
    	flash.put("iteration.description", iteration.description);
    	flash.put("iteration.name", iteration.name);
    	
    	models.Story story = new models.Story();
    	story.name = params.get("story.name");
    	story.description = params.get("story.description");
    	try {
    		story.storyPoints = Fibonacci.valueOf(Integer.parseInt(params.get("story.storyPoints")));
    		story.actualHours = Integer.parseInt(params.get("story.actualHours"));
    	}catch(NumberFormatException nfe) {}

    	iteration.stories.add(story);
    	
    	if(validation.hasErrors()) {
    		mode = CRUD.CREATE;
    		renderTemplate("Story/story.html", mode, iteration, story);
    		return;
    	}
    	
        iteration.save();
        
        renderTemplate("Iteration/iteration.html", mode, iteration);
    }

    public static void save(ORecordId id, ORecordId iteration_id) {
    	CRUD mode = CRUD.UPDATE;
    	
    	models.Iteration iteration = models.Iteration.findById(iteration_id);
 
    	models.Story story = models.Story.findById(id);
    	story.name = params.get("story.name");
        story.description = params.get("story.description");
        
        try {
	        story.actualHours = Integer.parseInt(params.get("story.actualHours"));
	        story.storyPoints = Fibonacci.valueOf(Integer.parseInt(params.get("story.storyPoints")));
        }catch(NumberFormatException nfe) {}
        
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
        flash.put("story.name",story.name);
        flash.put("story.description",story.description);
        flash.put("story.storyPoints",story.storyPoints);
        flash.put("story.actualHours",story.actualHours);
        
        renderTemplate("Story/story.html", mode, iteration, story);
    }

    public static void delete(ORecordId id) {
        models.Story story = models.Story.findById(id);
        story.delete();

        redirect("Application.index");
    }
    


}
