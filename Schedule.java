import java.util.ArrayList;
import java.util.List;

public class Schedule implements Comparable<Schedule> {
	private List<Job> jobs;
	// memoize tardiness and totalTime
	private double tardiness;
	private int totalTime;

	public Schedule() {
		jobs = new ArrayList<Job>();
		tardiness = 0.0;
		totalTime = 0;
	}

	public Schedule add(Job job) {
		if (job == null) return this;
		
		jobs.add(job);
		totalTime += job.getLength();
		tardiness += Math.max(0, getTotalTime() - job.getDue());
		
		return this;
	}

	public boolean contains(Job job) {
		return jobs.contains(job);
	}

	// TODO why do is this named getDepth? Hm.
	public int getDepth() {
		return jobs.size();
	}

	public double getTardiness() {
		return tardiness;
	}

	public int getTotalTime() {
		return totalTime;
	}
	
	public List<Job> getJobs() {
		return jobs;
	}

	@Override
	public int compareTo(Schedule schedule) {
		return getTardiness() > schedule.getTardiness()?1:
				((getTardiness() == schedule.getTardiness())?0:-1);
	}

	public Schedule add(Schedule schedule) {
		if (this == schedule) return this;
		
		for (Job j : schedule.getJobs())
			this.add(j);
		
		return this;
	}

}
