package MazeSolver;

	/* DisjointSetOfRooms.java combines the rooms of the maze within one area, so that all rooms are part of the same set */

public class DisjointSetOfRooms {
	  
	   int set[]; //set of all rooms
	   int size; //size inputted by user
	   
	   
	   public DisjointSetOfRooms(int size) {
		   	this.size = size;
		   	set = new int[size];
		   	// set all nodes in the room as the root at first
		   	for (int i = 0; i < size; i++) {
		   		set[i] = -1;
		   	}
	   }
	   
	 //this function creates a union by size for the maze
	   public void union(int root1, int root2) {
		   	if (set[root2] < set[root1]) {
		   		set[root2] += set[root1];
		   		set[root1] = root2;
		   	} 
		   	else {
		   		set[root1] += set[root2];
		   		set[root2] = root1;
		   	}
	   }
	   
	   
	   // function returns value in the set of rooms when there is a valid opening in the maze
	   public int find(int x) {
			//if x is the root, return x
			   	if (set[x] < 0) { 
			   		return x;
			   	} else {
			   		set[x] = find(set[x]);
			   		return set[x];
			   	}
		         }
		   
		   

}
