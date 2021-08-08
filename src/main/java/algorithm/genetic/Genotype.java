package algorithm.genetic;

import beans.CoefficientParameter;

public class Genotype {
	private int length;

	private double min;
	private double precision;

	public Genotype(CoefficientParameter paramCoef) {
		double min = paramCoef.getMinValue();
		double max = paramCoef.getMaxValue();
		double step = paramCoef.getStep();
		
		if(step <= 0.0) {
			throw new RuntimeException("The value of step time size must to be greater then ZERO");
		}
		if(max <= min + 10 * step) {
			throw new RuntimeException("The value of step time size must to be greater then ZERO");
		}
		
		int numSteps = (int) (1 + (max - min) / step);

		this.min = min;
		this.length = (int) Math.ceil(log2((double)numSteps));
		this.precision = (max - min) / (Math.pow(2.0, length) - 1.0);
	}
	
	public int getLength() { return length;}

	public double getMin() { return min; }

	public double getPrecision() { return precision; }

	public double decodeGenotype(byte[] subGenes) {
		double base10 = 0.0;
		int pow = 1;
		
		for(int i = 1; i < length; ++i) {
			pow *= 2.0;
			base10 += subGenes[i] * pow;
		}
		base10 += subGenes[0];
		
		return (min + base10 * precision);
	}
	
	private static double log2(double a) { return Math.log(a)/Math.log(2.0); }
}
