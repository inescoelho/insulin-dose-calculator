package edu.dei.qcs.ejb;

import java.util.ArrayList;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import edu.dei.qcs.logic.InsulinDoseCalculatorImpl;
import edu.dei.qcs.voter.Result;
import edu.dei.qcs.voter.Voter;

/**
 * Session Bean implementation class InsulinDoseCalculatorEJB
 */
@Stateless
@LocalBean
public class InsulinDoseCalculatorEJB implements InsulinDoseCalculatorEJBRemote {
    private static final int DEFAULT_WS_TO_USE = 7;

    private Voter voter;


    /**
     * Default constructor.
     */
    public InsulinDoseCalculatorEJB() {
	voter = new Voter(DEFAULT_WS_TO_USE);
    }

    public InsulinDoseCalculatorEJB(int numberWSToUse) {
	voter = new Voter(numberWSToUse);
    }

    @Override
    public Result backgroundInsulinDose(int bodyWeight) {
	Result result = voter.backgroundInsulinDose(bodyWeight);
	return result;
    }

    @Override
    public Result personalSensitivityToInsulin(int physicalActivityLevel, int[] physicalActivitySamples,
	    int[] bloodSugarDropSamples) {

	Result result = voter.personalSensitivityToInsulin(physicalActivityLevel, physicalActivitySamples,
		bloodSugarDropSamples);

	return result;
    }

    @Override
    public Result mealtimeInsulinDoseTimeout(int carbohydrateAmount, int carbohydrateToInsulinRatio,
	    int preMealBloodSugar, int targetBloodSugar, int personalSensitivity, long timeUntilTimeout) {

	Result result = voter.mealtimeInsulinDose(carbohydrateAmount, carbohydrateToInsulinRatio, preMealBloodSugar,
		targetBloodSugar, personalSensitivity, timeUntilTimeout);

	return result;
    }

    @Override
    public Result mealtimeInsulinDose(int carbohydrateAmount, int carbohydrateToInsulinRatio,
	    int preMealBloodSugar, int targetBloodSugar, int personalSensitivity) {

	Result result = voter.mealtimeInsulinDose(carbohydrateAmount, carbohydrateToInsulinRatio, preMealBloodSugar,
		targetBloodSugar, personalSensitivity);

	return result;
    }

    @Override
    public ArrayList<Result> personalSensitivityToInsulin(int carbohydrateAmount, int carbohydrateToInsulinRatio,
	    int preMealBloodSugar, int targetBloodSugar, int physicalActivityLevel,
	    int[] physicalActivitySamples, int[] bloodSugarDropSamples) {

	long startTime = System.currentTimeMillis();
	ArrayList<Result> ret = new ArrayList<>(2);

	ret.add(voter.personalSensitivityToInsulin(physicalActivityLevel, physicalActivitySamples, bloodSugarDropSamples));

	// if valid Sensitivity Data
	if (ret.get(0).getResult() >= InsulinDoseCalculatorImpl.MINIMUM_PERSONAL_SENSITIVITY
		&& ret.get(0).getResult() <= InsulinDoseCalculatorImpl.MAXIMUM_PERSONAL_SENSITIVITY) {

	    long timeRemaining = Voter.TIME_UNTIL_TIMOUT_MILIS - (System.currentTimeMillis() - startTime);

	    //System.out.println("valid " + timeRemaining);

	    ret.add(voter.mealtimeInsulinDose(carbohydrateAmount, carbohydrateToInsulinRatio, preMealBloodSugar,
		    targetBloodSugar, ret.get(0).getResult(), timeRemaining));
	}

	return ret;
    }
}
