/*	PROGRAMMING ASSIGNMENT #1
 *	Submitted by - Deepshi Mahajan
 * 	
 *  This problem is solved using DFS which is implemented using stack.
 *	
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class P1{
//stores i,j, operation
	public class Node{
		int i,j;
		String operation = null;
		Node parentNode = null;
	
		Node(){}
		
		//store values for the parent node
		Node(Node parentNode){
			this.i = parentNode.i;
			this.j = parentNode.j;
			this.parentNode = parentNode;
		}	
	}
//function to find minimum of three numbers
     public int minimum(int a, int b, int c){
		int x = Math.min(a, b);
		return Math.min(x,c);
    }
	   
     public void min_distance(String s, String t) {
		int n = s.length();      
		int m = t.length();
		
		//table to store results of sub problems
		int matrix[][] = new int[n+1][m+1]; 
		
		//fill the matrix[][] in bottom up manner
		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= m; j++) {
	                  
				      if(i==0)
	            	    	  matrix[i][j]=j;
	
	            	      else if(j==0)
	            	  	  matrix[i][j]=i;
	            	      
	            	      else if(s.charAt(i-1) == t.charAt(j-1))
	            	    	  matrix[i][j]= matrix[i-1][j-1];
	            	      
	            	      else 
	            	    	  matrix[i][j]= 1 + minimum(matrix[i][j-1], matrix[i-1][j], matrix[i-1][j-1]);            	      
              }	
		}
		
		Node root = new Node();
		root.i = n;
		root.j = m;
		root.parentNode = null;
		 
		Stack<Node> stack = new Stack<Node>();
		stack.push(root);
		ArrayList<Node> sequences = new ArrayList<Node>();
		
		/*pop each node in the stack and check the value current node's i and j
		 *
		 * */
		
		while(!stack.isEmpty()){
			Node currentNode = stack.pop();
		 
			if(currentNode.i==0 && currentNode.j==0) {	
				sequences.add(currentNode);	
			}
			else{	
				int i = currentNode.i;
				int j = currentNode.j;
				
				if(i-1>=0 && j-1>=0 && s.charAt(i-1) == t.charAt(j-1)){
					Node childNode = new Node(currentNode);
					childNode.i--;
					childNode.j--;		
					stack.push(childNode);
				}
				else{			 
			//check from the top	 
					if(i-1>=0 && matrix[i][j]==matrix[i-1][j]+1) {	
						Node childNode = new Node(currentNode);
						childNode.i--;
						childNode.operation = "delete";
						stack.push(childNode);
						}
					
			 //check from the diagonal	 
					if(	i-1>=0 && j-1>=0 && matrix[i][j]==matrix[i-1][j-1]+1) {
						Node childNode = new Node(currentNode);	
						childNode.i--;
						childNode.j--;
						childNode.operation = "replace";
						stack.push(childNode);
					}		 
			//check from the left   
					if(j-1>=0 && matrix[i][j]==matrix[i][j-1]+1) {
						Node childNode = new Node(currentNode);
						childNode.j--;
						childNode.operation = "insert";
						childNode.parentNode = currentNode; 
						stack.push(childNode);
					}			 
				}	 		 
			}	
		}        
		System.out.println("There are total of "+ sequences.size()+" sequences:");
		
		//print all the sequences 
		for(Node node : sequences) {
			int deviate = 0; //handles change in the length of the string
			StringBuilder sb = new StringBuilder(s);
            	System.out.print( s +" ");
            	while(node!=null) {   
            		if(node.operation=="insert"){
            			sb.insert(node.i+deviate,t.charAt(node.j));
					deviate++;
					System.out.print( node.operation +" "+ t.charAt(node.j)+" -> "  + sb+"   ");
            		}
				if(node.operation=="delete"){
					sb.deleteCharAt(node.i + deviate);
					deviate--;
					System.out.print( node.operation +" "+ s.charAt(node.i) +" -> " + sb+"   ");
				}
				if(node.operation=="replace"){
					sb.setCharAt(node.i+deviate,t.charAt(node.j));
					System.out.print( node.operation +" "+ s.charAt(node.i) + " by "+ t.charAt(node.j) + " -> " + sb + "   ");
				}
				node = node.parentNode;
            	}
            System.out.println();
		}
     }
	/*
	 * main method 	 	 
	 */
     public static void main(String[] args) throws IOException {
    	 
    	 	Scanner input = new Scanner(System.in);		
	
    	 	String s = input.nextLine();
		String t = input.nextLine(); 	
		
		if(s.isEmpty() && t.isEmpty()) {
			System.err.println("Empty Strings");
		}
		
		else if(s.equals(t)) {
			System.out.println("Same Strings");
			return;
		}
		
		
		else {
		/*if(input.hasNext()==true) { 
			System.out.println("More than two strings entered!");
		 	return;
		}*/
		
		if(!s.matches("[a-zA-Z]*") || (!t.matches("[a-zA-Z]*"))){
	    		System.out.println("Invalid input!");
		}
	    
		else {
	    		P1 object = new P1();
	    		object.min_distance(s, t);
	    }
	}
     }
}
  
		  
		      
	     		

	
     
  