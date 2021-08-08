package com.vesmer.fonamic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import model.math.Const;
import model.math.FOIntegral;
import model.math.TranserFunction1MUImp;

class TransferFunction1MUTest {

	private static TranserFunction1MUImp trFunc1mu;
	private static final double DELTA = 1e-09;
	private static double[] outputArray1;
	private static double[] outputArray2;
	
	@BeforeAll
	public static void setupAll() {
		outputArray1 = new double[2000];
		trFunc1mu = createTestedTransferFunction();
		for(int i = 0; i < 2000; ++i) {
			outputArray1[i] = trFunc1mu.getValue(5.1);
		}
		
		outputArray2 = new double[2000];
		double[] paramArray = createParamArray();
		trFunc1mu.setParameters(paramArray);
		for(int i = 0; i < 2000; ++i) {
			outputArray2[i] = trFunc1mu.getValue(5.1);
		}

	}
	
	@DisplayName("Check the value of fraction order (1+mu) transfer function")
	@ParameterizedTest
	@CsvFileSource(resources = "/1mu_1.csv")
	public void checkTheValueOfTransferFunction(double value, int index) {
		assertEquals(value, outputArray1[index], DELTA);
	}

	@DisplayName("Check the value of fraction order (1+mu) transfer function then parameters changed")
	@ParameterizedTest
	@CsvFileSource(resources = "/1mu_2.csv")
	public void checkTheValueOfTransferFunctionThenParametersChanged(double value, int index) {
		assertEquals(value, outputArray2[index], DELTA);
	}
	
	public static TranserFunction1MUImp createTestedTransferFunction() {
		FOIntegral foIntegral = new FOIntegral(0.5, 0.001, 2000);
		TranserFunction1MUImp trFunc1mu = new TranserFunction1MUImp(foIntegral, 0.2, 0.1, 3.2);

		return trFunc1mu;
	}
	
	public static double[] createParamArray() {
		double[] paramArray = new double[4];
		paramArray[Const.K_INDEX] = 8.3;
		paramArray[Const.MU_INDEX] = 0.78;
		paramArray[Const.A0_INDEX] = 0.4;
		paramArray[Const.A1_INDEX] = 0.15;
		
		return paramArray;
	}

}
