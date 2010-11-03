import java.io.*;
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
		/*
		for (Algorithm a : algorithms) {
			Schedule solution = a.solve(jobs);
			System.out.println(a.getName() + " produced a schedule with tardiness " + solution.getTardiness());
			
			//for (Job j : solution.getJobs())
			//{
			//	System.err.println(j);
			//}
		}*/
		try
		{
			BufferedWriter output = new BufferedWriter(new FileWriter("output0.4_1.0.txt"));
			String line = new String();
			for(Algorithm a : algorithms)
			{
				line += a.getName();
				line += "\t\t\t";
			}
			line += System.getProperty("line.separator");
			output.write(line);
			
			float[] rdd = {0.2f,0.4f,0.6f,0.8f,1.0f};
			int[]	sz  = {5,10,15,20,25,30,35,40,45,50,55,60,65,70,75,80,85,90,95,100};
			for(int i=0; i<rdd.length; i++)
				for(int j=0; j<rdd.length; j++)
					for(int k=0; k<sz.length; k++)
					{
						line = new String();
						String currentFile = "tests/random_RDD="+rdd[i]+"_TF="+rdd[j]+"_#"+sz[k]+".dat";
						this.jobs = readInput(currentFile);
						for( Algorithm a: algorithms)
						{
							long startTime = System.currentTimeMillis();
							Schedule solution = a.solve(jobs);
							long elapsedTime = System.currentTimeMillis() - startTime;
							line += solution.getTardiness()+"\t";
							line += elapsedTime +" ms\t";
						
						}
						line += currentFile;
						line += System.getProperty("line.separator");
						output.write(line);
					}
			output.close();
		}
		catch(IOException ex)
		{
			System.err.println("Error opening file");
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
