import java.util.Collections;
import java.util.LinkedList;

/**
 * Program used for computing a knight's tour for a variably sized board.
 * Tour can be printed in either the console or on a board interface.
 * A fewest successor heuristic was employed to reduce computation time.
 * @author Joan Ressing
 */
public class Main {
	private static int SIZE, STEPS_NEEDED;
	
	public static void main(String[] args) {
		if(args.length > 0)
			SIZE = Integer.parseInt(args[0]);
		else
			SIZE = 8;
		STEPS_NEEDED = SIZE*SIZE-1;
		
		int[] tour = new int[SIZE*SIZE];
		boolean[] visited = new boolean[SIZE*SIZE];
		visited[0] = true;
		
		boolean tourFound = createTour(tour, visited, 0, 0);
		
		if(tourFound)
			playConsoleTour(tour);
		else
			System.out.println("No tour possible for given SIZE.");
	}

	/**
	 * Recursively computes the knight's tour using a fewest successor heuristic.
	 * @param tour:		 	array used to store the tour
	 * @param visited:		array to keep track of visited fields
	 * @param pos:			current position
	 * @param stepsTaken:	number of steps taken
	 * @return whether tour was found
	 */
	private static boolean createTour(int[] tour, boolean[] visited, int pos, int stepsTaken) {
		if(stepsTaken == STEPS_NEEDED)
			return true;
		
		LinkedList<Jump> jumpOptions = findPossibleJumps(visited, pos);
		Collections.sort(jumpOptions, new FewestSuccessors());				//sort using fewest successor heuristic
		
		for(Jump jump: jumpOptions) {
			//prepare
			visited[jump.location] = true;
			tour[stepsTaken+1] = jump.location;
			
			//recursion
			if(createTour(tour, visited, jump.location, stepsTaken+1))
				return true;
			
			//repair
			visited[jump.location] = false;
		}
		
		return false;		
	}
	
	private static void print10(int[] tour) {
		for(int i=0; i<10; i++)
			System.out.print(tour[i] + "\t");
		System.out.println();
	}

	/**
	 * Compute list of possible jump locations.
	 * @param visited locations
	 * @param position of knight
	 * @return
	 */
	private static LinkedList<Jump> findPossibleJumps(boolean[] visited, int position) {
		LinkedList<Jump> jumps = new LinkedList<Jump>();
		
		int y = position % SIZE;
		int x = position / SIZE;

		checkAndAdd(jumps, visited, x-1, y-2);
		checkAndAdd(jumps, visited, x-2, y-1);
		checkAndAdd(jumps, visited, x+1, y-2);
		checkAndAdd(jumps, visited, x-2, y+1);
		checkAndAdd(jumps, visited, x+2, y-1);
		checkAndAdd(jumps, visited, x-1, y+2);
		checkAndAdd(jumps, visited, x+2, y+1);
		checkAndAdd(jumps, visited, x+1, y+2);
		
		return jumps;
	}

	/**
	 * Check if jump location is within bounds.
	 * @param x coordinate of potential jump.
	 * @param y coordinate of potential jump.
	 */
	private static void checkAndAdd(LinkedList<Jump> jumps, boolean[] visited, int x, int y) {
		if(x < 0 || y < 0 || x > SIZE-1 || y > SIZE-1)
			return;
		
		int jumpLocation = x*SIZE + y;
		
		if(visited[jumpLocation] != true) {
			int successors = numberOfSuccessors(visited, jumpLocation);
			jumps.add(new Jump(jumpLocation, successors));
		}
	}
	
	/**
	 * Computes the number of possible jump locations.
	 * @param visited locations
	 * @param position of knight
	 * @return number of possible jumps
	 */
	private static int numberOfSuccessors(boolean[] visited, int position) {
		int successors = 0;
		
		int x = position % SIZE;
		int y = position / SIZE;

		if(check(visited, x-1, y-2)) successors++;
		if(check(visited, x-2, y-1)) successors++;
		if(check(visited, x+1, y-2)) successors++;
		if(check(visited, x-2, y+1)) successors++;
		if(check(visited, x+2, y-1)) successors++;
		if(check(visited, x-1, y+2)) successors++;
		if(check(visited, x+2, y+1)) successors++;
		if(check(visited, x+1, y+2)) successors++;
		
		return successors;
	}

	/**
	 * Checks whether location can be jumped to.
	 */
	private static boolean check(boolean[] visited, int x, int y) {
		if(x < 0 || y < 0 || x > SIZE-1 || y > SIZE-1)
			return false;
		
		return visited[x*SIZE + y] != true;
	}

	private static void playConsoleTour(int[] tour) {
		int[][] grid = new int[SIZE][SIZE];
		int n=1;
		
		for(int loc: tour) {
			grid[loc % SIZE][loc / SIZE] = n;
			n++;
		}

		dashes();
		for(int[] row: grid) {
			System.out.print("|  ");
			for(int pos: row)
				System.out.print(pos + "\t|  ");
			dashes();
		}
	}

	private static void dashes() {
		System.out.println();
		for(int i=0; i<SIZE; i++)
			System.out.print("--------");
		System.out.println("-");
	}
}
