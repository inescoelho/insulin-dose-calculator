package edu.dei.qcs.ejb;

import java.util.ArrayList;

import javax.ejb.Remote;

import edu.dei.qcs.voter.Result;


/**
 * Interface for the Insulin Dose Calculator EJB Adapted from the InsulinDoseCalculator interface by Teachers
 * Raul Barbosa and Henrique Madeira
 *
 * Adapted to return additional information regarding the calculation of the result
 */
@Remote
public interface InsulinDoseCalculatorEJBRemote {

    /**
     * Calculates the number of insulin units needed after one meal.
     *
     * @param carbohydrateAmount amount of carbohydrate in the meal, in grams
     * @param carbohydrateToInsulinRatio carbohydrate grams disposed by one unit
     * @param preMealBloodSugar pre-meal measured blood sugar level, in mg/dl
     * @param targetBloodSugar prescribed target blood sugar level, in mg/dl
     * @param personalSensitivity personal sensitivity to insulin
     * @return Result object containing the mealtime units of insulin needed, or -1 in case of error as well
     *         as the detailed information regarding the responses of the multiple ws involved
     */
    public Result mealtimeInsulinDose(int carbohydrateAmount, int carbohydrateToInsulinRatio,
	    int preMealBloodSugar,
	    int targetBloodSugar, int personalSensitivity);

    /**
     * Calculates the total number of units of insulin needed between meals.
     *
     * @param bodyWeight the person's weight in kilograms
     * @return Result object containing the background units of insulin needed, or -1 in case of error as well
     *         as the detailed information regarding the responses of the multiple ws involved
     */
    public Result backgroundInsulinDose(int bodyWeight);

    /**
     * Determines an individual's sensitivity to one unit of insulin.
     *
     * @param physicalActivityLevel most recent activity level (the predictor)
     * @param physicalActivitySamples K samples of past physical activity
     * @param bloodSugarDropSamples corresponding K samples of blood sugar drop
     * @return Result object containing the blood sugar drop in mg/dl, or -1 in case of error as well as the
     *         detailed information regarding the responses of the multiple ws involved
     */
    public Result personalSensitivityToInsulin(int physicalActivityLevel, int[] physicalActivitySamples,
	    int[] bloodSugarDropSamples);

    /**
     * Calculates the number of insulin units needed after one meal according to the individual's sensitivity
     *
     * @param carbohydrateAmount amount of carbohydrate in the meal, in grams
     * @param carbohydrateToInsulinRatio carbohydrate grams disposed by one unit
     * @param preMealBloodSugar pre-meal measured blood sugar level, in mg/dl
     * @param targetBloodSugar prescribed target blood sugar level, in mg/dl
     * @param physicalActivityLevel most recent activity level (the predictor)
     * @param physicalActivitySamples K samples of past physical activity
     * @param bloodSugarDropSamples corresponding K samples of blood sugar drop
     * @return Result List containing the mealtime units of insulin needed, or -1 in case of error as well
     *         as the detailed information regarding the responses of the multiple ws involved
     */
    public ArrayList<Result> personalSensitivityToInsulin(int carbohydrateAmount, int carbohydrateToInsulinRatio,
	    int preMealBloodSugar,
	    int targetBloodSugar, int physicalActivityLevel, int[] physicalActivitySamples,
	    int[] bloodSugarDropSamples);

    /**
     * Calculates the number of insulin units needed after one meal with custom timeout
     *
     * @param carbohydrateAmount amount of carbohydrate in the meal, in grams
     * @param carbohydrateToInsulinRatio carbohydrate grams disposed by one unit
     * @param preMealBloodSugar pre-meal measured blood sugar level, in mg/dl
     * @param targetBloodSugar prescribed target blood sugar level, in mg/dl
     * @param personalSensitivity personal sensitivity to insulin
     * @param timeUntilTimeout
     * @return Result object containing the mealtime units of insulin needed, or -1 in case of error as well
     *         as the detailed information regarding the responses of the multiple ws involved
     */
    public Result mealtimeInsulinDoseTimeout(int carbohydrateAmount, int carbohydrateToInsulinRatio,
	    int preMealBloodSugar, int targetBloodSugar, int personalSensitivity, long timeUntilTimeout);

}
