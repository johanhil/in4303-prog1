import java.util.List;

public interface Algorithm {
	public String getName();
	public Schedule solve(List<Job> jobs);
}
