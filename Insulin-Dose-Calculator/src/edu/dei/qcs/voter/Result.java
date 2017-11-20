package edu.dei.qcs.voter;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class Result implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Structure to hold the valid results from the Web Services
     */
    private ConcurrentHashMap<String, Integer> validResults;
    /**
     * Structure to hold the invalid results from the Web Services
     */
    private ConcurrentHashMap<String, Integer> invalidResults;

    private AtomicInteger result;
    private AtomicBoolean wasSent;


    public Result() {
	validResults = new ConcurrentHashMap<>();
	invalidResults = new ConcurrentHashMap<>();
	this.result = new AtomicInteger(-1);
	this.wasSent = new AtomicBoolean(false);
    }

    public ConcurrentHashMap<String, Integer> getAllResults() {
	invalidResults.putAll(validResults);
	return invalidResults;
    }

    public int getNumberValidWS() {
	return validResults.size();
    }

    public int getResult() {
	return result.get();
    }

    public void setResult(int result) {
	this.result.set(result);
    }

    public ConcurrentHashMap<String, Integer> getValidResults() {
	return validResults;
    }

    public void setValidResults(ConcurrentHashMap<String, Integer> validResults) {
	this.validResults = validResults;
    }

    public ConcurrentHashMap<String, Integer> getInvalidResults() {
	return invalidResults;
    }

    public void setInvalidResults(ConcurrentHashMap<String, Integer> invalidResults) {
	this.invalidResults = invalidResults;
    }

    public boolean wasSent() {
	return wasSent.get();
    }

    public void setWasSent(boolean wasSent) {
	this.wasSent.set(wasSent);
    }

    public AtomicBoolean getWasSent() {
	return wasSent;
    }

}
