package MazeSolver;

import java.util.*;

public class Main {
	
	   public static void main (String[]args) {
		   	Scanner input = new Scanner(System.in);
		   	Maze maze = new Maze();
		   	if(args.length != 0) {
		   		maze = new Maze(args[0]);
		   	} else {
		   		int size = 0;
		   		while (size <= 1) {
		   			System.out.println("Please enter the desired size of your maze:");
		   			size = input.nextInt();
		   			if (size <= 1) {
		   				System.out.println("Uh Oh! The size must be larger than 1!\n");
		   			}
		   		}
		   		maze = new Maze(size);
		   		maze.MazeGenerate();
		   	}
		   	maze.BreadthFirstSearch();
		   	maze.MazeReset();
		   	maze.DepthFirstSearch();
		   	System.out.print("Do you want to save a .txt file of your maze? (Y/N)");
		   	String answer = input.next();
		   	if (answer.equals("y") || answer.equals("Y")) {
		   		maze.MazeGenerateFile();
		   	}
	   }
}

