package ui;

import java.io.IOException;

public class Solution {

	public static void main(String args[]) throws IOException {
		
		int dubina = -1;
		String trainingSet = args[0];
		String testSet = args[1];
		if(args.length ==2)
			dubina = Integer.parseInt(args[2]);
		
		Dataset trainingDataset = new Dataset(trainingSet);
		Dataset testDataset = new Dataset(testSet);
		
		
		ID3 model = new ID3(dubina);
		
		model.fit(trainingDataset);
		
		model.predict(testDataset);

	}

}
