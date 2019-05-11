package com.atomiton.util;

import static oda.common.TemplateProcessor.deepArg;

import java.util.ArrayList;
import java.util.List;

import oda.common.TemplateProcessor.IFunction;
import oda.lm.ListMap;
import oda.lm.ListMapEntry;

public class GetLinearRegressionCoeff implements IFunction {

	@Override
	public Object eval(ListMap cntx, List args) {
		ListMap coeffData = ListMap.newInstance();
		if (args.size() >= 1) {
			
			String dataSetName = (String)args.get(0);
			//ListMap root = (ListMap) args.get(0);
			ListMap root = (ListMap) cntx.get(deepArg((dataSetName)));
			double[] coeff = GetDataCoefficients.getParamInfo(root);			
			
			String rootAttribute = null;
			List<String> childAtrribute = new ArrayList<String>();
			for (ListMapEntry parent_element : root.iterEntries()) {

				rootAttribute = (String) parent_element.getKey();
				ListMap data = (ListMap) parent_element.getValue();
				data.iterEntries();

				for (ListMapEntry child_element : data.iterEntries()) {
					
					childAtrribute.add((String) child_element.getKey());
				}
				break;
			}

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
			
			for (int i = 0; i < coeff.length; i++) {
				if (i == 0) {
					coeffData.put("Intercept", coeff[i]);
				} else {
					coeffData.put(childAtrribute.get(i) + "Coefficient", coeff[i]);
				}
			}
		}
		return coeffData;
	}

}