import java.io.*;
import java.util.Collections;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;

import junit.framework.*;

public class DynamicProgrammingTest extends TestCase{
	
	private DynamicProgramming DP;
	
	public DynamicProgrammingTest(String name)
	{
		super(name)	;
		DP 	= null	;
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Scanner s = null;
		int numJobs = 0;
		JobList jobs = new JobList();
		
		try {
			s = new Scanner(new BufferedReader(new FileReader("tests/random_RDD=0.2_TF=0.2_#25.dat")));
			
			if (s.hasNextInt()) {
				numJobs = s.nextInt();
			}
			
			for (int i = 0; i < numJobs; i++) {
				jobs.add(new Job(s.nextInt(), s.nextInt()));
			}
			s.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		Collections.sort(jobs, new JobList.NonDecreasingDeadlineOrderComparator());
		DP = new DynamicProgramming(jobs);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	public void runTest()
	{
		testMaximumProcessingTime();
	}
	
	public void testMaximumProcessingTime()
	{
		assertEquals(DP.maxProcessingTime		(),			93);
		assertEquals(DP.maxProcessingTimeIndex	(),			14);
		assertEquals(DP.maxProcessingTimeIndex	(0,2,1), 	 0);
		assertEquals(DP.maxProcessingTimeIndex	(4,9,4), 	 7);
		assertEquals(DP.maxProcessingTimeIndex	(4,4,4),	-1);
		assertEquals(DP.maxProcessingTimeIndex	(1,5,5),	-1);
		assertEquals(DP.maxProcessingTimeIndex	(1,5,4), 	 3);
		assertEquals(DP.maxProcessingTimeIndex	(1,5,14),	 4);
		assertEquals(DP.maxProcessingTimeIndex	(-1,1,1),	-1);
		assertEquals(DP.sum						(-1,1,1),	 0);
		assertEquals(DP.sum						(0,3,3),   193);
		assertEquals(DP.sum						(1,1,1),   	 0);
		assertEquals(DP.sum						(1,1,2),     0);
	}
}
