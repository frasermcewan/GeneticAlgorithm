
public class Main {

	public static void main(String[] args) {
		
		/**
		 * Creates a new population, gets the fittest version and then evolves the population while the fitness does
		 * not equal zero while printing out the alpha version and its level of fitness as it procedes through evolution
		 * layers. With the current parameters set in the population class is takes between 50 and 110 evolutions to reach
		 * the target
		 */

		Collection col = new Collection();
		GeneticAlg alpha = col.getFittest();
		int i = 0;
		int j = 0;

		while (alpha.returnFitness() != 0) {
			System.out.println(alpha.getVersion() + ' ' + i + " Fitness  " + alpha.fitness);
			col.naturalSelection();
			alpha = col.getFittest();
			i++;
		}

		System.out.println("Final Version " + i + ": " + alpha.getVersion() + "\n");
		
		
		
		/**
		 * Below is the code to implement a random String generation in order to see how long it would take to randomly
		 * get Hello, world!, I set the limit for it at 100,000 attempts but as of yet it fails to reach this.
		 */
		
		// System.out.println("Starting Random Analysis \n\n\n");
		// GeneticAlg zeta = col.generateStrings();
		//
		// while(j <= 100000 && (!zeta.equals("Hello, world!"))) {
		// System.out.println(zeta.getVersion() + ' ' + i + " Fitness " +
		// zeta.fitness + " " + j);
		// zeta = col.generateStrings();
		// j++;
		// }

	}

}
