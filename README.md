# Evolutionary TSP Library

The travelling salesman problem (TSP) asks the following question: "Given a list of cities and the distances between each pair of cities, what is the shortest possible route that visits each city and returns to the origin city?" It is an NP-hard problem in combinatorial optimization, important in operations research and theoretical computer science.   

The repository is a java library designed to solve the tsp problem using various evolutionary algorithm configurations. This evolutionary algorithm (EA) solves the problem by employing an evolutionary strategy to an initial population of candidate solutions. Over many iterations, the population quality improves. This process involes performing mutations, crossover and selection operations on the population each iteration, as can be observed in biological evolution.   

<img src="/images/tsp.png?raw=true" width="400">
## Evolutionary Algorithm Instructions.   
The evolutionary algorithm implemented by this library is handled by the EvolutionaryAlgorithm object. This object holds a configuration for the algorithm and is used to perform the algorithm. It can be initiated using the following constructor:  

```EvolutionaryAlgorithm();```

Given an EvolutionaryAlgorithm object named "ea", two method calls on ea are required before the algorithm can perform using its default configuration. Which are:   

```void ea.setFile(String path);```
Sets the location of the testFile



```void ea.setDataSize(int IndividualNumber);```  
sets the dimension number of the test file 



Then, run the algorithm using the following:   
```float ea.performEvolutionaryAlgorithm();```   
which returns the best Individual's performance.



### The following public methods perform additional configurations on an EvolutionaryAlgorithm object. 

```void ea.setGenerations(int);```  
Set generation count. Default value is 50  
    
    
```void ea.setPopSize(int);```  
Set population size. Default value is 50  

```void ea.setCurrentMutation(int mutationID);```
Sets the mutation method used in the evolutionary algorithm, Acceptable values of mutationID are:  
```static int EvolutionaryAlgorithm.MUTATION_SWAP```  
```static int EvolutionaryAlgorithm.MUTATION_INSERT```  
```static int EvolutionaryAlgorithm.MUTATION_INVERSION```  
      -Default value is EvolutionaryAlgorithm.MUTATION_INSERT

```void ea.setCurrentCrossover(int crossoverID);```   
-Sets the crossover method used in the evolutaionry algorithm. Acceptable values of crossoverID are:   
```static int EvolutionaryAlgorithm.CROSSOVER_ORDER```   
```static int EvolutionaryAlgorithm.CROSSOVER_PMX```  
```static int EvolutionaryAlgorithm.CROSSOVER_CYCLE```   
```static int EvolutionaryAlgorithm.CROSSOVER_EDGE_RECOMBINATION```  
-Default value is EvolutionaryAlgorithm.CROSSOVER_ORDER  

```void ea.setCurrentSelector(int selectorID);```  
-Sets the selector method used in the evolutionary algorithm, Acceptable values of selectorID are:  
```static int  EvolutionaryAlgorithm.SELECTOR_TOURNAMENT```  
```static int  EvolutionaryAlgorithm.SELECTOR_FITNESS_PROPORTIONAL```  
-Default value is EvolutionaryAlgorithm.SELECTOR_TOURNAMENT  


```void ea.setUsingElitism(boolean);```   
-Setting to true will cause the best individual of each Generation (if using fitness proportional) or each pool (if using tournament) to be guarenteed survival. Default value is false.   


```void ea.setTournamentProbSelection(float p);```  
-If using tournament selector, a selection weight is applied. Each individual of each pool is selected with probability p*(1-p)^i, where i is the rank of the Individual in a pool based on lowest performance. Default value is 0.5.  

```void ea.setTournamentSize(int);```  
-If using tournament selector, sets the pool size. Default value is 5.  


A file, Example.java is provided detailing the method calls required to setup the three configurations developed in our report, and then print some results.  
