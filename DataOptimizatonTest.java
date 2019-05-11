package com.atomiton.util;
import org.junit.Assert;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.math3.optim.MaxIter;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.LinearConstraintSet;
import org.apache.commons.math3.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optim.linear.NonNegativeConstraint;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.linear.SimplexSolver;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.junit.Test;

public class DataOptimizatonTest {

	@Test
	public void testOptimizationOne() {
		LinearObjectiveFunction f = new LinearObjectiveFunction(new double[] { 2, 2, 1 }, 0);
		Collection<LinearConstraint> constraints = new ArrayList();
		constraints.add(new LinearConstraint(new double[] { 1, 1, 0 }, Relationship.GEQ, 1));
		constraints.add(new LinearConstraint(new double[] { 1, 0, 1 }, Relationship.GEQ, 1));
		constraints.add(new LinearConstraint(new double[] { 0, 1, 0 }, Relationship.GEQ, 1));

		LinearConstraintSet lcSet1 = new LinearConstraintSet(constraints);

		// create and run the solver
		PointValuePair solution = new SimplexSolver().optimize(new MaxIter(10), f, lcSet1, GoalType.MINIMIZE,
				new NonNegativeConstraint(true));

		Assert.assertEquals(0.0, solution.getPoint()[0], .0000001);
		Assert.assertEquals(1.0, solution.getPoint()[1], .0000001);
		Assert.assertEquals(1.0, solution.getPoint()[2], .0000001);
		Assert.assertEquals(3.0, solution.getValue(), .0000001);
	}

	@Test
	public void testOptimizationTwo() {
		LinearObjectiveFunction ftwo = new LinearObjectiveFunction(new double[] { 0.8, 0.2, 0.7, 0.3, 0.6, 0.4 }, 0);
		Collection<LinearConstraint> constraints = new ArrayList();
		constraints.add(new LinearConstraint(new double[] { 1, 0, 1, 0, 1, 0 }, Relationship.EQ, 23.0));
		constraints.add(new LinearConstraint(new double[] { 0, 1, 0, 1, 0, 1 }, Relationship.EQ, 23.0));
		constraints.add(new LinearConstraint(new double[] { 1, 0, 0, 0, 0, 0 }, Relationship.GEQ, 10.0));
		constraints.add(new LinearConstraint(new double[] { 0, 0, 1, 0, 0, 0 }, Relationship.GEQ, 8.0));
		constraints.add(new LinearConstraint(new double[] { 0, 0, 0, 0, 1, 0 }, Relationship.GEQ, 5.0));
		LinearConstraintSet lcSet1 = new LinearConstraintSet(constraints);

		// create and run the solver
		PointValuePair solution = new SimplexSolver().optimize(new MaxIter(10), ftwo, lcSet1, GoalType.MINIMIZE,
				new NonNegativeConstraint(true));

		Assert.assertEquals(21.200000000000003, solution.getValue(), .0000001);
		Assert.assertEquals(23.0, solution.getPoint()[0] + solution.getPoint()[2] + solution.getPoint()[4], 0.0000001);
		Assert.assertEquals(23.0, solution.getPoint()[1] + solution.getPoint()[3] + solution.getPoint()[5], 0.0000001);
		Assert.assertTrue(solution.getPoint()[0] >= 10.0 - 0.0000001);
		Assert.assertTrue(solution.getPoint()[2] >= 8.0 - 0.0000001);
		Assert.assertTrue(solution.getPoint()[4] >= 5.0 - 0.0000001);
	}

}