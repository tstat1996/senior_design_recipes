package graphbuilder;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Student {
	private Map<String, List<String>> courses;
	private Set<String> pastCourses;
	private Set<String> likedCourses;

	
	public Student() {
		this.courses = new HashMap<String, List<String>>();
		this.pastCourses = new HashSet<String>();
		this.likedCourses = new HashSet<String>();
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

	public void populatePastCourses(String courseHistory){
		Pattern p = Pattern.compile("\\w+ \\d{4}	([\\w\\s]+-\\d+-\\d+).*");
		Matcher m;
		String[] split = courseHistory.split("\n");

		for (int i = 0; i < split.length; i++) {
			m = p.matcher(split[i]);
			if (m.matches()) {
				String fullCourse = m.group(1);
				String[] splitCourse = fullCourse.split("-");
				String subject = splitCourse[0].trim();
				String number = splitCourse[1];
				pastCourses.add(subject + "-" + number);
			}
		}
	}

	public void populateLikedCourses(String courses){
        String[] split = courses.split(", ");
        for(int i = 0; i < split.length; i++){
            likedCourses.add(split[i]);
        }
	}
	
	public Set<String> getStudentCourses(){
		return pastCourses;
	}

    public Set<String> getLikedCourses(){
        return likedCourses;
    }
	
}
