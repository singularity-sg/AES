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
	
	public static void add() {
		CRUD mode = CRUD.CREATE;
		renderTemplate("Story/story.html", mode);
	}
	
    public static void create() {
    	models.Story story = new models.Story();
		story.name = params.get("name");
		story.description = params.get("description");
		story.storyPoints = Fibonacci.valueOf(Integer.parseInt(params.get("storyPoints")));
		story.actualHours = Integer.parseInt(params.get("actualHours"));
		story.save();
    }

    public static void edit(ORecordId id) {
    	models.Story story = models.Story.findById(id);
    	CRUD mode = CRUD.UPDATE;
    }

    public static void delete(ORecordId id) {

    }
    


}
