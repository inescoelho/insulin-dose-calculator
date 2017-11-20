package edu.dei.qcs.voter;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.xml.ws.WebServiceException;

import edu.dei.qcs.logic.InsulinDoseCalculator;
import edu.dei.qcs.wsclient.UniversalWSClient;


public class WSConnector implements Runnable {
    private static final int MAX_RETRY_ATTEMPTS = 2;
    private static final int DELAY_BETWEEN_ATTEMPTS_MILIS = 100;

    private URL url;
    private MethodEnum method;
    private AtomicBoolean wasSent;
    private ArrayList<Object> inputParams;

    private Voter voter;
    private Result results;


    public WSConnector(URL url, MethodEnum method, ArrayList<Object> inputParams, Voter voter, Result results) {
	this.url = url;
	this.method = method;
	this.results = results;
	this.inputParams = inputParams;

	this.wasSent = results.getWasSent();
	this.voter = voter;
    }


    @Override
    public void run() {
	UniversalWSClient client;
	InsulinDoseCalculator calc;
	int ret = -1;
	int attempt = 1;

	while (attempt <= MAX_RETRY_ATTEMPTS) {

	    try {
		client = new UniversalWSClient(url);
		calc = client.getInsulinDoseCalculatorPort();

		switch (method) {
		    case BACKGROUND_INSULIN_DOSE:

			ret = calc.backgroundInsulinDose((Integer) inputParams.get(0));

			if (Voter.DEBUG) {
			    System.out.println(ret + " " + url.toString());
			}
			break;

		    case MEALTIME_INSULIN_DOSE:

			ret = calc.mealtimeInsulinDose((int) inputParams.get(0),
				(int) inputParams.get(1), (int) inputParams.get(2),
				(int) inputParams.get(3), (int) inputParams.get(4));

			if (Voter.DEBUG) {
			    System.out.println(ret + " " + url.toString());
			}
			break;

		    case PERSONAL_SENSITIVITY_TO_INSULIN:

			ret = calc.personalSensitivityToInsulin((int) inputParams.get(0),
				(int[]) inputParams.get(1),
				(int[]) inputParams.get(2));

			if (Voter.DEBUG) {
			    System.out.println(ret + " " + url.toString());
			}
			break;

		    default:
			System.err.println("WSConnector: Error, unknown method...");
			return;
		}

		// no exception
		break;
	    }
	    // proteger chamadas contra por ex WS de com.sun.xml.internal.ws.fault.ServerSOAPFaultException
	    catch (WebServiceException e) {
		if (Voter.DEBUG) {
		    System.err.printf("WS got exception Attempt: %d/%d (%s)\n", attempt, MAX_RETRY_ATTEMPTS, url.toString());
		}
		try {
		    Thread.sleep(DELAY_BETWEEN_ATTEMPTS_MILIS);
		}
		catch (InterruptedException e1) {
		    return;
		}
	    }
	    attempt++;
	}

	// save 'valid' result
	if (ret != -1) {
	    results.getValidResults().put(url.toString(), ret);

	    // 1st check if the message was already sent
	    if (wasSent.get()) {
		if (Voter.DEBUG) {
		    System.out.printf("WSConnector: %s: message was already sent :/\n", url);
		}
	    }
	    else {
		// 2nd - check if there are enough answers to send
		int size = results.getValidResults().size();
		if (size >= voter.getMajorityThreshold()) {

		    // compute majority
		    voter.checkHandleMajority(results);
		}
		else if (Voter.DEBUG) {
		    System.out.printf("WSConnector: %s: not enough results to send (%d/%d)...\n", url,
			    size, voter.getMajorityThreshold());
		}
	    }
	}
	else {
	    results.getInvalidResults().put(url.toString(), ret);
	}

    }

    // deprecated?
    /*private List<Integer> convertIntArrayToObjectList(int[] array) {
	List<Integer> ret = new ArrayList<Integer>(array.length);

	for (int index = 0; index < array.length; index++) {
	    ret.add(array[index]);
	}

	return ret;
    }*/

}
