public class DynamicProgramming {
	
	private JobList 			jobs;
	private int[][][][]			Tardiness;
	/**
	 * The constructor assumes it receives jobs in non-decreasing deadline order
	 * @param j The list of jobs in non-decreasing deadline order
	 * */
	public DynamicProgramming(JobList jList)
	{
		jobs 		= jList;
		int n 		= jobs.size();
		Tardiness 	= new int[n][n][n][n*maxProcessingTime()];
	}
	
	/**
	 * @return The minimum total tardiness as computed by the Dynamic Programming algorithm 
	 * */
	public int minimumTotalTardiness()
	{
		return tardiness(0,jobs.size()-1,maxProcessingTimeIndex(),0);
	}
	
	private int tardiness(int i, int j, int k, int t)
	{
		if( i > j ) //Empty set
			return 0;
		if(Tardiness[i][j][k][t] != 0)	//already computed
			return Tardiness[i][j][k][t];
		int minTardiness = Integer.MAX_VALUE;
		for(int delta = 0; delta < jobs.size() - k; delta++)
		{
			int kPrime = maxProcessingTimeIndex(i,j,k);
			if (kPrime == -1)//S(i,j,k) = {i} = {j}
				return Math.max(0, t + jobs.get(i).getLength() - jobs.get(i).getDue());
			Tardiness[i][j][k][t] = tardiness(i,k+delta,kPrime,t) + Math.max(0,t + sumProcessingTimes(i,k+delta,kPrime) - jobs.get(kPrime).getDue()) +
			   						tardiness(kPrime + delta + 1, j, kPrime, sumProcessingTimes(i,k+delta,kPrime));
			if(Tardiness[i][j][k][t] < minTardiness)
				minTardiness = Tardiness[i][j][k][t];
		}
		return minTardiness;
	}

	/**
	 * @return The maximum processing time of all jobs, or -1 if the list is null or empty
	 * */
	public int maxProcessingTime()
	{
		if(jobs == null)
			return -1;
		if(jobs.size() == 0 )
			return -1;
		Job maximumJob = jobs.get(0); 
		for (Job j : this.jobs) {
			if(j.compareProcessingTimeTo(maximumJob) > 0)
				maximumJob = j;
		}
		return maximumJob.getLength();
	}
	
	/**
	 * @return The sum of processing times of all jobs within S(i,j,k), or -1 if the set is empty
	 * Assumes i, j and k are valid
	 * */
	public int sumProcessingTimes(int i,int j,int k)
	{
		if(jobs == null)
			return 0;
		if(jobs.size() == 0 )
			return 0;
		int 	sum = 0;
		boolean emptySet = true;
		Job jobK = jobs.get(k);
		for(int index = i; index <= j; index++)
			if(jobs.get(index).compareProcessingTimeTo(jobK) < 0)
			{
				sum 	+= jobs.get(index).getLength();
				emptySet = false;
			}
		if(emptySet)
			return -1;
		else
			return sum;
	}
	
	/**
	 * @return The index of the job having the maximum processing time, or -1 if the list is null or empty
	 * */
	public int maxProcessingTimeIndex()
	{
		if(jobs == null)
			return -1;
		if(jobs.size() == 0 )
			return -1;
		Job maximumJob 	= jobs.get(0);
		int index 		= 0;
		int maxIndex 	= 0; 
		for (Job j : this.jobs) {
			if(j.compareProcessingTimeTo(maximumJob) > 0)
			{
				maximumJob 	= j;
				maxIndex 	= index;
			}
			index++;
		}
		return maxIndex;
	}
	
	/**
	 * @return The index of the job between job i and job j (including i and j) having the 
	 * maximum processing time strictly less than the processing time of job k, or -1 if the 
	 * list is null or empty or if there is no such job. 
	 * */
	public int maxProcessingTimeIndex(int i,int j,int k)
	{
		if(jobs == null || i > j)
			return -1;
		int 	maxIndex	= i;
		boolean emptySet	= true;
		Job jobK 		= jobs.get(k);
		Job maximumJob	= new Job(-1,-1);
		for (int index = i; index <= j; index++) {
			if( jobs.get(index).compareProcessingTimeTo(jobK) < 0 )
			{
				emptySet = false;
				if(jobs.get(index).compareProcessingTimeTo(maximumJob) > 0)
				{
					maximumJob 	= jobs.get(index);
					maxIndex 	= index;
				}
			
			}
		}
		if(emptySet)
			return -1;
		else
			return maxIndex;
	}
}
