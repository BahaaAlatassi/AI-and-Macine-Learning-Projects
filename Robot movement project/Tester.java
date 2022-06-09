import java.util.*;

public abstract class Tester {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		int input ;
		Robot o1;
		
		do
		{
			System.out.println("Please choose the number of which Algorithm and tree to apply: ");
			System.out.println("1- Greedy Best First Search with a graph. ");
			System.out.println("2- A* Search with a graph. ");
			System.out.println("3- A* Search with a tree. ");
			System.out.println("4- to quite. ");
			System.out.println("");
			
			input = sc.nextInt();
			
			if (input == 1) { new Robot("GBFS", "graph");}
			else if (input == 2) { new Robot("A*", "graph");}
			else if  (input == 3) {new Robot("A*", "tree");}
			
			System.out.println("");
		}
		while (input !=4);
		

		

	}

}
