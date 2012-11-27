package controllers;

import java.util.ArrayList;

import models.Fibonacci;
import models.Story;
import play.mvc.Controller;

import com.orientechnologies.orient.core.id.ORecordId;

public class Iteration extends Controller {

	public static void add() {
        CRUD mode = CRUD.CREATE;
        models.Iteration iteration = new models.Iteration();

        renderTemplate("Iteration/iteration.html", mode, iteration);
	}
	
	public static void edit(ORecordId id) {
		CRUD mode = CRUD.UPDATE;
		models.Iteration iteration = models.Iteration.findById(id);

        renderTemplate("Iteration/iteration.html", mode, iteration);
	}
	
	public static void delete(ORecordId id) {
		models.Iteration iteration = models.Iteration.findById(id);
        iteration.delete();

        redirect("Application.index");
	}

    public static void save(ORecordId id) {
        models.Iteration iteration = models.Iteration.findById(id);

        iteration.name = params.get("name");
        iteration.description = params.get("description");
        iteration.save();

        redirect("Application.index");
	}
	
	public static void create() {
		
		models.Iteration iteration = new models.Iteration();
		iteration.name = params.get("name");
		iteration.description = params.get("description");
        iteration.stories = new ArrayList<Story>();

		iteration.save();

        redirect("Application.index");

	}

}
