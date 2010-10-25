import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

public class JobListComparatorTest extends TestCase {

	List<Job> l;
	
	@Before
	public void setUp() {
		l = new ArrayList<Job>();
		
		l.add(new Job(10, 10));
		l.add(new Job(20, 20));
		l.add(new Job(5, 5));
		l.add(new Job(1,1));
	}
	
	@After
	public void tearDown() {
		// nothing to do here 
	}
	
	public void runTest() {
		testComparator();
	}
	
	public void testComparator() {
		Collections.sort(l, new NonDecreasingDeadlineOrderComparator());
		
		int prevDeadline = -1;
		for (Job j : l) {
			assertFalse(j.getDue() < prevDeadline);
			prevDeadline = j.getDue();
		}
	}
}
