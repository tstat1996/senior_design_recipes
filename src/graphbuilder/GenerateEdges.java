package graphbuilder;

import java.io.*;
import java.util.*;

public class GenerateEdges {
//remove all line breaks and tabs from description when setting
    public Set<Vertex> getAllCourses(){
        Set<Vertex> allCourses = new HashSet<Vertex>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("allCourses.txt"));
            reader.readLine();
            String line = reader.readLine();
            Vertex v = null;
            while(line!= null){
                line.replaceAll("\n", "");

                String[] split = line.split("\t\t");

                if(split.length!=9){
                    line = reader.readLine();
                    continue;
                }
                if(split[1].trim().equals("")){
                    line = reader.readLine();
                    continue;
                }


                v = new Vertex(split[0], split[2], split[3]);
                if(allCourses.contains(v)){
                    continue;
                }
                v.setNumber(split[1]);
                v.setDescription(split[4].trim());
                //set aliases
                //v.setCourseQuality(Double.valueOf(split[6]));
                //v.setProfessorQuality(Double.valueOf(split[7]));
                //v.setDifficulty(Double.valueOf(split[8]));
                allCourses.add(v);
                line = reader.readLine();


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allCourses;
    }

    public void generateCosine(Set<Vertex> allCourses){
        FileWriter writer = null;
        try {
            writer = new FileWriter("allEdges.txt");
            for(Vertex v : allCourses){
                //dont want repeats
                writer.append(v.getName());
                writer.append(":\t");
                Map<Vertex, Double> edgeList = v.getEdgeList();
                for(Vertex edge : edgeList.keySet()){
                    writer.append(edge.getName());
                    writer.append(" ");
                    writer.append(String.valueOf(edgeList.get(edge)));
                    writer.append(",");

                }
                writer.append("\n");


            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args){
        GenerateEdges ge = new GenerateEdges();
        Set<Vertex> allCourses = ge.getAllCourses();
        Set<Vertex> fake = new HashSet<>();
        int count = 1800;
        Iterator<Vertex> iter = allCourses.iterator();
        while (count > 0) {
            fake.add(iter.next());
            count--;
        }
        System.out.println(fake.size());
        System.out.println("got all courses");
        Graph g = new Graph(fake);
        g.addEdges();
        ge.generateCosine(fake);
    }
}
