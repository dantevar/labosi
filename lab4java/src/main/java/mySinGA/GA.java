package mySinGA;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.SwingUtilities;


public class GA {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			try {
				new CircleWindow().setVisible(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	public static Jedinka run() {
		int iter = 10000;
		int elitism = 1;
		int popsize = 10;
		int values = 200;
		List<Jedinka> population = new ArrayList<>();
		
		for(int i=0; i < popsize; i++) {
			 List<Double> x = new ArrayList<>(Collections.nCopies(values, 0.0));
			 List<Double> y = new ArrayList<>(Collections.nCopies(values, 0.0));
			 Jedinka jedinka = new Jedinka(x, y);
			 jedinka.calculateErr();
			 population.add(jedinka);
			 
		}

		 
		population.sort( (jedinka1, jedinka2) ->
		 Double.compare(jedinka1.err, jedinka2.err) );
		
		for(int i = 1; i <= iter ; i++) {
			if(i % 1000 == 0)
				System.out.println(i + ": " + population.get(0).err);
			List<Jedinka> newPopulation = new ArrayList<>();

			for(int j = 0 ; j < elitism; j++)
				newPopulation.add(population.get(j));
			
			while(newPopulation.size() < popsize) {	
				Jedinka[] parents = selectParents(population);
				Jedinka child = crossAndMutate(parents[0], parents[1]);
				child.calculateErr();
				newPopulation.add(child);
			}

			population = newPopulation;
			population.sort( (jedinka1, jedinka2) ->
			 Double.compare(jedinka1.err, jedinka2.err) );
		}
	
		return population.get(0);
	}

	private static  Jedinka crossAndMutate(Jedinka parent1, Jedinka parent2) {
		
		double p = 0.1;
		double k = 0.1;
		Random rand = new Random();
		int n = parent1.x.size();
		
		List<Double> newX = new ArrayList<>();
		List<Double> newY = new ArrayList<>();
		
		for(int i = 0; i < n; i++) {
			
			double newXval = ( parent1.x.get(i) + parent2.x.get(i) ) /2;
			double newYval = ( parent1.y.get(i) + parent2.y.get(i) ) /2;
			
			if(rand.nextDouble() <= p)
				newXval += rand.nextGaussian(0, k);
			if(rand.nextDouble() <= p)
				newYval += rand.nextGaussian(0, k);
			newX.add(newXval);
			newY.add(newYval);

		}
		return new Jedinka(newX, newY);
	}

	private static  Jedinka[] selectParents(List<Jedinka> population) {
		Random rand = new Random();
		double sum = 0;
		for(Jedinka jedinka : population) sum+= jedinka.fit;
		double randomValue , totalLen ;
		
		Jedinka[] parents = new Jedinka[2] ;
		
		for(int i = 0; i < 2; i++) {
			randomValue = rand.nextDouble();
			totalLen = 0;
			for(Jedinka jedinka : population) {
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

}
