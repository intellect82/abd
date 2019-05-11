package com.atomiton.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.LinearConstraintSet;
import org.apache.commons.math3.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optim.linear.NonNegativeConstraint;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.linear.SimplexSolver;
import org.apache.commons.math3.optim.MaxIter;
import org.apache.commons.math3.optim.OptimizationData;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.MultivariateOptimizer;
//https://michaelmadhukalya.wordpress.com/2013/11/06/solving-linear-programming-lp-problems-using-java/

@SuppressWarnings("deprecation")
public class DataOptimizaton {

	public static void main(String[] args) {
		// describe the optimization problem

		LinearObjectiveFunction f1 = new LinearObjectiveFunction(new double[] { 3, 5 }, 0);

		ArrayList<LinearConstraint> constraints = new ArrayList<LinearConstraint>();
		constraints.add(new LinearConstraint(new double[] { 2, 8 }, Relationship.LEQ, 15));
		constraints.add(new LinearConstraint(new double[] { 5, -1 }, Relationship.LEQ, 20));

		constraints.add(new LinearConstraint(new double[] { 1, 0 }, Relationship.GEQ, 0));
		constraints.add(new LinearConstraint(new double[] { 0, 1 }, Relationship.GEQ, 0));

		LinearConstraintSet lcSet = new LinearConstraintSet(constraints);

		SimplexSolver solver1 = new SimplexSolver();

		PointValuePair optSolution = solver1.optimize(new MaxIter(100), f1, lcSet, GoalType.MAXIMIZE,
				new NonNegativeConstraint(false));
		// get the solution
		double x = optSolution.getPoint()[0];
		double y = optSolution.getPoint()[1];
		double min = optSolution.getValue();

		System.out.println("X : " + x + "Y :: " + y + "min ::::" + min);

		if (optSolution != null) {
			// get solution
			double max = optSolution.getValue();
			System.out.println("Opt: " + max);

			// print decision variables
			for (int i = 0; i < 2; i++) {
				System.out.print(optSolution.getPoint()[i] + "\t");

				System.out.print("KEY :::" + optSolution.getKey()[i] + "\t");
				System.out.print("Second  :::" + optSolution.getSecond() + "\t");
				System.out.print("FIRST :::" + optSolution.getFirst()[i] + "\t");

			}
		}

		/*
		 * 
		 * 
		 * // describe the optimization problem LinearObjectiveFunction f11 =
		 * new LinearObjectiveFunction(new double[] { -2, 1 }, -5); Collection
		 * constraints11 = new ArrayList(); constraints.add(new
		 * LinearConstraint(new double[] { 1, 2 }, Relationship.LEQ, 6));
		 * constraints.add(new LinearConstraint(new double[] { 3, 2 },
		 * Relationship.LEQ, 12)); constraints.add(new LinearConstraint(new
		 * double[] { 0, 1 }, Relationship.GEQ, 0));
		 * 
		 * LinearConstraintSet lcSet1 = new LinearConstraintSet(constraints11);
		 * 
		 * // create and run the solver PointValuePair solution = new
		 * SimplexSolver().optimize(new MaxIter(100),f11, lcSet1,
		 * GoalType.MINIMIZE, new NonNegativeConstraint(false));
		 * 
		 * // get the solution double x1 = solution.getPoint()[0]; double y1 =
		 * solution.getPoint()[1]; double min1 = solution.getValue();
		 * 
		 * System.out.println("X1 : " + x+"Y1 :: "+y+"min1 ::::"+min);
		 * 
		 */
		/*
		 * maximize: 30x + 40y s.t. x+y <= 240; 2x+y <= 320; x,y>=0;
		 */

		// objective f = 30x + 40y + 0
		LinearObjectiveFunction fl = new LinearObjectiveFunction(new double[] { 30, 40 }, 0);

		List<LinearConstraint> constraintsl = new ArrayList();

		// x + y <= 240
		constraints.add(new LinearConstraint(new double[] { 1, 1 }, Relationship.LEQ, 240));
		// x + y <= 320
		constraints.add(new LinearConstraint(new double[] { 2, 1 }, Relationship.LEQ, 320));
		// x,y >=0
		NonNegativeConstraint nonNegativeConstraint = new NonNegativeConstraint(false);

		LinearConstraintSet constraintSetl = new LinearConstraintSet(constraints);
		SimplexSolver linearOptimizer = new SimplexSolver();
		// put everything together in order to get a maximization problem
		// in the next line i receive
		// org.apache.commons.math3.optim.linear.UnboundedSolutionException:
		// unbounded solution
		PointValuePair solution11 = linearOptimizer.optimize(fl, constraintSetl, GoalType.MAXIMIZE,
				nonNegativeConstraint);

		if (solution11 != null) {
			// get solution
			double max = solution11.getValue();
			System.out.println("Opt********: " + max);
		}

	}
}
