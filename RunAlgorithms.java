import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

public class RunAlgorithms {
	JobList jobs;
	
	public static void main (String[] args) {
		
		if (args.length == 0)
		{
			System.err.println("No filename supplied.");
			System.exit(17);
		}
		
		String input = args[0];
		RunAlgorithms runner = new RunAlgorithms(input);
		
		// runner.printJobs(); // debug the input to see that everything looks ok.
		
		// run greedy
		runner.runGreedy();
		
		runner.runBFS();
	}


	public RunAlgorithms (String input) {
		this.jobs = readInput(input);
	}
	

	public void printJobs() {		
		for (Job j : this.jobs) {
			System.out.println(j);
		}
	}

	private void runGreedy() {
		Schedule schedule = greedy(jobs);
		System.out.println("Greedy generated a schedule with tardiness " + schedule.getTardiness());
	}
	
	private void runBFS() {
		Schedule schedule = bfs(jobs);
		System.out.println("BFS generated a schedule with tardiness " + schedule.getTardiness());
	}
	
	/**
	 * Greedy sorts the job in a non-decreasing deadline (due time) order and then returns the sorted jobs as a schedule. 
	 * @param jobs The jobs from which to build the schedule
	 * @return A job schedule
	 */
	private Schedule greedy(JobList jobs) {
		Schedule schedule = new Schedule();
		Collections.sort(jobs, new JobList.NonDecreasingDeadlineOrderComparator());

		for (Job j : jobs) {
			schedule = new Schedule(schedule, j);
		}
		
		return schedule;
	}
	
	private Schedule bfs(JobList jobs) {
		PriorityQueue<Schedule> Q = new PriorityQueue<Schedule>();
		
		for(int i = 0; i < jobs.size(); ++i){
			Q.offer(new Schedule(null, jobs.get(i)));
		}
		
		Schedule best_schedule = null;
		while(Q.peek() != null){
			Schedule s = Q.poll();
			
			// can be useful for debugging
			// System.err.println(Q.size());

			if(s.getDepth() >= jobs.size()){
				if(best_schedule == null || best_schedule.getTardiness() > s.getTardiness()){
					best_schedule = s;
				}
				continue;
			}
			
			if(best_schedule != null && best_schedule.getTardiness() < s.getTardiness()){
				continue;
			}
			
			for(int i = 0; i < algorithms.num_jobs; ++i){
				if(s.contains(jobs.get(i)) == false){
					Q.offer(new Schedule(s, jobs.get(i)));
				}
			}
		}
		return best_schedule;
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
	

}
