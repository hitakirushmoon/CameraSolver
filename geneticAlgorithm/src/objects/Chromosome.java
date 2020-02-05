package objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Chromosome {
	Coordinates[] gene;
	double fitness;
	static final Coordinates target = new Coordinates(500, 250);

	static final double range = 0.05;
	static final Random rand = new Random();

	public Chromosome(int length) {
		super();
		this.gene = new Coordinates[length];
		for (int i = 0; i < length; i++) {
			gene[i] = new Coordinates(nextUniformGaussian() * range, nextUniformGaussian() * range);
		}

	}

	public static ArrayList<Chromosome> generateSpecimen(int length, int ammount) {
		ArrayList<Chromosome> lmeo = new ArrayList<Chromosome>();
		for (int i = 0; i < ammount; i++) {
			lmeo.add(new Chromosome(length));
		}
		return lmeo;
	}

	public static ArrayList<Chromosome> selectMostFit(ArrayList<Chromosome> candidates, int ammount) {
		ArrayList<Chromosome> selected = new ArrayList<Chromosome>(candidates);
		selected.sort((a, b) -> (int) Math.signum(a.fitness - b.fitness));
		return new ArrayList<Chromosome>(selected.subList(0, ammount));
	}

	public static ArrayList<Chromosome> selectMostDiverse(ArrayList<Chromosome> candidates,
			ArrayList<Chromosome> alreadyChoosen, int ammount) {
		candidates.sort((a, b) -> (int) Math.signum(a.diversity(candidates) - b.diversity(candidates)));
		ArrayList<Chromosome> selected = new ArrayList<Chromosome>(candidates);
		selected.sort((a, b) -> (int) Math.signum(a.fitness - b.fitness));
		return new ArrayList<Chromosome>(selected.subList(0, ammount));
	}

	public double nextUniformGaussian() {
		return rand.nextDouble() * 2 - 1;
	}

	public Chromosome(Coordinates[] gene) {
		super();
		this.gene = gene;
	}

	public Coordinates[] absoluteMutate(double percentage) {
		for (int i = 0; i < gene.length; i++) {
			if (random(percentage)) {
				gene[i].x = nextUniformGaussian() * range;
				gene[i].y = nextUniformGaussian() * range;
			}
		}
		return gene;
	}

	public Coordinates[] relativeMutate(double percentage, double proportion) {
		for (int i = 0; i < gene.length; i++) {
			if (random(percentage)) {
				gene[i].x = MathUtils.limit(gene[i].x + rand.nextGaussian() * proportion * range, -range, range);
				gene[i].y = MathUtils.limit(gene[i].y + rand.nextGaussian() * proportion * range, -range, range);
			}
		}
		return gene;
	}

	public static Chromosome uniformCrossover(Chromosome a, Chromosome b, double chance) {
		Coordinates[] newGene = new Coordinates[a.gene.length];
		for (int i = 0; i < newGene.length; i++) {
			if (random(chance)) {
				newGene[i] = a.gene[i].clone();
			} else {
				newGene[i] = b.gene[i].clone();
			}
		}
		return new Chromosome(newGene);
	}

	public static Chromosome spliceCrossover(Chromosome a, Chromosome b, int segments) {
		int half = a.gene.length / 2;
		int index = 0;
		Coordinates[] newGene = new Coordinates[a.gene.length];
		while (half > 0 && segments > 1) {
			int spliceLength = rand.nextInt(half) + 1;
			half -= spliceLength;
			int lmeo = a.gene.length - half - spliceLength - index;
			int spliceIndex = rand.nextInt(lmeo);
			for (int i = index; i < index + spliceIndex; i++) {
				newGene[i] = a.gene[i].clone();
			}
			index += spliceIndex;
			for (int i = index; i < index + spliceLength; i++) {
				newGene[i] = b.gene[i].clone();
			}
			index += spliceLength;
			segments--;
		}
		for (int i = index; i < a.gene.length - half; i++) {
			newGene[i] = a.gene[i].clone();
		}
		for (int i = a.gene.length - half; i < a.gene.length; i++) {
			newGene[i] = b.gene[i].clone();
		}
		return new Chromosome(newGene);
	}

	public static Chromosome crossOver(Chromosome a, Chromosome b) {
		Coordinates[] newGene = new Coordinates[a.gene.length];
		int spliceIndex = rand.nextInt(a.gene.length);
		for (int i = 0; i < spliceIndex; i++) {
			newGene[i] = a.gene[i].clone();
		}
		for (int i = spliceIndex; i < a.gene.length; i++) {
			newGene[i] = b.gene[i].clone();
		}
		return new Chromosome(newGene);
	}

	public double evaluate(int trials) {
		fitness = 0;
		for (int i = 0; i < trials; i++) {
			AccelerationControlledRobot r = new AccelerationControlledRobot(this);
			for (int t = 0; t < gene.length; t++) {
				r.update();
			}
			fitness += fitness(r);
		}
		fitness /= trials;
		return fitness;
	}

	public double fitness(AccelerationControlledRobot r) {
		return r.position.distanceTo(target);
	}

	public double diversity(ArrayList<Chromosome> c) {
		double d = 0;
		for (Chromosome b : c) {
			d += diversity(b);
		}
		return Math.sqrt(d);
	}

	public double diversity(Chromosome b) {
		double d = 0;
		for (int i = 0; i < gene.length; i++) {
			d += gene[i].squareTo(b.gene[i]);
		}
		return d;
	}

	public static boolean random(double chance) {
		return chance > rand.nextDouble();
	}

	public String toString() {
		return Arrays.toString(gene);
	}

}
