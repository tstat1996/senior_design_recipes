package graphbuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class APIData {

	private String token;
	private String baseUrl;
	private Set<Vertex> allCourses;

	public APIData() {
		token = "?token=auUBPUTtr7CGev0K63JjmHQAAGplzx";
		baseUrl = "https://api.penncoursereview.com";
		allCourses = new HashSet<Vertex>();
	}

	public Set<String> getSemesters() {
		Set<String> semesters = new HashSet<String>();
		String[] trimesters = { "a", "b", "c" };
		String[] years = { "2015", "2016", "2017", "2018" };
		for (String yr : years) {
			if (yr.equals("2018")) {
				semesters.add(yr + "a");
				semesters.add(yr + "b");
			} else {
				for (String tri : trimesters) {
					semesters.add(yr + tri);
				}
			}

		}
		System.out.println(semesters);

		return semesters;
	}

	public Set<String> getDepts() {
		Set<String> semesters = getSemesters();
		Set<String> depts = new HashSet<String>();
		URL url;
		for (String semester : semesters) {
			try {

				url = new URL(baseUrl + "/v1/semesters/" + semester + "/" + token);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				con.setRequestProperty("Accept", "application/json");
				BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
				// br.
				String output;
				StringBuilder sb = new StringBuilder();
				while ((output = br.readLine()) != null) {
					// System.out.println(output);
					sb.append(output);
				}
				JSONParser parser = new JSONParser();
				// System.out.println(sb.toString());
				JSONObject json = (JSONObject) parser.parse(sb.toString());
				//
				JSONObject result = (JSONObject) json.get("result");
				JSONArray values = (JSONArray) result.get("depts");
				if (values != null) {
					Iterator<JSONObject> iterator = values.iterator();
					while (iterator.hasNext()) {
						JSONObject sem = iterator.next();
						String semPath = (String) sem.get("path");
						depts.add(semPath);
					}
				}

				con.disconnect();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
			}
			// } catch (ParseException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return depts;
	}

	public String getCourseDescription(String id) {
		URL url;
		try {
			url = new URL(baseUrl + "/v1/courses/" + id + "/" + token);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Accept", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
			// br.
			String output;
			StringBuilder sb = new StringBuilder();
			while ((output = br.readLine()) != null) {
				// addCourse(output);
				// System.out.println(output);
				sb.append(output);
			}
			JSONParser parser = new JSONParser();
			// System.out.println(sb.toString());
			JSONObject json = (JSONObject) parser.parse(sb.toString());
			JSONObject result = (JSONObject) json.get("result");
			String description = (String) result.get("description");

			con.disconnect();
			return description;
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public void addCourse(JSONObject course) {

		String path = (String) course.get("path");
		JSONArray aliases = (JSONArray) course.get("aliases");
		String number = (String) course.get("primary_alias");
		String name = (String) course.get("name");

		Long id = (Long) course.get("id");

		Vertex vertex = new Vertex(name, id.toString(), path);
		vertex.setNumber(number);
		if (!allCourses.contains(vertex)) {
			Iterator<String> iterator = aliases.iterator();
			while (iterator.hasNext()) {
				String alias = iterator.next();
				vertex.addAlias(alias);
			}
			// System.out.println(vertex.getAliases());
			String description = getCourseDescription(id.toString());
			
			vertex.setDescription(description);
			if (!allCourses.contains(vertex)) {
				System.out.println(vertex.toString());
				allCourses.add(vertex);
			}
		}

		/// courses/24160

	}

	public Set<String> getCoursesForDept() {
		Set<String> depts = getDepts();
		URL url2;
		for (String dept : depts) {
			/// semesters/:semester/:dept
			try {
				url2 = new URL(baseUrl + dept + "/" + token);
				HttpURLConnection con = (HttpURLConnection) url2.openConnection();
				con.setRequestMethod("GET");
				con.setRequestProperty("Accept", "application/json");
				BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
				// br.
				String output;
				StringBuilder sb = new StringBuilder();
				while ((output = br.readLine()) != null) {
					// addCourse(output);
					// System.out.println(output);
					sb.append(output);
				}
				JSONParser parser = new JSONParser();
				// System.out.println(sb.toString());
				JSONObject json = (JSONObject) parser.parse(sb.toString());
				JSONObject result = (JSONObject) json.get("result");
				JSONArray courses = (JSONArray) result.get("courses");
				if (courses != null) {
					Iterator<JSONObject> iterator = courses.iterator();
					while (iterator.hasNext()) {
						JSONObject course = iterator.next();
						// System.out.println(course);
						addCourse(course);
						// depts.add(semPath);
					}
				}

				con.disconnect();
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return null;
	}

}
