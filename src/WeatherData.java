/**
 * 
 *A class to represent weather data.
 *
 * 
 * @author Sashi Raj Amatya
 * ICS 440 - 01 - The art of multiprocessor programming (Summer 2019)
 * Programming Assignement 1
 * Instructor: Ryan Hankins
 *
 *
 */
class WeatherData {
	private String id;
	private int year;
	private int month;
	private int day;
	private String element;
	private int value;
	private String qflag;
	
	/**
	 * Constructor
	 */
	public WeatherData() {
		
	}
	
	/**
	 * Constructor
	 * @param id - id of WeatherData object
	 */
	public WeatherData(String id) {
		this.id = id; 
	}
	
	/**
	 * Constructor
	 * @param id - unique id of the data for which the weather reading was taken
	 * @param year - the year the reading was taken
	 * @param month - the month the reading was taken
	 * @param day - the day the reading was taken
	 * @param element - the type of reading 
	 * @param value - reading value based on element being read
	 * @param qflag - the qflag should be empty.
	 */
	public WeatherData(String id, int year, int month, int day, String element, int value, String qflag) {
		this.id = id;
		this.year = year;
		this.month = month;
		this.day = day;
		this.element = element;
		this.value = value;
		this.qflag = qflag;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getElement() {
		return element;
	}
	public void setElement(String element) {
		this.element = element;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getQflag() {
		return qflag;
	}
	public void setQflag(String qflag) {
		this.qflag = qflag;
	}
	
	@Override
	public String toString() {
		String output="ID:"+ this.id + " Year:" + this.year + " Month:" + this.month + 
				" Day:" + this.day + " Element:" + this.element + " Value:"+ ((double)this.value/10)+"C " + 
				" qflag:" +this.qflag;
		return output;
	}
}