package evo_assignment1;

import java.util.concurrent.ThreadLocalRandom;

//class that holds static methods for selector algorithms
public class Selectors {

	//helper method that returns a random integer with range num1-num2
	private static int randIntRn(int num1, int num2) {
		return ThreadLocalRandom.current().nextInt(num1, num2 + 1);
	}

	//helper method that ranks a population by performance
	public static void sortPopulationByPerformance(Population list){
		int j;
		Individual temp;
			
		for (int i = 0; i < list.size(); i++){
			j = i;
			
			while (j > 0 && list.getIndividual(j).getPerformance() < list.getIndividual(j-1).getPerformance()) {
				  temp = list.getIndividual(j);
				  list.set(j, list.getIndividual(j-1));
				  list.set(j-1, temp);
				  j--;
			}
		}
	}
	
	//helper method to return a random permutation of size size
	private static int[] setToRandomPermutation(int size) {
		int permutation[] = new int[size];
		//fill permutation array with ints 0 to size - 1
		for(int i=0; i<size; i++) {
			permutation[i] = i;
		}
		
		for(int i=0; i<size; i++) {
			//random number between i and size-1
			int rand_num = ThreadLocalRandom.current().nextInt(i, size);
			
			//swap ith element with random element
			int storage = permutation[i];
			permutation[i] = permutation[rand_num];
			permutation[rand_num] = storage;
		}
		
		return permutation;
	}
	
	//helper method to return k random individuals from population p
	private static Population getKRandIndividuals(int k, Population p) {
		if(k>p.size())
			return p;
		
		Population newPop = new Population(p.getCosts());
		int[] randomPerm = setToRandomPermutation(p.size());
		for(int i=0; i<k; i++) {
			newPop.add(p.getIndividual(randomPerm[i]));
		}
		return newPop;
	}
	
	//returns a Population of permutations selected through tournament selection
	//setting elitism to true ensures best performer of each tournament is selected at least once

	
public static Population tournament(Population oldPop, int k, double p, boolean elitism, int numSelected, boolean allowDuplicates) {
		//initialise new population
		Population newPop = new Population(oldPop.getCosts());
		
		
		//rank population
		Population ranks = new Population(oldPop);
		sortPopulationByPerformance(ranks);
		
		//until enough in new population
		while(newPop.size() != numSelected) {
			//select pool randomly
			Population pool = getKRandIndividuals(k, ranks);
			sortPopulationByPerformance(pool);

			//for each in pool
			for(int i=0; i<pool.size(); i++) {
				//if we've got enough, break
				if(newPop.size() >= numSelected)
					break;
				//if elitism is used, automatically add best individual in pool
				if(i==0 && elitism) {
					newPop.add(pool.getIndividual(0));
					if(!allowDuplicates)
						ranks.remove(pool.getIndividual(0));
					
				} else {
					//else, for each individual in pool, add it based on probability p * (1-p)^i, where ith is its rank in the pool
					float prob = (float) (p * Math.pow(1-p, i));
					double rand = Math.random();

					if(prob > rand) {
						//add to new population
						newPop.add(pool.getIndividual(i));
						if(!allowDuplicates)
							ranks.remove(pool.getIndividual(0));
					} 
				}
			}		
		}
		return newPop;  	
	}
	
	//returns a Population of permutations selected through fitness proportional selection
	//setting elitism to true ensures best performer is selected at least once
	
	//helper method that calculates the sum of weights of a population
	private static double calculateWeightSum(Population clone) {
		double weight_sum = 0;
		for(int i=0; i<clone.size(); i++) {
			weight_sum += clone.getIndividual(i).getPerformance();
			
		}
		return weight_sum;
	}
	
	//returns a Population of permutations selected through fitness proportional selection
	//setting elitism to true ensures best performer is selected at least once
	public static Population fitnessProportional(Population oldPop, boolean elitism, int numSelected, boolean allowDuplicates) {
		
		//duplicate new population to returs
		Population newPop = new Population(oldPop.getCosts());
		//record highes cost individual
		double max = 0;
		Population clone = new Population(oldPop.getCosts());
		for(int i=0; i<oldPop.size(); i++) {
			if(oldPop.getIndividual(i).getPerformance() > max) {
				max = oldPop.getIndividual(i).getPerformance();
			}
			
			clone.add(new Individual(oldPop.getIndividual(i)));
		}
		
		//if elitism is used, automatically add best performer
		if(elitism) {
			sortPopulationByPerformance(clone);
			newPop.add(clone.getIndividual(0));
			clone.remove(clone.getIndividual(0));
		}
		
		//inverse performance of clone population (since we're prioritising lowest cost rather than highest)
		for(int i=0; i<clone.size(); i++) {
			clone.getIndividual(i).setPerformance(max - clone.getIndividual(i).getPerformance());
		}
				
		//while our new population is not full
		while(newPop.size() != numSelected) {
			//create a value randomly between 0 and sum of all individual performances
			double value = Math.random() * calculateWeightSum(clone);	
			double counter = 0;
			int i = 0;
			
			while(true) {
				//if value is 0, this means all individuals have equal performance, in which case add first
				if(value == 0 ) {
					newPop.add(oldPop.getIndividual(0));
					if(!allowDuplicates)
						clone.remove(clone.getIndividual(i));
					break;					
				}
				//increment a counter by the performance of next individual
				counter += clone.getIndividual(i).getPerformance();

				//once the counter passes the before chosen value, add to the new population the individual whose performance was just incremented to counter
				if((counter >= value)) {
					newPop.add(oldPop.getIndividual(i));
					//remove it if we're not allowing duplicate survivors
					if(!allowDuplicates)
						clone.remove(clone.getIndividual(i));
					break;
				}
				i++;
			}
			
			
		}
		return newPop;
	}

	
}
