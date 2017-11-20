package edu.dei.qcs.voter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.dei.qcs.voter.auxiliar.ResultOccurence;


public class Voter {
    public static final boolean DEBUG = false;

    public static final long TIME_UNTIL_TIMOUT_MILIS = 3200;

    // maybe in the future replace with a file
    public static final ArrayList<URL> ws = new ArrayList<>(9);
    static {
	try {
	    ws.add(new URL("http://qcs10.dei.uc.pt:8080/InsulinDoseCalculator?wsdl"));	// ours
	    //ws.add(new URL("http://liis-lab.dei.uc.pt:8080/Server?wsdl"));		// errors
	    ws.add(new URL("http://qcs01.dei.uc.pt:8080/InsulinDoseCalculator?wsdl"));
	    ws.add(new URL("http://qcs02.dei.uc.pt:8080/insulinDosage?wsdl"));		// seems good
	    ws.add(new URL("http://qcs04.dei.uc.pt:8080/InsulinDoseCalculator?wsdl"));	// crash detected
	    ws.add(new URL("http://qcs05.dei.uc.pt:8080/insulin?wsdl"));
	    ws.add(new URL("http://qcs06.dei.uc.pt:8080/insulin?wsdl"));
	    ws.add(new URL("http://qcs07.dei.uc.pt:8080/insulin?wsdl"));
	    //ws.add(new URL("http://qcs08.dei.uc.pt:8080/InsulinDoseCalculator?wsdl")); // works but gives warnings in unwrap
	    //ws.add(new URL("http://qcs09.dei.uc.pt:8080/Insulin?wsdl")); // warnings since (doesn't respect the 'common' API
	    ws.add(new URL("http://qcs12.dei.uc.pt:8080/insulin?wsdl"));		// crash detected
	    ws.add(new URL("http://52.6.174.158:8082/insulin?wsdl"));
	}
	catch (MalformedURLException e) {
	    System.err.println("Error! Voter got bad URL List: "+ e.getMessage());
	}
    }
    /**
     * Minimum number of WS allowed
     */
    private static final int MINIMUM_WS_COUNT = 3;
    /**
     * Maximum result difference to be considered equivalent by the voter
     */
    private static final int MAXIMUM_RESULT_DIFFERENCE = 1;


    /**
     * Thread Pool structure
     */
    private ExecutorService executor;
    private int numberWSToUse;
    private int majorityThreshold;


    public Voter(int numberWSToUse) {
	int maxWS = ws.size();

	// Check if the select WS number is acceptable
	if (numberWSToUse < MINIMUM_WS_COUNT || numberWSToUse > maxWS) {
	    throw new IllegalStateException(String.format(
		    "Error! Selected %d version programming but the minimum is %d and the maximum is %d\n",
		    numberWSToUse, MINIMUM_WS_COUNT, maxWS));
	}

	// Check if WS Number is odd
	if (numberWSToUse % 2 == 0) {
	    throw new IllegalArgumentException(String.format(
		    "Error! The number of WS to be used must be odd\n", numberWSToUse));
	}

	this.numberWSToUse = numberWSToUse;
	this.majorityThreshold = numberWSToUse / 2 + 1;

	//executor = Executors.newFixedThreadPool(numberWSToUse);
	executor = Executors.newCachedThreadPool();
    }

    public Result mealtimeInsulinDose(int carbohydrateAmount, int carbohydrateToInsulinRatio,
	    int preMealBloodSugar, int targetBloodSugar, int personalSensitivity, long timeUntilTimeout) {

	Result results = new Result();

	ArrayList<Object> inputParams = new ArrayList<>(5);
	inputParams.add(carbohydrateAmount);
	inputParams.add(carbohydrateToInsulinRatio);
	inputParams.add(preMealBloodSugar);
	inputParams.add(targetBloodSugar);
	inputParams.add(personalSensitivity);

	runWebServices(MethodEnum.MEALTIME_INSULIN_DOSE, inputParams, results, timeUntilTimeout);

	return results;
    }

    public Result mealtimeInsulinDose(int carbohydrateAmount, int carbohydrateToInsulinRatio,
	    int preMealBloodSugar, int targetBloodSugar, int personalSensitivity) {

	return mealtimeInsulinDose(carbohydrateAmount, carbohydrateToInsulinRatio,
		preMealBloodSugar, targetBloodSugar, personalSensitivity, TIME_UNTIL_TIMOUT_MILIS);
    }

    public Result backgroundInsulinDose(int bodyWeight) {
	Result results = new Result();

	ArrayList<Object> inputParams = new ArrayList<>(1);
	inputParams.add(bodyWeight);

	runWebServices(MethodEnum.BACKGROUND_INSULIN_DOSE, inputParams, results);

	return results;
    }

    public Result personalSensitivityToInsulin(int physicalActivityLevel, int[] physicalActivitySamples,
	    int[] bloodSugarDropSamples) {

	Result results = new Result();

	ArrayList<Object> inputParams = new ArrayList<>(3);
	inputParams.add(physicalActivityLevel);
	inputParams.add(physicalActivitySamples);
	inputParams.add(bloodSugarDropSamples);

	runWebServices(MethodEnum.PERSONAL_SENSITIVITY_TO_INSULIN, inputParams, results);

	return results;
    }


