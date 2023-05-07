package ui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class ID3 {
		
	Node pocetniCvor;
	int dubina;
	
	public ID3(int dubina) {
		this.dubina = dubina;
	}
	
	public void predict(Dataset dataset) {
		
		List<String> predictions = new ArrayList<>();
		List<String> real = new ArrayList<>();
		List<String> differentValues = new ArrayList<>();
		
		for(HashMap<String, String> test : dataset.values) {
			String prediction = travelTree(pocetniCvor, test);
			predictions.add(prediction);
			
			String goalValue =  test.get(dataset.goalVariable);
			real.add(goalValue);
			if(!differentValues.contains(goalValue))
				differentValues.add(goalValue);
		}
		int size = predictions.size();
		
		System.out.print("[PREDICTIONS]:");
		for(int i = 0; i < size; i++)
			System.out.print(" " + predictions.get(i));
		System.out.println();
		
        double count =  IntStream.range(0, size)
                .filter(i -> predictions.get(i).equals(real.get(i)))
                .count();
        
        System.out.println("[ACCURACY]: "+ count/size);
        
        System.out.println("[CONFUSION_MATRIX]:");
        differentValues.sort(Comparator.naturalOrder());
        int n = differentValues.size();
        int[][] confusionMatrix = confusionMatrix(real, predictions, differentValues);
        for(int i = 0; i < n; i++) {
        	for(int j = 0; j < n; j++) 
        		System.out.print(confusionMatrix[i][j] + " ");
        	System.out.println();
        }
		
	}
	
	
	private int[][] confusionMatrix(List<String> real, List<String> predictions, List<String> differentValues) {
		int n = predictions.size();
		int[][] confusionMatrix = new int[n][n];
		
		for(int i = 0; i < n; i++) {
			String predVal = predictions.get(i);
			String realVal = real.get(i);
			int predIndex = differentValues.indexOf(predVal);
			int realIndex = differentValues.indexOf(realVal);
			confusionMatrix[realIndex][predIndex]++;
		}
		return confusionMatrix;
	}

	private String travelTree(Node cvor, HashMap<String, String> test) {
		
		if(cvor.subtrees == null) return cvor.value;
		String value = test.get(cvor.value);
		for(Subtree subtree : cvor.subtrees) {
			if(subtree.znacajka.equals(value)) {
				return travelTree(subtree.node, test);
			}
		}
		
		return null;
	}

	public void fit(Dataset dataset) {
		
		Set<String> X = new HashSet<>(dataset.differentValues.keySet());
		if(dubina >= 0)
			pocetniCvor = DecisionTree.id3(dataset, dataset, X, dubina);
		else
			pocetniCvor = DecisionTree.id3(dataset, dataset, X);
		System.out.println("[BRANCHES]:");
		printTree(pocetniCvor, 1, "");
	}
	
	private void printTree(Node cvor, int dubina, String s) {
				
		if(cvor.subtrees == null) {
			System.out.println(s + cvor.value);
			return;
		}
		s += dubina+":"+cvor.value ;
		for(Subtree subtree : cvor.subtrees) {

			printTree(subtree.node, dubina+1, s +"=" + subtree.znacajka + " " );
		}
	}
	




}
