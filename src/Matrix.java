/*
 * Tayyab Ahmad
 * 10197212
 * 
 * Provided with a csv file representing a matrix of integers, this class will 
 * read it into a 2-dimensional array of integers
 *
 * Alternate constructor will create a matrix of zeros provided with the desired
 * dimensions of the matrix
 * 
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Matrix {

	private int[][] matrix;
	public int numRows;
	public int numCols;
	
	// matrix constructor with csv file
	public Matrix(String file) throws FileNotFoundException, IOException {

		ArrayList<String> temp = new ArrayList<>();
		
		//read and store lines of data
        try (BufferedReader br = new BufferedReader(new FileReader(file)))  {
    	    String line;
    	    while ((line = br.readLine()) != null) {
    	    	temp.add(line);
    	    } //end while-loop
        } //end try block
        
        // determine number of columns needed
        int cols = 1;
        String lines = temp.get(0);
        for (int k = 0; k < lines.length(); k++) {
        	if (String.valueOf(lines.charAt(k)).equals(","))
        		cols++;
        } //end for-loop
        
        this.numRows = temp.size();
        this.numCols = cols;
        
        int[][] arr = new int[numRows][numCols]; // initialize matrix
        
        // fill in matrix using file data
        for (int i = 0; i < numRows; i++) {
        	
        	String line = temp.get(i);
        	int lineIndex = 0;
        	int lineLength = line.length();
        	
        	for (int j = 0; j < numCols; j++) {
        		
        		// iterate through chars up to next comma to determine current entry
        		String val = "";
        		while (! (line.charAt(lineIndex) == ',')) {
        			val += String.valueOf(line.charAt(lineIndex));
        			lineIndex++;
        			if (lineIndex == lineLength)
            			break;
        		} //end while-loop
        		
        		arr[i][j] = Integer.parseInt(val); //add current entry to matrix
        		lineIndex++;
        	
        	} //end for-loop INNER
        } //end for-loop OUTER
        
        this.matrix = arr;
	} //end matrix constructor 1
	
	// matrix constructor with dimensions
	public Matrix(int x, int y) {
		this.numRows = x;
		this.numCols = y;
		int[][] arr = new int[x][y];
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				arr[i][j] = 0;
			} //end for-loop INNER
		} //end for-loop OUTER
		this.matrix = arr;
	} //end matrix constructor 2
	
	// returns value of grid at index (i,j) (getter)
	public int at(int i, int j) {
		return matrix[i][j];
	} //end getter
	
	// sets index (i,j) to desired value (setter)
	public void set(int i, int j, int val) {
		matrix[i][j] = val;
	} //end setter
	
	// prints the matrix to the console
	public void printMatrix() {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
					System.out.printf("%-5d", matrix[i][j]);
			} //end for-loop INNER
			System.out.println("");
		} //end for-loop OUTER
	} //end printMatrix
	
} //end Matrix
