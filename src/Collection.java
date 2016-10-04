import java.util.Arrays;
import java.util.Random;

public class Collection {

	/**
	 * Collection class creates the population of different strains, carries out the evolution process and runs the tournament
	 * in order to determine what parents we should be taking forward into the next generation. This class also uses elitism
	 * to copy a set amount of the better strains over to the next generation to ensure that we reach the target quicker. An 
	 * instance of this class is created within the Main class. Below are the variables defining the sizes and rates for the
	 * evolution process.
	 *
	 */
	
	int tournamentSize = 5;
	int collectionSize = 1000;
	double mutationRatio = 0.02;
	boolean increaseMutation = false;
	boolean increaseMutation2 = false;
	double selectionRatio = 0.85;
	double elitismRatio = 0.2;
	String targetString = "Hello, world!";
	Random random = new Random();
	GeneticAlg[] arrayCollection;

	public Collection() {
		/**
		 * Class instance calls the generate collection method which populates the array arrayCollection with GeneticAlg
		 * class objects. These object contain the fitness of that object as well as the String that the object currently has
		 * in the form of a char array.
		 */
		this.arrayCollection = generateCollection();

	}

	public GeneticAlg[] generateCollection() {
		/** 
		 * This method creates a temporary array of the population and passes it back to be stored in the arrayCollection
		 * array, it calls the generate Strings method which populates the string within the object
		 */
		GeneticAlg[] temp;
		temp = new GeneticAlg[collectionSize];
		for (int i = 0; i < collectionSize; i++) {
			temp[i] = generateStrings();
		}
		
		return temp;
	}

	public void naturalSelection() {
		/**
		 * The evolution process creates a temporary array of GeneticAlgs, identifies an elitism point and copies the
		 * best versions over to the temporary array so that we have a good population for the next generation.
		 */
		
		GeneticAlg[] temporaryList = new GeneticAlg[arrayCollection.length];
		int elitismPoint = (int) Math.round(arrayCollection.length * elitismRatio);
		for (int i = 0; i <= elitismPoint; i++) {
			temporaryList[i] = arrayCollection[i];

		}

		/**
		 * While there is still strains to be copied
		 */
		
		while (elitismPoint < temporaryList.length) {
/**
 * Asses the best fitness, if fitness is <=5 increase the mutation to try and make the small changes needed
 * to reach the goal, however only do this once as we don't want mutation smoothing the over factors too much and becoming
 * too significant as too much mutation would lead us away from the target.
 */
			
			
			
			if (getFittest().fitness <= 5 && increaseMutation == false) {
				mutationRatio = mutationRatio * 10;
				increaseMutation = true;
			}
			
			if (getFittest().fitness <=1 && increaseMutation2 == false) {
				mutationRatio = mutationRatio * 25;
				increaseMutation2 = true;
			}

			
			/**
			 * Checks to see if crossover should happen by comparing a random double to the rate set at the start of the class
			 * This is done as crossover should only happen a percentage of the time and not always.
			 */
			
			if (random.nextDouble() <= selectionRatio) {

				/**
				 * If crossover is to happen, create a parents list, call our tournament two times in order to get the two 
				 * strains that are most suited to be parents, then crossover those parents using the crossover method
				 * Available in the GeneticAlg class which will then populate a children array which we then check to see
				 * if mutation should happen against.
				 */
				
				GeneticAlg[] parentVersions = new GeneticAlg[2];
				parentVersions[0] = tournament();
				parentVersions[1] = tournament();
				GeneticAlg[] childrenVersions = parentVersions[0].crossover(parentVersions[1]);

				if (random.nextDouble() <= mutationRatio) {
					/**
					 * If the next double indicates mutation should happen when checked against the mutationRatio
					 * then mutate the first childVersion that we have and add it to the temporaryList of the next
					 * generation, else mutation should not happen and it gets added to the temporaryList
					 */
					
					temporaryList[elitismPoint++] = childrenVersions[0].mutation();
				} else {
					temporaryList[elitismPoint++] = childrenVersions[0];
				}

				if (elitismPoint < temporaryList.length) {
					if (random.nextDouble() <= mutationRatio) {
						/**
						 * If the next double indicates mutation should happen when checked against the mutationRatio
						 * then mutate the second childVersion that we have and add it to the temporaryList of the next
						 * generation, else mutation should not happen and it gets added to the temporaryList
						 */
						
						temporaryList[elitismPoint] = childrenVersions[1].mutation();
					} else {
						temporaryList[elitismPoint] = childrenVersions[1];
					}
				}
				
			} 
			/**
			 * If crossover should not happen, then we do the mutation check as the are not mutually inclusive, check to see
			 * if the next double results in a double that will trigger mutation, if so mutate the GeneticAlg assigned at
			 * the elitismPoint and then add it to the temporaryList, however the mutationRatio may not trigger and the 
			 * else statement will assign it to the temporaryList as normal
			 */
			
			else {
				if (random.nextDouble() <= mutationRatio) {
					temporaryList[elitismPoint] = arrayCollection[elitismPoint].mutation();
				} else {
					temporaryList[elitismPoint] = arrayCollection[elitismPoint];
				}
			}
			/**
			 * Iterating the point forward
			 */
			++elitismPoint;
		}

		
		/**
		 * Arrays.Sort compares the list and then sorts it in descending order of fitness, it then moves the tempList
		 * into the arrayCollection
		 */
		Arrays.sort(temporaryList);
		arrayCollection = temporaryList;

	}

	public GeneticAlg getFittest() {
		/**
		 * returns the alpha version in order to determine how close we are to the Target
		 */
		return arrayCollection[0];
	}

	public int Fitness() {
		/**
		 * Returns the fitness of the alpha version
		 */
		return arrayCollection[0].fitness;

	}

	public GeneticAlg generateStrings() {
		/**
		 * Generates strings by by creating a character array and population the array with random ASCII values
		 * generated by the random. The strings are of fixed length to the target string to avoid complications.
		 */
		char[] newCharacterArray = new char[targetString.length()];
		for (int i = 0; i < newCharacterArray.length; i++) {
			newCharacterArray[i] = (char) (random.nextInt(90) + 32);
		}
		return new GeneticAlg(String.valueOf(newCharacterArray));
	}

	private GeneticAlg tournament() {
		/**
		 * Creates a tournament which gets called twice in order to determine the fittest parent to take forward, takes a
		 * random point within the arrayCollection and assigns it as the best, then takes another point and then checks in a 
		 * for loop up to the tournament size if the point that it has is the fitter version than the parent it already has, and
		 * if it is then assign that version to be the parent. It then returns the parent which is used in the evolution stage 
		 * to perform crossover
		 */
		
		GeneticAlg parent;
		parent = arrayCollection[random.nextInt(arrayCollection.length)];
		for (int j = 0; j < tournamentSize; j++) {
			int point = random.nextInt(arrayCollection.length);
			if (arrayCollection[point].compareTo(parent) < 0) {
				parent = arrayCollection[point];
			}
		}

		return parent;
	}

}
