package ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class NeuralNetwork {
	
	final static double  mean = 0;
	final static double  standardDeviation = 0.01;
	final static int outputDimension = 1;
	
	
	double err  ;
	double fit ;
	List<Layer> layers; //slojevi tezina
	
	public NeuralNetwork(List<Layer> layers) {
		this.layers = layers;
	}

	public NeuralNetwork(int inputDimension, int[] hiddenLayersDimensions) {
		
		layers = new ArrayList<>();
				
		for(int i = 0; i <= hiddenLayersDimensions.length; i++) {
			
			int previousDimension = i == 0 ? inputDimension : hiddenLayersDimensions[i-1];
			int currentDimension = i == hiddenLayersDimensions.length ? outputDimension : hiddenLayersDimensions[i];
			double tezine[][] = new double[currentDimension][previousDimension];
			double vektorPragova[] = new double[currentDimension];
			
			fill(tezine,vektorPragova,currentDimension,previousDimension);
			boolean isFinal = i == hiddenLayersDimensions.length ? true : false;
			layers.add(new Layer(tezine, vektorPragova, previousDimension, currentDimension, isFinal));
		}
	}

	public double evaluate(double[] input)  {
		double[] y = null;
		double[] x = null;
		for(int i= 0; i < layers.size(); i++) {
			x = i == 0 ? input : y;
			y  = layerOutput(layers.get(i), x);
		}

		return y[0] ; //zadnji sloj 1 dim
	}

	private double[] layerOutput(Layer layer, double[] input) {

		double[][] tezine = layer.getTezine();
		double[] vektorPragova = layer.getVektorPragova();
		double[] output = new double[layer.getCurrentDimension()];
		
		for(int i = 0; i < layer.getCurrentDimension(); i++) {
			double value = 0;
			for(int j = 0; j < layer.getPreviousDimension(); j++) {
				value += tezine[i][j] * input[j];
			}
			value += vektorPragova[i];

			if(!layer.isFinalLayer)
				value = 1 / ( 1 + Math.pow(Math.E, -value)) ;
			output[i] = value;
		}
		
		return output;
	}
	
	public double calculateError(List<double[]> dataset) {
		
		double err = 0;
		int n = dataset.size();
		for(double[] entry : dataset) {
			double realY = entry[entry.length-1];
			double[] input = Arrays.copyOf(entry, entry.length-1); 
			double calculatedY = evaluate(input);
			err += Math.pow(realY-calculatedY, 2);
		}
	
		this.err = err/n;
		
		this.fit =1/this.err;
		
		return this.err;
	}

	private void fill(double[][] parameters,double[] vector, int currentDimension, int previousDimension) {
		Random random = new Random();
		double normalRand ;
		for(int i = 0; i < currentDimension; i++) {
			normalRand = random.nextGaussian(mean, standardDeviation);
			vector[i] = normalRand;
			for(int j = 0; j < previousDimension; j++) {
				normalRand = random.nextGaussian(mean, standardDeviation);
				parameters[i][j] = normalRand;
			}
		}
		
	}
	
	
	
}
