/**
 * 
 * A callable class to read file with .dly exension.
 * Each line of the file is read and converted into WeatherData object.
 * It returns weatherDataQueue to the caller which is a queue of WeatherData type.
 * 
 * @author Sashi Raj Amatya
 * ICS 440 - 01 - The art of multiprocessor programming (Summer 2019)
 * Programming Assignement 2
 * Instructor: Ryan Hankins
 *
 *
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.Callable;

public class CallableWeatherDataReader implements Callable<Queue<WeatherData>> {
	
	private Queue<WeatherData> weatherDataQueue = new Queue<WeatherData>();
	private Queue<WeatherData> returnQueue = new Queue<WeatherData>();
	private String minMaxTemp;
	
	
	private File file;
	
	/**
	 * Constructor
	 * @param file - a variable to represent a file.
	 * @param minMaxTemp - a variable to represent minimum or maximum
	 * 				temperature based on user choice.
	 */
	public CallableWeatherDataReader(File file, String minMaxTemp) {
		this.file = file;
		this.minMaxTemp = minMaxTemp;
	}
	
	
	/**
	 * A call() method
	 */
	@Override
	public Queue<WeatherData> call() throws Exception {	
		
		//Creating weatherData variable
		WeatherData weatherData = null;
		
		//Checks whether the file has .dly extension
		if(file.getName().toLowerCase().endsWith(".dly")) {
			
			//try reading the file
			try(BufferedReader br = new BufferedReader(new FileReader(file))){
				String strLine;
				
				//Read lines from the file, returns null when end of stream
				//is reached
				while((strLine = br.readLine()) != null) {
					//System.out.println(strLine);	
					String id = strLine.substring(0,11);
					int year = Integer.valueOf(strLine.substring(11, 15).trim());
					int month = Integer.valueOf(strLine.substring(15, 17).trim());
					String element = strLine.substring(17, 21);
	
					//If year>starting year and year<ending year
					//and month>starting month and month<starting month
					//and elements = TMIN or TMAX
					
					if((year>=RealWeatherDataQueryDriver.startingYear && year<=RealWeatherDataQueryDriver.endingYear) && (month>=RealWeatherDataQueryDriver.startingMonth && month<=RealWeatherDataQueryDriver.endingMonth) && element.endsWith(RealWeatherDataQueryDriver.minMaxTemp))
					{
						//calculate the number of days in the line
						int days = (strLine.length() - 21) / 8;						
						
						
						//Process each day in the line
						for(int i = 0; i < days; i++) {
							
							
							int value = Integer.valueOf(strLine.substring(21+8*i, 26+8*i).trim());
							String qflag = strLine.substring(27+8*i, 28+8*i);
							
							//Only if value is not equal to -9999
							// and qflag is an empty space
							if(value != -9999 && qflag.equals(" ")) {								
								
									weatherData = new WeatherData();
									weatherData.setId(id);
									weatherData.setYear(year);
									weatherData.setMonth(month);
									weatherData.setDay(i+1);
									weatherData.setElement(element);
									weatherData.setValue(value);
									weatherData.setQflag(qflag);
									weatherDataQueue.enqueue(weatherData);
							}
						}
					}
				}
			}	
		}	

		
		
		//*********************************INSERTION SORT *****************
		if(weatherDataQueue.size()>0)
		{
		
			WeatherData[] sortedWeatherDataArray = new WeatherData[weatherDataQueue.size()];
			
			if(this.minMaxTemp.equals("TMAX")) {
				sortedWeatherDataArray = InsertionSortLargestToSmallest.insertionSortLargestToSmallest(weatherDataQueue);
			}
			if(this.minMaxTemp.equals("TMIN")) {
				sortedWeatherDataArray = InsertionSortSmallestToLargest.insertionSortSmallestToLargest(weatherDataQueue);
			}
			
			for(int i = 0; i<5; i++) {
				if(i==sortedWeatherDataArray.length) {break;}
				returnQueue.enqueue(sortedWeatherDataArray[i]);
				
				//TEST CODE
				//if(sortedWeatherDataArray[i].getValue()==539) {
				//System.out.println(sortedWeatherDataArray[i]);
				//}
				
				//arraySize++;
				//END OF TEST CODE
			}			
			//System.out.println(arraySize);
			
		}
		//System.out.println(arraySize);
		return returnQueue;
	}
}







