public class Jump {
	public int location;
	public int successors;
	
	public Jump(int location, int successors) {
		this.location = location;
		this.successors = successors;
	}
	
	public String toString() {
		return "(" + location + ", " + successors + ")";  
	}
}
