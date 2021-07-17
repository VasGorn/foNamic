package algorithm.genetic;

import beans.ReferenceFunction;
import interfaces.ITransferFunction;

public final class Fitness {
	private static ReferenceFunction data;
	private static ITransferFunction trFunc;
	
	private Fitness(){
	}
	
	public static void setRefFunction(ReferenceFunction refFunction) {
		data = refFunction;
	}

	public static void setTransferFuntion(ITransferFunction transferFunction) {
		trFunc = transferFunction;
	}
	
	public static double getValue(Individual individual) {
		double fitness = 0.0;
		
		double sumError = 0.0;
		double squareError = 0.0;
		
		int n_point = data.getArrayOutput().size(); 
		
		for(int i = 0; i < n_point; ++i) {
			double modelValue = trFunc.getValue(data.getInputValue());
			double expValue = data.getArrayOutput().get(i);
			
			squareError = (modelValue - expValue) * (modelValue - expValue);
			sumError += squareError;
		}
		
		fitness = sumError / n_point;
		return fitness;
	}

}
