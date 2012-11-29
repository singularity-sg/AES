package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;

import play.data.validation.Required;
import play.modules.orientdb.Model;

public class Iteration extends Model {

	
	@SuppressWarnings("unused")
	@Id
	private String id;
	
	@Required
	public String name;
	
	public String description;

	public List<Story> stories = new ArrayList<Story>();

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
