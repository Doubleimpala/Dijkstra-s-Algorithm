import java.util.Scanner;
import java.io.*;
import java.util.Stack;
import java.util.ArrayList; //ArrayLists are just handy, yo
// Need Scanner and java.io.* to read files
// Need MinHeap.java in the project so that the MinHeap works

class Main {
  public static void main(String[] args) throws Exception {
    System.out.println("Welcome to Dijkstra Town!");

    // Reading the file and creating the Matrix
    // Because the size of the Matrix is in the file,
    // we only have to read through the file once

    Scanner file = new Scanner(new File("graph.txt"));

    int size = file.nextInt();
    // We can assume the first integer in the file is the size of the Matrix
    // (remember the matrix must be square so we only need a size, not rows/cols)

    // Declare the graph matrix
    int[][] graph = new int[size][size];

    // Scan all the values into the graph
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        graph[i][j] = file.nextInt();
        /* ROW-MAJOR FORMAT (ie, row is the 1st number, column is the 2nd number */
      }
    }

    System.out.println("graph.txt loaded");
    // at this point, the graph matrix should be populated,
    // printing it just to make sure:

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        System.out.print(graph[i][j] + "|");
      }
      System.out.println();
    }

    boolean doStuff = true;
    while (doStuff == true) {
      Scanner keyboard = new Scanner(System.in);
      System.out.println("Which node to start at?");
      int start = keyboard.nextInt();
      if (start < 0 || start >= size) {
        System.out.println("Pick a valid node");
        continue; // continue tells java to immediately start the next "round" of the loop
      }
      System.out.println("Which node to end at?");
      int end = keyboard.nextInt();
      if (end == start || end < 0 || end >= size) {
        System.out.println("Pick a valid node");
        continue;
      }

      // Dijkstra's Algorithm START!

      // priority queue
      MinHeap<DNode> pq = new MinHeap<DNode>(); // read about DNode at bottom of file!
      Stack myStack = new Stack();
      ArrayList<Integer> visited = new ArrayList<Integer>();// list of visited nodes
      DNode o = new DNode();
      for (o.num = 0; o.num < size; o.num++) {
        o.tdist = Integer.MAX_VALUE;
      }
      DNode dstart = new DNode();
      dstart.num = start;
      dstart.prev = start;
      dstart.tdist = 0;
      pq.insert(dstart); // insert first node inot priority queue

      // might be missing other variables
      DNode curr = new DNode();
      DNode visit = new DNode();
      int dist[] = new int[size];
      visit = dstart;
      curr.num = dstart.num;
      curr.prev = dstart.num;
      curr.tdist = 0;
      boolean dijkgo = true;
      while (dijkgo == true) {// algorithm ends when you visit the end node
        if (visit.num == end) {
          break;
        }
        int newDist = -1;
        for(int i = 0; i< size; i++){
        if(graph[visit.num][i] != Integer.MAX_VALUE) {
        newDist = visit.tdist + graph[visit.num][i];
          if(newDist < dist[i]) {
            dist[i] = newDist;
            visit.num = i;
            visit.prev = visit.num;
            visit.tdist = newDist;
            pq.insert(visit);
          }
        }

        // 1 Get the next node to visit from the priority queue
        // NOTE: it's possible to have more than one "copy" of the same node in the
        // priority queue
        // So you must check that the node you got from the queue is unvisited.
        if (!visited.contains(visit)) {
          curr.num = visit.num; curr.tdist = visit.tdist;
          myStack.push(curr.num);
          visited.add(curr.num);
        }

        System.out.println("Visiting: " + pq.delete().num);
      }
    }
      while(myStack.peek()!=null){
        System.out.println(myStack.pop());
      }

  }

 // Our priority-queue has to store both the node number, the previous node, and 
  // the tentative distance - tentative distance is what represents the priority
  // and should be what determines placement in the MinHeap
  static class DNode implements Comparable {// needs to implement Comparable to work with MinHeap
    int num; // node label
    int prev; // previous node
    int tdist; // tentative distance
  
    public int compareTo(Object o) { //compareTo must take a generic Object as a parameter
      // because of java reasons
      DNode dn = (DNode) o; // convert it to a DNode
      if (this.tdist < dn.tdist) return -1;
      else if (this.tdist > dn.tdist) return 1;
      else return 0;
    }
  }
}
}
