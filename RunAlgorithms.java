import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;
import java.util.Scanner;

public class RunAlgorithms {
	JobList jobs;
	
	public static void main (String[] args) {
		String input = args[0];
		RunAlgorithms main = new RunAlgorithms(input);
		
		main.printJobs();
	}

	public RunAlgorithms (String input)
	{
		this.jobs = readInput(input);
	}
	
	public JobList readInput(String filename) {
		Scanner s = null;
		int numJobs = 0;
		JobList jobs = new JobList();
		
		try {
			s = new Scanner(new BufferedReader(new FileReader(filename)));
			
			if (s.hasNextInt()) {
				numJobs = s.nextInt();
			}
			
			for (int i = 0; i < numJobs; i++) {
				jobs.add(new Job(s.nextInt(), s.nextInt()));
			}
			
			s.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		
		return jobs;
	}
	
	public void printJobs()
	{
		Collections.sort(jobs, new JobList.NonDecreasingDeadlineOrderComparator());
		
		for (Job j : this.jobs) {
			System.out.println(j);
		}
	}
}
