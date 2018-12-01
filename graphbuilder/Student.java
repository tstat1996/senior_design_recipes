package graphbuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student {
	Map<String, List<String>> courses;
	
	public Student(){
		courses = new HashMap<String, List<String>>();
	}
	
	public void addCourse(String subject, String number){
		if(courses.containsKey(subject)){
			courses.get(subject).add(number);
		}
		else{
			List<String> numbers = new ArrayList<String>();
			numbers.add(number);
			courses.put(subject, numbers);
		}
	}
	
	public Map<String, List<String>> getStudentCourses(){
		return courses;
	}
	
}
