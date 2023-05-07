package ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DecisionTree {
	
	public static Node id3(Dataset dataset, Dataset parentDataset, Set<String> X) {
		
		if(dataset.size == 0) {
			String v =
					Collections.max(parentDataset.diffrentGoalValues.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
			return new Node(v, null) ;
		}
		String v =
				Collections.max(dataset.diffrentGoalValues.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
		if(X.isEmpty() || dataset.isSameAs(v)) {
			return new Node(v, null) ;
		}
		
		String x = argmaxIg(dataset, X);
		
		X.remove(x);
		List<Subtree> djeca = new ArrayList<>();
		
		for(String valueOfX : dataset.differentValues.get(x)) {
			
			Node t = id3(dataset.podskup(x, valueOfX), dataset, X);
			djeca.add(new Subtree(valueOfX, t));
		}
		return new Node(x, djeca) ;
	}
	
	public static String argmaxIg(Dataset dataset, Set<String> X) {
		String maxX = "";
		double maxIg = 0;
		for(String x : X) {
			double val = IG(dataset, x);
			if(val > maxIg) {
				maxIg = val;
				maxX = x;
			}
		}
		return maxX;
	}

	
	public static double entrophy(Dataset dataset) {

		double n = dataset.size;
		double entrophy = 0;
		
		for (Map.Entry<String, Integer> entry : dataset.diffrentGoalValues.entrySet()) {
			double prob =  entry.getValue() / n;
			if(prob != 0)
				entrophy += - prob * ( Math.log(prob) / Math.log(2) ); 
			
		}
		return entrophy;
		
	}
	
	public static double IG(Dataset dataset, String x) {
		
		double sum = 0;
		
		for(String value : dataset.differentValues.get(x)) {
			Dataset newDataset = dataset.podskup(x, value);
			sum += ((double)newDataset.size / dataset.size) * entrophy(newDataset) ;
		}
		return entrophy(dataset) - sum;
		
	}

	public static Node id3(Dataset dataset, Dataset parentDataset, Set<String> X, int dubina) {
		
		if(dubina == 0) {
			String v =
					Collections.max(dataset.diffrentGoalValues.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
			return new Node(v, null) ;
		}
		
		if(dataset.size == 0) {
			String v =
					Collections.max(parentDataset.diffrentGoalValues.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
			return new Node(v, null) ;
		}
		String v =
				Collections.max(dataset.diffrentGoalValues.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
		if(X.isEmpty() || dataset.isSameAs(v)) {
			return new Node(v, null) ;
		}
		
		String x = argmaxIg(dataset, X);
		
		X.remove(x);
		List<Subtree> djeca = new ArrayList<>();
		
		for(String valueOfX : dataset.differentValues.get(x)) {
			
			Node t = id3(dataset.podskup(x, valueOfX), dataset, X, dubina-1);
			djeca.add(new Subtree(valueOfX, t));
		}
		return new Node(x, djeca) ;
	}

}
