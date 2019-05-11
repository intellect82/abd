package com.atomiton.util;

/**
 * @author venkateswarluS
 *
 */
public class MultipleLinearRegression {
	public static void main(String[] args) {
		Observation[] inputVectors = FileHandler
				.read("/Users/venkateswarlusayana/Documents/workspaceTQL1/TE/src/org/atom/DataSet20199.csv");

		/***
		 * 
		 * Fits the model predicting the feature SteamDemand using the normal
		 * equation method LeastSqares.
		 *
		 **/
		
	
		System.out.println("\n\nFitting model in using the normal equation method ...");
		System.out.println("*************************************************************************\n");

		Model normalFit = Fit.normalEquation(inputVectors, "Demand");

		System.out.println("INFORMATION :::::"+normalFit.toString());
		
		System.out.println("R-Squared:   :: ::"+normalFit.rSquared);
		

		/**
		 * 
		 * Demonstrates the Model.predict() method, as well as the model fits,
		 *
		 * by predicting some arbitrary rows of the dataset.
		 * 
		 **/
		System.out.println("\n\nPredicting some arbitrary rows using LR models ...\n");
		
	
		int n=inputVectors.length;
		for(int i=0; i<n; i++)
		{
		double Demand = inputVectors[i].getFeature("Demand");
		double Temp = inputVectors[i].getFeature("Temp");
		double Humidity = inputVectors[i].getFeature("Humidity");
		double Rain = inputVectors[i].getFeature("Rain");
		double Windspeed = inputVectors[i].getFeature("Windspeed");
		double Steam_Demand_Prediction = normalFit.predict(inputVectors[i]);
		
		System.out.println("INDEPENDENT VARIABLES :::: " +" Temp ::"+Temp+" Humidity  ::"+Humidity+" Rain::"+Rain+" Windspeed ::"+Windspeed+"\n");
		System.out.println("DEPENDENT VARIABLE    ::::  Actual Demand :: "+Demand+"\n");
		System.out.println("PREDICTION VALUE      :: :: STREAM_DEMAND_PREDICTION ::" + Steam_Demand_Prediction+"\n");
		System.out.println("DIFFERENCE IN ACTUAL DEMAND-STREAM_DEMAND_PREDICTION ::" + (Demand-Steam_Demand_Prediction+"\n"));
		
		}
		


	}
}


