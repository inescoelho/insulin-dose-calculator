package edu.dei.qcs.wsclient;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import edu.dei.qcs.logic.InsulinDoseCalculator;


public class UniversalWSClient extends Service {
    private static final String DEFAULT_NAMESPACE = "http://server/";
    private static final String DEFAULT_SERVICE_NAME = "InsulinDoseCalculatorService";
    private static final String DEFAULT_PORT_NAME = "InsulinDoseCalculatorPort";

    private final QName servicePort;


    public UniversalWSClient(URL wsdlLocation) throws javax.xml.ws.WebServiceException {
	super(wsdlLocation, new QName(DEFAULT_NAMESPACE, DEFAULT_SERVICE_NAME));

	this.servicePort = new QName(DEFAULT_NAMESPACE, DEFAULT_PORT_NAME);
    }

    public InsulinDoseCalculator getInsulinDoseCalculatorPort() {
	return super.getPort(servicePort, InsulinDoseCalculator.class);
    }

    public static URL getURL(String url) throws MalformedURLException {
	return new URL(url);
    }

    /**
     * Code to attempt to auto-detect all the required data from wsdl got all attributes right but
     * 'targetNameSpace' and 'portTypeName' these 2 attributes are hardcoded using annotations in the code
     * generated from the WSDL
     **/
    /*public UniversalWSClient(URL wsdlLocation, String namespace, String serviceName, String portName) {
        super(wsdlLocation, new QName(namespace, serviceName));

        this.servicePort = new QName(namespace, portName);
    }


    public static Optional<String[]> evaluateWS(String wsdlURL) {
        //  parse wsdl
        String[] ret = null;
        WSDLParser parser = new WSDLParser();
        Definitions defs = parser.parse(wsdlURL);

        // get name space
        String namespace = defs.getTargetNamespace();

        // get service name and port name
        Optional<String[]> data = getServiceNameAndPort(defs);

        if (data.isPresent()) {
            ret = new String[] {namespace, data.get()[0], data.get()[1]};
        }

        return Optional.ofNullable(ret);
    }*/

    /** <----------------------------------- Private methods -----------------------------------> **/

    /*private static Optional<String[]> getServiceNameAndPort(Definitions defs) {
    boolean isValid = false;
    String[] ret = new String[2];

    List<com.predic8.wsdl.Service> services = defs.getServices();
    if (services != null && services.size() == 1) {
        ret[0] = services.get(0).getName();

        List<Port> ports = services.get(0).getPorts();
        if (ports != null && ports.size() == 1) {
    	ret[1] = ports.get(0).getName();
    	isValid = true;
        }
    }

    if (isValid) {
        return Optional.of(ret);
    }
    else {
        return Optional.empty();
    }
    }*/

}
