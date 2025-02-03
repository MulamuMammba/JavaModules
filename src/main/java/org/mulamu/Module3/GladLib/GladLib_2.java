package org.mulamu.Module3.GladLib;

import edu.duke.FileResource;
import edu.duke.URLResource;

import java.util.*;

public class GladLib_2 {
	private HashMap<String, ArrayList<String>> myMap;
	private ArrayList<String> wordsUsed;
	private ArrayList<String> categoriesUsed;
	private Random myRandom;

	private static String dataSourceDirectory = "src/main/java/org/mulamu/Module3/GladLib/datalong";

	public GladLib_2() {
		myMap = new HashMap<>();
		wordsUsed = new ArrayList<>();
		categoriesUsed = new ArrayList<>();
		myRandom = new Random();
		initializeFromSource(dataSourceDirectory);
	}

	public GladLib_2(String source) {
		myMap = new HashMap<>();
		wordsUsed = new ArrayList<>();
		categoriesUsed = new ArrayList<>();
		myRandom = new Random();
		initializeFromSource(source);
	}

	private void initializeFromSource(String source) {
		String[] categories = {"adjective", "noun", "color", "country", "name", "animal", "timeframe", "verb", "fruit"};
		for (String category : categories) {
			myMap.put(category, readIt(source + "/" + category + ".txt"));
		}
	}

	private String randomFrom(ArrayList<String> source) {
		int index = myRandom.nextInt(source.size());
		return source.get(index);
	}

	private String getSubstitute(String label) {
		if (myMap.containsKey(label)) {
			if (!categoriesUsed.contains(label)) {
				categoriesUsed.add(label);
			}
			return randomFrom(myMap.get(label));
		}
		if (label.equals("number")) {
			return "" + (myRandom.nextInt(50) + 5);
		}
		return "**UNKNOWN**";
	}

	public String processWord(String w) {
		int first = w.indexOf("<");
		int last = w.indexOf(">", first);
		if (first == -1 || last == -1) {
			return w;
		}
		String prefix = w.substring(0, first);
		String suffix = w.substring(last + 1);

		String sub;
		do {
			sub = getSubstitute(w.substring(first + 1, last));
		} while (wordsUsed.contains(sub));

		wordsUsed.add(sub);
		return prefix + sub + suffix;
	}

	private void printOut(String s, int lineWidth) {
		int charsWritten = 0;
		for (String w : s.split("\\s+")) {
			if (charsWritten + w.length() > lineWidth) {
				System.out.println();
				charsWritten = 0;
			}
			System.out.print(w + " ");
			charsWritten += w.length() + 1;
		}
	}

	private String fromTemplate(String source) {
		StringBuilder story = new StringBuilder();
		if (source.startsWith("http")) {
			URLResource resource = new URLResource(source);
			for (String word : resource.words()) {
				story.append(processWord(word)).append(" ");
			}
		} else {
			FileResource resource = new FileResource(source);
			for (String word : resource.words()) {
				story.append(processWord(word)).append(" ");
			}
		}
		return story.toString();
	}

	private ArrayList<String> readIt(String source) {
		ArrayList<String> list = new ArrayList<>();
		if (source.startsWith("http")) {
			URLResource resource = new URLResource(source);
			for (String line : resource.lines()) {
				list.add(line);
			}
		} else {
			FileResource resource = new FileResource(source);
			for (String line : resource.lines()) {
				list.add(line);
			}
		}
		return list;
	}

	public void makeStory() {
		System.out.println("\n");
		String story = fromTemplate("src/main/java/org/mulamu/Module3/GladLib/data/madtemplate2.txt");
		printOut(story, 60);
		System.out.println("\nTotal words possible: " + totalWordsInMap());
		System.out.println("Total words considered: " + totalWordsConsidered());
	}

	public int totalWordsInMap() {
		int total = 0;
		for (ArrayList<String> list : myMap.values()) {
			total += list.size();
		}
		return total;
	}

	public int totalWordsConsidered() {
		int total = 0;
		for (String category : categoriesUsed) {
			total += myMap.get(category).size();
		}
		return total;
	}
}