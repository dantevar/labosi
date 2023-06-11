package ui;

public class Layer {

	double[][] tezine ;
	double[] vektorPragova;
	int previousDimension;
	int currentDimension;
	boolean isFinalLayer;
	
	public Layer(double[][] tezine, double[] vektorPragova, int previousDimension, int currentDimension,boolean isFinalLayer) {
		super();
		this.tezine = tezine;
		this.vektorPragova = vektorPragova;
		this.previousDimension = previousDimension;
		this.currentDimension = currentDimension;
		this.isFinalLayer = isFinalLayer;
	}
	
	public int getCurrentDimension() {
		return currentDimension;
	}
	public int getPreviousDimension() {
		return previousDimension;
	}
	public double[][] getTezine() {
		return tezine;
	}
	public double[] getVektorPragova() {
		return vektorPragova;
	}
	public boolean isFinalLayer() {
		return isFinalLayer;
	}
	
	
}
