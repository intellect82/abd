package com.atomiton.util;

import static oda.common.TemplateProcessor.deepArg;

import java.util.List;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import oda.common.TemplateProcessor.IFunction;
import oda.lm.ListMap;
import oda.lm.ListMapEntry;
public class GetPolynomialCoeff implements IFunction {
	
	public static List<WeightedObservedPoint> getObservedPointList(ListMap root){
		final WeightedObservedPoints obs = new WeightedObservedPoints();
		if(root != null) {
			for (ListMapEntry data : root.iterEntries("data")) {
				ListMap lm = (ListMap) data.getValue();
				String x = lm.get("xValue").toString();
				String y = lm.get("yValue").toString();
				obs.add(Double.parseDouble(x),Double.parseDouble(y));
			}
		}
		System.out.println("list to return is: "+obs.toList());
		return obs.toList();		
	}
	
	
	@Override
	public Object eval(ListMap cntx, List args) {
		System.out.println("#### inside get polynomial coeff.eval ##########");
		String rootName, degree = null;
		ListMap root = null;
		if(args.size() <2 ) {
			System.out.println("invalid arguments passed to polynomial fitter.");
			return null;
		}
		
		rootName = (String) args.get(0);
		degree = (String) args.get(1);
			
		System.out.println(" rootName: " + rootName);
		System.out.println(" degree of polynomial : " + degree);
			
		root = (ListMap) cntx.get(deepArg((rootName)));
		System.out.println(" root: " + root);
		int deg = Integer.parseInt(degree);
		
		List<WeightedObservedPoint> list = getObservedPointList(root);
		final PolynomialCurveFitter fitter = PolynomialCurveFitter.create(deg);
		final double[] coeff = fitter.fit(list);
		System.out.println(""+coeff);
		String result = "";
		int i=0;
		for(double co : coeff){
			result += "<data>\n<a"+i+">" + co + "</a"+i+"></data>\n";
			i++;
		}	
		
		return result;
		
	}

}
