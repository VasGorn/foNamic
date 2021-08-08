package algorithm.genetic;

import interfaces.ISelection;

public class SelectionTournamentImp implements ISelection {
private int tournamentSize;
	
	public SelectionTournamentImp(int tournamentSize) {
		this.tournamentSize = tournamentSize;
	}
	
	@Override
	public Population getParentPopulation(Population pop) {
		Population parentPop = new Population(pop.size(),false);
		for(int j = 0; j < pop.size(); ++j) {
			Population tournament = new Population(this.tournamentSize, false);
			for(int i = 0; i < this.tournamentSize; ++i) {
				int randomIndivIndex = (int) (Math.random() * pop.size());
				tournament.saveIndividual(i, pop.getIndividual(randomIndivIndex));
			}
			Individual fittest = tournament.getFittest();
			parentPop.saveIndividual(j, fittest);
		}
		return parentPop;
	}
}
