package evo_assignment1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CrossoverOperators {
	
	//crossover id's
	public static int CROSSOVER_ORDER = 0;
	public static int CROSSOVER_PMX = 1;
	public static int CROSSOVER_CYCLE = 2;
	public static int CROSSOVER_EDGE_RECOMBINATION = 3;
	public static int CROSSOVER_DEFAULT = CROSSOVER_ORDER;
	
	private static int randIntRn(int num1, int num2) {
		return ThreadLocalRandom.current().nextInt(num1, num2 + 1);
	}
	
	//helper function that returns max of input
	private static int max(int a, int b) {
		if(a > b) {
			return a;
		} else {
			return b;
		}
	}
	
	//helper function that returns minimum of input
	private static int min(int a, int b) {
		if(a < b) {
			return a;
		} else {
			return b;
		}
	}
	
	//helper function that finds index of element in an array
	private static int findIndex(int find, int array[], int length) {
		for(int i=0; i<length; i++) {
			if(array[i] == find) {
				return i;
				
			}
		}
		return -1;
	}
	
	//method that performs order crossover on given permutations, returns a new permutation
	private static int[] CrossoverOrder(int permutation1[], int permutation2[], int length) {

		//initiate a new child permutation. 
		int child[] = new int[length];
		for(int i=0; i<length; i++) {
			child[i] = -1;
		}		
		
		//setup a lookup table to determine which permutation values have been added to child
		boolean lookup[] = new boolean[length];
		for(int i=0; i<length; i++) {
			lookup[i] = false;
		}
		
		//generate 2 random numbers
		int num1 = randIntRn(0, length-1);
		int num2 = randIntRn(0, length-1);

		
		if(num1 == num2) {
			//get all information from 2nd parent
			return permutation2;
		}
		
		//find min, max of random numbers
		int max = max(num1, num2);
		int min = min(num1, num2);
		
		//copy over permutation1 subset based on random numbers
		for(int i=min; i<=max; i++) {
			child[i] = permutation1[i];
			lookup[child[i]] = true;
		}

		//copy over permutation2 subset
		//initiate i to largest rand number + 1
		int i = max+1;
		if(i>=length) i=0;
		int counter = 0;
		int placeAt = max+1;
		if(placeAt>=length) placeAt=0;

		//while the new permutation is not full
		while(counter<(length-(max-min+1))) {
			if(lookup[permutation2[i]] == false) {
				child[placeAt] = permutation2[i];
				lookup[permutation2[i]] = true;
				//System.out.print("adding " + child[i] + " ");
				placeAt ++;
				counter++;
				if(placeAt>=length) placeAt=0;

			}
			i++;
			if(i>=length) i=0;

		}	

		//return result
		return child;
		
	}
	
	//method that performs pmx crossover on given permutations, returns a new permutation
	private static int[] CrossoverPMX(int permutation1[], int permutation2[], int length) {
		int child[] = new int[length];
		
		for(int i=0; i<length; i++) {
			child[i] = -1;
		}
		

		
		//generate 2 random numbers
		int num1 = randIntRn(0, length-1);
		int num2 = randIntRn(0, length-1);

		if(num1 == num2) {
			//get all information from 2nd parent
			return permutation2;
		}
		
		int max = max(num1, num2);
		int min = min(num1, num2);
		
		//copy over perm1 subset
		for(int i=min; i<=max; i++) {
			child[i] = permutation1[i];
		}

		//copy conflicting subset
		//copy over perm1 subset
		int counter = 0;
		for(int i=min; i<=max; i++) {
			int tryPos = i;
			
			if(findIndex(permutation2[tryPos], child, length) != -1) {
				//already in offspring
			} else {
				//find next available position
				while(true) {
					if((tryPos<=max) && (tryPos>=min)) {
						tryPos = findIndex(permutation1[tryPos], permutation2, length);
					} else {
						child[tryPos] = permutation2[i];
						break;
					}
				}
			}
			counter++;
			if(counter>length) {
				System.out.println("faulty permutation resulting in inf loop in CrossoverOperators.CrossoverPMX");
			}
		}		
		
		//add rest of new permutation from second parent
		int i = max+1;
		counter = 0;
		while(counter<(length)) {
			if(i>=length) 
				i=0;
			if(child[i] == -1)
				child[i] = permutation2[i];
			i++;
			counter++;
		}	
		
		//return result
		return child;
		
	}
	
	//helper function that deletes all instances of int n from given list of int lists
	private static void deleteInt(ArrayList<ArrayList<Integer>> table, int n) {
		for(int i=0; i<table.size(); i++) {
			for(int j=0; j<table.get(i).size(); j++) {
				if(table.get(i).get(j) == n) {
					table.get(i).remove(j);
					j--;
				}
			}
		}
	}
	
	//helper function that returns a new clone of given array using sublists of 'using'
	private static ArrayList<ArrayList<Integer>> buildArrayUsing(ArrayList<ArrayList<Integer>> table, ArrayList<Integer> using) {
		ArrayList<ArrayList<Integer>> newList = new ArrayList<ArrayList<Integer>>();
		
		for(int i=0; i<using.size(); i++) {
			newList.add(table.get(using.get(i)));
		}
		return newList;
	}
	
	//helper function using to return a list of common edges for edge recombination
	private static ArrayList<Integer> retrieveCommonEdges(ArrayList<Integer> list) {
		ArrayList<Integer> newList = new ArrayList<Integer>();
		
		for(int i=0; i<list.size(); i++) {
			if((i>0) && (list.get(i) == list.get(i-1))) {
				newList.add(list.get(i));
			}
		}
		
		return newList;
	}
	
	//helper function which returns size of list, excluding duplicates
	private static int sizeNoDups(ArrayList<Integer> list) {
		int counter = 0;
		for(int i=0; i<list.size(); i++) {
			if((i>0) && (list.get(i) == list.get(i-1))) {
				counter --;
			}
			counter ++;
		}
		return counter;
	}
	
	//helper function that returns smallest value from list of lists
	private static int returnSmallestFrom(ArrayList<ArrayList<Integer>> lists) {
		int smallest = 999999999;
		int currentSmallest = -1;
		for(int i=0; i<lists.size(); i++) {
			int size = sizeNoDups(lists.get(i));
			if(size < smallest) {
				currentSmallest = i;
				smallest = size;
			}
		}
		return currentSmallest;		
	}
	
	//helper function that generates random value
	private static int randomListFrom(ArrayList<Integer> from) {
		return randIntRn(0, from.size());
	}
	
	//method that performs edge recombination crossover on given permutations, returns a new permutation
	private static int[] CrossoverEdgeRecombination(int permutation1[], int permutation2[], int length) {
		//initiate edge table and child
		int child[] = new int[length];
		ArrayList<ArrayList<Integer>> edgeTable = new ArrayList<ArrayList<Integer>>();
		
		for(int i=0; i<length; i++) {
			ArrayList<Integer> init = new ArrayList<Integer>();
			edgeTable.add(init);
		}
		
		//construct edge table
		for(int i=0; i<length; i++) {
			int nextPos = i+1;
			if(nextPos>=length) 
				nextPos = 0;
			int prevPos = i-1;
			if(prevPos<0) 
				prevPos = length - 1;
			
			edgeTable.get(permutation1[nextPos]).add(permutation1[i]);
			edgeTable.get(permutation2[nextPos]).add(permutation2[i]);
			edgeTable.get(permutation1[prevPos]).add(permutation1[i]);
			edgeTable.get(permutation2[prevPos]).add(permutation2[i]);
		}

		//generate random number
		int random_num = randIntRn(0, length-1);
		
		//add initial random number to child permutation
		child[0] = random_num;
		deleteInt(edgeTable, random_num);
		
		//initiate a lookup table
		boolean lookup[] = new boolean[length];
		for(int i=0; i<length; i++) {
			lookup[i] = false;
		}
		
		//initiate a counter a int list currentList
		int counter = 0;
		ArrayList<Integer> currentList = edgeTable.get(random_num);
		
		//while new permutation is not full
		while(counter<length) {
			//find any remaining common edges
			ArrayList<Integer> duplicates = retrieveCommonEdges(currentList);

			//if there are common edges ...
			if(currentList.size() == 0) {
				for(int i=0; i<length; i++) {
					//if value not already added ... 
					if(lookup[i]==false) {
						//add to currentList
						currentList = edgeTable.get(i);
						deleteInt(edgeTable, i);
						child[counter] = i;

						lookup[i] = true;	
						//break once found
						break;
					}
				}
				//if no common edges, check duplicates
			} else if(duplicates.size()>0) {

				// if so add it to current list
				int next = currentList.get(randomListFrom(duplicates));
				deleteInt(edgeTable, next);
				currentList = edgeTable.get(next);

				child[counter] = next;
				lookup[next] = true;
			} else {

				//else find smallest from edgeTable
				int smallestPos = returnSmallestFrom(buildArrayUsing(edgeTable, currentList));
				
				//add it to current list
				child[counter] = currentList.get(smallestPos);
				lookup[currentList.get(smallestPos)] = true;
				int delete = currentList.get(smallestPos);
				currentList = edgeTable.get(currentList.get(smallestPos));
				deleteInt(edgeTable, delete);


			}
			//increment counter
			counter++;
		}
		//return result
		return child;	
	}
	
	//method that performs cycle crossover on given permutations
	private static int[] CrossoverCycle(int permutation1[], int permutation2[], int length) {
		int child[] = new int[length];

		//initiate lookup table
		boolean lookup[] = new boolean[length];
		for(int i=0; i<length; i++) {
			lookup[i] = false;
		}
		
		//represent current cycle as integer list
		List<Integer> cycle = new ArrayList<Integer>();
		int counter = 0;

		//who's turn it is to be added to the new permutation
		boolean currentParent1 = true;
		
		//while counter less than permutation length
		while(counter<length) {
			//find smallest unused starting position
			int startingPos = -1;
			for(int i=0; i<length; i++) {
				if(lookup[i]==false) {
					startingPos = i;
					break;
				}
			}
			if(startingPos==-1) 
				//something went wrong
				break;
			
			//add starting position to current cycle
			cycle.add(startingPos);
			lookup[startingPos] = true;
			
			//add each element in the subsequent cycle
			int currentPos = findIndex(permutation2[startingPos], permutation1, length);
			while(currentPos != startingPos) {
				lookup[currentPos] = true;
				cycle.add(currentPos);
				currentPos = findIndex(permutation2[currentPos], permutation1, length);
			}
			
			//finished cycle - add them to new permutation
			for(int i=0; i<cycle.size(); i++) {
				//add based on which parent's turn it is
				if(currentParent1) {
					child[cycle.get(i)] = permutation1[cycle.get(i)];
				} else {
					child[cycle.get(i)] = permutation2[cycle.get(i)];					
				}
			}
			//clear the cycle and repeat
			cycle.clear();

			//toggle turn tracker
			currentParent1 = !currentParent1;
			counter ++;
		}
		//return result
		return child;
		
		
		
	}
	
	//public method to perform permutation on given Individuals, using given crossover type
	public static Individual performCrossover(int crossover_type, Individual permutation1,  Individual permutation2) {
		
		if(crossover_type == CROSSOVER_ORDER) {
			int[] temp =  CrossoverOrder(permutation1.getPermutation(), permutation2.getPermutation(), permutation1.size());
			return new Individual(temp, permutation1.size());
		} else 	if(crossover_type == CROSSOVER_PMX) {
			int[] temp =   CrossoverPMX(permutation1.getPermutation(), permutation2.getPermutation(), permutation1.size());
			return new Individual(temp, permutation1.size());

		} else 	if(crossover_type == CROSSOVER_CYCLE) {
			int[] temp =   CrossoverCycle(permutation1.getPermutation(), permutation2.getPermutation(), permutation1.size());
			return new Individual(temp, permutation1.size());

		} else if(crossover_type == CROSSOVER_EDGE_RECOMBINATION) {
			int[] temp =   CrossoverEdgeRecombination(permutation1.getPermutation(), permutation2.getPermutation(), permutation1.size());
			return new Individual(temp, permutation1.size());

		} else {
			return performCrossover(CROSSOVER_DEFAULT, permutation1, permutation2);
		}
	}

}
