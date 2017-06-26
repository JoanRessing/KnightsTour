import java.util.Comparator;

public class FewestSuccessors implements Comparator<Jump> {

	@Override
	public int compare(Jump j1, Jump j2) {
		return j1.successors - j2.successors;
	}
	
}
