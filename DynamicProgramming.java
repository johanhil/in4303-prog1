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
		Tardiness 	= new int[n][n][n][n*jobs.get(maxProcessingTimeIndex()).getLength()];
	}
	
	/**
	 * @return The minimum total tardiness as computed by the Dynamic Programming algorithm 
	 * */
	public int minimumTotalTardiness()
	{
		int k = maxProcessingTimeIndex();
		int minimumTardiness = Integer.MAX_VALUE;
		for(int delta = 0; delta < jobs.size() - k; delta++)
		{
			int cDelta		= 0;
			for(int i=0; i<= k+delta; i++)
				cDelta += jobs.get(i).getLength();
			int tardiness 	= tardiness(0,k+delta,k,0) 						+
							  Math.max(0, cDelta - jobs.get(k).getDue()) 	+
							  tardiness(k+delta+1,jobs.size()-1,k,cDelta);
			if(tardiness < minimumTardiness)
				minimumTardiness = tardiness;
		}	
		return minimumTardiness;
	}
	
	private int tardiness(int i, int j, int k, int t)
	{
		if( i > j )
			return 0;
		if(Tardiness[i][j][k][t] != 0)	//Already computed and memorized
			return Tardiness[i][j][k][t];
		int nElements 	= 0;
		Job lastJob 	= null;
		/* Count the number of elements in set S(i,j,k) and remember the last one.
		 * This will be useful if there is only one job
		 */
		for(int index = i; index <= j; index++)
			if(jobs.get(index).compareProcessingTimeTo(jobs.get(k)) < 0)
			{
				lastJob = jobs.get(index);
				nElements++;
			}
		if( nElements == 0)
		{
			return 0;
		}
		if( nElements == 1 )
		{
			Tardiness[i][j][k][t] = Math.max(0,t + lastJob.getLength() - lastJob.getDue());
			return Tardiness[i][j][k][t];
		}
		/*Calculate k' (which must exist now)*/
		int kPrime = i;
		for(int index = i + 1; index <= j; index++)
		{
			if( (jobs.get(index).compareProcessingTimeTo(jobs.get(k)) < 0) &&
				(jobs.get(index).compareProcessingTimeTo(jobs.get(kPrime)) > 0))
				{
					kPrime = index;
				}
		}
		int minimumTardiness = Integer.MAX_VALUE;
		for(int delta = 0; delta <= j - kPrime; delta++)
		{
			int cDelta		= 0;
			for(int index = i; index <= kPrime+delta; index++)
				if(jobs.get(index).compareProcessingTimeTo(jobs.get(k)) < 0)
					cDelta += jobs.get(index).getLength();
			int tardiness = tardiness(i, kPrime+delta, kPrime, t) 				+
							Math.max(0, t + cDelta - jobs.get(kPrime).getDue()) +
							tardiness(kPrime + delta + 1, j, kPrime, t + cDelta)		;
			if( tardiness < minimumTardiness )
				minimumTardiness = tardiness;
		}
		Tardiness[i][j][k][t] = minimumTardiness;
		return Tardiness[i][j][k][t];
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
}
	/**
	 * @return The sum of processing times of all jobs within S(i,j,k), or -1 if the set is empty
	 * Assumes i, j and k are valid
	 * */
/*	public int sumProcessingTimes(TreeSet<Job> set)
	{
		int sum = 0;
		for(Job job : set)
		{
			sum += job.getLength();
		}
		return sum;
	}*/
	
	
	
	/**
	 * @return The index of the job between job i and job j (including i and j) having the 
	 * maximum processing time strictly less than the processing time of job k, or -1 if the 
	 * list is null or empty or if there is no such job. 
	 * */
/*	public int maxProcessingTimeIndex(int i,int j,int k)
	{
		if( i > j)
			return -1;
		boolean emptySet 	= true;
		int 	maxIndex	= -1;
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
	
	private TreeSet<Job> S(TreeSet<Job> jobs, Job k)
	{
		TreeSet<Job> S = new TreeSet<Job>(new JobList.NonDecreasingDeadlineOrderComparator());
		
		// make sure that all parameters are OK
		if (jobs == null || k == null) return S; // TODO is this proper failing behavior?
		
		for (Job j : jobs)
		{
			if (j.getLength() < k.getLength())
			{
				S.add(j);
			}
		}
		
		return (TreeSet<Job>)S;
	}*/
	
	/**
	 * Returns the set of jobs between (inclusive) <tt>i</tt> and <tt>j</tt>
	 * @param i The start job.
	 * @param j The end job.
	 * @return The set of jobs between i and j from list.
	 */
/*	private TreeSet<Job> listToSet(Job i, Job j)
	{
		TreeSet<Job> set = new TreeSet<Job>(new JobList.NonDecreasingDeadlineOrderComparator());
		set.addAll(jobs);
		return (TreeSet<Job>) set.subSet(i, true, j, true);
	}*/
	
	/**
	 * Overloads S to accept arguments that are more similar to the notation in the paper by Lawler.
	 * @param i See paper.
	 * @param j See paper.
	 * @param k See paper.
	 * @param l The job list
	 * @return Returns the jobs between (inclusive) i and j where p_l < p_k for all i <= l <= k.
	 */
/*	public TreeSet<Job> S(int i, int j, int k)
	{
		if(i > j)
			return new TreeSet<Job>(new JobList.NonDecreasingDeadlineOrderComparator());
		Job I = jobs.get(i);
		Job J = jobs.get(j);
		Job K = jobs.get(k);
		
		TreeSet<Job> S = listToSet(I, J);
		return S(S, K);
	}
}*/
