package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GeneticAlghorithm {
	
	int popsize ;
	int elitism ;
	double p ;
	double K ;
	int iter ;
	List<NeuralNetwork> population ;
	

	public GeneticAlghorithm(int inputDimension, int[] hiddenDimensions, int popsize, int elitism, double p, double K,
			int iter) {

		this.popsize = popsize;
		this.elitism = elitism;
		this.p = p;
		this.K = K;
		this.iter = iter;
		this.population = new ArrayList<>();
		for(int i=0; i < popsize; i++) {
			NeuralNetwork jedinka = new NeuralNetwork(inputDimension, hiddenDimensions) ;
			population.add(jedinka);
		}
	}

	public void run(List<double[]> dataset) {
		
		for(NeuralNetwork jedinka : population)
			jedinka.calculateError(dataset);
		
		population.sort( (jedinka1, jedinka2) ->
		 Double.compare(jedinka1.err, jedinka2.err) );
		
		for(int i = 1; i <= iter ; i++) {
			if(i % 2000 == 0)
				System.out.println("[Train error @"+i+"]: " + population.get(0).err);
			
			List<NeuralNetwork> newPopulation = new ArrayList<>();

			for(int j = 0 ; j < elitism; j++)
				newPopulation.add(population.get(j));
			
			while(newPopulation.size() < popsize) {	
				NeuralNetwork[] parents = selectParents(population);
				NeuralNetwork child = crossAndMutate(parents[0], parents[1]);
				child.calculateError(dataset);
				newPopulation.add(child);
			}

			population = newPopulation;
			population.sort( (jedinka1, jedinka2) ->
			 Double.compare(jedinka1.err, jedinka2.err) );
		}
	}

	private  NeuralNetwork crossAndMutate(NeuralNetwork parent1, NeuralNetwork parent2) {
		List<Layer> newLayers = new ArrayList<>();
		List<Layer> parent1Layers = parent1.layers;
		List<Layer> parent2Layers = parent2.layers;
		
		
		Random rand = new Random();
		for(int k=0; k < parent1Layers.size(); k++) {
			double[][] tezine = new double[parent1Layers.get(k).currentDimension]  [parent1Layers.get(k).previousDimension] ;
			double[] vektorPragova = new double[parent1Layers.get(k).currentDimension];
			
			for(int i = 0; i < parent1Layers.get(k).currentDimension; i++) {
				vektorPragova[i] = ( parent1Layers.get(k).getVektorPragova()[i] + parent2Layers.get(k).getVektorPragova()[i]   ) / 2 ;
				if(rand.nextDouble() <= p)
					vektorPragova[i] += rand.nextGaussian(0, K);
				for(int j = 0; j < parent1Layers.get(k).previousDimension; j++) {
					tezine[i][j] = ( parent1Layers.get(k).getTezine()[i][j] + parent2Layers.get(k).getTezine()[i][j] ) / 2 ;
					if(rand.nextDouble() <= p)
						tezine[i][j] += rand.nextGaussian(0, K);
				}
			}
			newLayers.add(new Layer(tezine, vektorPragova,  parent1Layers.get(k).previousDimension,  parent1Layers.get(k).currentDimension, parent1Layers.get(k).isFinalLayer));
		}
		return new NeuralNetwork(newLayers);
	}

	private  NeuralNetwork[] selectParents(List<NeuralNetwork> population) {
		Random rand = new Random();
		double sum = 0;
		for(NeuralNetwork jedinka : population) sum+= jedinka.fit;
		double randomValue , totalLen ;
		
		NeuralNetwork[] parents = new NeuralNetwork[2] ;
		
		for(int i = 0; i < 2; i++) {
			randomValue = rand.nextDouble();
			totalLen = 0;
			for(NeuralNetwork jedinka : population) {
				totalLen+= jedinka.fit / sum;
				if(totalLen >= randomValue) {
					parents[i] = jedinka;
					break;
				}
			}
			if(i == 1 && parents[1] == parents[0])
				i--;
		}


		return parents;
	}

	public void test(List<double[]> testDataset) {
		NeuralNetwork najboljaJedinka = population.get(0);
		najboljaJedinka.calculateError(testDataset);
		System.out.println("[Test error]: " + najboljaJedinka.err);
		
		
	}

}
