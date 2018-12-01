package graphbuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Data {
	public static String token = "?token=auUBPUTtr7CGev0K63JjmHQAAGplzx";
	public static String baseUrl = "https://api.penncoursereview.com";

	public String getCourseDescription(String id) throws IOException {
		URL url = new URL(baseUrl +id + token);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Accept", "application/json");
		BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
		String output;
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		con.disconnect();

		return "";
	}

	public static void main(String[] args) throws IOException, ParseException {
		APIData d = new APIData();
		d.getCoursesForDept();
		// String token = "?token=q0qeptSxy3HmiIOHlal3Qrx18I5xKB";
//		String extra = "/v1/depts/CIS";
//		Data d = new Data();
//		URL url = new URL(baseUrl + extra + token);
//		HttpURLConnection con = (HttpURLConnection) url.openConnection();
//		con.setRequestMethod("GET");
//		con.setRequestProperty("Accept", "application/json");
//		BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
//		StringBuilder sb = new StringBuilder();
//		String output;
//		System.out.println("Output from Server .... \n");
//		Pattern p = Pattern.compile(".*aliases.*");
//		Matcher m;
//
//		while ((output = br.readLine()) != null) {
//
//			// System.out.println(output);
//			// m = p.matcher(output.trim());
//			// if(m.matches()){
//			// Pattern getCourseNumber = Pattern.compile("\"(\\w+-\\d+)\".*");
//			// output = br.readLine().trim();
//			// Matcher numberMatcher = getCourseNumber.matcher(output);
//			// //System.out.println(output);
//			//
//			// numberMatcher.matches();
//			// String number = numberMatcher.group(1);
//			// br.readLine();
//			// output = br.readLine().trim();
//			// Pattern getCourseId = Pattern.compile("\"id\": (\\d+).*");
//			// Matcher idMatcher = getCourseId.matcher(output);
//			// System.out.println(output);
//			// idMatcher.matches();
//			// String id = idMatcher.group(1);
//			// output = br.readLine().trim();
//			// Pattern getCourseName = Pattern.compile("\"name\": \"(.+)\",");
//			// Matcher nameMatcher = getCourseName.matcher(output);
//			// nameMatcher.matches();
//			// String name = nameMatcher.group(1);
//			// output = br.readLine().trim();
//			// Pattern getCoursePath = Pattern.compile("\"path\": \"(.+)\"");
//			// Matcher pathMatcher = getCoursePath.matcher(output);
//			// pathMatcher.matches();
//			// String path = pathMatcher.group(1);
//			// //d.getCourseDescription(path);
//			// Vertex v = new Vertex(name, id, number, path);
//			//
//			//
//			// }
//			// p.matcher("as");
//			//System.out.println(output);
//			sb.append(output);
//		}
//		JSONParser parser = new JSONParser();
//		JSONObject json = (JSONObject) parser.parse(sb.toString());
//
//		JSONObject result = (JSONObject)json.get("result");
//		JSONArray coursehistories = (JSONArray) result.get("coursehistories");
//        Iterator<JSONObject> iterator = coursehistories.iterator();
//        while (iterator.hasNext()) {
//        	JSONObject course = iterator.next();
//        	JSONArray aliases = (JSONArray) course.get("aliases");
//        	
//        	System.out.println(aliases);
//        	String name = (String)course.get("name");
//        	System.out.println(name);
//        	Long id = (Long)course.get("id");
//        	System.out.println(id.toString());
//
//        	String path = (String)course.get("path");
//        	System.out.println(path);
//        	Vertex v = new Vertex(name, id.toString(), path);
//        	Iterator<String> iterator2 = aliases.iterator();
//            while (iterator2.hasNext()) {
//            	String aliasName = iterator2.next();
//            	if(!v.firstAlias()){
//            		v.setNumber(aliasName);
//            	}
//            	else{
//            		v.addAlias(aliasName);
//            	}
//            }
//            d.getCourseDescription(path);
//
//        }
//
////		long age = (Long) jsonObject.get("age");
////		System.out.println(age);
////		System.out.println(json);
//		con.disconnect();

	}

}
