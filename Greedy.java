import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Greedy implements Algorithm {
	@Override
	public String getName() {
		return "Greedy";
	}

	@Override
	public Schedule solve(List<Job> jobs) {
		Schedule schedule = new Schedule();
		List<Job> joblist = new ArrayList<Job>(jobs);

		Collections.sort(joblist,
				new NonDecreasingDeadlineOrderComparator());

		for (Job j : joblist) {
			schedule.add(j);
		}

		return schedule;
	}
}
