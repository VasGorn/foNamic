package model.math;

import interfaces.ITransferFunction;

public class TranserFunctionIntImp implements ITransferFunction{
	//--- a1*D*I + a0*D^mu*I + I = K ---
	private FOIntegral foIntegral;
	
	private double u1;
	private double ui0;
	
	private double a0;
	private double a1;
	private double K;
	
	public TranserFunctionIntImp(FOIntegral foIntegral, double a0, double a1, double K) {
		this.foIntegral = foIntegral;

		this.u1 = 0.0;
		this.ui0 = 0.0;
		
		this.a0 = a0;
		this.a1 = a1;
		this.K = K;
	}

	@Override
	public void setParameters(double[] paramArray) {
		this.u1 = 0.0;
		this.ui0 = 0.0;
		
		this.K = paramArray[Const.K_INDEX];
		this.a0 = paramArray[Const.A0_INDEX];
		this.a1 = paramArray[Const.A1_INDEX];
		
		double mu = paramArray[Const.MU_INDEX];
		foIntegral.setNewCoefRiem(mu);
		foIntegral.resetState();
	}

	@Override
	public double getValue(double inputValue) {
		double outputValue = 0.0;
		double dt = foIntegral.getDt();
		
		double du = inputValue - u1; 
		double ui = (ui0 * a1 + K * du * dt) / (a1 + dt);
		ui0 = ui;
		double fracIntegralValue = foIntegral.getValue(ui);
		u1 = a0 / K * fracIntegralValue;
		
		outputValue = ui;
		return outputValue;
	}
	
}
