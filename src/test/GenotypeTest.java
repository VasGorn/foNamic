package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import algorithm.genetic.Genotype;
import beans.CoefficientParameter;

class GenotypeTest {
	private static Genotype genotype;
	private static final double DELTA = 1e-09;
	
	@BeforeAll
	public static void setupAll() {
		double minCoefValue = 2.0;
		double maxCoefValue = 5.0;
		double step = 0.001;
		
		CoefficientParameter coefParam = new CoefficientParameter(minCoefValue, maxCoefValue, step);
		
		genotype = new Genotype(coefParam);
	}
	
	@Test
	@DisplayName("Correct value of fields after call of constructor")
	public void CheckStateOfGenotypeObject() {
		assertEquals(2.0, genotype.getMin(), DELTA);
		assertEquals(12, genotype.getLength(), DELTA);
		assertEquals(7.3260e-04, genotype.getPrecision(), DELTA);
	}
	
	@Test
	@DisplayName("Correct converting from binary to number")
	public void CheckConvertFromBinaryToDouble() {
		byte[] genes = new byte[12];
		genes[0] = 1; genes[1] = 1;  genes[2] = 1;  genes[3] = 1;
		genes[4] = 1; genes[5] = 1;	 genes[6] = 1;  genes[7] = 1;
		genes[8] = 1; genes[9] = 1; genes[10] = 1; genes[11] = 1;
		
		assertEquals(5.0, genotype.decodeGenotype(genes), DELTA);

		genes[0] = 0; genes[1] = 0;  genes[2] = 0;  genes[3] = 0;
		genes[4] = 0; genes[5] = 0;	 genes[6] = 0;  genes[7] = 0;
		genes[8] = 0; genes[9] = 0; genes[10] = 0; genes[11] = 0;
		
		assertEquals(2.0, genotype.decodeGenotype(genes), DELTA);
	
		genes[0] = 1; genes[1] = 0;  genes[2] = 1;  genes[3] = 0;
		genes[4] = 1; genes[5] = 0;	 genes[6] = 1;  genes[7] = 0;
		genes[8] = 1; genes[9] = 0; genes[10] = 1; genes[11] = 1;
		
		assertEquals(4.5003663003663, genotype.decodeGenotype(genes), DELTA);
	}
	
	@DisplayName("Step time size must be greater then ZERO, otherwise throw runtime exception")
	@ParameterizedTest
	@ValueSource(doubles = {-0.05, 0.0})
	void willThrowRuntimeExceptionThenStepTimeLessZero(double stepTime) {
		Assertions.assertThrows(RuntimeException.class, () -> {
			CoefficientParameter coefParam = new CoefficientParameter(2.0, 5.0, stepTime);
			Genotype gType = new Genotype(coefParam);
		});
	}

	@Test
	@DisplayName("'min' must have lower value then 'max', otherwise throw runtime exception")
	void willThrowRuntimeExceptionThenMaxTheSameToMin() {
		double stepTime = 0.01;
		Assertions.assertThrows(RuntimeException.class, () -> {
			CoefficientParameter coefParam = new CoefficientParameter(5.0, 5.0, stepTime);
			Genotype gType = new Genotype(coefParam);
		});
	}

	@Test
	@DisplayName("'min' must have lower value then 'max' by '10*step', otherwise throw runtime exception")
	void willThrowRuntimeExceptionThenMaxLowerThenMin() {
		double stepTime = 0.01;
		Assertions.assertThrows(RuntimeException.class, () -> {
			CoefficientParameter coefParam = new CoefficientParameter(5.0 - stepTime * 10, 5.0, stepTime);
			Genotype gType = new Genotype(coefParam);
		});
	}

}
