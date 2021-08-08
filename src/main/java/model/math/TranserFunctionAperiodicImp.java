package model.math;

import interfaces.ITransferFunction;

public class TranserFunctionAperiodicImp implements ITransferFunction {
	//--- a0*D^mu*I + I = K
		private double hnK;
		private double hnB0;
		private double b0 = 1.0;
		
		private FOIntegral foIntegral;
		
		private double y0;
		
		public TranserFunctionAperiodicImp(FOIntegral foIntegral, double a0, double K) {
			this.foIntegral = foIntegral;

			this.y0 = 0.0;
			
			double dt = foIntegral.getDt();
			double znam = a0 * dt * dt;
			this.hnK = K * dt * dt / znam;
			this.hnB0 = b0 * dt * dt / znam;
		}
		
		@Override
		public void setParameters(double[] paramArray) {
			y0 = 0.0;
			
			double dt = foIntegral.getDt();
			double K = paramArray[Const.K_INDEX];
			double mu = paramArray[Const.MU_INDEX];
			double a0 = paramArray[Const.A0_INDEX];

			double znam = a0 * dt * dt;
			hnK = K * dt * dt / znam;
			hnB0 = b0 * dt * dt / znam;
			
			foIntegral.setNewCoefRiem(mu);
			foIntegral.resetState();
		}

		@Override
		public double getValue(double inputValue) {
			double outputValue = 0.0;
			double dy = hnK * inputValue;
			dy = dy - hnB0 * y0;
			outputValue = foIntegral.getValue(dy);
			y0 = outputValue;
			return outputValue;
		}
}
