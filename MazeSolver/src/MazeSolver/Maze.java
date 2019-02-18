package MazeSolver;

	import java.util.Scanner;
	import java.util.Random;
	import java.io.*;

	/**
	* Models a square maze with a randomly generated pathway
	*/

	public class Maze {
		   public int size; 
		   public Room[][] ArrayOfRooms; 
		   public String[] Xpath; 
		   public Random rand = new Random();
		   public Maze() {
		   	int size = 0;
	   }
	   
	   
	/**
	* Constructs maze with given size integer. It uses an array of rooms, a string that is the shortest * path through the maze, and uses a random generation to create open walls in the rooms to *make up the pathways to traverse the maze. 
	*/
	   
	   
	   public Maze(int size) {
		   	// initialize variables
		   	this.size = size;
		   	ArrayOfRooms = new Room[size][size];
		   	//initialize the rooms and assign a room number to them
		   	int IndividualRoomNumber = 0;
		   	for (int i = 0; i < size; i++) {
		   		for (int j = 0; j < size; j++) {
		   			ArrayOfRooms[i][j] = new Room();
		   			ArrayOfRooms[i][j].rmNum = IndividualRoomNumber;
		   			IndividualRoomNumber++;
		   		}
		   	}
		   	ArrayOfRooms[0][0].SetRoomWall(0, new Room()); //set the north wall of the first room to be open so you can enter the maze
		   	ArrayOfRooms[size-1][size-1].SetRoomWall(1, new Room()); //set the south wall of the final room to open too so you can exit
	   }
	   
	   
	   
	   
	   /**
	*Constructs a maze with a given file containing the needed information to generate maze
	*/
	   
	   
	   public Maze (String file){
		   	try{
		   		Scanner scanner = new Scanner(new File(file));
		   		String input;
		   		size = scanner.nextInt();
		   		System.out.println(size);
		   		ArrayOfRooms = new Room[size][size];
		   		int IndividualRoomNumber = 0;
		   		for(int x = 0; x < size; x++) { //set all the rooms
		   			for(int y = 0; y < size; y++) {
		   				ArrayOfRooms[x][y] = new Room();
		   				ArrayOfRooms[x][y].rmNum = IndividualRoomNumber;
		   				IndividualRoomNumber++;
		   			}
		   		}
		   		ArrayOfRooms[0][0].SetRoomWall(0, new Room()); //set the north wall of the first room to be open so you can enter the maze	   		ArrayOfRooms[size-1][size-1].SetRoomWall(1, new Room()); //set the south wall of the final room to open too so you can exit
		   	   
		   		for(int i = 0; i < size; i++) {
		   			for(int j = 0; j < size; j++) {
		   				for (int a = 0; a < 4; a++) {
		   					input = scanner.next();
		   					int adj = PickNeighborRoom(ArrayOfRooms[i][j].rmNum, a);
		   					int neighborWall = getNeighborsWall(a);
		   					if (input.equals("1") && BorderCheck(ArrayOfRooms[i][j].rmNum, a) == false) {
		   						ArrayOfRooms[i][j].sides[a].wall= true;      
		   					} else if (input.equals("0") && BorderCheck(ArrayOfRooms[i][j].rmNum, a) == false) {
		   						DestroyWall(ArrayOfRooms[i][j], a, ArrayOfRooms[adj / size][(adj + size) % size], neighborWall);
		   					}
		   				}  
		   			}
		   		}
		   	   
		   	scanner.close();
		   	} catch (Exception uhoh) {
		   		uhoh.printStackTrace();
		   	}
		   		System.out.print(PrintOutMaze());
	   }
	   
	   
	   
	   
	   
	 
	   
	   
	     
	 
	//DISJOINT SET

	   public void MazeGenerate() {
	      
		   	DisjointSetOfRooms set = new DisjointSetOfRooms(size*size);
		   	while(set.find(0) != set.find((size*size) - 1)) {
		   		//pick a random room and wall to be removed and also pick the adjacent //room that shares the side of random wall and make its corresponding wall removed
		   		int randomRoom = rand.nextInt(size*size);
		   		int randomWall = RandomWallSelect(randomRoom);
		   	   
		   		
		   		int neighborRoom = PickNeighborRoom(randomRoom, randomWall);
		   		int neighborWall = getNeighborsWall(randomWall);
		   		//we make a path between them if they aren’t inside the same set
		   		if (set.find(randomRoom) != set.find(neighborRoom)) {
		   			DestroyWall(ArrayOfRooms[randomRoom / size][(randomRoom + size) % size], randomWall,
		   						ArrayOfRooms[neighborRoom / size][(neighborRoom + size) % size], neighborWall);
		   			set.union(set.find(randomRoom), set.find(neighborRoom));
		   		}
		   	}
		   	System.out.print(PrintOutMaze());
	   }

	   
	   
	   
	   
	   
	   
	   public void BreadthFirstSearch() {
		   	String TraversedPath = ""; //keeps track of the path traveresed using BFS
		   	String SolutionPath = ""; //is the shortest path from the exit to the first room of maze
		   	int distance = 0;
		   	Queue q = new Queue(size*size);
		   	Room ptr;
		   	q.enqueue(ArrayOfRooms[0][0]);
		   	ArrayOfRooms[0][0].visited = true;
		   	ArrayOfRooms[0][0].distance = distance;
		      
		   	while (!q.CheckMazeEmpty()) {
		   		ptr = q.dequeue();
		   		TraversedPath += ptr.rmNum + " ";
		   		if (ptr == ArrayOfRooms[size-1][size-1]) {
		   			System.out.println("Rooms that were visited by BFS: " + TraversedPath);
		   			System.out.println(ShortestPathFunction(ArrayOfRooms[size-1][size-1]));
		   			PrintShortest(Xpath);
		   		}
		   		for (int i = 0; i < 4; i++) {//checks all 4 of the room wallsmaze()
		   			Room r = ptr.ReturnRoomSides(i);
		   			if(r.wall == false && r.visited == false) {
		   				q.enqueue(r);
		   				r.visited = true;
		   				r.distance = distance+1;
		   			}
		   		}
		   		distance++;  
		   	}
		   	if (ArrayOfRooms[size-1][size-1].visited == false) {
		   		System.out.println("No BFS solution for this maze! :( ");
		   	}
		      
	   }
	   
	   
	   
	   
	   
	   
	   public void DepthFirstSearch() {
		   	String TraversedPath = ""; //needs to always start at the 1st room
		   	String SolutionPath = "";
		   	int distance = 0;
		   	Stack s = new Stack(size*size);
		   	Room ptr;
		   	s.push(ArrayOfRooms[0][0]);
		   	ArrayOfRooms[0][0].visited = true;
		   	ArrayOfRooms[0][0].distance = distance;
		      
		   	while(!s.CheckMazeEmpty()) {
		   		ptr = s.pop();
		   		TraversedPath += ptr.rmNum + " ";
		   		if (ptr == ArrayOfRooms[size-1][size-1]) {
		   			System.out.println("Rooms that were visited by DFS: " + TraversedPath);
		   			System.out.println(ShortestPathFunction(ArrayOfRooms[size-1][size-1]));
		   			PrintShortest(Xpath);
		   		}
		   		for (int i = 0; i < 4; i++) { //goes through all 4 of the walls
		   			Room r = ptr.ReturnRoomSides(i);
		   			if(r.wall == false && r.visited == false) {
		   				s.push(r);
		   				r.visited = true;
		   				r.distance = distance+1;
		   			}
		   		}
		   		distance++;
		   	}
		   	if (ArrayOfRooms[size-1][size-1].visited == false) {
		   		System.out.println("No DFS solution could be found for the maze!");
		   	}
	   }
	   
	   
	   
	   
	   
	   public String ShortestPathFunction(Room room) {
		   	Xpath = new String[size*size];
		   	Xpath[size*size - 1] = "X ";
		   	int step = 0;
		   	int temp = room.distance;
		   	String sol = "Here is the shortest path found in reverse order: " + room.rmNum + " ";
		   	while (room.distance != 0) {
		   		for (int i = 0; i < 4; i++) { //goes throuh all 4 of the room walls
		   			Room r = room.ReturnRoomSides(i); 
		   			if(r.distance < temp && r.wall == false) {
		   				temp = r.distance;
		   				step = i;
		   			}
		   		}
		   		room = room.ReturnRoomSides(step);
		   		Xpath[room.rmNum] = "X ";
		   		sol += room.rmNum + " ";
		   	}
		   	return sol;
	   }
	   
	   
	   
	   
	   
	   
	   public int PickNeighborRoom(int room, int side) {
		   	int a = room;
		   	if (side == 0) {
		   		a = a - size;
		   	}
		   	else if (side == 1) {
		   		a = a + size;
		   	}
		   	else if (side == 2) {
		   		a = a + 1;
		   	}
		   	else if (side == 3) {
		   		a = a - 1;
		   	}
		   	return a;
	   }
	   
	   
	   
	   
	   private int RandomWallSelect(int room) {
		   	boolean[] BorderCheck = new boolean[4];
		   	if(room < size) { //finds the top row boundary/border
		   		BorderCheck[0] = true;
		   	}
		   	if ((room+size) >= (size*size)) { //finds the top row border
		   		BorderCheck[1] = true;
		   	}
		   	if ((room+1) % size == 0) {
		   		BorderCheck[2] = true;
		   	}
		   	if(room % size == 0) {
		   		BorderCheck[3] = true;
		   	}
		   	int wall;
		   	do {
		   		wall = rand.nextInt(4);
		   	} while (BorderCheck[wall] == true);
		   	return wall;
	   }
	   
	   
	   
	   
	   private int getNeighborsWall(int wall) {
		   	int neighborWall =0; 
			switch(wall)
		   	{
		   	case 0:
		   		neighborWall = 1;
		   		break;
		   	case 1:
		   		neighborWall = 0;
		   		break;
		   	case 2:
		   		neighborWall = 3;
		   		break;
		   	case 3:
		   		neighborWall = 2;
		   		break;
		   	}
			return neighborWall;
	   }

	   
	   
	   
	   //makes pathway
	   public void DestroyWall(Room r1, int w1, Room r2, int w2) {
	   		r1.SetRoomWall(w1,r2);
	   		r2.SetRoomWall(w2,r1);
	   }
	   
	   
	   
	   
	   
	   public void MazeReset() {
		   	for (int i = 0; i < size; i++) {
		   		for (int j = 0; j < size; j++) {
		   			ArrayOfRooms[i][j].visited = false;
		   		}
		   	}
	   }
	   
	   
	   
	   
	   
	   public boolean BorderCheck(int room, int side) {
		   	boolean border = false;
		   	if(room < size && side == 0) { //gets the top bordering row
		   		border = true;
		   	}
		   	if ((room+size) >= (size*size) && side == 1) { //gets the bottom row border
		   		border = true;
		   	}
		   	if ((room+1) % size == 0 && side == 2) {//gets right column border 
		   		border = true;
		   	}
		   	if(room % size == 0 && side == 3) {//gets left column border
		   		border = true;
		   	}
		   	return border;
	   }
	   
	   
	   
	   
	   
	   
	   public void PrintShortest(String[] path) {
		   	for(int x = 0; x < size*size; x++) {
		   		if (x % size == 0 && x != 0) {
		   			System.out.print("\n");
		   		}
		   		if (path[x] != null) {
		   			System.out.print(path[x]);
		   		} else {
		   			System.out.print("  ");
		   		}
		   	}
		   	System.out.println("\n");
	   }
	   
	   
	   
	   
	   
	   
	    public String PrintOutMaze() {
		   	String s = "   "; //this is the opened wall at the maze start
		   	for (int x = 1; x < size; x++) {
		   		s += " __";
		   	}
		   	s += "\n";
		   	for (int i = 0; i < size; i++) {
		   		for (int j = 0; j < size; j++) {
		   			if (j == 0) {
		   				s += "|";
		   			}
		   			s += ArrayOfRooms[i][j].printRoom();
		   			if ((j+1) % size == 0){
		   				s += "\n";
		   			}
		   		}
		   	}
		   	return s;
	   }  
	    
	    
	    
	    
	    
	    
	   public void MazeGenerateFile () {
		   	try {
		      	File file = new File("mazefile.txt");
		      	FileWriter fw = new FileWriter(file);
		      	BufferedWriter bw = new BufferedWriter(fw);
		       
		        	System.out.println("Your file " + file + " was successfully saved!!!");
		        	bw.write(String.valueOf(size));
		        	bw.newLine();
		        	for(int i = 0; i < size; i++) {
		   			for(int j = 0; j < size; j++) {
		   				for (int x = 0; x < 4; x++) {
		   					if(ArrayOfRooms[i][j].sides[x].wall == true) {
		   						bw.write("1 ");
		   					} else {
		   						bw.write("0 ");
		   					}
		   				}
		   				bw.newLine();
		 				}
		     		}
		      	bw.close();
		   	} catch (Exception uhoh) {
		      	uhoh.printStackTrace();
		   	}
	   }
	   
	   
	   
	   
	   
	   
	   
	   

	   
	   
	   
	   
	}

