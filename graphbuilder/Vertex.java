package graphbuilder;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
	
	private String name;
	private String number;
	private String id;
	private String path;
	private String description;
	private List<String> aliases;
	
	public Vertex(String name, String id, String path){
		this.name = name;
		this.id = id;
		this.number = null;
		
		this.description = null;
		this.path = path;
		aliases = new ArrayList<String>();
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
}
