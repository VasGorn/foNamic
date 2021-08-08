package model.math;

public class FOIntegral {
	private final int n_max;
	private final double dt;
	
	private double[] inArray;
	private double[] coefRiem;
	
	private int ci;
	
	public FOIntegral(double mu, double dt, int n_max) {
		if(mu <= 0 || mu >= 2) {
			throw new RuntimeException("Order of integral must be in range from 0 to 2");
		}
		
		this.n_max = n_max;
		this.dt = dt;
		this.ci = 0;

		this.inArray = new double[n_max];
		this.coefRiem = calculateCoefRiem(mu);
	}
	
	public double getValue(double x) {
		double value = 0.0;
		inArray[ci] = x;
		++ci;
		for(int i = 0; i < ci; ++i) {
			value += inArray[ci - i - 1] * coefRiem[i];
		}
		
		return value;
	}
	
	public double[] getCoefRiem() {
		return coefRiem;
	}
	
	public void setNewCoefRiem(double mu) {
		this.coefRiem = calculateCoefRiem(mu);
	}
	
	public void resetState() {
		this.ci = 0;
	}
	
	public double getDt() {
		return dt;
	}

	private double[] calculateCoefRiem(double mu) {
		double[] coef = new double[n_max];
		double gamma = gammaOf(mu + 1.0);
		for(int i = 0; i < n_max; ++i) {
			coef[i] = Math.pow(dt, mu) / gamma *
					(Math.pow((i + 1.0), mu) - Math.pow(i, mu));
		}
		return coef;
	}

	public static double gammaOf(double x) {
		double gamma;
	    double[] c = new double[8];
	    c[0] = 2.506628274631;
	    c[1] = 1.000000000019;
	    c[2] = 76.1800917294715;
	    c[3] = -86.5053203294168;
	    c[4] = 24.0140982408309;
	    c[5] = -1.23173957245015;
	    c[6] = 1.20865097386618e-03;
	    c[7] = -5.395239384953e-06;

	    gamma = Math.exp((x + 0.5)*Math.log(x + 5.5)-(x + 5.5) +
	              Math.log(c[0] * (c[1] + c[2] / (x + 1) + c[3] / (x + 2) + c[4] / (x + 3) +
	                c[5] / (x + 4) + c[6] / (x + 5) + c[7] / (x + 6)) / x));

        return gamma;
	}
}
