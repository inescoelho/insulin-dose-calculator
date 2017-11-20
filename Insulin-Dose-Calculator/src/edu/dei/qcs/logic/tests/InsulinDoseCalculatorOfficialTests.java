package edu.dei.qcs.logic.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.dei.qcs.logic.InsulinDoseCalculatorImpl;

/**
 * Official tests
 */
public class InsulinDoseCalculatorOfficialTests {

    private InsulinDoseCalculatorImpl calculator;

    @Before
    public void init() {
	calculator = new InsulinDoseCalculatorImpl();
    }


    @Test
    public void backgroundInsulinDoseTest() {
	assertEquals(22, calculator.backgroundInsulinDose(79));
    }


    @Test
    public void mealtimeInsulinDoseTest() {
	assertEquals(14, calculator.mealtimeInsulinDose(60, 12, 200, 100, 25));
	assertEquals(0, calculator.mealtimeInsulinDose(95, 10, 100, 120, 50));
	assertEquals(8, calculator.mealtimeInsulinDose(120, 14, 170, 100, 60));
    }

    @Test
    public void personalSensitivityToInsulinTest() {
	assertEquals(50, calculator.personalSensitivityToInsulin(5, new int[]{0, 10}, new int[]{50, 50}));
	assertEquals(66, calculator.personalSensitivityToInsulin(6, new int[]{2, 8}, new int[]{32, 83}));
	assertEquals(30, calculator.personalSensitivityToInsulin(0, new int[]{1, 3, 10}, new int[]{33, 43, 70}));
	assertEquals(53, calculator.personalSensitivityToInsulin(4, new int[]{1, 6, 8, 9}, new int[]{32, 61, 91, 88}));
    }
}
