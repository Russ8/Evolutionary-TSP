package evo_assignment1;

import java.io.IOException;

public class EvolutionaryAlgorithm {

	//selector id's
	public static int  SELECTOR_TOURNAMENT = 0;
	public static int  SELECTOR_FITNESS_PROPORTIONAL = 1;
	public static int SELECTOR_DEFAULT = SELECTOR_TOURNAMENT;
	private int currentSelector = SELECTOR_DEFAULT;
	
	//default selector options
	private boolean usingElitism = false;
	private float tournamentProbSelection = 0.5f;
	private int tournamentSize = 5;
	
	//mutation id's
	public static int MUTATION_INSERT = 0;
	public static int MUTATION_SWAP = 1;
	public static int MUTATION_INVERSION = 2;
	public static int MUTATION_DEFAULT = MUTATION_INSERT;
	private int currentMutation = MUTATION_DEFAULT;
	
	//mutation options
	private float mutationProbability = 0.5f;
	
	
	//crossover id's
	public static int CROSSOVER_ORDER = 0;
	public static int CROSSOVER_PMX = 1;
	public static int CROSSOVER_CYCLE = 2;
	public static int CROSSOVER_EDGE_RECOMBINATION = 3;
	public static int CROSSOVER_DEFAULT = CROSSOVER_ORDER;
	private int currentCrossover = CROSSOVER_DEFAULT;
	
	//set data size
	private int dataSize = -1;

	//set file location
	private String file = "";
	
	//generations
	private int generations = 50;
	
	//population size
	private int popSize = 2000;
	
	EvolutionaryAlgorithm() {}
	
	//function that 
	public float performEvolutionaryAlgorithm() {
	     double[][] location = null;
    	try {
			location = Construction.readFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

	    
		//data size check
		if(dataSize == -1) {
			System.out.println("size of data file not specified (use setDataSize(int n)");
			return 0;
		}
		//check file
		if(file == "") {
			System.out.println("file not specified ( use setFile(String s)");
			return 0;			
		}
		
		double probMutation = mutationProbability;
		
		//initialise population randomly
		Population population = new Population(location);
		for(int i=0; i<popSize; i++) {
			Individual ind = new Individual(dataSize);
			population.add(ind);
		}

		//evaluate population
		population.performEvaluation();
		
		double best = 99999999999999.0;
		
		//stop when specified generation number is reached
		int counter = generations;
		int i=0;
		while(i < counter) {

			//parent Selection
			Population parents = null;
			if(this.currentSelector == EvolutionaryAlgorithm.SELECTOR_FITNESS_PROPORTIONAL) {
				parents = Selectors.fitnessProportional(population, true, population.size(), true);
			} else if(this.currentSelector == EvolutionaryAlgorithm.SELECTOR_TOURNAMENT) {
				parents = Selectors.tournament(population, this.tournamentSize, this.tournamentProbSelection, this.usingElitism, population.size(), false);
			}

			//  recombination
			Population children = new Population(location);
			for(int j=0; j<popSize-1; j++) {
				Individual child = CrossoverOperators.performCrossover(this.currentCrossover, parents.getIndividual(j), parents.getIndividual(j+1));
				children.add(child);
			}

			// mutation
			for(int j=0; j<children.size(); j++) {
				if(probMutation > Math.random()) {
					MutationOperators.performMutation(this.currentMutation, children.getIndividual(j));
				}
			}
			
			//evaluate children's performance
			children.performEvaluation();

			//combine parents and children
			Population parentsChildren = new Population(location);
			for(int j=0; j<children.size(); j++) {
				parentsChildren.add(children.getIndividual(j));
			}
			for(int j=0; j<parents.size(); j++) {
				parentsChildren.add(parents.getIndividual(j));
			}	

			//select surviving population
			population = Selectors.fitnessProportional(parentsChildren,true, parents.size(), false);
			population.performEvaluation();
			//record best Individual of surviving population
			Selectors.sortPopulationByPerformance(population);
			double currentBest = population.getIndividual(0).getPerformance();
			if(currentBest < best)
				best = currentBest;

			//print outcomes
			i++;
			if(i == 2000 || i == 5000 || i == 10000 || i == 20000 ) {

				System.out.println("best result after " + i + " generations:");
				System.out.printf(" %f\n", best);
			}
		}
		return (float)best;
		
	}


	//return current selector
	public int getCurrentSelector() {
		return currentSelector;
	}

	//set selector
	public void setCurrentSelector(int currentSelector) {
		this.currentSelector = currentSelector;
	}

	//get current mutator
	public int getCurrentMutation() {
		return currentMutation;
	}

	//set mutator
	public void setCurrentMutation(int currentMutation) {
		this.currentMutation = currentMutation;
	}

	//get current crossover
	public int getCurrentCrossover() {
		return currentCrossover;
	}

	//set crossover
	public void setCurrentCrossover(int currentCrossover) {
		this.currentCrossover = currentCrossover;
	}

	//returns true if elitism is current selected 
	public boolean isUsingElitism() {
		return usingElitism;
	}

	//set to true to enable elitism, false to disable
	public void setUsingElitism(boolean usingElitism) {
		this.usingElitism = usingElitism;
	}

	//get current probability of tournament selection choosing a candidate from pool
	public float getTournamentProbSelection() {
		return tournamentProbSelection;
	}

	//set probability of tournament selection choosing a candidate from pool
	public void setTournamentProbSelection(float tournamentProbSelection) {
		this.tournamentProbSelection = tournamentProbSelection;
	}

	//get current tournament size
	public int getTournamentSize() {
		return tournamentSize;
	}

	//set tournament size
	public void setTournamentSize(int tournamentSize) {
		this.tournamentSize = tournamentSize;
	}

	//get mutation probability
	public float getMutationProbability() {
		return mutationProbability;
	}

	//set mutation probability
	public void setMutationProbability(float mutationProbability) {
		this.mutationProbability = mutationProbability;
	}

	//get size of data
	public int getDataSize() {
		return dataSize;
	}

	//set number of locations in data set
	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}

	//get current file path
	public String getFile() {
		return file;
	}

	//set file path
	public void setFile(String file) {
		this.file = file;
	}

	//getter for generations
	public int getGenerations() {
		return generations;
	}

	//set number of generations to perform
	public void setGenerations(int generations) {
		this.generations = generations;
	}

	//getter for popSize
	public int getPopSize() {
		return popSize;
	}

	//set number of individuals in a population
	public void setPopSize(int popSize) {
		this.popSize = popSize;
	}
}
