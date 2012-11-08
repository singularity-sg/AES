package models;

import javax.persistence.Id;

import play.modules.orientdb.Model;

public class Story extends Model {

	@SuppressWarnings("unused")
	@Id
	private String id;
	
	public String name;
	public Fibonacci storyPoints = Fibonacci.ZERO;
	public String description = "";
	public int actualHours;
		
}
