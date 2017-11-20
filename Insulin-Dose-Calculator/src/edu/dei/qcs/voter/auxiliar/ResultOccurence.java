package edu.dei.qcs.voter.auxiliar;

public class ResultOccurence implements Comparable<ResultOccurence> {
    public int occurence;
    public int value;


    public ResultOccurence(int occurence, int value) {
	this.occurence = occurence;
	this.value = value;
    }


    @Override
    public int compareTo(ResultOccurence o) {
	if (occurence < o.occurence) {
	    return 1;
	}
	if (occurence == o.occurence) {
	    if (value < o.value) {
		return 1;
	    }
	    else {
		return -1;
	    }
	}
	else {
	    return -1;
	}
    }

}
