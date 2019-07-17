package evo_assignment1;

import java.util.ArrayList;

public class Population {

	//list of all Individuals in this Population
	private ArrayList<Individual> individuals;
	
	//holds distances for each pair of individual
	private double[][] costs;
	
	//default constructor
	Population(double[][] costs ) {
		this.costs = costs;
		individuals = new ArrayList<Individual>();
	}
	
	//clone constructor
	Population(Population p) {
		individuals = new ArrayList<Individual>();
		for(int i=0; i<p.size(); i++) {
			add(p.getIndividual(i));
		}
		this.costs = p.costs;
		
	}
	
	//add an individual to this population
	public void add(Individual i) {
		individuals.add(i);
	}
	
	//getter for individual i
	public Individual getIndividual(int i) {
		return individuals.get(i);
	}
	
	//insert individual into ith position
	public void set(int i, Individual ind) {
		individuals.set(i, ind);
	}
	
	//get Index for individual in this population
	public int indexOf(Individual i) {
		return individuals.indexOf(i);
	}
	
	//remove individual from population
	public void remove(Individual i) {
		individuals.remove(i);
	}
	
	//getter for size
	public int size() {
		return individuals.size();
	}

	//perform evaluation for all individuals
	public void performEvaluation() {
		for(int i =0; i<individuals.size(); i++) {
			individuals.get(i).evaluatePerformance(costs);
		}		
	}
	
	//evaluate average performance of all individuals and print
	public void printAveragePerformance() {
		double ave = 0;
		for(int i =0; i<individuals.size(); i++) {
			ave += individuals.get(i).getPerformance();
		}	
		ave /= size();
		System.out.println("average performance: " + ave);
	}
	
	//print all individuals
	public void print() {
		for(int i =0; i<individuals.size(); i++) {
			individuals.get(i).print();
		}
		
	}
	
	//filePathSetter
	public void setCosts(double[][] s) {
		this.costs = s;
	}
	
	//file getter
	public double[][] getCosts() {
		return this.costs;
	}
	
}
