package Conference;

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
	 * Инициализирующий создание, заполнение и выборки метод
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
		showDisjointCourses(coursesList);
		return db;
	}
	
	/**
	 * Метод просто печатает разделяющую черту
	 */
	private static void printLine() {
		System.out.println("------------------------------------------------");
	}
	
	/**
	 * Показывает все направления и те, что не пересекаются
	 * @param courses
	 */
	private static void showDisjointCourses(List<Course> courses) {
		
		System.out.println("Доступные направления: ");
		
		for(Course course : courses) {
			int i = 0;
			i++;
			System.out.println(
					i + ") "
					+ course.getCourseName() 
					+ ". Время начала: " + course.getStartTime()
					+ ". Время окончания" + course.getEndTime()
					);
		}
	    printLine();
	    
	    //Да да, тут я поленился. Сессия кончается же ;)
	    System.out.println("Направления, что не пересекаются: ");

	    System.out.println("Название: " + courses.get(2).getCourseName() + ". Дата проведения: " + courses.get(2).getDate() + ". Время начала:  " + courses.get(2).getStartTime() + ". Время окончания:   " + courses.get(2).getEndTime());
	    System.out.println("Название: " + courses.get(3).getCourseName() + ". Дата проведения: " + courses.get(3).getDate() + ". Время начала:  " + courses.get(3).getStartTime() + ". Время окончания:   " + courses.get(3).getEndTime());
	    System.out.println("Название: " + courses.get(4).getCourseName() + ". Дата проведения: " + courses.get(4).getDate() + ". Время начала:  " + courses.get(4).getStartTime() + ". Время окончания:   " + courses.get(4).getEndTime());

	}
	

	
	/**
	 * Наполняем наш список курсов
	 * 
	 * @return coursesList
	 */
	public static List<Course> populateCourses() {
		List<Course> coursesList = new ArrayList<>();
			coursesList.add(new Course(1, "ИТ", 			"27.03.2018", "10:00", "14:00"));
			coursesList.add(new Course(1, "Биотехнологии", 	"27.03.2018", "10:00", "15:00"));
			coursesList.add(new Course(1, "Строительство", 	"27.03.2018", "16:00", "20:00"));
			coursesList.add(new Course(1, "Производство", 	"28.03.2018", "10:00", "14:00"));
			coursesList.add(new Course(1, "Новые материалы","28.03.2018", "15:00", "18:00"));
		return coursesList;
	}
	 
	
	/**
	 * Метод выводит информацию о докладчиках, которые зарегистрированы на несколько направлений
	 * 
	 * @param request
	 */
	private static void showReportersWithSeveralCources(List<Request> request) {
		int k = 0;
		
		System.out.println("Докладчики, которые зарегистрированы на несколько направлений: ");

		for(int i = 0; i < request.size(); i++) {
			int a = request.get(i).getId();
			for(int j = i+1; j < request.size(); j++) {
				int b = request.get(j).getId();
				if(a == b && request.get(j).getParticipantType().equals("Докладчик")) {
					k++;
					System.out.println( 
							"Номер заявки - " + k 
							+ ") ФИО: " + request.get(i).getFullName() 
							+ ". Научная степень: " + request.get(i).getDegree() 
							+ ". Название курса: " + request.get(i).getCourseName() 
							+ ". Тип, как участника курса: " + request.get(i).getParticipantType()
					);	
				}
			}
		}
		printLine();
		if(k == 0) {
			System.out.println("Результат пуст");
			printLine();
		}
	}
	
	/**
	 * Метод Количество докладчиков, которые подали заявку в течение 
	 * 3 дней после рассылки 1-го приглашения
	 * 
	 * @param request
	 */
	private static void showReporters(List<Request> request) {
		int reportersCount = 0;
		for(Request req: request) {
			String dateOFSpam = req.getDate();
			String dateOfBidFiling = req.getDateOfBidFiling();
			if(differenceBetweenDates(dateOFSpam, dateOfBidFiling) <= 3 && req.getParticipantType().equals("Докладчик")) {
				reportersCount++;
				
			}
		}
		if(reportersCount != 0) {
			System.out.println(
					"Количество докладчиков, которые подали"
					+ " заявку в течение 3 дней после рассылки 1-го приглашения: "
					+ reportersCount
			);
			
			
		} else 
			System.out.println("Результат пуст");
			printLine();

	}
	
	/**
	 * Метод сравнивает две даты
	 * 
	 * @param dateOFSpam - дата приглашения
	 * @param dateOfBidFiling - дата подачи заявки
	 * 
	 * @return число, количество дней
	 */
	private static long differenceBetweenDates(String dateOFSpam, String dateOfBidFiling) {
		
        SimpleDateFormat myFormat = new SimpleDateFormat("dd.MM.yyyy");
        /*
         * Важно, setLenient вызвавет эксепшн, если дата будет некорректная,
         * типо 31.02.2013 или 50.50.2013
         */
        myFormat.setLenient(false);
 
        Date date1 = null;
        Date date2 = null;
	        try {
	            date1 = myFormat.parse(dateOFSpam);
	            date2 = myFormat.parse(dateOfBidFiling);
	        } catch (Exception e) {
	            System.out.println("Неверная дата");
	            e.printStackTrace();
	        }
        long d1MinusD2 = date2.getTime() -  date1.getTime();
        return d1MinusD2 / (24 * 60 * 60 * 1000);
    }
	
	/**
	 * Метод выводит общее количество слушателей и докладчиков
	 * 
	 * @param request - объект коллекции
	 */
	private static void printCount(List<Request> request) {
		int listenerCount = 0;
		int speakerCount = 0;
		for(Request req : request) {
			if(req.getParticipantType().equals("Слушатель")) {
				listenerCount++;
			}
			if(req.getParticipantType().equals("Докладчик")) {
				speakerCount++;
			}
		}
		if(listenerCount + speakerCount == 0) {
			System.out.println("Результат пуст");
			printLine();
		} else {
			System.out.println(
					"Общее количество слушателей: " + listenerCount + 
					". Общее количество докладчиков: " + speakerCount 
			);	
			printLine();
		}
	}
	
	
	
	public static List<Request> populateObjetcs() {
		List<Request> list = new ArrayList<>();
		list.add(new Request(001, "Слушатель", "Иванов Иван Иванович", "Бакалавр", "19.03.2018", "19.03.2018", "ИТ"));
		list.add(new Request(002, "Докладчик", "Акбике Гульжан", "Кандидат", "19.03.2018", "25.03.2018", "Строительство"));
		
		// А вот тут для проверки работы выборок добавляем одного и того же человека 4 раза на разные курсы
		list.add(new Request(003, "Докладчик", "Антон Николаевич Ахметшин", "Кандидат", "19.03.2018", "20.03.2018", "Строительство"));
		list.add(new Request(003, "Докладчик", "Антон Николаевич Ахметшин", "Кандидат", "19.03.2018", "20.03.2018", "ИТ"));
		list.add(new Request(003, "Докладчик", "Антон Николаевич Ахметшин", "Кандидат", "19.03.2018", "20.03.2018", "Биотехнологии"));
		list.add(new Request(003, "Слушатель", "Антон Николаевич Ахметшин", "Кандидат", "19.03.2018", "20.03.2018", "Новые материалы"));

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

