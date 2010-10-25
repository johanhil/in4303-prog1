import java.util.List;

public class JobListUtils {
	/**
	 * Finds the job in jobs with the longest processing time.
	 * @param jobs The list of jobs.
	 * @return The job with the longest processing time.
	 */
	public static Job findMaxJob(List<Job> jobs)
	{
		int maxLength = 0;
		Job maxJob = null;
		
		for (Job j : jobs)
		{
			if (j.getLength() > maxLength)
			{
				maxJob = j;
				maxLength = j.getLength();
			}
		}
		
		return maxJob;
	}
	
	/**
	 * Returns the total processing times of the jobs in jobs.
	 * @param jobs The list of jobs.
	 * @return The total processing time.
	 */
	public static int totalTime(List<Job> jobs)
	{
		int totalTime = 0;
		
		for (Job j : jobs)
		{
			totalTime =+ j.getLength();
		}
		
		return totalTime;
	}
	
	
}
