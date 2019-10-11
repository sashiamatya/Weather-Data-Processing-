/**
 * 
 *A class to read ghcnd-stations.txt file.
 *Each line read from the file is converted into StationData object and returned.
 *
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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class StationDataReader {
	
	
	private String id;
	
	/**
	 * Constructors
	 */
	public StationDataReader() {
		
	}
	
	public StationDataReader(String id) {
		this.id = id;
	}
	
	public StationData readFile(String id) throws FileNotFoundException, IOException {
		
		StationData stationData = new StationData();
		
		//File folder = new File("C:\\Users\\sashi\\Desktop\\SASHI METROSTATE\\ICS 440 - Parallel and Distribute Algorithm\\Programming Assignments\\Prog. Assignment 2\\ghcnd_hcn\\ghcnd-stations.txt");
		File folder = new File("C:"+File.separator+"ghcnd_hcn"+File.separator+"ghcnd-stations.txt");
		
		try(BufferedReader br = new BufferedReader(new FileReader(folder))) {
			String strLine;
			
			while( (strLine = br.readLine()) != null) {
				this.id = strLine.substring(0,11);
				
				//Only create station object for those that matches the id
				if(this.id.equals(id)) {
					stationData.setId(strLine.substring(0,11));
					stationData.setLatitude(Float.valueOf(strLine.substring(12, 20).trim()));
					stationData.setLongitude(Float.valueOf(strLine.substring(21, 30).trim()));
					stationData.setElevation(Float.valueOf(strLine.substring(31, 37).trim()));
					stationData.setState(strLine.substring(38, 40));
					stationData.setName(strLine.substring(41, 71));
				}
			}
		}		
		return stationData;
	}
	
	
}
