package com.atomiton.util;

import java.text.NumberFormat;
import java.util.List;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public class LRParamUtil {

	/**
	 * Implements ordinary least squares (OLS) to estimate the parameters of a
	 * multiple linear regression model.
	 * 
	 * @param y
	 *            Y sample data - one dimension array
	 * @param x
	 *            X sample data - two dimension array
	 * @return Estimated regression parameters
	 */
	
	
	public static double[] mutipleLineRegress_OLSParams(List<Double> Y, List<List> X) {
	
		//YValue Array 
        double[] YValues = new double[Y.size()];
       
        //numOfIndependentVariables size
        int numOfIndependentVariables=X.get(0).size();
        
        //XValue Array 
		double XValues[][] = new double[X.size()][numOfIndependentVariables];
		
		// parameter initialization
		double[] parameters = null;
				
       // creating Y[] Values 
		for (int i = 0; i < YValues.length; i++) {
			YValues[i] = Y.get(i);
		}
		
       // creating X[][] values 
		for (int i = 0; i < X.size(); i++) {
			for (int j = 0; j < X.get(i).size(); j++) {
				XValues[i][j] = (double) X.get(i).get(j);
				System.out.println("X = "+j+" : " + XValues[i][j]);
			}
		}

	
		// Apply OLSMultipleLinearRegression on X[] and X[][]  data
		OLSMultipleLinearRegression ols = new OLSMultipleLinearRegression();
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		ols.newSampleData(YValues, XValues);
		parameters = ols.estimateRegressionParameters();
		
		return parameters;

	}

}