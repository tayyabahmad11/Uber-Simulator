# Uber-Simulator

Given an edge-labeled graph representing a city (and the amount of time it takes to travel between each point), and a list of ride requests (with each request consisting of a submit time, starting point, and ending point), this program services each request with a given number of "Uber drivers" with the goal of servicing all of the requests in the smallest amount of time. 

Djikstra's algorithm was used to find the shortest paths between two points for each request (as well as travelling from the end of one ride to the beginning of the next ride).

I implemented several different path finding algorithms and decided to go with Djikstra's in the end as opposed to the A* search algorithm, even though A* is faster and consumes less memory. I did this because given one point, Djikstra's algorithm finds the shortest path to every single other point. As a result, I was able to store past results and reuse them instead of having to re-run the algorithm when a driver encounters a request starting from a point that the algorithm already analyzed the paths for.
