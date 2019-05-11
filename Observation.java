package com.atomiton.util;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * @author venkateswarluS
 *
 */
public class Observation {
	// Represents a observation vector with a map of features and values.
	private LinkedHashMap<String, Double> features = new LinkedHashMap<String, Double>();

	/*
	 * 
	 * Mutators and accessors.
	 * 
	 */
	public void putFeature(String feature, double value) {
		features.put(feature, value);
	}

	public double getFeature(String feature) {
		return features.get(feature);
	}

	public int size() {
		return features.size();
	}

	public Set<String> getFeatures() {
		return features.keySet();
	} // Iterator over features.

	public String toString() {
		String output = "";
		for (String feature : getFeatures()) {
			output = output + feature + ": " + getFeature(feature) + "\n";
		}
		return output;
	}
}
