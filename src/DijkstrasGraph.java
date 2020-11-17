/*
 * Tayyab Ahmad
 * 10197212
 * 
 * This class contains the representation of the graph and method containing Dijkstra's algorithm used to find the shortest path 
 * between 2 points. 
 * 
 *  Attributes:
 *    nodes - contains the nodes of the graph
 *    edges - contains the edges of the graph
 *    numNodes - the number of nodes this graph contains
 *    numEdges - the number of edges this graph contains
 *    shortestPaths - contains the shortest paths already calculated by Dijkstra's algorithm
 *    
 *  Methods:
 *    DijkstrasGraph - constructor for creating the graph of the city
 *    shortestDist - calculates the shortest distances from a source to all other nodes using Dijkstra's algorithm. 
 *    	With a destination value, it will specifically return the distance between the source and the destination.
 *    getNextNode - an auxiliary method for shortestDist that determines the next node at each iteration of the algorithm
 * 
 */

import java.util.ArrayList;

public class DijkstrasGraph {
  
	private Node[] nodes;
	private Edge[] edges;
	private int numNodes;
	private int numEdges;
	private int[][] shortestPaths;
  
	// constructor for Graph
	public DijkstrasGraph(Edge[] edges) {
		
		this.edges = edges;
		
		//create the nodes (currently without edges)
		int numNodes = 0;
	    
		for (Edge e : edges) {
			if (e.getToNodeIndex() > numNodes)
				numNodes = e.getToNodeIndex();
			if (e.getFromNodeIndex() > numNodes)
				numNodes = e.getFromNodeIndex();
		} //end for-loop
		numNodes++;
		
		this.numNodes = numNodes;
		
		this.nodes = new Node[this.numNodes];
		for (int n = 0; n < this.numNodes; n++) {
			this.nodes[n] = new Node();
		} //end for-loop
    
		//add all edges to their respective nodes (connect the graph)
		this.numEdges = edges.length;
		for (int i = 0; i < this.numEdges; i++) {
			this.nodes[edges[i].getFromNodeIndex()].getEdges().add(edges[i]);
			this.nodes[edges[i].getToNodeIndex()].getEdges().add(edges[i]);
		} //end for-loop
		
		this.shortestPaths = new int[50][50];
		
	} //end Graph constructor
	
	
	// Uses Djikstra's algorithm to find the shortest path to all points from one source.
	// Given a source and destination, it will return the shortest distance between them.
	public int shortestDist(int source, int dest) {
		
		// if we already know the distances for this source, use the previously calculated values
		if (shortestPaths[source][dest] != 0)
			return shortestPaths[source][dest];
		
		// if we don't already know the answer, run Dijkstra's algorithm to find the distance
		nodes[source].setDistFromSrc(0); // given node is the source
		int currentNode = source;
    
		for (int i = 0; i < nodes.length; i++) {
			
			nodes[currentNode].setVisited(true); // this node is now visited
			
			// loop around the edges of current node
			ArrayList<Edge> nodeEdges = nodes[currentNode].getEdges();
			
			for (int edge = 0; edge < nodeEdges.size(); edge++) {
				
				int neighbourIndex = nodeEdges.get(edge).getNeighbourIndex(currentNode);
				
				// if this node has not been visited
				if (! nodes[neighbourIndex].isVisited()) {
					
					int tentative = nodes[currentNode].getDistFromSrc() + nodeEdges.get(edge).getLength();
					if (tentative < nodes[neighbourIndex].getDistFromSrc())
						nodes[neighbourIndex].setDistFromSrc(tentative);

				} //end if-statement
			} // end for-loop INNER
			currentNode = getNextNode();
		} //end for-loop OUTER

		// add calculated values for this source to matrix to save for later use
		for (int i = 0; i < nodes.length; i++) {
			shortestPaths[source][i] = nodes[i].getDistFromSrc(); 
		}
		
		return nodes[dest].getDistFromSrc();
		
	} //end calculateShortestDistances

	// determines the next node to iterate through during Dijkstra's algorithm
	private int getNextNode() {
				
		int storedNodeIndex = 0;
		int storedDist = Integer.MAX_VALUE;
		
		for (int i = 0; i < nodes.length; i++) {
			int currentDist = nodes[i].getDistFromSrc();
			if (! nodes[i].isVisited() && currentDist < storedDist) {
				storedDist = currentDist;
				storedNodeIndex = i;
			} //end if-statement
		} //end for-loop
		
		return storedNodeIndex;
		
	} //end getNextNode	
	
} //end Graph