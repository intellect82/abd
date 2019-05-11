package com.atomiton.util;
import java.util.LinkedHashMap;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.LUDecomposition;

/**
 * @author venkateswarlusayana
 *
 */

/**
 * Takes an array of continuous features and fits a multiple linear regression model predicting a named
 * feature using all other features.
 *
**/
public class Fit {
 
/** 
 * Evaluates the cost function (squared error) for a given level of parameters. 
 * @param thetas
 * @param data
 * @param featureIndex
 * @return
 */
    // Used in the update step of gradient descent. 
    private static double evaluateCost(double[] thetas, double[][] data, int featureIndex){
        double result = 0;
        
        // For every row in the dataset:
        for (int i = 0; i < data[0].length; i++){
            double error = 0;
            
            // Calculates the value predicted by the parameters.
            for (int j = 0; j < data.length - 1; j++)
                error += data[j][i]*thetas[j];
            
            // Subtracts the actual value to get the error and scales by the value in that row for the feature being updated. 
            error -= data[data.length - 1][i];
            error *= data[featureIndex][i];
            
            // Adds the result to the sum. 
            result += error;
        }
        
        return result; 
    }
    
/**
 *  Fits the model in closed form using the normal equation method. 
 * @param inputVectors
 * @param dependent
 * @return
 */
    public static Model normalEquation(Observation[] inputVectors, String dependent){
        // Builds the design matrix of features from the array of Observation objects, as well as its transpose. 
        double[][] design = new double[inputVectors.length][inputVectors[0].size()]; 
        double[][] designT = new double[inputVectors[0].size()][inputVectors.length]; // Transpose
        for (int i = 0; i < inputVectors.length; i++){
            design[i][0] = 1; // Intercept
            designT[0][i] = 1; 
            int j = 1;
            for (String feature : inputVectors[i].getFeatures()){
                if (!feature.equals(dependent)){
                    design[i][j] = inputVectors[i].getFeature(feature);
                    designT[j][i] = inputVectors[i].getFeature(feature);
                    j++;
                }
            }
        }
        RealMatrix X = new Array2DRowRealMatrix(design);
        RealMatrix XPrime = new Array2DRowRealMatrix(designT);

        // Builds the vector of y values. 
        double[] yArray = new double[inputVectors.length];
        for (int i = 0; i < inputVectors.length; i++)
            yArray[i] = inputVectors[i].getFeature(dependent); 
        RealMatrix y = new Array2DRowRealMatrix(yArray);
        
        // Solves for the parameter vector: theta = (X'X)-1 X'y
        RealMatrix theta = new LUDecomposition(XPrime.multiply(X)).getSolver().getInverse().multiply(XPrime).multiply(y);

        // Creates a hashmap of the name of each feature and its associated fitted parameter. 
        LinkedHashMap<String, Double> parameters = new LinkedHashMap<String, Double>();
        double[] thetas = theta.getColumn(0);
        parameters.put("Intercept", thetas[0]);
        int j = 1;
        for (String feature : inputVectors[1].getFeatures()){
            if (!feature.equals(dependent)){ // Ignores the dependent variable 
                parameters.put(feature, thetas[j]);
                j++;
            }
        }

        // Constructs the Model object. 
        Model outputModel = new Model(parameters, dependent, 0);

        // Calculates r-squared and sets it on the model. 
        outputModel.rSquared = calculateRSquared(inputVectors, outputModel);

        return outputModel;
    }

    // Contains an array of standardised Observation objects, an array of feature means and an array of feature standard deviations.
    // Stores the values used to standardise data so that data and fitted parameters can be destandardised. 
    private static class Standardisation {
        public Observation[] observations;
        public double[] xbars;
        public double[] sigmas;

