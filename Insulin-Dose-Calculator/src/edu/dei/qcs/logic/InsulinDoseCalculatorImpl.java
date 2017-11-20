package edu.dei.qcs.logic;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.commons.math3.stat.regression.SimpleRegression;


@WebService(name = "InsulinDoseCalculator",
	    serviceName = "InsulinDoseCalculatorService",
	    targetNamespace = "http://server/")
public class InsulinDoseCalculatorImpl implements InsulinDoseCalculator {
    private static final int ERROR_CODE = -1;
    private static final float THRESHOLD_TO_ROUND_UP = 0.7f;


    private static final float BACKGROUND_INSULIN_DOSE = 0.5f;

    // mealtimeInsulinDose -> carbohydrateAmount
    private static final int MINIMUM_CARB_AMOUNT = 60; // grams
    private static final int MAXIMUM_CARB_AMOUNT = 120; // grams
    // mealtimeInsulinDose -> carbohydrateToInsulinRatio
    private static final int MINIMUM_CARB_INSULIN_RATIO = 10; // g/unit
    private static final int MAXIMUM_CARB_INSULIN_RATIO = 15; // g/unit
    // mealtimeInsulinDose -> preMealBloodSugar
    private static final int MINIMUM_BLOOD_SUGAR_PRE_MEAL = 120; // mg/dl
    private static final int MAXIMUM_BLOOD_SUGAR_PRE_MEAL = 250; // mg/dl
    // mealtimeInsulinDose -> targetBloodSugar
    private static final int MINIMUM_TARGET_BLOOD_SUGAR = 80; // mg/dl
    private static final int MAXIMUM_TARGET_BLOOD_SUGAR = 120; // mg/dl
    // mealtimeInsulinDose -> personalSensitivity
    public static final int MINIMUM_PERSONAL_SENSITIVITY = 15; // mg/dl
    public static final int MAXIMUM_PERSONAL_SENSITIVITY = 100; // mg/dl

    // backgroundInsulinDose -> bodyWeight
    private static final int MINIMUM_BODY_WEIGHT = 40; // kg
    private static final int MAXIMUM_BODY_WEIGHT = 130; // kg

    // personalSensitivityToInsulin -> physicalActivityLevel
    private static final int MINIMUM_PHYSICAL_ACTIVITY_LEVEL = 0;
    private static final int MAXIMUM_PHYSICAL_ACTIVITY_LEVEL = 10;
    // personalSensitivityToInsulin -> bloodSugarDrop
    private static final int MINIMUM_BLOOD_SUGAR_DROP = 15; // mg/dl
    private static final int MAXIMUM_BLOOD_SUGAR_DROP = 100; // mg/dl
    // personalSensitivityToInsulin -> physicalActivityLevel
    private static final int MINIMUM_PHYSICAL_ACTIVITY_SAMPLES = 2;
    private static final int MAXIMUM_PHYSICAL_ACTIVITY_SAMPLES = 10;


    /*public static void main(String[] args) {
    System.out.println(new InsulinDoseCalculatorImp().mealtimeInsulinDose(90, 9, 120, 120, 34));
    }*/

    @Override
    @WebMethod
    public int mealtimeInsulinDose(int carbohydrateAmount, int carbohydrateToInsulinRatio,
	    int preMealBloodSugar, int targetBloodSugar, int personalSensitivity) {

	int ret = -1;
	float highBloodSugarDose, carbohydrateDose, res;
	ReturnEnum validInputParams = areMealtimeInsulinDoseParametersValid(carbohydrateAmount,
		carbohydrateToInsulinRatio,
		preMealBloodSugar, targetBloodSugar, personalSensitivity);

	switch (validInputParams) {
	    case VALID:
		highBloodSugarDose = (float) (preMealBloodSugar - targetBloodSugar) / personalSensitivity;

		carbohydrateDose = (float) carbohydrateAmount / carbohydrateToInsulinRatio
			/ personalSensitivity * 50;

		res = highBloodSugarDose + carbohydrateDose;
		// System.out.println(res);

		ret = roundFloat(res);
		break;

	    case INVALID:
		ret = ERROR_CODE;
		break;

	    case NO_INSULIN:
		ret = 0;
		break;
	}

	return ret;
    }

