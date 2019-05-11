package com.atomiton.util;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public class MultiLinearLRParamUtil {

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

	public static double[] multipleLineRegress_OLSParams(List<Double> Y, List<List> X) {

		// YValue Array
		double[] YValues = new double[Y.size()];

		// numOfIndependentVariables size
		int numOfIndependentVariables = X.get(0).size();

		// XValue Array
		double XValues[][] = new double[X.size()][numOfIndependentVariables];

		// parameter initialization
		double[] parameters = null;

		List<Double> al = new ArrayList<Double>();

		// creating Y[] Values
		for (int i = 0; i < YValues.length; i++) {
			YValues[i] = Y.get(i);
		}

		// creating X[][] values
		for (int i = 0; i < X.size(); i++) {
			for (int j = 0; j < X.get(i).size(); j++) {
				XValues[i][j] = (double) X.get(i).get(j);
				System.out.println("X = " + j + " : " + XValues[i][j]);
			}
		}

		MultiLinearRegWithMatrix regression = new MultiLinearRegWithMatrix(XValues, YValues);

		for (int i = 0; i < 8; i++) {

			al.add(regression.beta(i));
		}

		double[] target = new double[al.size()];
		for (int i = 0; i < al.size(); i++) {

			target[i] = al.get(i).doubleValue();

			
		}

		return target;

	}

}