package graphbuilder;


import java.util.*;

public class Vertex {

	private String name;
	private String number;
	private String id;
	private String path;
	private String description;
	private List<String> aliases;
	private int numReviewers;
	private double courseQuality, professorQuality, difficulty, amountLearned, workRequired, reccomendMaj, recomendNonMaj;
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
		amountLearned = 0.0;
		workRequired = 0.0;
		reccomendMaj = 0.0;
		recomendNonMaj = 0.0;
		numReviewers = 0;
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

	public String getName(){
		return this.name;
	}

	public String getNumber(){
		return this.number;
	}

	public String getPath(){
		return this.path;
	}

	public String getDescription(){
		return this.description;
	}

	public double getCourseQuality(){
		return this.courseQuality;
	}

	public void setCourseQuality(double qual){
		this.courseQuality = qual;
	}

	public double getProfessorQuality(){
		return this.professorQuality;
	}

	public void setProfessorQuality(double qual){
		this.professorQuality = qual;
	}

	public double getDifficulty(){
		return this.difficulty;
	}

	public void setDifficulty(double diff){
		this.difficulty = diff;
	}

	public double getAmountLearned() { return amountLearned; }

	public void setAmountLearned(double d) { this.amountLearned = d; }

	public double getWorkRequired() { return workRequired; }

	public void setWorkRequired(double d) { this.workRequired = d; }

	public double getReccomendMaj() { return reccomendMaj; }

	public void setReccomendMajor(double d) { this.reccomendMaj = d; }

	public double getRecomendNonMaj() { return recomendNonMaj; }

	public void setReccomendNonMajor(double d) { this.recomendNonMaj = d; }

	public int getNumReviewers() { return numReviewers; }

	public void setNumReviewers(int numReviewers) { this.numReviewers = numReviewers; }

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


	public String toEdgeString() {
		StringBuilder sb = new StringBuilder();
		for(Vertex v: edgeList.keySet()) {
			sb.append("Name:" + v.name + " id: " + v.getId() + " weight: " + edgeList.get(v) + "\n");
		}
		return sb.toString();
	}

//	@Override
//	public int compareTo(Object o) {
//		//System.out.println(o);
//		Vertex v = (Vertex) o;
//
//		return this.name.compareTo(v.name);
//	}
//
//	@Override
//	public int compare(Vertex o1, Vertex o2) {
//		return Integer.parseInt(o1.getId()) - Integer.parseInt(o2.getId());
//	}
}

