package com.atomiton.util;

import org.osgi.service.component.annotations.Component;

import com.atomiton.sff.api.nio.SffTpFunctionsSvc;

import oda.lm.ListMap;

@Component(service = SffTpFunctionsSvc.class)
public class CustomTPFactory implements SffTpFunctionsSvc {

	private ListMap info;

	private static final String GetPredictedCurvePoint = "GetPredictedCurvePoint";
	private static final String GetPolynomialCoeff = "GetPolynomialCoeff";
	private static final String GetPolynomialCurve = "GetPolynomialCurve";
	private static final String GetLinearRegressionCoeff = "GetLinearRegressionCoeff";
	private static final String GetMultiLinearRegressionCoeff = "GetMultiLinearRegressionCoeff";

	@Override
	public ListMap getInfo() {

		info = ListMap.newInstance();
		info.put(GetPredictedCurvePoint, new GetPredictedCurvePoint());
		info.put(GetPolynomialCoeff, new GetPolynomialCoeff());
		info.put(GetPolynomialCurve, new GetPolynomialCurve());
		info.put(GetLinearRegressionCoeff, new GetLinearRegressionCoeff());
		info.put(GetMultiLinearRegressionCoeff, new GetMultiLinearRegressionCoeff());

		return info;
	}
}
