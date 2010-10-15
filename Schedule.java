
/**
 * This is a very similar class to the one in algorithms.java, just extracted from there and having small changes.
 */
public class Schedule {
	private Schedule previous;
	private Job job; // job is the job that was scheduled in this node (if any)
	
	// we memoize tardiness, for some reason that's probably useful.
	private int tardiness;
	
	/**
	 * Initialize an empty schedule.
	 */
	public Schedule() {
		job = null;
		previous = null;
		tardiness = 0;
	}
	
	/**
	 * Add a job to the schedule.
	 * @param previous the schedule which will be the previous schedule in this new schedule TODO fix this description :(
	 * @param job The job that will be added to the schedule.
	 */
	public Schedule(Schedule previous, Job job) {
		this.previous = previous;
		this.job = job;
		// TODO we assume that job will not be null in the assignment of tardiness, not good
		this.tardiness = Math.max(0, getTotalTime() - job.getDue());
		
		if(previous != null)
			tardiness += previous.tardiness;
	}
	
	// used by the best-first search
	// currently, schedules are traversed in smallest total tardiness order
	public int compareTo(Object o) {
		return (getTardiness()) - (((Schedule)o).getTardiness());
		
		// replace with the following to get a depth-first search
		// return get_depth() - ((schedule)o).get_depth();
	}
	
	public int getDepth() {
		if(previous != null)
			return previous.getDepth() + 1;
		return 1;
	}
	
	public int getTotalTime() {
		if(previous != null)
			return previous.getTotalTime() + job.getLength();
		if (job == null) return 0; // TODO this is a lil weird. must be a better way
		return job.getLength();
	}
	
	public int getTardiness() {
		return tardiness;
	}
	
	public boolean contains(Job job) {
		return (this.job == job) || (previous != null && previous.contains(job));
	}
	
	@Override
	public String toString() {
		if (previous == null) {
			if (job == null) return "foo"; // TODO don't like this at all. there's a bigger problem going on here.
			return job.toString();
		}
		return previous.toString() + "->" + job.toString();  
	}
}
