package evo_assignment1;

import java.util.concurrent.ThreadLocalRandom;

public class MutationOperators {
	
	//mutation id's
	public static int MUTATION_INSERT = 0;
	public static int MUTATION_SWAP = 1;
	public static int MUTATION_INVERSION = 2;
	public static int MUTATION_DEFAULT = MUTATION_INSERT;
	
	//helper function that returns random number
	private static int randIntRn(int num1, int num2) {
		return ThreadLocalRandom.current().nextInt(num1, num2 + 1);
	}
	
	//helper function that return max of input
	private static int max(int a, int b) {
		if(a > b) {
			return a;
		} else {
			return b;
		}
	}
	
	//helper function that return min of input
	private static int min(int a, int b) {
		if(a < b) {
			return a;
		} else {
			return b;
		}
	}
	
	//method that performs insert mutation on given permutation
	private static void mutationInsert(int permutation[], int length) {
		//generate 2 random numbers
		int num1 = randIntRn(0, length-1);
		int num2 = randIntRn(0, length-1);
		
		//no insert can occur
		if(num1 == num2) return;
		
		int max = max(num1, num2);
		int min = min(num1, num2);

		int storage = permutation[max];
		
		for(int i=max; i > min+1; i--) {
			//shift right
			permutation[i] = permutation[i-1];
		}
		//insert max next to min
		permutation[min+1] = storage;
	}
	
	//method that performs swap mutation on given permutation
	private static void mutationSwap(int permutation[], int length) {
		//generate 2 random numbers
		int num1 = randIntRn(0, length-1);
		int num2 = randIntRn(0, length-1);
		
 		if(num1 == num2) return;
		//no swap can occur  
		
		//perform swap
		int storage = permutation[num1];
		permutation[num1] = permutation[num2];
		permutation[num2] = storage;
	}
	
	//method that performs inversion mutation on given permutation
	private static void mutationInversion(int permutation[], int length) {
		//generate 2 random numbers
		int num1 = randIntRn(0, length-1);
		int num2 = randIntRn(0, length-1);

		//no inversion can occur 
		if(num1 == num2) {
			return;
		}
		
		int max = max(num1, num2);
		int min = min(num1, num2);

		int j=0;
		for(int i=min; i<(max-min+1)/2+min; i++) {
			//perform swap

			int storage = permutation[i];
			permutation[i] = permutation[max-j];
			permutation[max-j] = storage;
			j++;
		}
	}
	
	//public method to perform mutation on given Individual
	public static void performMutation(int mutation_type, Individual currentPermutation) {
				
		if(mutation_type == MUTATION_INSERT) {
			mutationInsert(currentPermutation.getPermutation(), currentPermutation.size());
		} else 	if(mutation_type == MUTATION_SWAP) {
			mutationSwap(currentPermutation.getPermutation(), currentPermutation.size());
		} else 	if(mutation_type == MUTATION_INVERSION) {
			mutationInversion(currentPermutation.getPermutation(), currentPermutation.size());
		} else {
			performMutation(MUTATION_DEFAULT, currentPermutation);
		}
	}
}
