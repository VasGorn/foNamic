package model.math;

import interfaces.ITransferFunction;

public class TranserFunction1MUImp implements ITransferFunction {
	//--- a1*D^(1+mu)*I + a0*D^mu*I + I = K ---
		private double hnK;
		private double hnB0;
		private double hnA1;
		private double b0 = 1.0;
		
		private FOIntegral foIntegral;
		
		private double y0;
		private double dy0;
		
		public TranserFunction1MUImp(FOIntegral foIntegral, double a0, double a1, double K) {
			this.foIntegral = foIntegral;

			this.y0 = 0.0;
			this.dy0 = 0.0;
			
			double dt = foIntegral.getDt();
			double znam = a1 * dt + a0 * dt * dt;
			this.hnK = K * dt * dt / znam;
			this.hnA1 = (a1 * dt) / znam;
			this.hnB0 = b0 * dt * dt / znam;
		}

		@Override
		public void setParameters(double[] paramArray) {
			y0 = 0.0;
			dy0 = 0.0;
			
			double dt = foIntegral.getDt();
			double K = paramArray[Const.K_INDEX];
			double mu = paramArray[Const.MU_INDEX];
			double a0 = paramArray[Const.A0_INDEX];
			double a1 = paramArray[Const.A1_INDEX];

			double znam = a1 * dt + a0 * dt * dt;
			hnK = K * dt * dt / znam;
			hnA1 = (a1 * dt) / znam;
			hnB0 = b0 * dt * dt / znam;
			
			foIntegral.setNewCoefRiem(mu);
			foIntegral.resetState();
		}

		@Override
		public double getValue(double inputValue) {
			double outputValue = 0.0;
			double dy = hnK * inputValue;
			dy = dy + hnA1 * dy0 - hnB0 * y0;
			dy0 = dy;
			outputValue = foIntegral.getValue(dy);
			y0 = outputValue;
			return outputValue;
		}
}