    @Override
    @WebMethod
    public int backgroundInsulinDose(int bodyWeight) {
	if (bodyWeight < MINIMUM_BODY_WEIGHT || bodyWeight > MAXIMUM_BODY_WEIGHT) {
	    logError("bodyWeight", bodyWeight, MINIMUM_BODY_WEIGHT, MAXIMUM_BODY_WEIGHT);

	    return ERROR_CODE;
	}
	else {
	    return roundFloat(BACKGROUND_INSULIN_DOSE * 0.55f * bodyWeight);
	}
    }

    @Override
    @WebMethod
    public int personalSensitivityToInsulin(int physicalActivityLevel, int[] physicalActivitySamples,
	    int[] bloodSugarDropSamples) {

	int i;
	double alpha, beta;
	int retPersonalSensitivity = ERROR_CODE;
	boolean validInputParams = areBackgroundInsulinDoseParamsValid(physicalActivityLevel,
		physicalActivitySamples, bloodSugarDropSamples);

	SimpleRegression regression = new SimpleRegression(true);

	if (validInputParams) {
	    // fill the regression object
	    for (i = 0; i < bloodSugarDropSamples.length; i++) {
		regression.addData(physicalActivitySamples[i], bloodSugarDropSamples[i]);
	    }

	    // obtain the alpha and beta values from the regression calculator object
	    alpha = regression.getIntercept();
	    beta = regression.getSlope();

	    // calculate the return value based on the given formula
	    retPersonalSensitivity = roundFloat(alpha + (beta * physicalActivityLevel));

	    // validate the return value
	    if (retPersonalSensitivity < MINIMUM_PERSONAL_SENSITIVITY
		    || retPersonalSensitivity > MAXIMUM_PERSONAL_SENSITIVITY) {

		logError("personalSensitivityCalculation", retPersonalSensitivity,
			MINIMUM_PERSONAL_SENSITIVITY,
			MAXIMUM_PERSONAL_SENSITIVITY);

		retPersonalSensitivity = ERROR_CODE;
	    }
	}

	return retPersonalSensitivity;
    }


    /** <-------------------------------- Private methods ---------------------------------> **/

    /** Rounds a float value according the the variable THRESHOLD_TO_ROUND_UP
     * @param result of a calculation
     * @return rounded integer
     */
    private int roundFloat(double result) {
	int ret;

	if (Math.abs(result - Math.floor(result)) >= THRESHOLD_TO_ROUND_UP) {
	    // 8.7 -> 9
	    ret = (int) Math.ceil(result);
	}
	else {
	    // 8.5 -> 8
	    ret = (int) Math.floor(result);
	}

	return ret;
    }

    private boolean areBackgroundInsulinDoseParamsValid(int physicalActivityLevel,
	    int[] physicalActivitySamples,
	    int[] bloodSugarDropSamples) {

	int i;

	if (physicalActivityLevel < MINIMUM_PHYSICAL_ACTIVITY_LEVEL
		|| physicalActivityLevel > MAXIMUM_PHYSICAL_ACTIVITY_LEVEL) {

	    logError("physicalActivityLevel", physicalActivityLevel,
		    MINIMUM_PHYSICAL_ACTIVITY_LEVEL,
		    MAXIMUM_PHYSICAL_ACTIVITY_LEVEL);

	    return false;
	}

	// validate array existence and corresponding sizes
	if (physicalActivitySamples == null
		|| bloodSugarDropSamples == null
		|| physicalActivitySamples.length != bloodSugarDropSamples.length
		|| physicalActivitySamples.length < MINIMUM_PHYSICAL_ACTIVITY_SAMPLES
		|| physicalActivitySamples.length > MAXIMUM_PHYSICAL_ACTIVITY_SAMPLES) {

	    return false;
	}

	for (i = 0; i < physicalActivitySamples.length; i++) {
	    if (physicalActivitySamples[i] < MINIMUM_PHYSICAL_ACTIVITY_LEVEL
		    || physicalActivitySamples[i] > MAXIMUM_PHYSICAL_ACTIVITY_LEVEL) {

		logError("physicalActivitySamples", physicalActivitySamples[i],
			MINIMUM_PHYSICAL_ACTIVITY_LEVEL, MAXIMUM_PHYSICAL_ACTIVITY_LEVEL);

		return false;
	    }
	}

	for (i = 0; i < bloodSugarDropSamples.length; i++) {
	    if (bloodSugarDropSamples[i] < MINIMUM_BLOOD_SUGAR_DROP
		    || bloodSugarDropSamples[i] > MAXIMUM_BLOOD_SUGAR_DROP) {

		logError("bloodSugarDropSamples", bloodSugarDropSamples[i],
			MINIMUM_BLOOD_SUGAR_DROP,
			MAXIMUM_BLOOD_SUGAR_DROP);

		return false;
	    }
	}

	return true;
    }

