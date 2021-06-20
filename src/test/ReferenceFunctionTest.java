package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import beans.ReferenceFunction;

class ReferenceFunctionTest {

	private static ReferenceFunction refFunction;

	@BeforeAll
	public static void setupAll() {
		double inputStepSignal = 2.0;
		double stepTimeSize = 0.05;

		Double[] array = {0.0, 1.0, 2.0, 3.0, 4.0, 5.0};
		List<Double> lArray = Arrays.asList(array);
		
		refFunction = new ReferenceFunction(lArray, inputStepSignal, stepTimeSize);
	}
	
	@Test
	@DisplayName("Check the time array")
	void validTimeArrayTest() {
		Double[] timeArray = {0.0, 0.05, 0.1, 0.15, 0.2, 0.25};
		ArrayList<Double> aList = new ArrayList<>(Arrays.asList(timeArray));
		final double DELTA = 1e-12;
		
		for(int i = 0; i < aList.size(); ++i) {
			assertEquals(aList.get(i), refFunction.getArrayTime().get(i), DELTA);
		}
	}
	
	@DisplayName("Step time size must be greater then ZERO")
	@ParameterizedTest
	@ValueSource(doubles = {-0.05, 0.0})
	void willThrowRuntimeExceptionThenStepTimeLessZero(double stepTime) {
		double inputStepSignal = 2.0;
		double stepTimeSize = stepTime;

		Double[] array = {0.0, 1.0, 2.0, 3.0, 4.0, 5.0};
		List<Double> lArray = Arrays.asList(array);

		Assertions.assertThrows(RuntimeException.class, () -> {
			ReferenceFunction newRefFunction = new ReferenceFunction(lArray, inputStepSignal, stepTimeSize);
		});
	}

}
