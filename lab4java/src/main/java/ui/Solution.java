package ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Solution {

	public static void main(String[] args) throws IOException {
	

		String trainPath = "files/rastrigin_train.txt";
		String testPath = "files/rastrigin_test.txt";
		
		int[] hiddenDimensions = {5,5};
		int popsize = 10;
		int elitism = 1;
		double p = 0.5;
		double K = 4;
		int iter = 2000;
		String arch = "";
		
		for(int i = 0; i  < args.length;i++) {
			if(args[i].equals("--train")) trainPath = args[i+1];
			if(args[i].equals("--test")) testPath = args[i+1];
			if(args[i].equals("-nn")) arch = args[i+1];
			if(args[i].equals("--elitism")) elitism = Integer.parseInt(args[i+1]);
			if(args[i].equals("--p")) p = Double.parseDouble(args[i+1]);
			if(args[i].equals("--K")) K = Double.parseDouble(args[i+1]);
			if(args[i].equals("--iter"))  iter = Integer.parseInt(args[i+1]);
		}
		if(arch.equals("5s")) {
			int[] pom = {5};
			hiddenDimensions = pom;
		}
		if(arch.equals("5s5s")) {
			int[] pom = {5,5};
			hiddenDimensions = pom;
		}
		if(arch.equals("20s")) {
			int[] pom = {20};
			hiddenDimensions = pom;
		}
		
		List<double[]> trainDataset = readFromFile(trainPath);
		
		int inputDimension = trainDataset.get(0).length-1;
		
		System.out.println("[Train error@2000] " + trainPath + " " + popsize + " " + elitism + " " + p + " " + K + " " + iter);
		GeneticAlghorithm ga = new GeneticAlghorithm(inputDimension, hiddenDimensions, popsize, elitism, p, K, iter);
		ga.run(trainDataset);
		
		List<double[]> testDataset = readFromFile(testPath);
		ga.test(testDataset);
	}
	
	public static List<double[]>  readFromFile(String csvPath) throws IOException{
		List<String> lines = Files.readAllLines(Path.of(csvPath));
		
		List<double[]> dataset = new ArrayList<>();
		for(int i = 1; i < lines.size(); i++) {
			String[] valuesString = lines.get(i).split(",");
			double[] values = Arrays.stream(valuesString).mapToDouble(Double::parseDouble).toArray();
			dataset.add(values);
		}
		return dataset;
		
	}

}
