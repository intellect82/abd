package com.atomiton.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import oda.lm.ListMap;
import oda.lm.ListMapEntry;
import java.text.NumberFormat;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public class GetDataCoefficients {

	/**
	 * @param xydata
	 * @return getting data from TQL
	 */
	public static double[] getParamInfo(ListMap xydata) {

		List<Double> Y = new ArrayList<Double>();
		List<List> X = new ArrayList<List>();
		double[] parameters = null;
		int count = 0;
		String root = null;
		List<String> childAtrribute = new ArrayList<String>();
		for (ListMapEntry e1 : xydata.iterEntries()) {

			root = (String) e1.getKey();
			ListMap data = (ListMap) e1.getValue();
			data.iterEntries();

			for (ListMapEntry e2 : data.iterEntries()) {
				count++;

				childAtrribute.add((String) e2.getKey());
			}
			break;
		}

		/*
		 * Generating Y and X list elements
		 * 
		 */
		for (ListMapEntry parententry : xydata.iterEntries(root)) {

			ListMap data = (ListMap) parententry.getValue();
			List<Double> xValues = new ArrayList<Double>();

			Y.add(Double.parseDouble("" + data.get(childAtrribute.get(0))));
			for (int i = 1; i < data.listSize(); i++) {
				xValues.add(Double.parseDouble("" + data.get(childAtrribute.get(i))));

			}

			X.add(xValues);
		}

		/**
		 * Implements ordinary least squares (OLS) to estimate the parameters of
		 * a multiple linear regression model.
		 * 
		 * @param y
		 *            Y sample data - one dimension array
		 * @param x
		 *            X sample data - two dimension array
		 * @return Estimated regression parameters
		 */
		parameters = LRParamUtil.mutipleLineRegress_OLSParams(Y, X);

		/**
		 * List of all attributes can assign it to respective coefficients
		 * 
		 * for Example :
		 * 
		 * <Intercept>-0.0</Intercept>
		 * <TempCoefficient>-4.1388019931402168E18</TempCoefficient>
		 * <HumidityCoefficient>-1.50568551642001472E17</HumidityCoefficient>
		 * <RainCoefficient>-1.0014531778822455E18</RainCoefficient>
		 * <WindspeedCoefficient>4.1388019931402163E18</WindspeedCoefficient>
		 * <PitchTankTempCoefficient>1.5056855164201856E17</PitchTankTempCoefficient>
		 * <PitchTankVolCoefficient>4.0212641519578204E18</PitchTankVolCoefficient>
		 * <SterolTankTempCoefficient>30362.457401828713</SterolTankTempCoefficient>
		 * <SterolTankVolCoefficient>-3.0198109740755748E18</SterolTankVolCoefficient>
		 * 
		 * 
		 * 
		 */
		/*
		 * String rootAttribute = null; List<String> child1 = new
		 * ArrayList<String>();
		 * 
		 * for (ListMapEntry e1 : xydata.iterEntries()) {
		 * System.out.println(" PARENT ::  :::: " + e1.getKey());
		 * 
		 * rootAttribute = (String) e1.getKey(); ListMap data = (ListMap)
		 * e1.getValue(); data.iterEntries();
		 * 
		 * for (ListMapEntry e2 : data.iterEntries()) { count++;
		 * System.out.println(count + " KEYS :::::: " + e2.getKey());
		 * child1.add((String) e2.getKey());
		 * 
		 * } break; }
		 * 
		 * for (int i = 0; i < parameters.length; i++) { if (i == 0) {
		 * System.out.println(childAtrribute.get(0) + "  Intercept ::" +
		 * parameters[0]); } else { System.out.println(childAtrribute.get(+i) +
		 * " ::" + parameters[+i]); }
		 * 
		 * }
		 */
		return parameters;
	}

}
