package MazeSolver;

public class Room {
	   // boolean used to determine if wall is closed (when false) or open (when true) 
	   boolean visited, wall;
	   Room sides[];
           // room number within the maze will be traced and followed 
	   int rmNum; 
           // aids in discovering the shortest path by following distance from the beginning 
	   int distance = Integer.MAX_VALUE; 
	  
	   
	   // initialize the maze to close all sides
	   Room() {
	   	sides = new Room[4];
	   	for (int i = 0; i < 4; i++) {
	   		sides[i] = new Room(true);
	   	}
	   	visited = false;
	   }
	   
	   
	   
	   //determine if room is open or closed 
	   Room(boolean wall) {
	   	this.wall = wall;
	   	visited = false;
	   }
	   
	   
	   
	   
	   //initialize the maze for user input 
	   Room (int wall) {
	   	if (wall == 1) {
	   		this.wall = true;
	   	} else if (wall == 0) {
	   		this.wall = false;
	   	}
	   }
	  
	   
	   //set room wall
	   public void SetRoomWall(int wall, Room room) {
	   	sides[wall] = room;
	   }
	   
	   
	   //get room and return sides
	   public Room ReturnRoomSides(int side) {
	   	return sides[side];
	   }

	   
	   
	   // return wall
	   public boolean isWall() {
	   	return wall;
	   }
	   
	   
	   
	   //print room values based on user input
	   public String printRoom() {  
		   	String s = "";
		   	if (sides[1].wall != false) {
		   		s += "__";
		   	} else { 
		   		s += "  ";
		   	}
		   	if (sides[2].wall != false) {
		   		s += "|";
		   	} else {
		   		s += " ";
		   	}
		   	return s;
	
		   }
	}


