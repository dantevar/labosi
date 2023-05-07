package ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Dataset {
	
	int size;
	String goalVariable;
	List<HashMap<String, String>> values;
	HashMap<String,Integer> diffrentGoalValues;
	HashMap<String, Set<String>> differentValues;
	
	public Dataset(String csvPath) throws IOException {
		
		List<String> lines = Files.readAllLines(Path.of(csvPath));
		List<String> variables = new ArrayList<>();
		
		String[] header = lines.get(0).split(","); 
		
		values = new ArrayList<>();
		diffrentGoalValues = new HashMap<>();
		differentValues = new HashMap<>();
		
		for(String variable : header) {
			variables.add(variable);
		}
		goalVariable = variables.get(variables.size()-1);
		
		for(int i = 1; i < lines.size(); i++) {
			String[] valuesArray = lines.get(i).split(",");
			HashMap<String,String> entry = new HashMap<>();
			for(int j = 0; j < valuesArray.length; j++) {
				entry.put(variables.get(j), valuesArray[j]);
				
				differentValues.computeIfAbsent(variables.get(j), v -> new HashSet<>()).add(valuesArray[j]);
			}
			values.add(entry);
			
			String goalValue = entry.get(goalVariable);
			diffrentGoalValues.compute(goalValue, (k,v) -> v== null ? 1 : v+1 );
			
		}
		differentValues.remove(goalVariable);
		size = values.size();
	}

	public Dataset(int size, String goalVariable, List<HashMap<String, String>> values,
			HashMap<String, Integer> diffrentGoalValues, HashMap<String, Set<String>> differentValues) {
		this.size = size;
		this.goalVariable = goalVariable;
		this.values = values;
		this.diffrentGoalValues = diffrentGoalValues;
		this.differentValues = differentValues ;
	}
	
	public Dataset podskup(String x, String v) {
		
		List<HashMap<String, String>> newValues = new ArrayList<>();
		
		HashMap<String,Integer> newDiffrentGoalValues =(HashMap<String,Integer>) diffrentGoalValues.keySet().stream()
				.collect(Collectors.toMap(key -> key, value -> 0)) ;
		
		for(HashMap<String,String> entry : values) {
			if(entry.get(x).equals(v)) {
				newValues.add(entry);
				newDiffrentGoalValues.compute(entry.get(goalVariable), (k,val) -> val +1 );
			}
		}
		
		return new Dataset(newValues.size(), goalVariable, newValues, newDiffrentGoalValues, differentValues);
		
	}

	public boolean isSameAs(String v) {
		int numOfV =  diffrentGoalValues.get(v);
		return size == numOfV;
	}


}
