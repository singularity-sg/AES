package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Story;
import play.mvc.Before;
import play.mvc.Controller;

import com.orientechnologies.orient.core.id.ORecordId;

public class Iteration extends Controller {

	@Before(only={"save","create"})
	static void validate(){
		validation.required("iteration_name", params.get("iteration_name")).message("Name is required");
		
		if(validation.hasErrors()) {
			params.flash();
		}
	}
	
	@Before
	static void clearMessage() {
		flash.clear();
	}
	
	public static void add() {
        CRUD mode = CRUD.CREATE;
        models.Iteration iteration = new models.Iteration();

        renderTemplate("Iteration/iteration.html", mode, iteration);
	}
	
	public static void edit(ORecordId id) {
		CRUD mode = CRUD.UPDATE;
		models.Iteration iteration = models.Iteration.findById(id);
		
		populateFlash(iteration);
		
        renderTemplate("Iteration/iteration.html", mode, iteration);
	}
	
	public static void delete(ORecordId id) {
		models.Iteration iteration = models.Iteration.findById(id);
        iteration.delete();

        redirect("Application.index");
	}

    public static void save(ORecordId id) {
    	CRUD mode = CRUD.UPDATE;
    	
        models.Iteration iteration = models.Iteration.findById(id);
        iteration.name = params.get("iteration_name");
        iteration.description = params.get("iteration_description");
        populateFlash(iteration);
        
        if(validation.hasErrors()) {
    		renderTemplate("Iteration/iteration.html", mode, iteration);
    		return;
        }
        
        iteration.save();
		flash.put("message","Iteration saved");
        renderTemplate("Iteration/iteration.html", mode, iteration);
	}
	
	public static void create() {
		CRUD mode = CRUD.CREATE;
		
		models.Iteration iteration = new models.Iteration();
		iteration.name = params.get("iteration_name");
		iteration.description = params.get("iteration_description");
        iteration.stories = new ArrayList<Story>();

        if(validation.hasErrors()) {
        	populateFlash(iteration);
    		renderTemplate("Iteration/iteration.html", mode, iteration);
    		return;
        }
        
		iteration.save();

		mode = CRUD.UPDATE;
		populateFlash(iteration);
		flash.put("message","Iteration created");
		renderTemplate("Iteration/iteration.html", mode, iteration);

	}
	
	public static void deleteSelected(String[] selected) {
		if(selected != null) {
			for(int i=0;i<selected.length;i++) {
				models.Iteration iteration = models.Iteration.findById(new ORecordId(selected[i]));
				List<Story> stories = iteration.stories;
				for(Story story : stories) {
					story.delete();
				}
				
				iteration.delete();
			}
		}
			
		flash.clear();
		flash.put("message", "No record selected!");
		
		redirect("Application.index");
	}
	
    private static void populateFlash(models.Iteration iteration) {
    	flash.put("iteration_name", iteration.name);
		flash.put("iteration_description", iteration.description);
    }

}
