package edu.dei.qcs.voter;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class VoterTest {

    @Test
    public void testVotingAlgorithm3Version() {
	Voter v = new Voter(3);

	assertEquals(5, v.checkMajority(Arrays.asList(1, 5, 5)));
	assertEquals(9, v.checkMajority(Arrays.asList(1, 10, 9)));
	assertEquals(4, v.checkMajority(Arrays.asList(4, 5, 4)));
	assertEquals(1, v.checkMajority(Arrays.asList(1, 11, 2)));

	// tie + test floor rounding
	assertEquals(9, v.checkMajority(Arrays.asList(10, 9, 1)));

	// fails:
	assertEquals(-1, v.checkMajority(Arrays.asList(1, 2, 3)));
    }

    @Test
    public void testVotingAlgorithm5Version() {
	Voter v = new Voter(5);

	assertEquals(9, v.checkMajority(Arrays.asList(10, 10, 9, 9, 8)));
	assertEquals(7, v.checkMajority(Arrays.asList(7, 7, 8, 8, 9)));

	assertEquals(6, v.checkMajority(Arrays.asList(5, 5, 6, 6, 6)));
	assertEquals(7, v.checkMajority(Arrays.asList(10, 10, 8, 8, 7)));
	assertEquals(10, v.checkMajority(Arrays.asList(10, 9, 11, 11, 8)));
	assertEquals(10, v.checkMajority(Arrays.asList(10, 10, 10, 10, 9)));

	// ties
	assertEquals(-1, v.checkMajority(Arrays.asList(10, 10, 11, 9, 1)));
	assertEquals(-1, v.checkMajority(Arrays.asList(10, 8, 9, 10, 8)));
	assertEquals(-1, v.checkMajority(Arrays.asList(1, 1, 2, 3, 3)));
    }

    @Test
    public void testVotingAlgorithm7Version() {
	Voter v = new Voter(7);

	assertEquals(1, v.checkMajority(Arrays.asList(1, 1, 1, 2, 2, 3)));
	assertEquals(1, v.checkMajority(Arrays.asList(1, 1, 1, 2, 2, 3, 3)));

	// ties
	assertEquals(-1, v.checkMajority(Arrays.asList(1, 1, 1, 2, 3, 3, 3)));

	// tie + test floor rounding
	assertEquals(9, v.checkMajority(Arrays.asList(10, 10, 10, 9, 9, 9, 1)));
	assertEquals(9, v.checkMajority(Arrays.asList(10, 10, 10, 9, 7, 7, 1)));
    }

}
