/**
 * 
 *A driver to run the program.
 *It prompt users for their input so as to specify whether they wish to 
 *find minimum of maximum temperature within the specified data range.
 *
 *The driver find the maximum or minimum based on the user choice
 *and list them along with the location specific data.
 * 
 * @author Sashi Raj Amatya
 * ICS 440 - 01 - The art of multiprocessor programming (Summer 2019)
 * Programming Assignement 2
 * Instructor: Ryan Hankins
 *
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RealWeatherDataQueryDriver {
	public static int startingYear;
	public static int endingYear;
	public static int startingMonth;
	public static int endingMonth;
	public static String minMaxTemp;
	
	public static <T> void main(String[] args) throws FileNotFoundException, IOException {
		
		
		//Using scanner for Getting input from user
		Scanner inputScanner = new Scanner(System.in);
		
		//Reading user choices
		System.out.println("Please enter the starting year: ");
		startingYear = inputScanner.nextInt();
		System.out.println("Please enter the ending year: ");
		endingYear = inputScanner.nextInt();
		while(endingYear<startingYear) {
			System.out.println("*** ENDING YEAR must be GREATER than STARTING MONTH! ***");
			System.out.println("Please enter ending year again: ");
			endingYear = inputScanner.nextInt();
		}
		
		System.out.println("Please enter the starting month: ");
		startingMonth = inputScanner.nextInt();		
		System.out.println("Please enter the ending month: ");
		endingMonth = inputScanner.nextInt();
		while(endingMonth<startingMonth) {
			System.out.println("*** ENDING MONTH must be GREATER than STARTING MONTH! ***");
			System.out.println("Please enter ENDING MONTH again: ");
			endingMonth = inputScanner.nextInt();
		}
		
		System.out.println("Calculate minimum or maximum temperature?");
		System.out.println("Enter TMIN for minumum and TMAX for maximum (TMAX or TMIN): ");
		minMaxTemp = inputScanner.next();
		while(true) {
			if(minMaxTemp.equals("TMIN")) {break;}
			if(minMaxTemp.equals("TMAX")) {break;}
			System.out.println("----- WRONG INPUT!!! -----");
			System.out.println("Must enter either \'TMIN\' or \'TMAX\'");
			System.out.println("Please enter TMAX for maximum temperature or TMIN for minimum temperature:");
			minMaxTemp = inputScanner.next();
		}
		
		inputScanner.close();
		
				
		//******************************************************************************************
		//Part#1 and 2 of Assignment
		//Using thread pools, future, and callables
		//Part#1 of the assignment goes beyond "Starting of Part#2" section of the comment
		
		//Get ExecutorService from Executors utility class, thread pool is 10
		ExecutorService executor = Executors.newFixedThreadPool(10);	
		
		//Create a queue to hold the Future Object associated with callable		
		Queue<Future<Queue<WeatherData>>> futureWeatherDataQueue = new Queue<Future<Queue<WeatherData>>>();
		//Creating queue of WeatherData to store the return value of future
		Queue<WeatherData> weatherDataQueue = new Queue<WeatherData>();
		Queue<WeatherData> topFiveFromAllFilesQueue = new Queue<WeatherData>();
		
		
		
		//*****************************************************************************************
		// Starting of Part #2 of the Assignment of consolidating all the queries from all the files
		//into a single list will be full-filled starting here
		
		//THis will read everything in that folder regardless of the file type
		File folder = new File("C:"+File.separator+"ghcnd_hcn");
		File[] fileNames = folder.listFiles();
		
		//Until there is a file in the folder
		for(File file: fileNames) {
			
			try {
				//Create CallableWeatherDataReader instance
				Callable<Queue<WeatherData>> callableWeatherData =new CallableWeatherDataReader(file,minMaxTemp);
				//Submit Callable task to be executed by thread pool
				Future<Queue<WeatherData>> future = executor.submit(callableWeatherData);
				//Adding future to the queue
				futureWeatherDataQueue.enqueue(future);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
				
		//Until the queue that holds future weatherData is not empty
		while(!futureWeatherDataQueue.isEmpty()) {
			try {
				//storing the return value of future in a weathreDataQueue since the
				//future is returning a queue of WeatherData.
				weatherDataQueue = futureWeatherDataQueue.dequeue().get();	
					WeatherData weatherData = new WeatherData();
					//Storing weather Data into topFiveFromAllFilesQueue
					while(!weatherDataQueue.isEmpty()) {
						weatherData = weatherDataQueue.dequeue();
						topFiveFromAllFilesQueue.enqueue(weatherData);
					}
			} catch(InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		//End of part #1 and #2 of the assignment
		//------------------------------------------------------------
		
		
		
		
		
		//****************************************************************
		//Starting of Part 3 of the Assignment
		
		//Creating a queue to store top results 
		Queue<WeatherData> topResultsQueue = new Queue<WeatherData>();		
		
		//Creating reference for maximum against which to compare
		//other values
		WeatherData weatherData = new WeatherData();	
		
		//Generating set of four futures.
		//Futures will iterate over topFiveFromAllFilesQueue
		//in order to find top results.
		//Top results are anything that is above initial maximum
		//or below initial minimum based on whether the user
		//wants to find the maximum of minimum.
		
		ExecutorService executor2 = Executors.newFixedThreadPool(4);
				
				
		//Creating a queue to hold Future objects associated with callable
		Queue<Future<Queue<WeatherData>>> futureTopResultsWeatherDataQueue = new Queue<Future<Queue<WeatherData>>>();
				
				
		//Creating queues to store values from the future
		Queue<WeatherData> queueToStoreFutureGet = new Queue<WeatherData>();
				
				
		//Initializing callable variable
		Callable<Queue<WeatherData>> CallableMinMaxFilterUsingQueueForFuture1 = null;
		Callable<Queue<WeatherData>> CallableMinMaxFilterUsingQueueForFuture2 = null;
		Callable<Queue<WeatherData>> CallableMinMaxFilterUsingQueueForFuture3 = null;
		Callable<Queue<WeatherData>> CallableMinMaxFilterUsingQueueForFuture4 = null;
				
		//creating array that is equal to the size of the queue
		int arrayIndex = 0;
		WeatherData[] topFiveFromAllFilesArray = new WeatherData[topFiveFromAllFilesQueue.size()];
		while(!topFiveFromAllFilesQueue.isEmpty()) {
			topFiveFromAllFilesArray[arrayIndex] = topFiveFromAllFilesQueue.dequeue();
			arrayIndex++;
		}
				
		//Calculating indexes for one fourth of the list items
		int arraySplit1 = arrayIndex/4;
		int arraySplit2 = arrayIndex/2;
		int arraySplit3 = (arrayIndex*3)/4; 
				
				
		//Creating callable instance based on user choice of displaying Minumum or Maximum Temperature
		CallableMinMaxFilterUsingQueueForFuture1 = new CallableMinMaxFilterUsingQueue(Arrays.copyOfRange(topFiveFromAllFilesArray, 0, arraySplit1),minMaxTemp); 
		CallableMinMaxFilterUsingQueueForFuture2 = new CallableMinMaxFilterUsingQueue(Arrays.copyOfRange(topFiveFromAllFilesArray, arraySplit1, arraySplit2),minMaxTemp);
		CallableMinMaxFilterUsingQueueForFuture3 = new CallableMinMaxFilterUsingQueue(Arrays.copyOfRange(topFiveFromAllFilesArray, arraySplit2, arraySplit3),minMaxTemp);
		CallableMinMaxFilterUsingQueueForFuture4 = new CallableMinMaxFilterUsingQueue(Arrays.copyOfRange(topFiveFromAllFilesArray, arraySplit3, arrayIndex),minMaxTemp);
		
		//Submit Callable task to be executed by thread pool
		Future<Queue<WeatherData>> future1 = executor2.submit(CallableMinMaxFilterUsingQueueForFuture1);		
		Future<Queue<WeatherData>> future2 = executor2.submit(CallableMinMaxFilterUsingQueueForFuture2);		
		Future<Queue<WeatherData>> future3 = executor2.submit(CallableMinMaxFilterUsingQueueForFuture3);		
		Future<Queue<WeatherData>> future4 = executor2.submit(CallableMinMaxFilterUsingQueueForFuture4);	
				
		//Adding future to the queue
		futureTopResultsWeatherDataQueue.enqueue(future1);
		futureTopResultsWeatherDataQueue.enqueue(future2);
		futureTopResultsWeatherDataQueue.enqueue(future3);
		futureTopResultsWeatherDataQueue.enqueue(future4);
							
		while(!futureTopResultsWeatherDataQueue.isEmpty()) {
			try {
				queueToStoreFutureGet = futureTopResultsWeatherDataQueue.dequeue().get();	
				while(!queueToStoreFutureGet.isEmpty()) {
					weatherData = queueToStoreFutureGet.dequeue();
					topResultsQueue.enqueue(weatherData);
				}
			} catch (InterruptedException | ExecutionException e) {						
				e.printStackTrace();
			}
		}
		
		// End of part 3 of the Assignment
		//-----------------------------------------------------------------------
		
		
				
				
		//*************************************************************************
		//Starting Part4 of the Assignment
		//Finding the top results for all of the futures		
			
		//Creating an array of weather data in order to receive
		//sorted array once selection sort algorithm returns
		WeatherData[] weatherDataArray = new WeatherData[topResultsQueue.size()];
		WeatherData[] finalFiveWeatherDataArray = new WeatherData[5];
		
		//If user choose to find minimum temperature,
		//sort it from lowest temperature to the highest
		if(minMaxTemp.equals("TMIN")) {
			weatherDataArray = InsertionSortSmallestToLargest.insertionSortSmallestToLargest(topResultsQueue);
		}
		
		////If user choose to find maximum temperature,
		//sort it from highest temperature to the lowest
		if(minMaxTemp.equals("TMAX")) {
			weatherDataArray = InsertionSortLargestToSmallest.insertionSortLargestToSmallest(topResultsQueue);
		}		
		
		for(int index = 0; index < 5; index++) {
			finalFiveWeatherDataArray[index] = weatherDataArray[index];
		}
			
		//End of Part4
		//-------------------------------------------------------------------------
		
		
		
		//**************************************************************************
		//Starting Part5 of the Assignment
		//Printing final five results along with state, location name, latitude
		//and longitude for each query.		
		String weatherDataID;
		StationData stationData = new StationData();
		StationDataReader stationDataReader = new StationDataReader();
		
		//printing final 5 results
		for(int index = 0; index < 5; index++) {
			System.out.println("");
			weatherDataID = finalFiveWeatherDataArray[index].getId();
			System.out.println(finalFiveWeatherDataArray[index]);
				
			//getting Station Data that matches the weather data ID.
			stationData = stationDataReader.readFile(weatherDataID);
			System.out.println(stationData);				
		}		
		//End of Part5	
		//--------------------------------------------------------------------
		
		
		
		//Shut down the executor services now
		executor.shutdown();
		executor2.shutdown();
	}
	
	
	
	
	
	
	
	
	/**
	 * Insertion sort - sorting the content of input queue in ASCENDING ORDER
	 * by comparing the value of weather data.
	 * @param inputQueue - a queue whose values are to be sorted
	 * @return - sorted data will be returned as an array of WeatherData
	 */
	 public static WeatherData[] insertionSortSmallestToLargest(Queue<WeatherData> inputQueue) {
		 
		 WeatherData[] weatherDataArray = new WeatherData[inputQueue.size()];
			
		 int index = 0;
			while(!inputQueue.isEmpty()) {
				weatherDataArray[index] = inputQueue.dequeue();			
				index++;
			}		
		
		for(int i = 1; i < weatherDataArray.length; i++) {
			
			WeatherData weatherData = weatherDataArray[i];
			int j = i;
			
			//shift weatherData to the right as long as they are greater than weatherData
			for(j =i; j > 0 && weatherDataArray[j-1].getValue() > weatherData.getValue(); j--) {
				weatherDataArray[j] = weatherDataArray[j-1];
			}
			
			weatherDataArray[j] = weatherData;
		}
		return weatherDataArray;
	 }
	
	 
	 
	 /**
		 * Insertion sort - sorting the content of input queue in DESCENDING ORDER
		 * by comparing the value of weather data.
		 * @param inputQueue - a queue whose values are to be sorted
		 * @return - sorted data will be returned as an array of WeatherData
		 */
		
		
		 public static WeatherData[] insertionSortLargestToSmallest(Queue<WeatherData> inputQueue) {
			 
			 WeatherData[] weatherDataArray = new WeatherData[inputQueue.size()];
				
			 int index = 0;
				while(!inputQueue.isEmpty()) {
					weatherDataArray[index] = inputQueue.dequeue();			
					index++;
				}		
			
			for(int i = 1; i < weatherDataArray.length; i++) {
				
				WeatherData weatherData = weatherDataArray[i];
				int j = i;
				
				//shift weatherData to the right as long as they are greater than weatherData
				for(j =i; j > 0 && weatherDataArray[j-1].getValue() < weatherData.getValue(); j--) {
					weatherDataArray[j] = weatherDataArray[j-1];
				}
				
				weatherDataArray[j] = weatherData;
			}
			
			 
			 return weatherDataArray;
		 }
	
	
}
