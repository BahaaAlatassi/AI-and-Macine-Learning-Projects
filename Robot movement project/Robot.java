import java.text.DecimalFormat;
import java.util.*;

public class Robot {
	
	List<String> nodes = new ArrayList<String>(
	Arrays.asList("A", "C", "B", "D", "E", "F", "G", "H") );
	
	List<Integer> nodes_heuristic = new ArrayList<Integer>(
	Arrays.asList(5, 8, 6, 4, 4, 5, 2, 0) );
	
	List<String> frontier = new ArrayList<String>( Arrays.asList("A") ) ; 
	
	String goal_node = "H";
	int cost= 0;
	
	List<String> result = new ArrayList<String>() ;
	List<String> explored_set = new ArrayList<String>() ;;
	
	
	public Robot(String algorithm, String tree_type)
	{
		if (algorithm == "GBFS" && tree_type == "graph")
		{
			result = gbfs_graph();
			System.out.println("the result is : "+ result);
		}
		
		else if (algorithm == "A*" && tree_type == "graph")
		{
			result = a_star_graph();
			System.out.println("the result is : "+ result);
		}
		
		else if (algorithm == "A*" && tree_type == "tree")
		{
			result = a_star_tree();
			System.out.println("the result is : "+ result);
		}

		else
		{
			System.out.println("Error selecting algorithm or tree/graph type!!");
		}
	}

	public List<String> gbfs_graph()
	{
		 int h=1000;
		 String temp_node="";
		 do 
		 {
			 for (int i=0; i < frontier.size(); i++) //iterating in the frontier to confine the node with the least heuristic value
			 {
				 if (  (h >nodes_heuristic.get( nodes.indexOf(frontier.get(i) ) )) && !(explored_set.contains( frontier.get(i) ) ) )
					// if the value the heuristic of the picked frontier node is less than h and the node is not in the explored set then assign its heuristic to h and select it
					 // temporarily to potentially consider it for expansion if other nodes in the frontier do not have less value 
				 { 
					 h = nodes_heuristic.get( nodes.indexOf(frontier.get(i) ) );
					 
					 temp_node = frontier.get(i); 
				 } 
			 }
			 explored_set.add(temp_node); //add the node in the frontier of the least heuristic value to the explored set. so we avoid visiting it again and the loopy path occurrence 
			 result.add(temp_node); // storing the concluded node in the results array list
			 frontier.clear();// clear the frontier so we can study other nodes
			 expand(temp_node); // expand the nodes in reference to the selected node and the function will add the children to the frontier
			 h=1000; //reset x value. 1000 is chosen as it exceeds the value of all heuristics to allow picking the smallest one.			 
		 }		 
		 while (temp_node != goal_node ); 		  
		 return  result;	
	}
	
	public List<String> a_star_graph()
	{
		frontier.clear(); 
		frontier.add("A00"); //Frontier is filled here with the node preceded with 2 digits that show the cost spent to reach this node 
		int h; //heuristic value
		int g; // the sum of steps cost
		int f = 1000;
		String temp_node="00"; //a node to hold the node value temporarily 
		String least_value="A"; //to compare the frontier components. It was initialized to avoid empty String manipulation error in the first iteration.
		 
		 do 
		 {
			 for (int i=0; i < frontier.size(); i++) //iterating in the frontier to confine the node with the least F value
			 {

					 h = nodes_heuristic.get( nodes.indexOf(String.valueOf(frontier.get(i).charAt(0)) ) ); //getting the heuristic value using the class arrays
					 g= Integer.parseInt( frontier.get(i).substring(frontier.get(i).length()-2)   );
					 // g is set when any node in the frontier is expanded as the last 2 digits. The step cost is automatically calculated and added in the expand2() function.
					 // expand2() function also checks the explored set and expand the nodes to the frontier only those that were not expanded before.
					 if (f> g+h) {f=g+h; least_value = frontier.get(i);} // now checking the F value comparing to other nodes in the frontier apart the ones in the explored set.		 	 
			 }
			 temp_node = least_value;
			 least_value=""; //resetting least value to null before starting the new iteration
			 explored_set.add(temp_node.substring(0,1)); //adding the qualified node to the explored set. Only the node name is added, without the aggregated cost steps value.
			 result.add(String.valueOf(temp_node.charAt(0))); // storing the concluded node in the results array list
			 f = 1000;// resetting F value, so its ready for the comparison with the new nodes in the frontier in the next iteration
			 expand2(temp_node); // expand the nodes in reference to the selected nodes and the function will add the children to the frontier in addition to the aggregated cost value
			 					 // expand2() function also checks the explored set and expand the nodes to the frontier only those that were not expanded before.
		 }
		 while (!("H".equals(temp_node.substring(0,1)))); 
		 return  result;
	}
	
