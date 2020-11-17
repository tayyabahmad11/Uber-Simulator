/*
 * Tayyab Ahmad
 * 10197212
 * 
 * This is the main class used to drive the code for this project.
 * 
 * Methods:
 * 	 main - code that sets up the algorithm and calls the methods needed to run the algorithm
 * 	 performRequests - the central part of the algorithm. It simulates the pickup and dropoff of each passenger that makes
 * 		a request and calculates the total wait time of all passengers throughout all requests
 * 	 createGraph - this method will create the graph using the network data provided
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) throws FileNotFoundException, IOException  {
		
		// read in matrices
		Matrix network = new Matrix("network.csv");
		//Matrix requests = new Matrix("requests (1).csv");
		Matrix requests = new Matrix("supplementpickups.csv");
		//network.printMatrix();
		//requests.printMatrix();
		
		// scale nodes to 0-49 instead of 1-50
		for (int i = 0; i < requests.numRows; i++) {
			requests.set(i, 1, requests.at(i, 1) - 1);
			requests.set(i, 2, requests.at(i, 2) - 1);
		} //end for-loop

		DijkstrasGraph graph = createGraph(network);
		int totalWait = performRequests(graph, requests, network);
		System.out.println("Total wait time was " + totalWait);

	} //end main
	
	// using the requests, this method will use the graph and drivers to determine the total waiting time
	public static int performRequests(DijkstrasGraph graph, Matrix requests, Matrix network) {
		
		// instantiate drivers
		UberDriver driver1 = new UberDriver(1, graph, network, 0);
		UberDriver driver2 = new UberDriver(2, graph, network, 49);
		
		int totalWait = 0;
		
		for (int i = 0; i < requests.numRows; i++) {
			
			System.out.println("There is request at time " + requests.at(i, 0) + " to go from " + requests.at(i, 1) + " to " + requests.at(i, 2));
			int dr1 = graph.shortestDist(driver1.currentLocation, requests.at(i, 1)); //distance to request start from driver 1
			graph = createGraph(network);
			int dr2 = graph.shortestDist(driver2.currentLocation, requests.at(i, 1)); //distance to request start from driver 2
			
			System.out.println("Driver 1 is at " + driver1.currentLocation + " and could get to this request in " + dr1);
			System.out.println("Driver 2 is at " + driver2.currentLocation + " and could get to this request in " + dr2);

			// if both drivers are free, then the one closest to the request will take it
			if (requests.at(i, 0) >= driver1.freeAt && requests.at(i, 0) >= driver2.freeAt) {
				if (dr1 < dr2) {
					totalWait = totalWait + graph.shortestDist(driver1.currentLocation, requests.at(i, 1));
					driver1.assignRequest(requests.at(i, 0), requests.at(i, 1), requests.at(i, 2));
				} else {
					totalWait = totalWait + graph.shortestDist(driver2.currentLocation, requests.at(i, 1));
					driver2.assignRequest(requests.at(i, 0), requests.at(i, 1), requests.at(i, 2));
				} //end if-else statements INNER
			} else {
				// if driver 2 is busy, driver 1 will take the request
				if (requests.at(i, 0) >= driver1.freeAt) {
					System.out.println("Driver 2 is busy so driver 1 is taking this request.");
					totalWait = totalWait + graph.shortestDist(driver1.currentLocation, requests.at(i, 1));
					driver1.assignRequest(requests.at(i, 0), requests.at(i, 1), requests.at(i, 2));
				// if driver 1 is busy, driver 2 will take the request
				} else if (requests.at(i, 0) >= driver2.freeAt) {
					System.out.println("Driver 1 is busy so driver 2 is taking this request.");
					totalWait = totalWait + graph.shortestDist(driver2.currentLocation, requests.at(i, 1));
					driver2.assignRequest(requests.at(i, 0), requests.at(i, 1), requests.at(i, 2));
				// if both drivers are busy, the one who will be free sooner will take the request
				} else {
					System.out.print("Both drivers are busy...");
					if (driver1.freeAt < driver2.freeAt) { 
						System.out.println("Driver 1 will be free first so he will take this request.");
						totalWait = totalWait + driver1.freeAt - requests.at(i, 0) + graph.shortestDist(driver1.currentLocation, requests.at(i, 1)); 
						driver1.assignRequest(driver1.freeAt, requests.at(i, 1), requests.at(i, 2));
					} else {
						System.out.println("Driver 2 will be free first so he will take this request.");
						totalWait = totalWait + driver2.freeAt - requests.at(i, 0) + graph.shortestDist(driver2.currentLocation, requests.at(i, 1)); 
						driver2.assignRequest(driver2.freeAt, requests.at(i, 1), requests.at(i, 2));
					}
				} //end if-else statements INNER
			} //end if-else statements OUTER
			graph = createGraph(network);
			System.out.println("Total wait so far is " + totalWait);
			System.out.println("");
		} //end for-loop
		return totalWait;
	} //end performRequests

	// using the 2-dimensional array, this creates a weighted graph using the Graph, Edge, and Node classes
	public static DijkstrasGraph createGraph(Matrix network) { 

		ArrayList<Edge> tempEdges = new ArrayList<>();  
	    int a = 0;
	
	    //creates the edges by iterating only through the top-right diagonal matrix so as to not create
	    //repeated edges, because the matrix is mirrored (A(x,y) == A(y,x))
	    for (int i = 0; i < network.numRows; i++) {
	    	for (int j = a; j < network.numCols; j++) {
	    		// create the edge if i,j is not 0 (nodes are connected)
	    		if (network.at(i,j) != 0) {
	    			tempEdges.add(new Edge(i, j, network.at(i,j)));
	    		} //end if-statement
	    	} //end inner for-loop
	    	a++;
	    } //end outer for-loop
	    
	    Edge[] edges = new Edge[tempEdges.size()];
	    for (int i = 0; i < tempEdges.size(); i++) {
	    	edges[i] = tempEdges.get(i);
	    } //end for-loop

	    //instantiate the graph using the edges created
	    DijkstrasGraph g = new DijkstrasGraph(edges);
	    return g;
	    
	} //end createGraph

} //end UberRequest