package threads;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

import algorithm.genetic.Algorithm;
import algorithm.genetic.Fitness;
import algorithm.genetic.Individual;
import algorithm.genetic.Population;
import beans.ChampionRecord;
import model.math.Const;

public class EvolveProcess implements Runnable{
	private Thread thread;
	private AtomicBoolean exit;

	private Algorithm algorithm;
	private Population population;
	
	private List<ChampionRecord> champions;
	private Queue<ChampionRecord> qSendToClient;
	
	public EvolveProcess(Algorithm algorithm, Population population) {
		this.algorithm = algorithm;
		this.population = population;
		this.exit = new AtomicBoolean(false);
		this.champions = new LinkedList<>();
		this.qSendToClient = new LinkedList<>();
	}

	@Override
	public void run() {
		int generationCount = 1;
		for(int i = 0; i < 1000; ++i) {
			if(exit.get()) break;
			Individual champion = population.getFittest();
			ChampionRecord bestInPopulation = new ChampionRecord(generationCount, 
					champion);
			champions.add(bestInPopulation);
			qSendToClient.add(bestInPopulation);
			
			System.out.println("Generation: " + generationCount +
					" Fittest: " + champion.getFitness() + 
					" K: " + champion.getDecodeArray()[Const.K_INDEX] +
					" mu: " + champion.getDecodeArray()[Const.MU_INDEX] +
					" a0: " + champion.getDecodeArray()[Const.A0_INDEX] );
			
			population = algorithm.evolvePopulation(population);
			++generationCount;
			
		}
		System.out.println("Thread ends");
	}
	
	public void start() {
		if(thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public void stop() {
		exit.set(true);;
	}

	public Queue<ChampionRecord> getqSendToClient() {
		return qSendToClient;
	}

}
