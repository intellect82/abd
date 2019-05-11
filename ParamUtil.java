package com.atomiton.util;

import java.text.NumberFormat;
import java.util.List;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public class ParamUtil {

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
		
		int numOfIndependentVar=8;
        double[] YValues = new double[Y.size()];
		double XValues[][] = new double[X.size()][numOfIndependentVar];
		double[] parameters = null;
		

		for (int i = 0; i < YValues.length; i++) {
			YValues[i] = Y.get(i);
			System.out.println("Y1 = " + YValues[i]);
		}

		for (int i = 0; i < X.size(); i++) {
			for (int j = 0; j < X.get(i).size(); j++) {
				XValues[i][j] = (double) X.get(i).get(j);
				System.out.println("X"+j+" :" + XValues[i][j]);
			}
			System.out.println("-----------------------------------------");
		}

		//System.out.println("X1 = " + XValues);
		//System.out.println("Y1 =" + YValues);

		//
		OLSMultipleLinearRegression ols = new OLSMultipleLinearRegression();
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		ols.newSampleData(YValues, XValues);
		parameters = ols.estimateRegressionParameters();

		return parameters;

	}

}