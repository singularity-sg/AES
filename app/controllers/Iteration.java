package controllers;

import java.util.ArrayList;

import models.Story;
import play.mvc.Before;
import play.mvc.Controller;

import com.orientechnologies.orient.core.id.ORecordId;

public class Iteration extends Controller {

	@Before(only={"save","create"})
	static void validate(){
		validation.required("iteration.name", params.get("iteration.name")).message("Name is required");
		
		if(validation.hasErrors()) {
			params.flash();
		}
	}
	
	public static void add() {
        CRUD mode = CRUD.CREATE;
        models.Iteration iteration = new models.Iteration();

        renderTemplate("Iteration/iteration.html", mode, iteration);
	}
	
	public static void edit(ORecordId id) {
		CRUD mode = CRUD.UPDATE;
		models.Iteration iteration = models.Iteration.findById(id);

		flash.put("iteration.name", iteration.name);
		flash.put("iteration.description", iteration.description);
		
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
        iteration.name = params.get("iteration.name");
        iteration.description = params.get("iteration.description");
        
        if(validation.hasErrors()) {
    		renderTemplate("Iteration/iteration.html", mode, iteration);
    		return;
        }
        
        iteration.save();
        flash.put("iteration.name", iteration.name);
		flash.put("iteration.description", iteration.description);
        renderTemplate("Iteration/iteration.html", mode, iteration);
	}
	
	public static void create() {
		CRUD mode = CRUD.CREATE;
		
		models.Iteration iteration = new models.Iteration();
		iteration.name = params.get("iteration.name");
		iteration.description = params.get("iteration.description");
        iteration.stories = new ArrayList<Story>();

        if(validation.hasErrors()) {
    		renderTemplate("Iteration/iteration.html", mode, iteration);
    		return;
        }
        
		iteration.save();

		mode = CRUD.UPDATE;
		flash.put("iteration.name", iteration.name);
		flash.put("iteration.description", iteration.description);
		renderTemplate("Iteration/iteration.html", mode, iteration);

	}

}
