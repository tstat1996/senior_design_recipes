package graphbuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassBuilder {

    public void parser(String courseHistory) {
        Reader r;
        Student s = new Student();

        Pattern p = Pattern.compile("\\w+ \\d{4}	([\\w\\s]+-\\d+-\\d+).*");
        Matcher m;
        String[] split = courseHistory.split("\n");
        Set<String> classes = new HashSet<String>();
        //System.out.println(Arrays.toString(split));
        for (int i = 0; i < split.length; i++) {
            m = p.matcher(split[i]);
            if (m.matches()) {
                String fullCourse = m.group(1);
                String[] splitCourse = fullCourse.split("-");
                String subject = splitCourse[0].trim();
                String number = splitCourse[1];
                classes.add(subject + "-" + number);
                s.addCourse(subject, number);
            }
        }
        System.out.println(classes);
        System.out.println(s.getStudentCourses());

    }

    public static void main(String[] args) {
        ClassBuilder cb = new ClassBuilder();
        try {
            Reader r = new FileReader("courseHistory.txt");
            BufferedReader br = new BufferedReader(r);
            String x = br.readLine();
            String courses = "";
            while (x != null) {
                courses = courses + "\n" + x;
                x = br.readLine();
            }
            //System.out.println(courses);
            cb.parser(courses);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
