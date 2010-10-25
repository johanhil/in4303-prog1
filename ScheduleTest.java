import java.util.Collections;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

public class ScheduleTest extends TestCase {

	Schedule s;
	
	@Before
	public void setUp() {
		s = new Schedule();
	}
	
	@After
	public void tearDown() {
		// nothing to do here 
	}
	
	public void runTest() {
		testAddJob();
	}
	
	public void testAddJob() {
		s.add(new Job(2, 2));
		assertTrue("length", s.getTotalTime() == 2);
		assertTrue("depth", s.getDepth() == 1);
		assertTrue("tardiness", s.getTardiness() == 0);
	}
}
