package com.vesmer.fonamic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import model.math.FOIntegral;

public class FOIntefralTest {
	private static FOIntegral foIntegral;
	private static final double DELTA = 1e-10;
	private static double[] outputArray;
	
	@BeforeAll
	public static void setupAll() {
		outputArray = new double[2000];
		foIntegral = new FOIntegral(0.5, 0.001, 2000);
		
		for(int i = 0; i < 2000; ++i) {
			outputArray[i] = foIntegral.getValue(7.7);
		}
	}

	@Test
	@DisplayName("Check the value of gamma function")
	public void checkTheValueOfGammaFunction() {
		assertEquals(1.772453850905516, FOIntegral.gammaOf(0.5), DELTA);
	}
	
	@DisplayName("Check array of riemann coefficients")
	@ParameterizedTest
	@CsvFileSource(resources = "/RiemannCoef.csv")
	public void checkArrayOfRiemanCoefficients(double coef, int index) {
		assertEquals(coef, foIntegral.getCoefRiem()[index], DELTA);
	}

	@DisplayName("Check the value of fraction order integral")
	@ParameterizedTest
	@CsvFileSource(resources = "/FractionOrderIntegral.csv")
	public void checkTheValueOfFractionOrderIntegral(double value, int index) {
		assertEquals(value, outputArray[index], DELTA);
	}
}