    public synchronized int checkHandleMajority(Result result) {
	int ret = checkMajority(result.getValidResults().values());

	if (ret != -1 && !result.wasSent()) {
	    if (DEBUG) {
		System.out.printf("Voter: Mandaria %d\n", ret);
	    }

	    result.setResult(ret);
	    result.setWasSent(true);
	    //executor.shutdownNow();
	    synchronized (result.getWasSent()) {
		result.getWasSent().notify();
	    }
	}

	return ret;
    }

    // TODO Docs
    protected int checkMajority(Collection<Integer> values) {
	int tmp, ret = -1;

	ArrayList<ResultOccurence> listOfResults = countResultFrequency(values);

	// optimistic scenario
	if (listOfResults.get(0).occurence >= majorityThreshold) {
	    ret = listOfResults.get(0).value;
	}
	else {
	    tmp = 0;
	    boolean found = false;

	    for (int j, i = 0; i < listOfResults.size(); i++) {
		for (j = i+1; j < listOfResults.size(); j++) {

		    if ((listOfResults.get(i).occurence + listOfResults.get(j).occurence) >= majorityThreshold &&
			    Math.abs(listOfResults.get(i).value - listOfResults.get(j).value) <= MAXIMUM_RESULT_DIFFERENCE) {

			// equal tie -> can't decide which is correct value
			if (found && (listOfResults.get(i).occurence + listOfResults.get(j).occurence) == tmp) {
			    if (DEBUG) {
				System.err.println("Equal tie :/");
			    }
			    ret = -1;
			    i = listOfResults.size();
			    break;
			}
			else if ((listOfResults.get(i).occurence + listOfResults.get(j).occurence) > tmp){
			    tmp = listOfResults.get(i).occurence + listOfResults.get(j).occurence;

			    found = true;

			    // in case of values slightly different - always use the smallest one
			    ret = Math.min(listOfResults.get(i).value, listOfResults.get(j).value);
			}
		    }
		}
	    }
	}
	return ret;
    }

    public void doTimeout() {
	executor.shutdownNow();
    }

    /* <-------------------- Getters --------------------> */

    public int getMajorityThreshold() {
	return majorityThreshold;
    }

    /** <----------------------------------- Private methods ----------------------------------->
     * @return **/

    private ArrayList<ResultOccurence> countResultFrequency(Collection<Integer> values) {
	int tmp;
	HashMap<Integer, Integer> frequency = new HashMap<>();

	for (Integer value : values) {
	    tmp = 1;
	    if (frequency.containsKey(value)) {
		tmp += frequency.get(value);
	    }
	    frequency.put(value, tmp);
	}

	ArrayList<ResultOccurence> listOfResults = new ArrayList<>(frequency.size());

	// lambda requires java 8
	// frequency.forEach((k, v) -> listOfResults.add(new ResultOccurence(v, k)));
	Set<Integer> keys = frequency.keySet();
	for (Integer key : keys) {
	    listOfResults.add(new ResultOccurence(frequency.get(key), key));
	}

	Collections.sort(listOfResults);
	return listOfResults;
    }

    /**
     * Executes the WS Calls and waits until their threads finish or are killed using default timeout.
     *
     * @param insulinCalculationMethod
     * @param inputParams
     * @param results
     */
    private void runWebServices(MethodEnum insulinCalculationMethod, ArrayList<Object> inputParams, Result results) {
	runWebServices(insulinCalculationMethod, inputParams, results, TIME_UNTIL_TIMOUT_MILIS);
    }

    /**
     * Executes the WS Calls and waits until their threads finish or are killed (timeout)
     *
     * @param insulinCalculationMethod
     * @param inputParams
     * @param results
     * @param timeUntilTimeout
     */
    private void runWebServices(MethodEnum insulinCalculationMethod, ArrayList<Object> inputParams, Result results, long timeUntilTimeout) {
	ArrayList<Integer> indexes = getRandomWSIndexes(numberWSToUse);

	// iterate and call threads
	for (int i = 0; i < numberWSToUse; i++) {
	    executor.execute(new WSConnector(ws.get(indexes.get(i)), insulinCalculationMethod, inputParams, this, results));
	}

	try {
	    synchronized (results.getWasSent()) {
		results.getWasSent().wait(TIME_UNTIL_TIMOUT_MILIS);
	    }
	    //executor.awaitTermination(Timeout.TIME_UNTIL_TIMOUT_MILIS, TimeUnit.MILLISECONDS);
	}
	catch (InterruptedException e) {
	    System.err.println("Voter interrupted");
	}
    }

    /**
     * Randomly chooses WS from the pool
     * Ensures our WS is always chosen
     *
     * @param wsToRun (including the team ws)
     * @return List with the indexes of the ws to run
     */
    private ArrayList<Integer> getRandomWSIndexes(int wsToRun) {
	ArrayList<Integer> ret = null;
	int i;

	if (wsToRun >= MINIMUM_WS_COUNT && wsToRun <= ws.size()) {
	    ret = new ArrayList<>(wsToRun);

	    for (i = 1; i < ws.size(); i++) {
		ret.add(i);
	    }

	    Collections.shuffle(ret);

	    ret.add(0, 0); // add the team ws to the 1st position
	}
	else {
	    System.err.println("Voter: getRandomWSIndexes: Invalid number of WS");
	}

	return ret;
    }

}
