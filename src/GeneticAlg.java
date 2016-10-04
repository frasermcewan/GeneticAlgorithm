import java.util.Random;

/**
 * 
 * This class is used as an object class which implements Comparable in order to allow it to be compared
 * against other objects of the same type. This is used to determine which object is the fitter when compared against
 * each other. The object takes in a String which is then compared to the targetString and the object is assigned a fitness
 * score. The class also contains methods that allow mutation, crossover and fitness comparison
 *
 */

public class GeneticAlg implements Comparable<GeneticAlg> {
	Random random = new Random();
	char[] chromoIn;
	int fitness = 0;
	int stringLength;
	char[] targetStringinArray = "Hello, world!".toCharArray();

	public GeneticAlg(String initialString) {
		/**
		 * Takes in the string and calls the fitness function to determine how close it is to target
		 */
		this.stringLength = initialString.length();
		this.chromoIn = toCharArray(initialString);
		setFitness(chromoIn);
	}

	public String getVersion() {
		/**
		 * Accessory method in order to return the String value of the current char array
		 */
		return String.valueOf(chromoIn);
	}

	public void setFitness(char[] chromo) {
		/**
		 * Called by instance of the class, works out the fitness of the Object by comparing the 
		 * numerical ASCII values against each other. Maths.abs is used to ensure non negative values
		 */
		for (int i = 0; i < chromo.length; i++) {
			fitness += Math.abs(((int) chromo[i]) - ((int) targetStringinArray[i]));
		}

	}

	public int returnFitness() {
		/**
		 * Accessory method to return fitness 
		 */
		return fitness;
	}

	public char[] toCharArray(String In) {
		/**
		 * Converts the string to a character array for use within the object class, is used as part of determining 
		 * fitness of the object
		 */
		char[] convertorArray;
		convertorArray = In.toCharArray();
		return convertorArray;
	}

	public GeneticAlg mutation() {
		/**
		 * Method for mutation, takes the current char array and stores it in a temporary char array. Then takes a random
		 * point of length of that character array. Then creates a new int value of a random ASCII character and then assigns
		 * the char value to that random point within the temporary Array and returns the updated mutated version in the form
		 * of a new Object
		 */
		char[] mutArray = chromoIn;
		int randomPoint = random.nextInt(mutArray.length);
		int newValue = (random.nextInt(90) + 32);
		mutArray[randomPoint] = (char) (newValue);
		return new GeneticAlg(String.valueOf(mutArray));

	}

	public GeneticAlg[] crossover(GeneticAlg partner) {
		/**
		 * Takes in another Object of Genetic Alg and stores its own String and the other objects string in a char array.
		 * It then works out a random pivotPoint and creates two children Arrays of the same length as the parents in order
		 * to store the crossover.
		 * 
		 */
		char[] parent1 = chromoIn;
		char[] parent2 = partner.chromoIn;
		int pivotPoint = random.nextInt(parent1.length);
		char[] child1 = new char[stringLength];
		char[] child2 = new char[stringLength];

		/**
		 * For everything up to and including the pivotPoint, populate the children with the characters of parents 1/2 for
		 * children 1/2 and then repeat the reverse for the second child. I rather stupidly started my second for loop with
		 * j=0 which wiped out the first portion of each child, however fixed this problem and it helped improve my number of
		 * generations by the tens of thousands! 
		 */
		
		for (int i = 0; i <= pivotPoint; i++) {
			child1[i] = parent1[i];
			child2[i] = parent2[i];

		}

		for (int j = pivotPoint; j < child1.length; j++) {
			child1[j] = parent2[j];
			child2[j] = parent1[j];
		}

		/**
		 * Return the two children as new instances of GeneticAlg objects which are used in the evolution stage
		 * to add to the population
		 */
		return new GeneticAlg[] { new GeneticAlg(String.valueOf(child1)), new GeneticAlg(String.valueOf(child2)) };
	}

	@Override
	public int compareTo(GeneticAlg o) {
		/**
		 * Implementation of compare to which is part of the implementation of Comparable. This allows different objects
		 * to compare themselves and determine which one has the best fitness in order to choose that as a potential parent
		 */
		if (fitness < o.fitness) {
			return -1;
		} else if (fitness > o.fitness) {
			return 1;
		} else {
		return 0;
		}

	}

}
