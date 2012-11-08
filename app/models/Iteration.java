package models;

import java.util.List;

import javax.persistence.Id;

import play.modules.orientdb.Model;

public class Iteration extends Model {

	
	@SuppressWarnings("unused")
	@Id
	private String id;
	
	public String name;
	
	public String description;
	
	public List<Story> stories;

	public int getTotalStoryPoints() {
		int totalStoryPoints = 0;
		for(Story story : stories) {
			totalStoryPoints += story.storyPoints.getNumber();
		}
		
		return totalStoryPoints;
	}
	public int getTotalActualHours() {
		int totalActualHours = 0;
		for(Story story: stories) {
			totalActualHours += story.storyPoints.getNumber();
		}
		return totalActualHours;
	}
	
}
