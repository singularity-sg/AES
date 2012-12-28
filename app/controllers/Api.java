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

import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import play.mvc.Controller;

public class Api extends Controller {

	public static void average() {

		Map<Fibonacci, Double> averageMap = new HashMap<Fibonacci, Double>();
		Map<Fibonacci, List<Story>> map = findStoriesMappedByStoryPoints();

		Set<Entry<Fibonacci, List<Story>>> entries = map.entrySet();
		Iterator<Entry<Fibonacci, List<Story>>> it = entries.iterator();

		while(it.hasNext()) {
			Entry<Fibonacci, List<Story>> entry = it.next();
			List<Story> stories = entry.getValue();

			int size = stories.size();
			double sum = 0.0D;
			for(Story story : stories) {
				sum += story.actualHours;
			}

			averageMap.put(entry.getKey(), (sum / size));
		}

		renderJSON(averageMap);
	}

	private static Map<Fibonacci, List<Story>> findStoriesMappedByStoryPoints() {
		List<Story> stories = Story.find("select * from Story order by storyPoints desc");

		Map<Fibonacci, List<Story>> storyMap = new HashMap<Fibonacci, List<Story>>();

		for(Story story : stories) {
			List<Story> list = storyMap.get(story.storyPoints);
			if(list == null) {
				list = new ArrayList<Story>();
				storyMap.put(story.storyPoints, list);
			}
			list.add(story);
		}

		return storyMap;
	}

	public static void median() {

		Map<Fibonacci, Double> medianMap = new HashMap<Fibonacci, Double>();
		Map<Fibonacci, List<Story>> map = findStoriesMappedByStoryPoints();

		Set<Entry<Fibonacci, List<Story>>> entries = map.entrySet();
		Iterator<Entry<Fibonacci, List<Story>>> it = entries.iterator();

		while(it.hasNext()) {
			Entry<Fibonacci, List<Story>> entry = it.next();
			List<Story> stories = entry.getValue();

			DescriptiveStatistics stats = new DescriptiveStatistics();

			for(Story story : stories) {
				stats.addValue(story.actualHours);
			}

			medianMap.put(entry.getKey(), stats.getPercentile(50));
		}

		renderJSON(medianMap);
	}

	public static void linearRegression() {

		List<Story> stories = Story.find("select * from Story");
		Iterator<Story> it = stories.iterator();
		SimpleRegression regression = new SimpleRegression(false);

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
