package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import model.math.Const;
import model.math.FOIntegral;
import model.math.TranserFunctionAperiodicImp;

class TransferFunctionAperiodicTest {
	private static TranserFunctionAperiodicImp aperiodic;
	private static final double DELTA = 1e-09;
	private static double[] outputArray1;
	private static double[] outputArray2;
	
	@BeforeAll
	public static void setupAll() {
		outputArray1 = new double[2000];
		aperiodic = createTestedTransferFunction();
		for(int i = 0; i < 2000; ++i) {
			outputArray1[i] = aperiodic.getValue(5.1);
		}
		
		outputArray2 = new double[2000];
		double[] paramArray = createParamArray();
		aperiodic.setParameters(paramArray);
		for(int i = 0; i < 2000; ++i) {
			outputArray2[i] = aperiodic.getValue(5.1);
		}

	}
	
	@DisplayName("Check the value of fraction order aperiodic transfer function")
	@ParameterizedTest
	@CsvFileSource(resources = "/Aperiodic1.csv")
	public void checkTheValueOfTransferFunction(double value, int index) {
		assertEquals(value, outputArray1[index], DELTA);
	}

	@DisplayName("Check the value of fraction order aperiodic transfer function then parameters changed")
	@ParameterizedTest
	@CsvFileSource(resources = "/Aperiodic2.csv")
	public void checkTheValueOfTransferFunctionThenParametersChanged(double value, int index) {
		assertEquals(value, outputArray2[index], DELTA);
	}
	
	public static TranserFunctionAperiodicImp createTestedTransferFunction() {
		FOIntegral foIntegral = new FOIntegral(0.5, 0.001, 2000);
		TranserFunctionAperiodicImp aperiodic = new TranserFunctionAperiodicImp(foIntegral, 0.2, 3.2);

		return aperiodic;
	}
	
	public static double[] createParamArray() {
		double[] paramArray = new double[3];
		paramArray[Const.K_INDEX] = 8.3;
		paramArray[Const.MU_INDEX] = 0.78;
		paramArray[Const.A0_INDEX] = 0.4;
		
		return paramArray;
	}
}
