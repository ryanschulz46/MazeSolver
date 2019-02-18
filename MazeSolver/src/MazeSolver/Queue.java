package MazeSolver;

public class Queue {
	   // Declare variables executed by the queue
	   private Room[] QueueList;
	   private int size, first, last;
	   private final int MAX_SIZE;
	   public Queue() {
	                       // tracks amount of items in the queue
		   	size = 0;
	                       //Marks the first item in queue
		   	first = 0;
	                       //marks last item in queue
		   	last = 0;
	                       //declare default array size that can be adjusted by user input
		   	MAX_SIZE = 50; //default
		   	QueueList = new Room[MAX_SIZE];
	   }
	   
	   //initialize maxsize
	   public Queue(int maximumsize) {
		   	size = 0;
		   	first = 0;
		   	last = 0;
		   	MAX_SIZE = maximumsize;
		   	QueueList = new Room[MAX_SIZE];
	   }
	   
	   //initialize enqueue to be able to track/insert elements from the back of the queue
	   public void enqueue(Room x) {
		   	QueueList[last] = x;
		   	last++;
		   	if(last == MAX_SIZE) {
		   		last = 0;
		   	}
		   	size++;
	   }
	   
	   // initialize dequeue to be able to track/remove elements from the front of the queue
	   public Room dequeue() {
		   	Room item = QueueList[first];
		   	first++;
		   	if (first == MAX_SIZE) {
		   		first = 0;
		   	}
		   	size--;
		   	return item;
	   }
	   
	   // check if string is empty or not
	   public boolean CheckMazeEmpty() {
		   return (size == 0);
	   }
	   
	   //print queue after determining above factors
	   public void printQueue() {
		   	for (int i = first+1; i < last+1; i++) {
		   		System.out.print(QueueList[i] + " ");
		   	}
		   	System.out.println();
		   }
	   

}
