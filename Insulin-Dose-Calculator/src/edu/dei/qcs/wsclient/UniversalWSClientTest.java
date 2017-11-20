package edu.dei.qcs.wsclient;

import java.net.URL;
import java.util.ArrayList;

import edu.dei.qcs.voter.Voter;

/**
 * Tests multiple WS defined by the voter class
 */
public class UniversalWSClientTest {

    /*@Test
    public void testUniversalWSClientURL() {
    ArrayList<URL> ws = Voter.ws;
    for (URL url : ws) {
        try {
    	UniversalWSClient client = new UniversalWSClient(url);
    	InsulinDoseCalculator service = client.getInsulinDoseCalculatorPort();
    	System.out.println(url);
    	assertEquals(8, service.mealtimeInsulinDose(60, 10, 220, 120, 50));
        }
        catch (Exception e) {
    	System.err.printf("%s gave exception: %s\n", url.toString(), e.getMessage());
        }
    }
    }*/

    public static void main(String[] args) {
	ArrayList<URL> ws = Voter.ws;
	for (URL url : ws) {
	    try {
		UniversalWSClient client = new UniversalWSClient(url);
		edu.dei.qcs.logic.InsulinDoseCalculator service = client.getInsulinDoseCalculatorPort();
		/*System.out.printf("%d - %s ",
			service.personalSensitivityToInsulin(7, Arrays.asList(5, 5, 5),
				Arrays.asList(50, 50, 50)), url);*/
		//System.out.printf("%d - %s ", service.mealtimeInsulinDose(60, 10, 220, 120, 50), url);
		//System.out.printf("%d - %s ", service.personalSensitivityToInsulin(5, new int[]{5, 5, 5}, new int[]{50, 50, 50}), url);
		System.out.printf("%d - %s ", service.backgroundInsulinDose(75), url);
	    }
	    catch (Exception e) {
		System.err.printf("%s gave exception: %s\n", url.toString(), e.getMessage());
	    }
	    System.out.println();
	}
    }
}
