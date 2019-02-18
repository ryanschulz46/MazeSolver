package MazeSolver;

public class Stack {
	   private final int size; // size of the stack 	  
	   private int top, items; // 
	   private Room[] stList; // stack list based on the Room object
	   
	   
	   public Stack() {
		   	size = 50; //standard size of the stack
		   	top = 0;
		   	items = 0;
		   	stList = new Room[size]; //creates new room through stack
	   }
	   
	   
	   
	//Creates a stack with input specified by user 
	   public Stack(int size) {
		   	this.size = size;
		   	top = 0;
		   	items = 0;
		   	stList = new Room[size];
	   }
	   
	   
	   
	   //pushes Room into stack if a room has been visited in the maze
	   public void push (Room room) {
		   	if (top == size - 1){ //indicates that stack is full
		   		System.out.println("Stack is full");
		   	}
		   	stList[top] = room;
		   	top++;
		   	items++;
	   }
	   
	   
	   //pops out room numbers when maze has reached finish line or some dead end
	   public Room pop() {
		   	if (top == 0) {
		   		System.out.println("Stack is empty, nothing to pop!");
		   	}
		   	Room room = stList[top - 1];
		   	top--;
		   	items--;
		   	return room;
	   }
	   
	   
	   // returns 0 if stack is empty
	   public boolean CheckMazeEmpty() {
		   	return (top == 0);
		   }

}