	public List<String> a_star_tree()
	{
		frontier.clear(); 
		frontier.add("A00"); //Frontier is filled here with the node preceded with 2 digits that show the cost spent to reach this node 
		int h; //heuristic value
		int g; // the sum of steps cost
		int f = 1000;
		String temp_node="00"; //a node to hold the node value temporarily 
		String least_value="A"; //to compare the frontier components. It was initialized to avoid empty String manipulation error in the first iteration.
		 do 
		 {
			 for (int i=0; i < frontier.size(); i++) //iterating in the frontier to confine the node with the least F value
			 {
				//No explored set as this is a tree algorithm not a graph
				 
					 h = nodes_heuristic.get( nodes.indexOf(String.valueOf(frontier.get(i).charAt(0)) ) ); //getting the heuristic value using the class arrays
					 g= Integer.parseInt( frontier.get(i).substring(frontier.get(i).length()-2)   );
					 // g is set when any node in the frontier is expanded as the last 2 digits. The step cost is automatically calculated and added in the expand3() function.

					 if (f> g+h) {f=g+h; least_value = frontier.get(i);} // now checking the F value comparing to other nodes in the frontier apart the ones in the explored set.				 
				
			 }
			 temp_node = least_value;
			 least_value=""; //resetting least value to null before starting the new iteration
			 //explored_set.add(temp_node.substring(0,1)); //adding the qualified node to the explored set. Only the node name is added, without the aggregated cost steps value.
			 result.add(String.valueOf(temp_node.charAt(0))); // storing the concluded node in the results array list
			 f = 1000;// resetting F value, so its ready for the comparison with the new nodes in the frontier in the next iteration
			 expand3(temp_node); // expand the nodes in reference to the selected nodes and the function will add the children to the frontier in addition to the aggregated cost value
		 }
		 while ( !("H".equals(temp_node.substring(0,1)))); 
		 return  result;
	}
	public int transition_cost(String x, String y) //function that takes 2 nodes and return the step cost value between both of them
	{
		if ("A".equals(x) && "B".equals(y)) {return 3;} // instead of == comparison, .equals() is used as strings are objects and cant be compared.
		else if ("B".equals(x) && "A".equals(y)) {return 3;}
		else if ("A".equals(x) && "C".equals(y)) {return 3;}
		else if ("C".equals(x) && "A".equals(y)) {return 3;}
		else if ("B".equals(x) && "D".equals(y)) {return 2;}
		else if ("D".equals(x) && "B".equals(y)) {return 2;}
		else if ("C".equals(x) && "F".equals(y)) {return 3;}
		else if ("F".equals(x) && "C".equals(y)) {return 3;}
		else if ("D".equals(x) && "E".equals(y)) {return 4;}
		else if ("E".equals(x) && "D".equals(y)) {return 4;}
		else if ("E".equals(x) && "F".equals(y)) {return 1;}
		else if ("F".equals(x) && "E".equals(y)) {return 1;}
		else if ("E".equals(x) && "G".equals(y)) {return 2;}
		else if ("G".equals(x) && "E".equals(y)) {return 2;}
		else if ("F".equals(x) && "G".equals(y)) {return 3;}
		else if ("G".equals(x) && "F".equals(y)) {return 3;}
		else if ("G".equals(x) && "H".equals(y)) {return 2;}
		else if ("H".equals(x) && "G".equals(y)) {return 2;}
		else {return 0;}
	}
	
	public int node_heuristic(String node)
	{
		if (node == "A") {return 5;}
		else if (node == "B") {return 6;}
		else if (node == "C") {return 8;}
		else if (node == "D") {return 4;}
		else if (node == "E") {return 4;}
		else if (node == "F") {return 5;}
		else if (node == "G") {return 2;}
		else if (node == "H") {return 0;}
		else {return 100;}
	}
	
	public void  expand(String node) // This function to expand the GBFS nodes.
	{
		if (node == "A") {frontier.add("B"); frontier.add("C");}
		else if (node == "C") {frontier.add("A"); frontier.add("F");}
		else if (node == "B") {frontier.add("A"); frontier.add("D");}
		else if (node == "D") {frontier.add("B"); frontier.add("E");}
		else if (node == "E") {frontier.add("D"); frontier.add("G"); frontier.add("F");}
		else if (node == "F") {frontier.add("C"); frontier.add("E"); frontier.add("G");}
		else if (node == "G") {frontier.add("E"); frontier.add("F"); frontier.add("H");}
		else if (node == "H") {System.out.println("node "+ node + " is reached!!");}
		else {System.out.println("node "+ node + " doesnt exist!");}
	}
	
