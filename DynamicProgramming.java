import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DynamicProgramming implements Algorithm {	
	Map<List<Job>, Schedule> memoization = new HashMap<List<Job>, Schedule>();
	
	@Override
	public String getName() {
		return "DynamicProgramming";
	}

	@Override
	public Schedule solve(List<Job> jobs) {
		Collections.sort(jobs, new NonDecreasingDeadlineOrderComparator());
		return solve(new Problem(jobs, 0));
	}
	
	private Schedule solve(Problem p)
	{
		List<Job> jobs = p.getJobs();
		
		// use the memoized value, if any.
		if (memoization.containsKey(jobs)) 
			return memoization.get(jobs);	
		
		// take care of the base cases with 0 or 1 jobs.
		if (jobs.size() == 0)
		{
			memoization.put(jobs, new Schedule());
			return new Schedule();
		}
		
		if (jobs.size() == 1)
		{
			Schedule s = new Schedule().add(jobs.get(0));
			memoization.put(jobs, s);
			return s;
		}
		
		Job largestJob = JobListUtils.findMaxJob(jobs);		
		int largestJobIndex = jobs.indexOf(largestJob);
		
		/* 
		 * Find the delta that gives us the minimal tardiness
		 * by trying them all.
		 */
		int maxDelta = jobs.size() - largestJobIndex;
		Schedule minimumSchedule = null;
		for (int delta = 0; delta < maxDelta; delta++)
		{
			// divide and conquer the problem
			List<Job> leftProblemJobs = new ArrayList<Job>(jobs.subList(0, largestJobIndex + delta + 1));
			List<Job> rightProblemJobs = new ArrayList<Job>(jobs.subList(largestJobIndex + delta + 1, jobs.size()));
			leftProblemJobs.remove(largestJob);
			
			Problem left = new Problem(leftProblemJobs, 0);
			Problem right = new Problem(rightProblemJobs, 
					JobListUtils.totalTime(leftProblemJobs) + largestJob.getLength());
						
			Schedule leftSchedule = solve(left);
			Schedule rightSchedule = solve(right);
			
			// merge the solved subproblems into one schedule
			Schedule result = new Schedule().add(leftSchedule).add(largestJob).add(rightSchedule);

			// we only care about the smallest schedule
			if (minimumSchedule == null)
			{
				minimumSchedule = result;
			}
			else if (result.getTardiness() < minimumSchedule.getTardiness())
			{
				minimumSchedule = result;
			}
		}
		
		memoization.put(jobs, minimumSchedule);
		
		return minimumSchedule;
	}
}
