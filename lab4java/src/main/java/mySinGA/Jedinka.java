package mySinGA;

import java.util.List;

public class Jedinka {
	
	List<Double> x;
	List<Double> y;
	double err;
	double fit;
	
	public Jedinka(List<Double> x, List<Double> y) {
		this.x = x;
		this.y = y;
	}
	
	void calculateErr() {
		int n = x.size();
		
		double err=0;
		for(int i = 0; i < n; i++) {
			double x_value = x.get(i);
			double y_value = y.get(i);
			err += circleErr(x_value, y_value);
		}
		this.err = err/n;
		this.fit = 1/this.err;
	}

	private double circleErr(double x, double y) {
		double r = 1;
		double calculatedR = x*x + y*y;
		return Math.pow(calculatedR -r,2); 
	}
	

}
