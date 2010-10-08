
public class Job {
	private int length;
	private int due;
	
	public Job (int length, int due) {
		this.length = length;
		this.due = due;
	}
	
	public int getLength() {
		return length;
	}
	public int getDue() {
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
}
