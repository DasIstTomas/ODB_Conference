package Classes;

import java.util.Date;

public class Course{
	//Violation
	int id; // Ид курса
	String courseName; // название курса
	String date; // Дата проведения
	String startTime;
	String endTime;

	public Course(int id, String courseName, String date, String startTime, String endTime) {
		this.id = id;
		this.courseName = courseName;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	public Course(int id, String courseName, String date) {
		this.id = id;
		this.courseName = courseName;
		this.date = date;
	}
	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCourseName() {
		return courseName;
	}


	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	@Override
	public String toString() {
		return "Название научного направления: " + courseName + "\n Дата проведения: " + date;
	}
}

