package graphbuilder;

import java.util.*;

public class Vertex implements Comparable{
	
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

	@Override
	public int compareTo(Object o) {
		//System.out.println(o);
		Vertex v = (Vertex) o;

		return this.name.compareTo(v.name);
	}
}

