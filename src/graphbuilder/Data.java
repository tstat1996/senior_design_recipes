package graphbuilder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;

public class Data {
	public static String token = "?token=auUBPUTtr7CGev0K63JjmHQAAGplzx";
	public static String baseUrl = "http://api.penncoursereview.com";

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


	public static void main(String[] args)  {
//		APIData d = new APIData();
//		d.getCoursesForDept();
		Graph g = new Graph();
		System.out.println("starting edges");
		g.addEdges();
		HashSet<String> idList = new HashSet<>();
		for(Vertex v: g.getDocuments()) {
			if (!idList.contains(v.getId())) {
				System.out.println(v.toEdgeString() + '\n' + "NEW VERTEX:");
			}
			idList.add(v.getId());

		}

	}

}
