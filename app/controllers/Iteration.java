package controllers;

import java.util.List;

import play.mvc.Controller;

public class Iteration extends Controller {
	
	public static void add() {
		String mode = "add";
		renderTemplate("Iteration/form.html", mode);
	}
	
	public static void edit(String id) {
		
	}
	
	public static void delete(String id) {
		
	}
	
	public static void save() {
	
	}
	
	public static void create() {
		
		models.Iteration iteration = new models.Iteration();
		iteration.name = params.get("name");
		iteration.description = params.get("description");
		iteration.save();
		
		List<models.Iteration> iterations = models.Iteration.find("select * from Iteration");
		
		renderTemplate("Application/index.html", iterations);
	}

}
