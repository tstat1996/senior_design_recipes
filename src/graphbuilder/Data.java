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

	public static void makeNodeTextFiles(Graph g) {
		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("allNodes.txt"), "utf-8"));
			HashSet<String> idList = new HashSet<>();
			for(Vertex v: g.getDocuments()) {
				if (!idList.contains(v.getId())) {
					writer.write(v.toGraphString() + '\n');
				}
				idList.add(v.getId());

			}

		} catch (IOException ex) {
			// Report
		} finally {
			try {writer.close();} catch (Exception ex) {/*ignore*/}
		}
	}

	public static void main(String[] args)  {
//		APIData d = new APIData();
//		d.getCoursesForDept();
		Graph g = new Graph();
		makeNodeTextFiles(g);
//		g.addEdges();

	}

}
