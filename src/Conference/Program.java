package Conference;

import java.time.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.db4o.Db4o;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import com.db4o.query.Query;

import Classes.Course;
import Classes.Request;

import com.db4o.query.Evaluation;

public class Program {

	public static void main(String[] args) {
		ObjectContainer db = initDb();
		db.close();
		
	}
	/**
	 * ���������������� ��������, ���������� � ������� �����
	 */
	public static ObjectContainer initDb() {
		ObjectContainer db;
		String dB4oFileName = "Request_List";
		db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), dB4oFileName);
		List<Request> list = populateObjetcs();
		List<Course> coursesList = populateCourses();
		ObjectSet<Request> result = db.query(Request.class);
		boolean removeAll = list.removeAll(new HashSet<>(result));
		if(removeAll || result.size() == 0)
			for (Request request : list) {
				db.store(request);
		}
		printCount(list);
		showReporters(list);
		showReportersWithSeveralCources(list);
		try {
			showDisjointCourses(coursesList);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return db;
	}
	
	/**
	 * ����� ������ �������� ����������� �����
	 */
	private static void printLine() {
		System.out.println("------------------------------------------------");
	}
	
	/**
	 * ���������� ��� ����������� � ��, ��� �� ������������
	 * @param courses
	 * @throws ParseException 
	 */
	private static void showDisjointCourses(List<Course> courses) throws ParseException {
		
		System.out.println("��������� �����������: ");
		
		for(Course course : courses) {
			int i = 0;
			i++;
			System.out.println(
					i + ") "
					+ course.getCourseName() 
					+ ". ���� ���������� " + course.getDate()
					+ ". ����� ������: " + course.getStartTime()
					+ ". ����� ���������" + course.getEndTime()
					);
		}
	    printLine();
	    List list = new ArrayList();
	    
	    int k = 1;
	    SimpleDateFormat myFormat = new SimpleDateFormat("dd.MM.yyyy");
	    
	    for(Course currentCourse : courses) {
	    	Date currentCourseDate = myFormat.parse(currentCourse.getDate());
	    	
	    	for (int i = 0; i < courses.size(); i++) {
	    		Date entryCourseDate = myFormat.parse(currentCourse.getDate());
				if(currentCourseDate.getTime() != entryCourseDate.getTime()) {
					list.add(currentCourse);
				}
			}
	    	k++;
	    }
	    
	    for (int i = 0; i < list.size(); i++) {
	    	System.out.println(list.get(i));
		}
	   
	    
	}
	

	
	/**
	 * ��������� ��� ������ ������
	 * 
	 * @return coursesList
	 */
	public static List<Course> populateCourses() {
		List<Course> coursesList = new ArrayList<>();
			coursesList.add(new Course(1, "��", 			"27.03.2018", "10:00", "14:00"));
			coursesList.add(new Course(1, "�������������", 	"27.03.2018", "10:00", "15:00"));
			coursesList.add(new Course(1, "�������������", 	"27.03.2018", "16:00", "20:00"));
			coursesList.add(new Course(1, "������������", 	"28.03.2018", "10:00", "14:00"));
			coursesList.add(new Course(1, "����� ���������","28.03.2018", "15:00", "18:00"));
		return coursesList;
	}
	 
	
	/**
	 * ����� ������� ���������� � �����������, ������� ���������������� �� ��������� �����������
	 * 
	 * @param request
	 */
	private static void showReportersWithSeveralCources(List<Request> request) {
		int k = 0;
		
		System.out.println("����������, ������� ���������������� �� ��������� �����������: ");

		for(int i = 0; i < request.size(); i++) {
			int a = request.get(i).getId();
			for(int j = i+1; j < request.size(); j++) {
				int b = request.get(j).getId();
				if(a == b && request.get(j).getParticipantType().equals("���������")) {
					k++;
					System.out.println( 
							"����� ������ - " + k 
							+ ") ���: " + request.get(i).getFullName() 
							+ ". ������� �������: " + request.get(i).getDegree() 
							+ ". �������� �����: " + request.get(i).getCourseName() 
							+ ". ���, ��� ��������� �����: " + request.get(i).getParticipantType()
					);	
				}
			}
		}
		printLine();
		if(k == 0) {
			System.out.println("��������� ����");
			printLine();
		}
	}
	
	/**
	 * ����� ���������� �����������, ������� ������ ������ � ������� 
	 * 3 ���� ����� �������� 1-�� �����������
	 * 
	 * @param request
	 */
	private static void showReporters(List<Request> request) {
		int reportersCount = 0;
		for(Request req: request) {
			String dateOFSpam = req.getDate();
			String dateOfBidFiling = req.getDateOfBidFiling();
			if(differenceBetweenDates(dateOFSpam, dateOfBidFiling) <= 3 && req.getParticipantType().equals("���������")) {
				reportersCount++;
				
			}
		}
		if(reportersCount != 0) {
			System.out.println(
					"���������� �����������, ������� ������"
					+ " ������ � ������� 3 ���� ����� �������� 1-�� �����������: "
					+ reportersCount
			);
			
			
		} else 
			System.out.println("��������� ����");
			printLine();

	}
	
	/**
	 * ����� ���������� ��� ����
	 * 
	 * @param dateOFSpam - ���� �����������
	 * @param dateOfBidFiling - ���� ������ ������
	 * 
	 * @return �����, ���������� ����
	 */
	private static long differenceBetweenDates(String dateOFSpam, String dateOfBidFiling) {
		
        SimpleDateFormat myFormat = new SimpleDateFormat("dd.MM.yyyy");
        /*
         * �����, setLenient �������� �������, ���� ���� ����� ������������,
         * ���� 31.02.2013 ��� 50.50.2013
         */
        myFormat.setLenient(false);
 
        Date date1 = null;
        Date date2 = null;
	        try {
	            date1 = myFormat.parse(dateOFSpam);
	            date2 = myFormat.parse(dateOfBidFiling);
	        } catch (Exception e) {
	            System.out.println("�������� ����");
	            e.printStackTrace();
	        }
        long d1MinusD2 = date2.getTime() -  date1.getTime();
        return d1MinusD2 / (24 * 60 * 60 * 1000);
    }
	
	/**
	 * ����� ������� ����� ���������� ���������� � �����������
	 * 
	 * @param request - ������ ���������
	 */
	private static void printCount(List<Request> request) {
		int listenerCount = 0;
		int speakerCount = 0;
		for(Request req : request) {
			if(req.getParticipantType().equals("���������")) {
				listenerCount++;
			}
			if(req.getParticipantType().equals("���������")) {
				speakerCount++;
			}
		}
		if(listenerCount + speakerCount == 0) {
			System.out.println("��������� ����");
			printLine();
		} else {
			System.out.println(
					"����� ���������� ����������: " + listenerCount + 
					". ����� ���������� �����������: " + speakerCount 
			);	
			printLine();
		}
	}
	
	
	
	public static List<Request> populateObjetcs() {
		List<Request> list = new ArrayList<>();
		list.add(new Request(001, "���������", "������ ���� ��������", "��������", "19.03.2018", "19.03.2018", "��"));
		list.add(new Request(002, "���������", "������ �������", "��������", "19.03.2018", "25.03.2018", "�������������"));
		
		// � ��� ��� ��� �������� ������ ������� ��������� ������ � ���� �� �������� 4 ���� �� ������ �����
		list.add(new Request(003, "���������", "����� ���������� ��������", "��������", "19.03.2018", "20.03.2018", "�������������"));
		list.add(new Request(003, "���������", "����� ���������� ��������", "��������", "19.03.2018", "20.03.2018", "��"));
		list.add(new Request(003, "���������", "����� ���������� ��������", "��������", "19.03.2018", "20.03.2018", "�������������"));
		list.add(new Request(003, "���������", "����� ���������� ��������", "��������", "19.03.2018", "20.03.2018", "����� ���������"));

		return list;
	}
	
	
	
	/*
	 * public static void showRes(List list) {
		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			   System.out.println(list.get(i));
		}
	}
	
	public static void listResult(ObjectSet result) {
		 System.out.println(result.size());
		 while(result.hasNext()) {
			 System.out.println(result.next());
		 }
	}
	 */
	
}