    /** Validates the parameters of mealtimeInsulinDose according to the specification
     *
     * @param carbohydrateAmount
     * @param carbohydrateToInsulinRatio
     * @param preMealBloodSugar
     * @param targetBloodSugar
     * @param personalSensitivity
     * @return ReturnEnum
     */
    private ReturnEnum areMealtimeInsulinDoseParametersValid(int carbohydrateAmount,
	    int carbohydrateToInsulinRatio,
	    int preMealBloodSugar, int targetBloodSugar, int personalSensitivity) {

	if (targetBloodSugar > preMealBloodSugar) {
	    return ReturnEnum.NO_INSULIN;
	}

	if (carbohydrateAmount < MINIMUM_CARB_AMOUNT || carbohydrateAmount > MAXIMUM_CARB_AMOUNT) {
	    logError("carbohydrateAmount", carbohydrateAmount, MINIMUM_CARB_AMOUNT, MAXIMUM_CARB_AMOUNT);

	    return ReturnEnum.INVALID;
	}

	if (carbohydrateToInsulinRatio < MINIMUM_CARB_INSULIN_RATIO
		|| carbohydrateToInsulinRatio > MAXIMUM_CARB_INSULIN_RATIO) {
	    logError("carbohydrateToInsulinRatio", carbohydrateToInsulinRatio, MINIMUM_CARB_INSULIN_RATIO,
		    MAXIMUM_CARB_INSULIN_RATIO);

	    return ReturnEnum.INVALID;
	}

	if (preMealBloodSugar < MINIMUM_BLOOD_SUGAR_PRE_MEAL
		|| preMealBloodSugar > MAXIMUM_BLOOD_SUGAR_PRE_MEAL) {
	    logError("preMealBloodSugar", preMealBloodSugar, MINIMUM_BLOOD_SUGAR_PRE_MEAL,
		    MAXIMUM_BLOOD_SUGAR_PRE_MEAL);

	    return ReturnEnum.INVALID;
	}

	if (targetBloodSugar < MINIMUM_TARGET_BLOOD_SUGAR || targetBloodSugar > MAXIMUM_TARGET_BLOOD_SUGAR) {
	    logError("targetBloodSugar", targetBloodSugar, MINIMUM_TARGET_BLOOD_SUGAR,
		    MAXIMUM_TARGET_BLOOD_SUGAR);

	    return ReturnEnum.INVALID;
	}

	if (personalSensitivity < MINIMUM_PERSONAL_SENSITIVITY
		|| personalSensitivity > MAXIMUM_PERSONAL_SENSITIVITY) {

	    logError("personalSensitivity", personalSensitivity, MINIMUM_PERSONAL_SENSITIVITY,
		    MAXIMUM_PERSONAL_SENSITIVITY);

	    return ReturnEnum.INVALID;
	}

	return ReturnEnum.VALID;
    }

    /** Logs an error when validating input parameters
     * @param paramName
     * @param val
     * @param min
     * @param max
     */
    private void logError(String paramName, int val, int min, int max) {
	System.err.printf("Error: %s was %d but was expected to be between %d and %d\n", paramName, val, min, max);
    }

}
