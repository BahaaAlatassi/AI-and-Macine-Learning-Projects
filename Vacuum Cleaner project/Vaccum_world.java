import java.util.*;

public class Vaccum_world {
	
	String vacuum_pos; //either right or left
	boolean dirt_right;
	boolean dirt_letf;
	int state;
	
	Queue <Integer>  frontier = new LinkedList<Integer>();
	ArrayList<Integer> explored_set = new ArrayList<Integer>();
	
	Hashtable < Integer , ArrayList <Integer>> look_up_table = new Hashtable < Integer , ArrayList <Integer>> ();// not used but kept in case of look up dictionary is needed.
	
	public Vaccum_world (int state)
	{
		this.state= state;
		frontier.add(state);		
		String result = bfs();
		
		System.out.println(result);
		System.out.println("After expanding the following nodes: " + explored_set);
			
	}
	
	public int trans(String act) //transition model which makes the transition as per the state and the passed action

	{
		
		if (state==1 && act=="left") return 1;
		else if (state==1 && act=="right") return 2;
		else if (state==1 && act=="suck") return 3;
		
		else if (state==2 && act=="left") return 1;
		else if (state==2 && act=="right") return 2;
		else if (state==2 && act=="suck") return 6;
		
		else if (state==3 && act=="left") return 3;
		else if (state==3 && act=="right") return 4;
		else if (state==3 && act=="suck") return 3;
		
		else if (state==4 && act=="left") return 3;
		else if (state==4 && act=="right") return 4;
		else if (state==4 && act=="suck") return 8;
		
		else if (state==5 && act=="left") return 5;
		else if (state==5 && act=="right") return 6;
		else if (state==5 && act=="suck") return 7;
		
		else if (state==6 && act=="left") return 5;
		else if (state==6 && act=="right") return 6;
		else if (state==6 && act=="suck") return 6;
		
		else if (state==7 && act=="left") return 7;
		else if (state==7 && act=="right") return 8;
		else if (state==7 && act=="suck") return 7;
		
		else if (state==8 && act=="left") return 7;
		else if (state==8 && act=="right") return 8;
		else if (state==8 && act=="suck") return 8;
		
		else
			return 0;
		
	}
	
	

	public String bfs ()
	{
		int f_component; //variable to hold temporarily the FIFO frontier value that will be polled
		if (state==7 || state==8) {return "succesfuly reached state: "+ state;}
		
		do
		{

			if (frontier.isEmpty()) {return "Empty frontier";}
			f_component = frontier.poll() ;
			state= f_component;

			
			if (!(explored_set.contains(f_component)) && (state != 7) && (state != 8) ) //verifying that the studied node is not explored to avoid loopy path
				{
				explored_set.add(f_component) ;
				
				System.out.println("expanding node: "+ f_component);
				frontier.add(trans("left")); //expanding the node with action by calling the transition model function
				frontier.add(trans("right")) ; //expanding the node with action by calling the transition model function
				frontier.add(trans("suck")) ; //expanding the node with action by calling the transition model function
			
				}
			
			
		} 
		while ((state != 7) && (state != 8));
		
		
		return "State " + state + " has been reached";
	}

	public void fill_table() //not used, was just made in case of future need of looking up every node possible transitions.
	{
		look_up_table.put(1, new ArrayList<Integer>());
		look_up_table.get(1).add(1);
		look_up_table.get(1).add(2);
		look_up_table.get(1).add(3);
		
		look_up_table.put(2, new ArrayList<Integer>());
		look_up_table.get(2).add(1);
		look_up_table.get(2).add(2);
		look_up_table.get(2).add(6);
		
		look_up_table.put(3, new ArrayList<Integer>());
		look_up_table.get(3).add(3);
		look_up_table.get(3).add(4);
		look_up_table.get(3).add(3);
		
		look_up_table.put(4, new ArrayList<Integer>());
		look_up_table.get(4).add(3);
		look_up_table.get(4).add(4);
		look_up_table.get(4).add(8);
		
		look_up_table.put(5, new ArrayList<Integer>());
		look_up_table.get(5).add(5);
		look_up_table.get(5).add(6);
		look_up_table.get(5).add(7);
		
		look_up_table.put(6, new ArrayList<Integer>());
		look_up_table.get(6).add(5);
		look_up_table.get(6).add(6);
		look_up_table.get(6).add(6);
		
		look_up_table.put(7, new ArrayList<Integer>());
		look_up_table.get(7).add(7);
		look_up_table.get(7).add(8);
		look_up_table.get(7).add(7);
		
		look_up_table.put(8, new ArrayList<Integer>());
		look_up_table.get(8).add(7);
		look_up_table.get(8).add(8);
		look_up_table.get(8).add(8);

	}
	
	
	public int state_determination() // not used but it helps determining the current node in reference to the dirt existence and vacuum position
	{
		if (dirt_letf==true && dirt_right==true && vacuum_pos=="left")
		{
			state=1;
			return 1;
		}
		else if (dirt_letf==true && dirt_right==true && vacuum_pos=="right")
		{
			return 2;
		}
		
		else if (dirt_letf==false && dirt_right==true && vacuum_pos=="left")
		{
			return 3;
		}
		
		else if (dirt_letf==false && dirt_right==true && vacuum_pos=="right")
		{
			return 4;
		}
		
		else if (dirt_letf==true && dirt_right==false && vacuum_pos=="left")
		{
			return 5;
		}
		
		else if (dirt_letf==true && dirt_right==false && vacuum_pos=="right")
		{
			return 6;
		}
		
		else if (dirt_letf==false && dirt_right==false && vacuum_pos=="left")
		{
			return 7;
		}
		
		else if (dirt_letf==false && dirt_right==false && vacuum_pos=="right")
		{
			return 8;
		}
		
		else {return 0;}
		
	}
	
	public void update_state() // not used but it helps determining the the dirt existence and vacuum position in reference to the current node.
							   // This function does exactly the opposite of state_determination() function. 
	{
		if (state==1)
		{
			dirt_letf=true; dirt_right=true; vacuum_pos="left";
		}
		else if (state==2)
		{
			dirt_letf=true; dirt_right=true ; vacuum_pos="right" ;
		}
		
		else if (state==3)
		{
			dirt_letf=false; dirt_right=true; vacuum_pos="left";
		}
		
		else if (state==4)
		{
			dirt_letf=false; dirt_right=true; vacuum_pos="right";
		}
		
		else if (state==5)
		{
			dirt_letf=true; dirt_right=false; vacuum_pos="left";
		}
		
		else if (state==6)
		{
			dirt_letf=true; dirt_right=false; vacuum_pos="right";
		}
		
		else if (state==7)
		{
			dirt_letf=false; dirt_right=false; vacuum_pos="left";
		}
		
		else if (state==8)
		{
			dirt_letf=false; dirt_right=false; vacuum_pos="right";
		}
		
		
	}
}
