package Classes;

public class Participant {

	private int id; // �� ���������
	int age;
	String fullName; // ���
	String participantType; // ��� ���������
	String degree; // ������� �������
	private String country; // ������

	

	public Participant(int id, int age, String fullName, String participantType, String degree, String country) {
		this.id = id;
		this.age = age;
		this.fullName = fullName;
		this.participantType = participantType;
		this.degree = degree;
		this.country = country;
	}
	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getParticipantType() {
		return participantType;
	}


	public void setParticipantType(String participantType) {
		this.participantType = participantType;
	}


	public String getDegree() {
		return degree;
	}


	public void setDegree(String degree) {
		this.degree = degree;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	@Override
	public String toString() {
		return "�� ���������: " + id + "\n ������� " + age + "\n ���: " + fullName + "\n ��� ���������" + participantType 
				+ "\n ������� �������" + degree + "\n ������" + country; 
	}
}
