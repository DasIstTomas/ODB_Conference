package Classes;

import java.util.Date;

public class Request {

	private int id;
	private String fullName;
	String participantType;
	private String degree;
	/**Дата рассылки*/
	private String date;
	/**Дата подачи заявки участником*/
	private String dateOfBidFiling;
	private String courseName;
	
			
	public Request(int id, String participantType, String fullName, String degree, String date, String dateOfBidFiling, String courseName) {
		this.id = id;
		this.participantType = participantType;
		this.fullName = fullName;
		this.degree = degree;
		this.date = date;
		this.dateOfBidFiling = dateOfBidFiling;
		this.courseName = courseName;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getParticipantType() {
		return participantType;
	}

	public void setParticipantType(String participantType) {
		this.participantType = participantType;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}
	
	public String getDate() {
		return this.date;
	}
	
	public String getDateOfBidFiling() {
		return this.dateOfBidFiling;
	}
	
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	@Override
	public String toString() {
		return "ИД заявки " + id + "\n Имя участника " + fullName + "\n Тип участия" + participantType + "\n Научная степень " + degree +
				"\n Дата проведения " + date + "\n Название курса " + courseName;
	}
}
