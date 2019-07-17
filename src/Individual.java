package evo_assignment1;

import java.util.concurrent.ThreadLocalRandom;


//represents an individual solution to the TSP as a permutation
public class Individual {

	//permutation length/size
	private int size;
	
	//permutation as an integer array
	private int permutation[];
	
	//solution performance
	private double performance = 0;
	

	//constructor that initialises an Individual with a random permutation
	Individual(int length) {
		this.permutation = new int[length];
		this.size = length;
		
		setToRandomPermutation();
	}
	
	//constructor that initialised an individual with given permutation and length
	Individual(int [] permutation, int length) {
		setPermutation(permutation, length);
	}
	
	public Individual(Individual clone) {
		this.performance = clone.performance;
		this.permutation = clone.permutation.clone();
		this.size = clone.size;
	}
	
	//initialises permutation randomly
	public void setToRandomPermutation() {
		
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
	}
	
	
	//get permutation size
	public int size() {
		return size;
	}

	//get permutation
	public int[] getPermutation() {
		return permutation;
	}
	
	//set permutation
	public void setPermutation(int [] permutation, int length) {
		this.permutation = permutation;
		this.size = length;
	}

	//get current performance
	public double getPerformance() {
		return performance;
	}

	//setter for performance
	public void setPerformance(double performance) {
		this.performance = performance;
	}
	
	//print the permutation and associated performance
	public void print() {
		for(int i =0; i<size; i++) {
			System.out.print(permutation[i]);
		}
		System.out.println(" " + performance);

	}

	//evaluates performance using given filePath
	public void evaluatePerformance(double[][] costs) {
		double perf = 0;
		perf += Construction.getDistance(permutation[0], permutation[size-1], costs);
		for(int i =1; i<size; i++) {
			perf += Construction.getDistance(permutation[i], permutation[i-1], costs);
		}
		
		this.performance = perf;
	}

}
