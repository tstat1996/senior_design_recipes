package graphbuilder;

import java.util.*;

public class Graph {

    private Set<Vertex> allCourses;
    private Map<String, Set<Vertex>> invertedIndex;


    public Graph(){
        APIData x = new APIData();
        x.getCoursesForDept();
        allCourses = x.getAllCourses();
        invertedIndex = new HashMap<String, Set<Vertex>>();

        createInvertedIndex();
    }

    private void createInvertedIndex() {

        for (Vertex course : allCourses) {
            Set<String> terms = course.getTermList();

            for (String term : terms) {
                if (invertedIndex.containsKey(term)) {
                    Set<Vertex> list = invertedIndex.get(term);
                    list.add(course);
                } else {
                    Set<Vertex> list = new TreeSet<Vertex>();
                    list.add(course);
                    invertedIndex.put(term, list);
                }
            }
        }


    }

    public double getInverseDocumentFrequency(String term) {
        if (invertedIndex.containsKey(term)) {
            double size = allCourses.size();
            Set<Vertex> list = invertedIndex.get(term);
            double documentFrequency = list.size();
            return Math.log10(size / documentFrequency);
        } else {
            return 0;
        }
    }

    public Set<Vertex> getDocuments() {
        return allCourses;
    }

    /**
     * @return the invertedIndex
     */
    public Map<String, Set<Vertex>> getInvertedIndex() {
        return invertedIndex;
    }

    public void addEdges(){
        CosineSimilarity cs = new CosineSimilarity(this);
        List<Vertex> arrayCourses = new ArrayList(allCourses);
        for(int i = 0; i < arrayCourses.size()-1; i++){
            for(int j = i+1; i < arrayCourses.size(); j++){
                Vertex v1 = arrayCourses.get(i);
                Vertex v2 = arrayCourses.get(j);
                if(!v1.descriptionEquals(v2)){
                    double similarity = cs.cosineSimilarity(v1, v2);
                    if(similarity >= .2){
                        v1.addEdge(v2, similarity);
                        v2.addEdge(v1, similarity);
                        System.out.println(v1.toString());
                    }
                }
            }
        }
    }



}
