package graphbuilder;

import javafx.beans.binding.StringBinding;

import java.util.*;

public class Vertex implements Comparator<Vertex>, Comparable {

	private String name;
	private String number;
	private String id;
	private String path;
	private String description;
	private List<String> aliases;
	private double courseQuality, professorQuality, difficulty;
	Map<String, Integer> termFrequency;
	Map<Vertex, Double> edgeList;

	public Vertex(String name, String id, String path){
		this.name = name;
		this.id = id;
		this.number = null;
		termFrequency = new HashMap<String, Integer>();
		this.description = null;
		this.path = path;
		aliases = new ArrayList<String>();
		edgeList = new HashMap<Vertex, Double>();
		courseQuality = 0.0;
		professorQuality = 0.0;
		difficulty = 0.0;
	}

	public String getId() {
		return this.id;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public void addAlias(String course){
		aliases.add(course);
	}

	public void setNumber(String number){

	}

	public boolean firstAlias(){
		return number == null;
	}

	public boolean equals(Vertex vertex){
		return this.name.equals(vertex.name);
	}

	public List<String> getAliases(){
		return aliases;
	}

	public String toString(){
		return name;
	}

	public void computeTf(){


		String[] words = this.description.split(" ");
		for(String word : words) {
			String removePunctuation = word.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
			if(removePunctuation.equals("")){
				continue;
			}
			if (termFrequency.containsKey(removePunctuation)) {
				int initialCount = termFrequency.get(removePunctuation);
				termFrequency.put(removePunctuation, initialCount + 1);
			} else {
				termFrequency.put(removePunctuation, 1);
			}
		}
	}

	public double getTermFrequency(String word){
		if(termFrequency.containsKey(word)){
			return termFrequency.get(word);
		}
		return 0;
	}

	public Set<String> getTermList(){
		computeTf();
		return termFrequency.keySet();
	}

	public void addEdge(Vertex v, double cs){
		edgeList.put(v, cs);
	}

	public Map<Vertex, Double> getEdgeList(){
		return edgeList;
	}

	public boolean descriptionEquals(Vertex v){
		return this.description.equals(v.description);
	}



	public String toGraphString() {
		return "CREATE (:Course{" +
				"name:\"" + this.name.replaceAll("\"", "\'") + "\", " +
				"number:\"" + this.number + "\", " +
				"id:\"" + this.id + "\", " +
				"path:\"" + this.path + "\", " +
				"description:\"" + this.description.trim().replaceAll("\"", "\'") + "\", " +
				"courseQuality:\"" + this.courseQuality + "\", " +
				"professorQuality:\"" + this.professorQuality + "\", " +
				"difficulty:\"" + this.difficulty + "\"})";
	}

	public String toEdgeString() {
		StringBuilder sb = new StringBuilder();
		for(Vertex v: edgeList.keySet()) {
			sb.append("Name:" + v.name + " id: " + v.getId() + " weight: " + edgeList.get(v) + "\n");
		}
		return sb.toString();
	}

	@Override
	public int compareTo(Object o) {
		//System.out.println(o);
		Vertex v = (Vertex) o;

		return this.name.compareTo(v.name);
	}

	@Override
	public int compare(Vertex o1, Vertex o2) {
		return Integer.parseInt(o1.getId()) - Integer.parseInt(o2.getId());
	}
}

