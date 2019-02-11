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

	public APIData(Set<Vertex> v) {
		token = "?token=auUBPUTtr7CGev0K63JjmHQAAGplzx";
		baseUrl = "https://api.penncoursereview.com";
		allCourses = v;
	}

	public double[] getRatings(String id) {
		URL url;
		try {
			url = new URL(baseUrl + "/courses/" + id + "/reviews/" + token);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Accept", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
			String output;
			StringBuilder sb = new StringBuilder();
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(sb.toString());
			JSONObject result = (JSONObject) json.get("result");
			JSONArray values = (JSONArray) result.get("values");

			JSONParser parser1 = new JSONParser();
			JSONObject json1 = (JSONObject) parser1.parse(values.get(0).toString());
			Long num_rev = (Long) json1.get("num_reviewers");
			JSONObject ratings = (JSONObject) json1.get("ratings");
			String al = (String) ratings.get("rAmountLearned");
			String cq = (String) ratings.get("rCourseQuality");
			String dif = (String) ratings.get("rDifficulty");
			String iq = (String) ratings.get("rInstructorQuality");
			String maj = (String) ratings.get("rRecommendMajor");
			String nonMaj = (String) ratings.get("rRecommendNonMajor");
			String wr = (String) ratings.get("rWorkRequired");

			double[] arr = new double[8];
			arr[0] = num_rev == null ? -1.0 : Double.parseDouble(num_rev.toString());
			arr[1] = al == null ? -1.0 : Double.parseDouble(al);
			arr[2] = cq == null ? -1.0 : Double.parseDouble(cq);
			arr[3] = dif == null ? -1.0 : Double.parseDouble(dif);
			arr[4] = iq == null ? -1.0 : Double.parseDouble(iq);
			arr[5] = maj == null ? -1.0 : Double.parseDouble(maj);
			arr[6] = nonMaj == null ? -1.0 : Double.parseDouble(nonMaj);
			arr[7] = wr == null ? -1.0 : Double.parseDouble(wr);
			con.disconnect();
			return arr;

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
//		}
		return null;
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
		System.out.println("getting departments");
		Set<String> semesters = getSemesters();
		Set<String> depts = new HashSet<String>();
		URL url;
		for (String semester : semesters) {
			try {

				url = new URL(baseUrl + "/semesters/" + semester + "/" + token);

				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				con.setRequestProperty("Accept", "application/json");
				BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
				String output;
				StringBuilder sb = new StringBuilder();
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(sb.toString());

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
				continue;

			}
			catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		//depts.add("/semesters/2017A/CIS");
		//System.out.println(depts);
		return depts;
	}

	public String getCourseDescription(String id) {
		URL url;
		try {
			url = new URL(baseUrl + "/courses/" + id + "/" + token);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Accept", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
			String output;
			StringBuilder sb = new StringBuilder();
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			JSONParser parser = new JSONParser();
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
			String description = getCourseDescription(id.toString());
			double[] ratings = getRatings(id.toString());
			vertex.setNumReviewers((int) ratings[0]);
			vertex.setAmountLearned(ratings[1]);
			vertex.setCourseQuality(ratings[2]);
			vertex.setDifficulty(ratings[3]);
			vertex.setProfessorQuality(ratings[4]);
			vertex.setReccomendMajor(ratings[5]);
			vertex.setReccomendNonMajor(ratings[6]);
			vertex.setWorkRequired(ratings[7]);

			vertex.setDescription(description);
			if (!allCourses.contains(vertex)) {
				//System.out.println(vertex.toString());
				allCourses.add(vertex);
			}
		}

	}

	public Set<String> getCoursesForDept() {
		Set<String> depts = getDepts();
		System.out.println("got departments");
		URL url2;
		for (String dept : depts) {
			try {
				url2 = new URL(baseUrl + dept + "/" + token);
				HttpURLConnection con = (HttpURLConnection) url2.openConnection();
				con.setRequestMethod("GET");
				con.setRequestProperty("Accept", "application/json");
				BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
				String output;
				StringBuilder sb = new StringBuilder();
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(sb.toString());
				JSONObject result = (JSONObject) json.get("result");
				JSONArray courses = (JSONArray) result.get("courses");
				if (courses != null) {
					Iterator<JSONObject> iterator = courses.iterator();
					while (iterator.hasNext()) {
						JSONObject course = iterator.next();
						addCourse(course);
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

	public Set<Vertex> getAllCourses(){
		return allCourses;
	}

}
