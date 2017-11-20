package edu.dei.qcs.voter.auxiliar;

import edu.dei.qcs.voter.Voter;


/**
 * Temporary class to call the voter
 */
public class VoterClient {
    public static void main(String[] argv) {

	Voter v = new Voter(9);
	// System.out.println(v.backgroundInsulinDose(75));
	v.mealtimeInsulinDose(60, 12, 200, 100, 25);


	//v.checkHandleMajority(Arrays.asList(10, 10, 10, 10, 9));


	/*System.out.println("Resposta:" +v.personalSensitivityToInsulin(5, new int[] { 2, 2 }, new int[] { 50, 50 }));


	// TODO FIXME
	try {
	    Thread.sleep(5000);
	}
	catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	System.out.println("Resposta:" +v.mealtimeInsulinDose(60, 12, 200, 100, -1));*/

	/*//String url = "http://qcs18.dei.uc.pt:8080/insulin?wsdl";
	//String url = "http://qcs19.dei.uc.pt/InsulinDoseCalculator/WebService?wsdl";
	String url ="http://qcs04.dei.uc.pt:8080/InsulinDoseCalculator?wsdl";
	//String url = "http://qcs10.dei.uc.pt:8080/InsulinDoseCalculator?wsdl";

	URL url1 = null;
	try {
	    url1 = new URL(url);
	} catch (MalformedURLException ex) {
	    System.err.println("url mal");
	}

	UniversalWSClient obj = new UniversalWSClient(url1);
	InsulinDoseCalculator service = obj.getInsulinDoseCalculatorPort();

	service.personalSensitivityToInsulin(5, new int[] { 0, 10 }, new int[] { 50, 50 });
	// System.out.println(service.mealtimeInsulinDose(60, 12, 200, 100, 25));*/
    }

    /*@Test
    public void backgroundInsulinDoseTest() {
	Voter v = new Voter(5);
	assertEquals(22, v.backgroundInsulinDose(79));
    }*/

}
