package objects;

import java.util.ArrayList;

public class Main {
	static int poolSize = 100, selectionSize = 10, diverseSize = 5,
			needToGenerate = poolSize - selectionSize - diverseSize, generations = 10;

	public static void main(String[] args) {
		ArrayList<Chromosome> lmao = Chromosome.generateSpecimen(500, poolSize);
		for (int i = 0; i < 20; i++) {
			gen(lmao);
		}
		Chromosome best = lmao.get(0);
		System.out.println(best.evaluate(20));
//		System.out.println(lmao.get(0));
	}

	public static void gen(ArrayList<Chromosome> lmao) {
		for (Chromosome c : lmao) {
			c.evaluate(10);
		}
		ArrayList<Chromosome> selected = (ArrayList<Chromosome>) Chromosome.selectMostFit(lmao, selectionSize);
		selected.addAll(Chromosome.selectMostDiverse(lmao, selected, diverseSize));
		lmao.clear();
		lmao.addAll(selected);
		selected.clear();
		while (selected.size() < needToGenerate) {
			int a = Chromosome.rand.nextInt(lmao.size());
			int b = Chromosome.rand.nextInt(lmao.size());
			Chromosome cross = Chromosome.spliceCrossover(lmao.get(a), lmao.get(b), 2);
			cross.relativeMutate(0.2, 0.2);
			selected.add(cross);
		}
		lmao.addAll(selected);
	}
}
