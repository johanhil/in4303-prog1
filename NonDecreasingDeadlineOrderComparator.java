import java.util.Comparator;

public class NonDecreasingDeadlineOrderComparator implements
		Comparator<Job> {
	@Override
	public int compare(Job o1, Job o2) {
		if (o1.getDue() < o2.getDue()) {
			return -1;
		} else if (o1.getDue() > o2.getDue()) {
			return 1;
		} else {
			return 0;
		}
	}
}
