package com.atomiton.util;

import static oda.common.TemplateProcessor.deepArg;

import java.util.ArrayList;
import java.util.List;

import oda.common.TemplateProcessor.IFunction;
import oda.lm.ListMap;
import oda.lm.ListMapEntry;

public class GetMultiLinearRegressionCoeff implements IFunction {

	@Override
	public Object eval(ListMap cntx, List args) {

		ListMap coeffData = ListMap.newInstance();
		if (args.size() >= 2) {

			String dataSetName = (String) args.get(0);
			String coeffSuffix = (String) args.get(1);
			// ListMap root = (ListMap) args.get(0);
			ListMap root = (ListMap) cntx.get(deepArg((dataSetName)));
			double[] coeff = GetMultiLinearRegressionDataCoefficients.getParamInfo(root);

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
			 *
			 * <Intercept>-826.3455741515968</Intercept>
			 * <Temptest>636.3000075806714</Temptest>
			 * <Humiditytest>503.9067678545871</Humiditytest>
			 * <Raintest>-144.51416490486292</Raintest>
			 * <Windspeedtest>-17203.200000000015</Windspeedtest>
			 * <PitchTankTemptest>829.1315010570808</PitchTankTemptest>
			 * <PitchTankVoltest>-1535.9999999999989</PitchTankVoltest>
			 * <SterolTankTemptest>1023.999999999999</SterolTankTemptest>
			 * 
			 * 
			 * 
			 * 
			 */
			if (coeff != null) {

				for (int i = 0; i < coeff.length; i++) {
					if (i == 0) {
						coeffData.put("Intercept", coeff[i]);

					} else {
						coeffData.put(childAtrribute.get(i) + coeffSuffix, coeff[i]);

					}
				}
			} else {
			}
		}

		return coeffData;
	}

}
