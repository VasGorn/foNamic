package beans;

import java.util.ArrayList;
import java.util.List;

public class RefFunction {

	private double inputValue;
	private ArrayList<Double> arrayTime;
	private ArrayList<Double> arrayOutput;
	private double stepTimeSize;
	
	public RefFunction(List<Double> outputValues, double inputValue, double stepTimeSize) {
		int size = outputValues.size();
		this.arrayOutput = new ArrayList<Double>(size);
		this.inputValue = inputValue;
		this.arrayTime = new ArrayList<Double>(size);
		this.stepTimeSize = stepTimeSize;

		for(int i = 0; i < size; ++i) {
			this.arrayOutput.add(outputValues.get(i));
			this.arrayTime.add(i*stepTimeSize);
		}
	}

	public double getInputValue() {
		return inputValue;
	}

	public ArrayList<Double> getArrayTime() {
		return arrayTime;
	}

	public ArrayList<Double> getArrayOutput() {
		return arrayOutput;
	}

	public double getStepTimeSize() {
		return stepTimeSize;
	}


}
