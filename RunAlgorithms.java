import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class RunAlgorithms {
	List<Job> jobs;
	
	public static void main (String[] args) {
		
		if (args.length == 0)
		{
			System.err.println("No filename supplied.");
			System.exit(17);
		}

		String input = args[0];
		List<Algorithm> algorithms = new LinkedList<Algorithm>();
		
		algorithms.add(new Greedy());
		algorithms.add(new DynamicProgramming());
		algorithms.add(new FPTAS(0.5));
		//algorithms.add(new BestFirstSearch());

		RunAlgorithms runner = new RunAlgorithms(input);
		
		runner.run(algorithms);
	}


	public RunAlgorithms(String input) {
		this.jobs = readInput(input);
	}
	
	public void run(List<Algorithm> algorithms) {
		for (Algorithm a : algorithms) {
			Schedule solution = a.solve(jobs);
			System.out.println(a.getName() + " produced a schedule with tardiness " + solution.getTardiness());
			
			//for (Job j : solution.getJobs())
			//{
			//	System.err.println(j);
			//}
		}
		
	}

	public void printJobs() {		
		for (Job j : this.jobs) {
			System.out.println(j);
		}
	}
		
	public List<Job> readInput(String filename) {
		Scanner s = null;
		int numJobs = 0;
		List<Job> jobs = new ArrayList<Job>();
		
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
	

}
