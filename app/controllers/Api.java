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

	public static void averageManhours() {
		
		List<Story> stories = Story.find("select * from Story order by storyPoints desc");
		
		Map<Fibonacci, Double> avgMap = findAvgActualHoursByStoryPoints(stories);
		
		renderJSON(avgMap);
	}

	private static Map<Fibonacci, Double> findAvgActualHoursByStoryPoints(List<Story> stories) {
		
		Map<Fibonacci, Double> avgMap = new HashMap<Fibonacci, Double>();
		
		int sumActualHours = 0;
		int ctr = 0;
		Story prevStory = null;
		for (Story story : stories) {
			ctr++;
			if(prevStory == null) {
				sumActualHours += story.actualHours;
			} else if(prevStory.storyPoints != story.storyPoints) {
				sumActualHours += story.actualHours;
				avgMap.put(story.storyPoints, ((double)sumActualHours/ctr));
				sumActualHours = 0;
				ctr = 0;
			}
			prevStory = story;
		}
		
		return avgMap;
	}
	
	public static void standardDeviation() {
		
		Map<Fibonacci, List<Story>> storyMap = new HashMap<Fibonacci, List<Story>>();
		List<Story> stories = Story.find("select * from Story order by storyPoints desc");
		
		Story prevStory = null;
		List<Story> someStories = null;
		for (Story story : stories) {
			if(prevStory == null || prevStory.storyPoints != story.storyPoints) {
				someStories = new ArrayList<Story>();
				storyMap.put(story.storyPoints, someStories);
			}
			prevStory = story;
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
			
			
			double stdDev = Math.sqrt(sumSquare / count);
			stdDev = Math.round(stdDev * 2) / 2;
			
			stdDeviationMap.put(entry.getKey(), stdDev);
		}
  		
		renderJSON(stdDeviationMap);
	}
	
	public static void expectedManhours() {
		
		List<Story> stories = Story.find("select * from Story");
		Iterator<Story> it = stories.iterator();
		SimpleRegression regression = new SimpleRegression();
		
		while(it.hasNext()) {
			Story story = it.next();
			regression.addData((double) story.storyPoints.getNumber(), story.actualHours);
		}
		
		Map<Fibonacci, Double> predictions = new HashMap<Fibonacci, Double>();
		
		for (Fibonacci fibonacci : Fibonacci.list()) {
			Double prediction = regression.predict(fibonacci.getNumber());
			Double roundedPrediction = ((double)Math.round(prediction * 2) / 2);
			predictions.put(fibonacci, roundedPrediction);
		}
		
		renderJSON(predictions);
	}
	
}
