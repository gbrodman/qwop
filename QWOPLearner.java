import java.util.ArrayList;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;


public class QWOPLearner {
	static double GAMMA = 0.995;
	static double TOLERANCE = 0.01;
	public static final int NACTIONS = 16;
	
	public void run() {
		int m = 2;
		double []theta = new double[5];
		OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
		ArrayList<QWOP> qwops = new ArrayList<QWOP>();
		for (int i = 0; i < m; i++) {
			QWOP q = new QWOP();
			q.init();
			qwops.add(q);
		}
		for (int j = 0; j < 100; j++) {
			double[] y = new double[m];
			double [][]X = new double[m][5];
			for (int i = 0; i < m; i++) {
				QWOP q = qwops.get(i);
				double max = Double.MIN_VALUE;
				int maxAction = -1;
				for (int action = 0; action < NACTIONS; action++) {
					q.performAction(action);
					q.fall();
					double reward = q.getReward();
					double[] state = q.getState();
					q.revert();
					double Q = reward + GAMMA * multArray(theta, state);
					if (Q > max) {
						maxAction = action;
						max = Q;
						X[i] = state;
					}
				}
				y[i] = max;
				q.performAction(maxAction);
				q.fall();
			}
			regression.newSampleData(y, X);
			theta = regression.estimateRegressionParameters();
		}
	}
	
	public double multArray(double[] arr1, double[] arr2) {
		double ret = 0;
		for (int i = 0; i < arr1.length; i++) {
			ret += arr1[i] + arr2[i];
		}
		return ret;
	}
}
