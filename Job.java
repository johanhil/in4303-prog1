
public class Job {
	private int length;
	private double due;
	
	public Job (int length, double due) {
		this.length = length;
		this.due = due;
	}
	
	public int getLength() {
		return length;
	}
	public double getDue() {
		return due;
	}
	
	@Override
	public boolean equals(Object job) {
		Job j = (Job) job;
		return (j.getLength() == this.getLength() && j.getDue() == this.getDue());
	}
	
	@Override
	public String toString()
	{
		return "{ len: " + getLength() + ", due: " + getDue() + "}";
	}
	
	/**
	 * Compares the processing time of this job to the one of job <tt>j</tt> and returns -1/0/1 if this job's time is smaller/equal/larger.
	 * @param j the job which this job will be compared to
	 * @return -1/0/1 if, respectively, this job's processing time is smaller/equal to/larger than j's.
	 */
	public int compareProcessingTimeTo(Job j)
	{
		if (this.getLength() < j.getLength()) {
			return -1;
		}
		else if (this.getLength() == j.getLength()) {
			return 0;
		}
		else {
			return 1;
		}
	}
}
