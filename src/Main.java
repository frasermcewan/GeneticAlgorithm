
public class Main {

	public static void main(String[] args) {

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
		// System.out.println("Starting Random Analysis \n\n\n");
		// GeneticAlg zeta = col.generateStrings();
		//
		// while(j <= 10000 && (!zeta.equals("Hello, world!"))) {
		// System.out.println(zeta.getVersion() + ' ' + i + " Fitness " +
		// zeta.fitness + " " + j);
		// zeta = col.generateStrings();
		// j++;
		// }

	}

}
