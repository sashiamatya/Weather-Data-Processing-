/**
 * 
 * A class to implement Insertion Sort algorithm that will
 * sort elements from smallest to largest based on their integer value.
 * 
 * @author Sashi Raj Amatya
 * ICS 440 - 01 - The art of multiprocessor programming (Summer 2019)
 * Programming Assignement 2
 * Instructor: Ryan Hankins
 *
 */
public class InsertionSortSmallestToLargest {

	public InsertionSortSmallestToLargest() {
		
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
}
