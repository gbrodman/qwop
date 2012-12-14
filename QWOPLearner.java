import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;


public class QWOPLearner {
	static double GAMMA = 0.9;
	public static final int NACTIONS = 16;
	
	public void run() {
		int m = 20;
		double []theta = new double[QWOP.NFEATURES + 1];
		OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
		//regression.setNoIntercept(true);
		ArrayList<QWOP> qwops = new ArrayList<QWOP>();
		for (int i = 0; i < m; i++) {
			QWOP q = new QWOP();
			q.init(true);
			qwops.add(q);
		}
		int start = 1;
		for (int rep = 0; rep < 60; rep++) {
		  if (rep == start) System.out.println("Starting guided actions...");
		  System.out.println("Trial #" + rep);
		  double maxReward = 0;
		  double minReward = 0;
	    double maxDistance = 0;
	    int longestLife = 0;
	    int[] lives = new int[m];
	    double minDistance = Double.MAX_VALUE;
		  for (int j = 0; j < 20000; j++) {
		    double[] y = new double[m];
		    double [][]X = new double[m][QWOP.NFEATURES + 1];
		    for (int i = 0; i < m; i++) {
		      QWOP q = qwops.get(i);
		      if (q.isDead()) {
		        if (q.getDistance() > maxDistance) maxDistance = q.getDistance();
		        if (q.getDistance() < minDistance) minDistance = q.getDistance();
		        q = new QWOP();
		        q.init(true);
		        qwops.set(i, q);
		        lives[i] = 0;
		      }
		      else lives[i]++;
		      if (lives[i] > longestLife) longestLife = lives[i];
		      double max = -Double.MAX_VALUE;
		      double[] maxState = new double[QWOP.NFEATURES];
		      int maxAction = -1;
		      for (int action = 0; action < NACTIONS; action++) {
		        q.save();
		        q.performAction(action);
		        q.fall();
		        double reward = q.getReward();
		        double[] state = q.getState();
		        q.revert();
		        double Q = reward + GAMMA * multArray(theta, concat(state, new double[]{1}));
		        if (reward > maxReward) {
		          maxReward = reward;
		        }
		        if (reward < minReward) {
              minReward = reward;
            }
		        if (Q > max) {
		          maxAction = action;
		          maxState = state;
		          max = Q;
		          y[i] = Q;
		          X[i] = Arrays.copyOf(state, state.length);
		          // noise to prevent singular matrix
		          X[i][0] += Math.random() * .01 - .005;
              X[i][1] += Math.random() * .01 - .005;
		          //X[i][4] += Math.random() * .01 - .005;
              //X[i][5] += Math.random() * .01 - .005;
		          //X[i][6] += Math.random() * .01 - .005;
		        }
		      }
		      if (rep < start){
		        if (Math.random() < .25) q.performAction(0);
		        else q.performAction((int)(Math.random() * 5));
		      }
		      else {
		        q.performAction(maxAction);
		      }
		      q.fall();
          double reward = q.getReward();
          double[] state = q.getState();
          //if (!Arrays.equals(state, maxState)) {
            //System.out.println("Porblem");
          //}
		    }
		    regression.newSampleData(y, X);
		    theta = regression.estimateRegressionParameters();
		  }
		  System.out.println("Theta: " + Arrays.toString(theta) + "\n   maxReward: "
		                     + maxReward + "; maxDistance: " + maxDistance 
		                     + "; minDistance: " + minDistance + "; longest life: " + longestLife);
		}
	}
	
	private double multArray(double[] arr1, double[] arr2) {
		double ret = 0;
		for (int i = 0; i < arr1.length; i++) {
			ret += arr1[i] * arr2[i];
		}
		return ret;
	}
	
	private double[] concat(double[] arr1, double[] arr2) {
	   double[] C= new double[arr1.length + arr2.length];
	   System.arraycopy(arr1, 0, C, 0, arr1.length);
	   System.arraycopy(arr2, 0, C, arr1.length, arr2.length);

	   return C;
	}
}
