import java.util.List;

/**
 * A problem consists of a list of jobs and a time when these jobs are supposed to start.
 */
public class Problem {
	private int start;
	private List<Job> jobs;
	
	public Problem(List<Job> jobs, int start)
	{
		this.start = start;
		this.jobs = jobs;
	}

	public int getStart() {
		return start;
	}

	public List<Job> getJobs() {
		return jobs;
	}
	
	// TODO add hashcode.
	
}
