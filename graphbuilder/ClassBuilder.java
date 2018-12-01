package graphbuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassBuilder {

	public void parser(){
		Reader r;
		Student s = new Student();
		try {
			r = new FileReader("courseList.txt");
			BufferedReader br = new BufferedReader(r);
			String x = br.readLine();
			Pattern p = Pattern.compile("\\w+ \\d{4}	([\\w\\s]+-\\d+-\\d+).*");
			Matcher m;
			while(x != null){
				m = p.matcher(x);
				if(m.matches()){
					String fullCourse = m.group(1);
					String[] splitCourse = fullCourse.split("-");
					String subject = splitCourse[0].trim();
					String number = splitCourse[1];
					s.addCourse(subject, number);
					
				}
				
				x = br.readLine();
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(s.getStudentCourses());
		
	}
	
	public static void main(String[] args){
		ClassBuilder cb = new ClassBuilder();
		cb.parser();
	}
}
