/*
 * Tayyab Ahmad
 * 10197212
 * 
 * This class represents the uber drivers used while iterating through the requests.
 * 
 * Attributes: 
 * 	 driverID - used to represent each driver uniquely 
 * 	 freeAt - represents what the next available time for this driver will be. Updated each time the driver takes a request
 * 	 currentLocation - represent where the driver is at each point in time. Updated each time the driver takes a request
 * 	 map - contains the graph so that the driver can calculate shortest distances
 * 	 network - used to create the map
 * 
 * Methods:
 *   UberDriver - constructor to instantiate an uber driver with initial location and availability time, along with the map
 *   assignRequest - this method is called when a driver is assigned a request. It updates their current information while calculating 
 *   	the time needed for the current journey
 * 
 */

public class UberDriver {

	public int driverID;
	public int freeAt;
	public int currentLocation;
	private DijkstrasGraph map;
	private Matrix network;
	
	public UberDriver(int driverID, DijkstrasGraph graph, Matrix network, int startLocation) {
		this.driverID = driverID;
		this.freeAt = 0;
		this.currentLocation = startLocation;
		this.map = graph;
		this.network = network;
	} //end UberDriver constructor
	
	// with request time, and start and end locations, assigns a ride to the driver and determines when they will be free
	public void assignRequest(int requestTime, int start, int end) {
		DijkstrasGraph map = Main.createGraph(network);
		this.freeAt = requestTime + map.shortestDist(this.currentLocation, start) + map.shortestDist(start, end);
		System.out.println("Driver " + driverID + " is taking passenger from " + start + 
				" to " + end + ". He will be free at " + this.freeAt);
		this.currentLocation = end;
	} //end assignRequest
	
} //end UberDriver