	public void  expand2(String node_in) // This function is for A* graph
										 // This function analyze the A* node, extracting the node name and the aggregated cost and expand next node adding the cost to the original one.
	{
		int temp_cost = Integer.parseInt( node_in.substring(node_in.length()-2)); //extracting the aggregated cost value from the passed node.
		int original_cost = temp_cost;
		String node = node_in.substring(0,1); //extracting the node name to expand it below.
		DecimalFormat formatter = new DecimalFormat("00"); // an object that used below to keep the aggregated step value as 2 digits, so extracting them is easy.
		String temp_value;
		
		if ("A".equals(node)) // .equals is used as here we are comparing an object (string) to a string. It doesn't match if (==) is used instead.
			{
			temp_cost = temp_cost +3;
			temp_value = formatter.format(temp_cost); 
			if ( !(explored_set.contains("B") ) ) { frontier.add("B" + temp_value);}
			temp_cost = 0; temp_value="";
			
			temp_cost = original_cost +3;
			temp_value = formatter.format(temp_cost);
			if ( !(explored_set.contains("C") ) ) { frontier.add("C" + temp_value);}
			temp_cost = 0; temp_value=""; original_cost=0;
			}
		
		else if ("C".equals(node)) 
			{
			temp_cost = temp_cost +3;
			temp_value = formatter.format(temp_cost);
			if ( !(explored_set.contains("A") ) ) { frontier.add("A" + temp_value); }
			temp_cost = 0; temp_value="";
			
			temp_cost = original_cost +3;
			temp_value = formatter.format(temp_cost);
			if ( !(explored_set.contains("F") ) ) { frontier.add("F" + temp_value); }
			temp_cost = 0; temp_value=""; original_cost=0;
			}
		else if ("B".equals(node)) 
			{
			temp_cost = temp_cost +3;
			temp_value = formatter.format(temp_cost);
			if ( !(explored_set.contains("A") ) ) { frontier.add("A" + temp_value); }
			temp_cost = 0; temp_value="";
			
			temp_cost = original_cost +2;
			temp_value = formatter.format(temp_cost);
			if ( !(explored_set.contains("D") ) ) { frontier.add("D" + temp_value); }
			temp_cost = 0; temp_value=""; original_cost=0;
			}
		else if ("D".equals(node)) 
			{
			temp_cost = temp_cost +2;
			temp_value = formatter.format(temp_cost);
			if ( !(explored_set.contains("B") ) ) { frontier.add("B" + temp_value); }
			temp_cost = 0; temp_value="";
			
			temp_cost = original_cost +4;
			temp_value = formatter.format(temp_cost);
			if ( !(explored_set.contains("E") ) ) { frontier.add("E" + temp_value); }
			temp_cost = 0; temp_value=""; original_cost=0;
			}
		else if ("E".equals(node)) 
			{
			temp_cost = temp_cost +4;
			temp_value = formatter.format(temp_cost);
			if ( !(explored_set.contains("D") ) ) { frontier.add("D" + temp_value); }
			temp_cost = 0; temp_value="";
			
			temp_cost = original_cost +1;
			temp_value = formatter.format(temp_cost);
			if ( !(explored_set.contains("F") ) ) { frontier.add("F" + temp_value); }
			temp_cost = 0; temp_value="";
			
			temp_cost = original_cost +2;
			temp_value = formatter.format(temp_cost);
			if ( !(explored_set.contains("G") ) ) { frontier.add("G" + temp_value); }
			temp_cost = 0; temp_value=""; original_cost=0;
			}
		else if ("F".equals(node)) 
			{
			temp_cost = temp_cost +3;
			temp_value = formatter.format(temp_cost);
			if ( !(explored_set.contains("C") ) ) { frontier.add("C" + temp_value); }
			temp_cost = 0; temp_value="";
			
			temp_cost = original_cost +1;
			temp_value = formatter.format(temp_cost);
			if ( !(explored_set.contains("E") ) ) { frontier.add("E" + temp_value); }
			temp_cost = 0; temp_value="";
			
			temp_cost = original_cost +3;
			temp_value = formatter.format(temp_cost);
			if ( !(explored_set.contains("G") ) ) { frontier.add("G" + temp_value); }
			temp_cost = 0; temp_value=""; original_cost=0;
			}
		else if ("G".equals(node)) 
			{
			temp_cost = temp_cost +3;
			temp_value = formatter.format(temp_cost);
			if ( !(explored_set.contains("F") ) ) { frontier.add("F" + temp_value); }
			temp_cost = 0; temp_value="";
			
			temp_cost = original_cost +2;
			temp_value = formatter.format(temp_cost);
			if ( !(explored_set.contains("E") ) ) { frontier.add("E" + temp_value); }
			temp_cost = 0; temp_value="";
			
			temp_cost = original_cost +2;
			temp_value = formatter.format(temp_cost);
			if ( !(explored_set.contains("H") ) ) { frontier.add("H" + temp_value); }
			temp_cost = 0; temp_value=""; original_cost=0;
			}
		else if ("H".equals(node)) {System.out.println("node "+ node + " is reached!!");}
		else {System.out.println("node "+ node + " doesnt exist!");}
		
		frontier.remove(node_in); //no need for the parent node to be in the frontier as it was already expanded.
	}

