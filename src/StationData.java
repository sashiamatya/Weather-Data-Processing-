/**
 * 
 *A class to represent Station data.
 * 
 * @author Sashi Raj Amatya
 * ICS 440 - 01 - The art of multiprocessor programming (Summer 2019)
 * Programming Assignement 2
 * Instructor: Ryan Hankins
 *
 *
 */
public class StationData {
	private String id;
	private float latitude;
	private float longitude;
	private float elevation;
	private String state;
	private String name;
	
	/**
	 * Constructor
	 */
	public StationData() {
		
	}
	
	
	@Override
	public String toString() {
		String output="";
		
		output="ID:"+ this.id + " latitude:" + this.latitude + " longitude:" + this.longitude + 
				" elevation:" + this.elevation + " State:" + this.state + " name:"+ this.name;
		return output;
	}
	
	/**
	 * All getters and Setters below this point
	 */
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	
	public float getLongitude() {
		return longitude;
	}


	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}


	public float getElevation() {
		return elevation;
	}
	public void setElevation(float elevation) {
		this.elevation = elevation;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
