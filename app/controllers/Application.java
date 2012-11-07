package controllers;

import java.util.List;

import models.Iteration;
import play.mvc.Controller;

public class Application extends Controller {

    public static void index() {
    	List<Iteration> iterations = Iteration.find("select * from Iteration");
        render(iterations);
    }

}