	public void  expand3(String node_in) // This function is for A* tree
										 // This function analyze the A* node, extracting the node name and the aggregated cost and expand next node adding the cost to the original one.
	{
		int temp_cost = Integer.parseInt( node_in.substring(node_in.length()-2)); //extracting the aggregated cost value from the passed node.
		int original_cost = temp_cost;
		String node = node_in.substring(0,1); //extracting the node name to expand it below.
		DecimalFormat formatter = new DecimalFormat("00"); // an object that used below to keep the aggregated step value as 2 digits, so extracting them is easy.
		String temp_value;
		frontier.remove(node_in); //no need for the parent node to be in the frontier as it was already expanded.
		
		if ("A".equals(node)) // .equals is used as here we are comparing an object (string) to a string. It doesn't match if (==) is used instead.
			{
			temp_cost = temp_cost +3;
			temp_value = formatter.format(temp_cost); 
			frontier.add("B" + temp_value);
			temp_cost = 0; temp_value="";
			
			temp_cost = original_cost +3;
			temp_value = formatter.format(temp_cost);
			frontier.add("C" + temp_value);
			temp_cost = 0; temp_value=""; original_cost=0;
			}
		
		else if ("C".equals(node)) 
			{
			temp_cost = temp_cost +3;
			temp_value = formatter.format(temp_cost);
			frontier.add("A" + temp_value);
			temp_cost = 0; temp_value="";
			
			temp_cost = original_cost +3;
			temp_value = formatter.format(temp_cost);
			frontier.add("F" + temp_value);
			temp_cost = 0; temp_value=""; original_cost=0;
			}
		else if ("B".equals(node)) 
			{
			temp_cost = temp_cost +3;
			temp_value = formatter.format(temp_cost);
			frontier.add("A" + temp_value);
			temp_cost = 0; temp_value="";
			
			temp_cost = original_cost +2;
			temp_value = formatter.format(temp_cost);
			frontier.add("D" + temp_value);
			temp_cost = 0; temp_value=""; original_cost=0;
			}
		else if ("D".equals(node)) 
			{
			temp_cost = temp_cost +2;
			temp_value = formatter.format(temp_cost);
			frontier.add("B" + temp_value);
			temp_cost = 0; temp_value="";
			
			temp_cost = original_cost +4;
			temp_value = formatter.format(temp_cost);
			frontier.add("E" + temp_value);
			temp_cost = 0; temp_value=""; original_cost=0;
			}
		else if ("E".equals(node)) 
			{
			temp_cost = temp_cost +4;
			temp_value = formatter.format(temp_cost);
			frontier.add("D" + temp_value);
			temp_cost = 0; temp_value="";
			
			temp_cost = original_cost +1;
			temp_value = formatter.format(temp_cost);
			frontier.add("F" + temp_value);
			temp_cost = 0; temp_value="";
			
			temp_cost = original_cost +2;
			temp_value = formatter.format(temp_cost);
			frontier.add("G" + temp_value);
			temp_cost = 0; temp_value=""; original_cost=0;
			}
		else if ("F".equals(node)) 
			{
			temp_cost = temp_cost +3;
			temp_value = formatter.format(temp_cost);
			frontier.add("C" + temp_value);
			temp_cost = 0; temp_value="";
			
			temp_cost = original_cost +1;
			temp_value = formatter.format(temp_cost);
			frontier.add("E" + temp_value);
			temp_cost = 0; temp_value="";
			
			temp_cost = original_cost +3;
			temp_value = formatter.format(temp_cost);
			frontier.add("G" + temp_value);
			temp_cost = 0; temp_value=""; original_cost=0;
			}
		else if ("G".equals(node)) 
			{
			temp_cost = temp_cost +3;
			temp_value = formatter.format(temp_cost);
			frontier.add("F" + temp_value);
			temp_cost = 0; temp_value="";
			
			temp_cost = original_cost +2;
			temp_value = formatter.format(temp_cost);
			frontier.add("E" + temp_value);
			temp_cost = 0; temp_value="";
			
			temp_cost = original_cost +2;
			temp_value = formatter.format(temp_cost);
			frontier.add("H" + temp_value);
			temp_cost = 0; temp_value=""; original_cost=0;
			}
		else if ("H".equals(node)) {System.out.println("node "+ node + " is reached!!");}
		else {System.out.println("node "+ node + " doesnt exist!");}
	}
	
	
	
	
}
