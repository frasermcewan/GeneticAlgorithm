import java.util.Random;

public class GeneticAlg implements Comparable<GeneticAlg> {
	Random random = new Random();
	char[] chromoIn;
	int fitness = 0;
	int stringLength;
	char[] targetStringinArray = "Hello, world!".toCharArray();

	public GeneticAlg(String initialString) {
		this.stringLength = initialString.length();
		this.chromoIn = toCharArray(initialString);
		setFitness(chromoIn);
	}

	public String getVersion() {
		return String.valueOf(chromoIn);
	}

	public void setFitness(char[] chromo) {
		for (int i = 0; i < chromo.length; i++) {
			fitness += Math.abs(((int) chromo[i]) - ((int) targetStringinArray[i]));
		}

	}

	public int returnFitness() {
		return fitness;
	}

	public char[] toCharArray(String In) {
		char[] convertorArray;
		convertorArray = In.toCharArray();
		return convertorArray;
	}

	public GeneticAlg mutation() {
		char[] mutArray = chromoIn;
		int randomPoint = random.nextInt(mutArray.length);
		int newValue = (random.nextInt(90) + 32);
		mutArray[randomPoint] = (char) (newValue);
		return new GeneticAlg(String.valueOf(mutArray));

	}

	public GeneticAlg[] crossover(GeneticAlg partner) {
		char[] parent1 = chromoIn;
		char[] parent2 = partner.chromoIn;
		int pivotPoint = random.nextInt(parent1.length);
		char[] child1 = new char[stringLength];
		char[] child2 = new char[stringLength];

		for (int i = 0; i <= pivotPoint; i++) {
			child1[i] = parent1[i];
			child2[i] = parent2[i];

		}

		for (int j = pivotPoint; j < child1.length; j++) {
			child1[j] = parent2[j];
			child2[j] = parent1[j];
		}

		return new GeneticAlg[] { new GeneticAlg(String.valueOf(child1)), new GeneticAlg(String.valueOf(child2)) };
	}

	@Override
	public int compareTo(GeneticAlg o) {
		if (fitness < o.fitness) {
			return -1;
		} else if (fitness > o.fitness) {
			return 1;
		}

		return 0;

	}

}
