import java.util.ArrayList;
import java.util.Comparator;

/**
 * This is a wrapper class for storing jobs in a list, such as when we read them from stdin.
 * Currently it does not do much.
 */
public class JobList extends ArrayList<Job> {
	/**
	 * With this comparator the list will be ordered in a non-decreasing deadline order.
	 */
	static class NonDecreasingDeadlineOrderComparator implements Comparator<Job>
	{
		@Override
		public int compare(Job o1, Job o2) {
			if (o1.getDue() < o2.getDue())
			{
				return -1;
			}
			else if (o1.getDue() > o2.getDue())
			{
				return 1;
			}
			else
			{
				return 0;
			}
		}
	}
}
