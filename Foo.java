import java.util.TreeSet;

public class Foo {
	
	JobList l;
	
	Foo()
	{
		l = new JobList();
		
		l.add(new Job(10, 10));
		l.add(new Job(20, 20));
		l.add(new Job(5, 5));
		l.add(new Job(1,1));
	}
	
	public static void main() {
		Foo n = new Foo();
		//n.
	}
	
	/**
	 * 
	 * @param jobs The list of jobs between (inclusive) i and j, as denoted in the paper by lawler
	 * @param k The job with the largest processing time.
	 * @return S The set with all jobs from <tt>jobs</tt> where p_j < p_k. 
	 */
	TreeSet<Job> S(TreeSet<Job> jobs, Job k)
	{
		TreeSet<Job> S = new TreeSet<Job>();
		
		// make sure that all parameters are OK
		if (jobs == null || k == null) return S; // TODO is this proper failing behaviour?
		
		for (Job j : jobs)
		{
			if (j.getLength() < k.getLength())
			{
				S.add(j);
			}
		}
		
		return S;
	}
	
	/**
	 * Returns the set of jobs between (inclusive) <tt>i</tt> and <tt>j</tt> in the list <tt>list</tt>.
	 * @param list The job list.
	 * @param i The start job.
	 * @param j The end job.
	 * @return The set of jobs between i and j from list.
	 */
	TreeSet<Job> listToSet(JobList list, Job i, Job j)
	{
		TreeSet<Job> set = new TreeSet<Job>(new JobList.NonDecreasingDeadlineOrderComparator());
		set.addAll(list);
		return (TreeSet<Job>) set.subSet(i, true, j, true);
	}
	
	/**
	 * Overloads S to accept arguments that are more similar to the notation in the paper by Lawler.
	 * @param i See paper.
	 * @param j See paper.
	 * @param k See paper.
	 * @param l The job list
	 * @return Returns the jobs between (inclusive) i and j where p_l < p_k for all i <= l <= k.
	 */
	TreeSet<Job> S(int i, int j, int k, JobList l)
	{
		Job I = l.get(i);
		Job J = l.get(j);
		Job K = l.get(k);
		
		TreeSet<Job> S = listToSet(l, I, J);
		return S(S, K);
	}
}
