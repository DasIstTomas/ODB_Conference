package Classes;

public class Audience {
	//Driver
	private int number; // номер аудитории
	private String name; // наименование универа

	public Audience(int number, String name) {
		this.number = number;
		this.name = name;
	}
	
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Номер аудитории " + number + "\n Университет " + name;
	}
}
