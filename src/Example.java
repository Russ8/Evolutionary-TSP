package evo_assignment1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//example of library methods
public class Example {
	
	
    static String path = "eil51.tsp";
    static int fileLength = 51;

    public static void main(String[] args) throws IOException {

   	EvolutionaryAlgorithm ea = new EvolutionaryAlgorithm();
    	ea.setFile(path);
    	ea.setDataSize(fileLength);
    	ea.setGenerations(20000);

        //Algorithm 1 Configuration:
       	ea.setCurrentMutation(EvolutionaryAlgorithm.MUTATION_INVERSION);
       	ea.setCurrentCrossover(EvolutionaryAlgorithm.CROSSOVER_PMX);
       	ea.setMutationProbability(0.40f);
       	ea.setCurrentSelector(EvolutionaryAlgorithm.SELECTOR_TOURNAMENT);
       	ea.setTournamentProbSelection(0.8f);
       	ea.setTournamentSize(10);
       	ea.setUsingElitism(true);

        //Algorithm 2 Configuration:
       	/*
       	ea.setCurrentMutation(EvolutionaryAlgorithm.MUTATION_INVERSION);
       	ea.setCurrentCrossover(EvolutionaryAlgorithm.CROSSOVER_ORDER);
       	ea.setMutationProbability(0.20f);
       	ea.setCurrentSelector(EvolutionaryAlgorithm.SELECTOR_FITNESS_PROPORTIONAL);
       	ea.setUsingElitism(true);

       	 */
       	
        //Algorithm 3 Configuration:
       	/*
       	ea.setCurrentMutation(EvolutionaryAlgorithm.MUTATION_INVERSION);
       	ea.setCurrentCrossover(EvolutionaryAlgorithm.CROSSOVER_ORDER);
       	ea.setMutationProbability(0.30f);
       	ea.setCurrentSelector(EvolutionaryAlgorithm.SELECTOR_TOURNAMENT);
       	ea.setTournamentProbSelection(0.5f);
       	ea.setTournamentSize(10);
       	ea.setUsingElitism(true);
		*/
       	
       	List<Float> results = new ArrayList<>();
   		double sum = 0;
   		float elapsedTime =0;
		ea.setPopSize(50);
	   	long start = System.currentTimeMillis();
   		for(int i=0; i<30; i++) {
   			float res = ea.performEvolutionaryAlgorithm();
        	sum += res;
        	results.add(res);
   		}
   		long time = System.currentTimeMillis() - start;

   		
   		System.out.println("mean performance of best result " + sum/30);
   		System.out.println("mean stand deviation of results" + sd(results, sum));
   		System.out.println("mean time to complete algorithm" + time/30);


    }
    
    public static double sd (List<Float> table, double sum)
		{
		    // Step 1: 
		    double mean = sum/table.size();
		    double temp = 0;

		    for (int i = 0; i < table.size(); i++)
		    {
		        float val = table.get(i);

		        double squrDiffToMean = Math.pow(val - mean, 2);

		        temp += squrDiffToMean;
		    }

		    // Step 4:
		    double meanOfDiffs = (double) temp / (double) (table.size());

		    // Step 5:
		    return Math.sqrt(meanOfDiffs);
		}

}