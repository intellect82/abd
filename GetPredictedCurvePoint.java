package com.atomiton.util;

import static oda.common.TemplateProcessor.deepArg;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import oda.common.TemplateProcessor.IFunction;
import oda.lm.ListMap;
import oda.lm.ListMapEntry;

public class GetPredictedCurvePoint implements IFunction{

	@Override
	public Object eval(ListMap cntx, List args) {
		if (args.size() > 2) {
			String data = (String) args.get(0);
			String timest = (String) args.get(1);
			ListMap listOfData = (ListMap) cntx.get(deepArg((data)));
			ListMap timestamplist = (ListMap) cntx.get(deepArg((timest)));
			ListMap responseList = (ListMap) cntx.get(deepArg((String) args
					.get(2)));
			System.out.println(responseList);
			WeightedObservedPoints obs = new WeightedObservedPoints();
			for (ListMapEntry e : listOfData.iterEntries("Data")) {
				ListMap lm = (ListMap)	e.getValue();
				System.out.println(lm.get("Timestamp"));
				System.out.println(lm.get("Reading"));
				double timestamp = new Double(""+lm.get("Timestamp"));
				double val = new Double(""+lm.get("Reading"));
				//System.out.println(timestamp);
				//System.out.println(val);
				obs.add(timestamp, val);
			}
			List<WeightedObservedPoint> list = obs.toList();
			final PolynomialCurveFitter fitter = PolynomialCurveFitter.create(2);
			final double[] coeff = fitter.fit(list);
			
			System.out.println(coeff);
			
			for (ListMapEntry e : timestamplist.iterEntries("Data")) {
				double timeValue = new Double(""+e.getValue());
				//System.out.println("Second input"+timeValue);
				ListMap retLM = ListMap.newInstance();
				retLM.put("timestamp",new Long(""+e.getValue()) );
				double newValue = getValue(coeff,timeValue);
				retLM.put("reading", newValue);
				System.out.println(newValue);
				responseList.add("Data", retLM);
			}
		}
		
		return "true";
	}
	public static void main(String[] args) {
		String s = "1221321";
		double d = 2312321;
		ListMap retLM = ListMap.newInstance();
		retLM.put("time", s);
		retLM.put("read", d);
		
		System.out.println(new Double(""+retLM.get("time")));
		System.out.println(new Double(""+retLM.get("read")));
	}
	public double getValue(double[] coif,double x){		
		return coif[0]+(coif[1]*x)+(coif[2]*x*x);
	}
	
}