        public Standardisation(Observation[] observations, double[] xbars, double[] sigmas){
            this.observations = observations;
            this.xbars = xbars;
            this.sigmas = sigmas;
        }
    }

/**
 *  Standardises data such that each feature has mean 0 and standard deviation 1. 
 * @param inputVectors
 * @return
 */
/*
 *  Speeds up convergence of gradient descent. 
 */
    private static Standardisation standardise(Observation[] inputVectors){
        // Arrays containing the means and standard deviations of each feature. 
        double[] xbars = new double[inputVectors[0].size()];
        double[] sigmas = new double[inputVectors[0].size()];

        // Iterates over the input data and adds the value of each feature to the mean. 
        for (int i = 0; i < inputVectors.length; i++){
            int j = 0;
            for (String feature : inputVectors[i].getFeatures()){
                xbars[j] += inputVectors[i].getFeature(feature);
                j++;
            }
        }
        
        // Divides each value by the number of observations to yield the mean.
        for (int i = 0; i < xbars.length; i++)
            xbars[i] = xbars[i] / inputVectors.length;

        // Iterates over the input data and adds the squared difference between that value and the mean to the standard deviation. 
        for (int i = 0; i < inputVectors.length; i++){
            int j = 0;
            for (String feature : inputVectors[i].getFeatures()){
                sigmas[j] += Math.pow(inputVectors[i].getFeature(feature) - xbars[j], 2);
                j++;
            }
        }

        // Square roots and divides each value by the number of observations to yield the standard deviation.
        for (int i = 0; i < sigmas.length; i++)
            sigmas[i] = Math.sqrt(sigmas[i] / inputVectors.length);

        // Iterates over the input data and standardises each value based on feature means and standard deviations. 
        for (int i = 0; i < inputVectors.length; i++){
            int j = 0;
            for (String feature : inputVectors[i].getFeatures()){
                inputVectors[i].putFeature(feature, (inputVectors[i].getFeature(feature) - xbars[j]) / sigmas[j]);
                j++;
            }
        }

        Standardisation output = new Standardisation(inputVectors, xbars, sigmas);

        return output;
    }
    
/**
 * Takes a Standardisation object and destandardises data and fitted parameters based on means and standard deviations. 
 * @param standard
 * @param inputVectors
 * @param thetas
 */
    private static void deStandardise(Standardisation standard, Observation[] inputVectors, double[] thetas){
        // Destandardises the intercept and parameters. 
        for (int i = 1; i < thetas.length; i++){
            thetas[0] -= thetas[i] * (standard.xbars[i-1] / standard.sigmas[i-1]);
            thetas[i] = (thetas[i] * standard.sigmas[standard.sigmas.length - 1]) / standard.sigmas[i-1];
        }
        thetas[0] *= standard.sigmas[standard.sigmas.length - 1];
        thetas[0] += standard.xbars[standard.xbars.length - 1];

        // Destandardises the input data. 
        for (int i = 0; i < inputVectors.length; i++){
            int j = 0;
            for (String feature : inputVectors[i].getFeatures()){
                inputVectors[i].putFeature(feature, ((inputVectors[i].getFeature(feature) * standard.sigmas[j]) + standard.xbars[j]));
                j++;
            }
        }
    }

/***
 *  Calculates the R-squared of a fitted model based on the model and the input used to fit it. 
 * @param inputVectors
 * @param model
 * @return
 */
    private static double calculateRSquared(Observation[] inputVectors, Model model){
        // Calculates the mean value of y.
        double ybar = 0;
        for (int i = 0; i < inputVectors.length; i++){
            ybar += inputVectors[i].getFeature(model.dependent);
        }
        ybar /= inputVectors.length;
        
        // Calculates residual and total sum of squares for the model.
        double rss = 0;
        double tss = 0;
        for (int i = 0; i < inputVectors.length; i++){
            rss += Math.pow((inputVectors[i].getFeature(model.dependent) - model.predict(inputVectors[i])), 2);
            tss += Math.pow((inputVectors[i].getFeature(model.dependent) - ybar), 2);
        }
        
    /*
     *  Returns the R-squared value.
     */
        return (1 - rss/tss);
    }
}
