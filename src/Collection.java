import java.util.Arrays;
import java.util.Random;

public class Collection {

	int tournamentSize =5;
	int collectionSize = 10000;
	double mutationRatio = 0.02;
	boolean increaseMutation = false;
	double selectionRatio = 0.85;
	double elitismRatio = 0.2;
	String targetString = "Hello, world!";
	Random random = new Random();
	GeneticAlg[] arrayCollection;
	
	public Collection() {
		this.arrayCollection = generateCollection();
		
	}
		
	public GeneticAlg[] generateCollection() {
		GeneticAlg [] temp;
		temp = new GeneticAlg[collectionSize];
		for (int i = 0; i < collectionSize; i++) {
			temp[i] = generateStrings();
		}
		Arrays.sort(temp);
		return temp;
	}
	
	
	
	
	public void naturalSelection() {
		GeneticAlg[] temporaryList = new GeneticAlg[arrayCollection.length];
		int elitismPoint = (int) Math.round(arrayCollection.length * elitismRatio);
		for (int i = 0; i <= elitismPoint; i++) {
			temporaryList[i] = arrayCollection[i];
			
		}
		
		while (elitismPoint < temporaryList.length) {
			
			
			if(getFittest().fitness <=5 && increaseMutation == false) {
				mutationRatio = mutationRatio * 10;
				increaseMutation = true;
			}
			
			if (random.nextDouble() <= selectionRatio) {
				
				GeneticAlg[] parents = new GeneticAlg[2];
				parents[0] = selectParent();
				parents[1] = selectParent();
				GeneticAlg[] children = parents[0].crossover(parents[1]);
				
				if (random.nextDouble() <= mutationRatio) {
					temporaryList[elitismPoint++] = children[0].mutation();
				} else {
					temporaryList[ elitismPoint++] = children[0];
				}
				
				if (elitismPoint < temporaryList.length) {
					if (random.nextDouble() <= mutationRatio) {
						temporaryList[elitismPoint] = children[1].mutation();
					} else {
						temporaryList[elitismPoint] = children[1];
					}
				}
			} else {
				if (random.nextDouble() <= mutationRatio) {
					temporaryList[elitismPoint] = arrayCollection[elitismPoint].mutation();
				} else {
					temporaryList[elitismPoint] = arrayCollection[elitismPoint];
				}
			}
	
			++ elitismPoint;
		}

		Arrays.sort(temporaryList);
		arrayCollection = temporaryList;

		
	}

	public GeneticAlg getFittest() {
		return arrayCollection[0];
	}
	
	public int Fitness() {
		return arrayCollection[0].fitness;
		
	}
	
	public GeneticAlg generateStrings() {
		char[] newCharacterArray = new char[targetString.length()];
		for (int i = 0; i < newCharacterArray.length; i++) {
			newCharacterArray[i] = (char) (random.nextInt(90) + 32);
		}
		return new GeneticAlg(String.valueOf(newCharacterArray));
	}

		
	private GeneticAlg selectParent() {
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
