package beans;

public class CoefficientParameter {
	private double minValue;
	private double maxValue;
	private double step;

	public CoefficientParameter(double minValue, double maxValue, double step) {
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.step = step;
	}

	public double getMinValue() {
		return minValue;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public double getStep() {
		return step;
	}
}
