package controllers;

import models.Fibonacci;
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
	
	public static void add(ORecordId id) {
        models.Iteration iteration = models.Iteration.findById(id);
		CRUD mode = CRUD.CREATE;
		renderTemplate("Story/story.html", mode, iteration);
	}
	
    public static void create(ORecordId iteration_id) {
    	models.Story story = new models.Story();
		story.name = params.get("name");
		story.description = params.get("description");
		story.storyPoints = Fibonacci.valueOf(Integer.parseInt(params.get("storyPoints")));
		story.actualHours = Integer.parseInt(params.get("actualHours"));
        story.save();

        models.Iteration iteration = models.Iteration.findById(iteration_id);
        iteration.stories.add(story);
        iteration.save();

        CRUD mode = CRUD.UPDATE;
        renderTemplate("Iteration/iteration.html", mode, iteration);
    }

    public static void save(ORecordId id, ORecordId iteration_id) {
        models.Story story = models.Story.findById(id);

        story.name = params.get("name");
        story.description = params.get("description");
        story.actualHours = Integer.parseInt(params.get("actualHours"));
        story.storyPoints = Fibonacci.valueOf(Integer.parseInt(params.get("storyPoints")));
        story.save();

        CRUD mode = CRUD.UPDATE;
        models.Iteration iteration = models.Iteration.findById(iteration_id);
        renderTemplate("Iteration/iteration.html", mode, iteration);
    }

    public static void edit(ORecordId id, ORecordId iteration_id) {
    	models.Story story = models.Story.findById(id);
    	CRUD mode = CRUD.UPDATE;

        models.Iteration iteration = models.Iteration.findById(iteration_id);
        renderTemplate("Story/story.html", mode, story, iteration);
    }

    public static void delete(ORecordId id) {
        models.Story story = models.Story.findById(id);
        story.delete();

        redirect("Application.index");
    }
    


}
