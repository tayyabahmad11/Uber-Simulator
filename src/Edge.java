/*
 * Tayyab Ahmad
 * 10197212
 *
 * Auxiliary class used to create a graph.
 * This class represents the edges used by the graph. 
 * 
 */
public class Edge {
  
	private int fromNodeIndex;
	private int toNodeIndex;
	private int length;
  
	public Edge(int fromNodeIndex, int toNodeIndex, int length) {
		this.fromNodeIndex = fromNodeIndex;
		this.toNodeIndex = toNodeIndex;
		this.length = length;
	}
  
	public int getFromNodeIndex() {
		return fromNodeIndex;
	}
  
	public int getToNodeIndex() {
		return toNodeIndex;
	}
  
	public int getLength() {
		return length;
	}
  
	public int getNeighbourIndex(int nodeIndex) {  
		if (this.fromNodeIndex == nodeIndex) 
			return this.toNodeIndex;
		else
			return this.fromNodeIndex;
	} //end getNeighbourIndex

} //end Edges
