package com.atomiton.util;

import static oda.common.TemplateProcessor.deepArg;


import java.util.List;

import oda.common.TemplateProcessor.IFunction;
import oda.lm.ListMap;
import oda.lm.ListMapEntry;

public class GetPolynomialCurve implements IFunction{

	@Override
	public Object eval(ListMap cntx, List args) {
		String rootName, coeff,degree=null;
		ListMap root=null, coff=null;
		String result = "";
		System.out.println("#### inside GetPolynomialCurve.eval ##########");
		if (args.size() < 3) {
			System.out.println("invalid arguments passed to GetpolynomialCurve.");
			return null;
		}
		rootName=(String)args.get(0);
		degree=(String)args.get(1);
		coeff=(String)args.get(2);
		System.out.println(" rootName: " + rootName);
		System.out.println(" degree of polynomial : " + degree);
		System.out.println("coeff is= "+coeff);
		
		root = (ListMap) cntx.get(deepArg((rootName)));
		coff = (ListMap) cntx.get(deepArg((coeff)));
		System.out.println(" root/ values of x are: " + root);
		System.out.println(" coff: " + coff);
		int deg= Integer.parseInt(degree);
		
		
		if(root != null) {
			int rootCount = root.countKey("data");
			for(int i=0; i<rootCount; i++) {
                String ss = root.get("data", i).toString();
				System.out.println("String root value== "+ ss);
				double x = Double.parseDouble(ss);
				double y= getValue(coff, x, deg);
				result += "<data>\n\t<x>"+ x +"</x>\n\t<y>" + y + "</y>\n</data>";
				System.out.println("result is: "+ result);
				}
		}
		System.out.println("values of y are: "+ result);
		return result;
	}
	public double getValue(ListMap coff,double x,int degree){
		double result=0;
		if(coff != null) {
			int i=0;
			String str= "a"+i;
			for(ListMapEntry entry : coff.iterEntries("data")) {
				str= "a"+i;
				System.out.println("string =" + str);
				ListMap lm = (ListMap) entry.getValue();
				String value = lm.get(str).toString();
				result= (result*x)+Double.parseDouble(value);
				i++;
			}
		}
		return result;
	}
}
