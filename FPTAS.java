package programming1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class FPTAS extends DynamicProgramming
{
	private double epsilon;
	
	public FPTAS(double eps)
	{
		super();
		epsilon = eps;
	}
	@Override
	public Schedule solve(List<Job> jobs)
	{
		Collections.sort(jobs, new NonDecreasingDeadlineOrderComparator());
		// Build the EDD order schedule and retrieve the maximum tardiness 
		// of any job in that schedule
		Schedule scheduleEDD 		= new Schedule();
		// The initial tardiness of the empty schedule is 0
		double 	 previousTardiness 	= 0;
		// The maximum tardiness of any job in the schedule
		double 	 maxTardiness		= 0;
		for(Job j : jobs)
		{
			scheduleEDD.add(j);
			// Invariant: the  total tardiness of the whole schedule after adding the new job
			// is the sum of the tardiness prior to adding the job and the tardiness of that job
			double newJobTardiness = scheduleEDD.getTardiness() - previousTardiness;
			// Update the previous tardiness
			previousTardiness	= scheduleEDD.getTardiness();
			// Is the job just added the new job with the maximum tardiness?
			if( newJobTardiness > maxTardiness )
				maxTardiness = newJobTardiness;
		}
		if( maxTardiness == 0)
			// EDD is optimal
			return scheduleEDD;
		// If EDD isn't optimal, use maxTardiness to determine the scaleFactor
		double scaleFactor  = (((2*epsilon)/(jobs.size()*(jobs.size()+1)))*maxTardiness);
		ArrayList<Job> rescaledJobs = new ArrayList<Job>();
		
		// Memorize initial processing times
		HashMap<Job,Integer> initialProcessingTime = new HashMap<Job,Integer>();
		for(Job j : jobs)
		{
			Job rescaledJob = new Job((int)(j.getLength()/scaleFactor),j.getDue()/scaleFactor);
			initialProcessingTime.put(rescaledJob, j.getLength());
			rescaledJobs.add(rescaledJob);
		}
		Schedule rescaledSchedule = super.solve(rescaledJobs);
		//Bring schedule back to scale
		Schedule result = new Schedule();
		for(Job j:rescaledSchedule.getJobs())
		{
			result.add(new Job(initialProcessingTime.get(j),scaleFactor*j.getDue()));
		}
		return result;
	}
	
	@Override
	public String getName()
	{
		return "FPTAS";
	}
}
