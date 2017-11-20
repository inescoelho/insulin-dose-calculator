package edu.dei.qcs.logic.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.dei.qcs.logic.InsulinDoseCalculatorImpl;

/**
 * Custom tests
 */
public class InsulinDoseCalculatorTests {

    private InsulinDoseCalculatorImpl calculator;

    @Before
    public void init() {
	calculator = new InsulinDoseCalculatorImpl();
    }


    /**
     * Based on http://dtc.ucsf.edu/types-of-diabetes/type1/treatment-of-type-1-diabetes/medications-and-therapies/type-1-insulin-therapy/calculating-insulin-dose/
     */
    @Test
    public void mealtimeInsulinDoseTest() {
	assertEquals(8, calculator.mealtimeInsulinDose(60, 10, 220, 120, 50));
    }

    /**
     * In the special case when the target blood sugar level is greater than the pre-meal blood sugar level,
     * the return value should be zero (no insulin).
     */
    @Test
    public void mealtimeInsulinDoseSpecialCaseTest() {
	assertEquals(0, calculator.mealtimeInsulinDose(0, 10, 120, 121, 50));
    }

    @Test
    public void backgroundInsulinDoseTest() {
	assertEquals(20, calculator.backgroundInsulinDose(75));
    }

    @Test
    public void backgroundInsulinDoseErrorTest() {
	assertEquals(-1, calculator.backgroundInsulinDose(0));
    }

    @Test
    public void personalSensitivityToInsulinTest() {
	int activity = 5;
	int[] samplesPhysicalActivity = {1, 5, 3, 2, 7, 4};
	int[] samplesBloodSugar = {25, 50, 37, 25, 75, 40};
	assertEquals(53, calculator.personalSensitivityToInsulin(activity, samplesPhysicalActivity, samplesBloodSugar));
    }

    @Test
    public void personalSensitivityToInsulinTestErrorDiv0() {
	int activity = 7;
	int[] samplesPhysicalActivity = {5, 5, 5};
	int[] samplesBloodSugar = {50, 50, 50};

	assertEquals(-1, calculator.personalSensitivityToInsulin(activity, samplesPhysicalActivity, samplesBloodSugar));
    }

    @Test
    public void personalSensitivityToInsulinTestErrorNullArrays() {
	int activity = 5;
	int[] samplesPhysicalActivity = {1, 5, 3, 2, 7, 4};
	int[] samplesBloodSugar = {25, 50, 37, 25, 75, 40};

	assertEquals(-1, calculator.personalSensitivityToInsulin(activity, samplesPhysicalActivity, null));
	assertEquals(-1, calculator.personalSensitivityToInsulin(activity, null, samplesBloodSugar));
	assertEquals(-1, calculator.personalSensitivityToInsulin(activity, null, null));
    }

    @Test
    public void personalSensitivityToInsulinTestErrorDiffLengthArrays() {
	int activity = 5;
	int[] samplesPhysicalActivity = {1, 5, 3, 2, 7};

	assertEquals(-1, calculator.personalSensitivityToInsulin(activity, samplesPhysicalActivity, new int[]{25, 50, 37, 25, 75, 40}));
	assertEquals(-1, calculator.personalSensitivityToInsulin(activity, samplesPhysicalActivity, new int[]{25, 50, 37, 25}));
    }

    @Test
    public void personalSensitivityToInsulinTestParam1() {
	int[] samplesPhysicalActivity = {1, 4, 3};
	int[] samplesBloodSugar = {25, 75, 40};

	assertEquals(-1, calculator.personalSensitivityToInsulin(11, samplesPhysicalActivity, samplesBloodSugar));
	assertEquals(-1, calculator.personalSensitivityToInsulin(-1, samplesPhysicalActivity, samplesBloodSugar));
    }

    @Test
    public void personalSensitivityToInsulinTestParam2() {
	int activity = 5;
	int[] samplesBloodSugar = {25, 75, 40};

	assertEquals(-1, calculator.personalSensitivityToInsulin(activity, new int[] {1, 'a', 3}, samplesBloodSugar));
	assertEquals(-1, calculator.personalSensitivityToInsulin(activity, new int[] {1, -1, 3}, samplesBloodSugar));
    }

    @Test
    public void personalSensitivityToInsulinTestParam3() {
	int activity = 8;
	int[] samplesPhysicalActivity = {1, 4, 3};

	assertEquals(-1, calculator.personalSensitivityToInsulin(activity, samplesPhysicalActivity, new int[] {25, 101, 40}));
	assertEquals(-1, calculator.personalSensitivityToInsulin(activity, samplesPhysicalActivity, new int[] {25, -1, 40}));
    }

}
