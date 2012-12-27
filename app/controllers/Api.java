package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import models.Fibonacci;
import models.Story;

import org.apache.commons.math.stat.regression.SimpleRegression;

import play.mvc.Controller;

public class Api extends Controller {

	public static void AverageManhours() {
		
		List<Story> stories = Story.find("select * from Story order by storyPoints desc");
		
		Map<Fibonacci, Integer> sumMap = findSumOfActualHoursByStoryPoints(stories);
		
		renderJSON(sumMap);
	}

	private static Map<Fibonacci, Integer> findSumOfActualHoursByStoryPoints(List<Story> stories) {
		
		Map<Fibonacci, Integer> sumMap = new HashMap<Fibonacci, Integer>();
		
		int sumActualHours = 0;
		Story prevStory = null;
		for (Story story : stories) {
			if(prevStory == null || prevStory.storyPoints != story.storyPoints) {
				sumMap.put(story.storyPoints, new Integer(sumActualHours));
				sumActualHours = 0;
			}
			sumActualHours += story.actualHours;
		}
		
		return sumMap;
	}
	
	public static void StandardDeviation() {
		
		Map<Fibonacci, List<Story>> storyMap = new HashMap<Fibonacci, List<Story>>();
		List<Story> stories = Story.find("select * from Story order by storyPoints desc");
		
		Story prevStory = null;
		List<Story> someStories = null;
		for (Story story : stories) {
			if(prevStory == null || prevStory.storyPoints != story.storyPoints) {
				someStories = new ArrayList<Story>();
				storyMap.put(story.storyPoints, someStories);
			}
			someStories.add(story);
		}
		
		Set<Entry<Fibonacci, List<Story>>> entries = storyMap.entrySet();
		Iterator<Entry<Fibonacci,  List<Story>>> it = entries.iterator();
		
		Map<Fibonacci, Double> stdDeviationMap = new HashMap<Fibonacci, Double>();
		
		while(it.hasNext()) {
			Entry<Fibonacci, List<Story>> entry = it.next();
			
			List<Story> myStories = entry.getValue();
			double count = 0;
			double sum = 0;
			
			for (Story story : myStories) {
				sum += story.actualHours;
				count++;
			}
			
			double avg = sum / count;
			double sumSquare = 0;
			
			for (Story story : myStories) {
				double square = Math.pow((story.actualHours - avg), 2);
				sumSquare += square;
			}
			
			stdDeviationMap.put(entry.getKey(), Math.sqrt(sumSquare / count));
		}
  		
		renderJSON(stdDeviationMap);
	}
	
	public static void expectedManhours(Integer storyPoints) {
		
		List<Story> stories = Story.find("select * from Story order by storyPoints desc");
		Iterator<Story> it = stories.iterator();
		SimpleRegression regression = new SimpleRegression();
		
		while(it.hasNext()) {
			Story story = it.next();
			regression.addData(story.storyPoints.getNumber(), story.actualHours);
		}
		
		Double prediction = regression.predict(storyPoints);
		Double roundedPrediction = ((double)Math.round(prediction * 2) / 2);
		
		renderJSON(roundedPrediction);
	}
	
}
