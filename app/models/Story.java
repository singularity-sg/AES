package models;

import javax.persistence.Id;

import play.data.validation.Required;
import play.modules.orientdb.Model;

public class Story extends Model {

	@SuppressWarnings("unused")
	@Id
	private String id;
	
	@Required
	public String name;
	
	@Required
	public Fibonacci storyPoints = Fibonacci.ZERO;
	
	public String description = "";
	
	public int actualHours;
		
}
