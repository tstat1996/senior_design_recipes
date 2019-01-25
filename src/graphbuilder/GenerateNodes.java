package graphbuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class GenerateNodes {

    public static void main(String[] args){
        Graph g = new Graph();
        Set<Vertex> allCourses = g.getDocuments();
        System.out.println("got courses!!");
        String fileHeader = "name\t\tnumber\t\tid\t\tpath\t\tdescription\t\taliases\t\tcourseQuality\t\tprofessorQuality\t\tdifficulty";
        String comma = "\t\t";
        try {
            FileWriter writer = new FileWriter("allCourses.txt");
            writer.append(fileHeader);
            for(Vertex v : allCourses){
                //dont want repeats
                writer.append("\n");
                writer.append(v.getName());
                //if(v.getName())
                writer.append(comma);
                writer.append(v.getNumber());
                writer.append(comma);
                writer.append(v.getId());
                writer.append(comma);
                writer.append(v.getPath());
                writer.append(comma);
                String description = v.getDescription().trim();
                description.replaceAll("\t", " ");
                description.replaceAll("\n", " ");
                writer.append(description);
                writer.append(comma);
                writer.append(v.getAliases().toString());
                writer.append(comma);
                writer.append(String.valueOf(v.getCourseQuality()));
                writer.append(comma);
                writer.append(String.valueOf(v.getProfessorQuality()));
                writer.append(comma);
                writer.append(String.valueOf(v.getDifficulty()));


            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
