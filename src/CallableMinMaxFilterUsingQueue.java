/**
 * A callable class to find top 5 minimum or top 5 maximum
 * based on the user choice.
 * 
 * Uses insertion sort to sort the data.
 * 
 * * @author Sashi Raj Amatya
 * ICS 440 - 01 - The art of multiprocessor programming (Summer 2019)
 * Programming Assignement 2
 * Instructor: Ryan Hankins
 *
 */
import java.util.concurrent.Callable;

public class CallableMinMaxFilterUsingQueue implements Callable<Queue<WeatherData>> {

	private WeatherData[] weatherDataArray = null;
	private WeatherData[] sortedWeatherDataArray = null;
	private Queue<WeatherData> returnQueue = new Queue<WeatherData>();
	private String minMaxTemp;
	
	/**
	 * Constructor
	 * @param weatherDataArray - an array that holds items of type WeatherData.
	 * @param minMaxTemp - user choice of finding minimum or maximum temperature.
	 */
	public CallableMinMaxFilterUsingQueue(WeatherData[] weatherDataArray, String minMaxTemp) {
		this.weatherDataArray = weatherDataArray;
		this.minMaxTemp = minMaxTemp;
	}
	
	/**
	 * A call() method.
	 */
	
	@Override
	public Queue<WeatherData> call() throws Exception {
		
		//Execute code based on user choice
		if(this.minMaxTemp.equals("TMAX")) {
			sortedWeatherDataArray = insertionSortLargestToSmallest(this.weatherDataArray);			
		}
		if(this.minMaxTemp.equals("TMIN")) {
			sortedWeatherDataArray = insertionSortSmallestToLargest(this.weatherDataArray);			
		}
		
		//For returning only the top 5
		for(int index = 0; index < 5; index++) {			
			if(index == sortedWeatherDataArray.length) {break;}
			returnQueue.enqueue(sortedWeatherDataArray[index]);
		}		
		return returnQueue;
	}
	
	
	/**
	 * A method to sort largest to smallest using insertion sort algorithm
	 * @param weatherDataArray - an array of type WeatherData.
	 * @return - an array of type WeatherData.
	 */
	//Sorting largest to smallest
	public static WeatherData[] insertionSortLargestToSmallest(WeatherData[] weatherDataArray) {
		 		 	
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
	
	
	
	/**
	 * A method to sort smallest to largest using insertion sort algorithm
	 * @param weatherDataArray - an array of type WeatherData.
	 * @return - an array of type WeatherData.
	 */
	//Sorting smallest to largest
	 public static WeatherData[] insertionSortSmallestToLargest(WeatherData[] weatherDataArray) {
			 			
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
	
}
