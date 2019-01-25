package graphbuilder;


import java.io.*;
import java.util.HashSet;

public class CreateNeo4jEdges {

    public static void main(String[] args) {
        File csvFile = new File("./edges.txt");
        BufferedReader br = null;
        Writer writer = null;
        String line = "";
        HashSet<String> nodes = new HashSet<>();

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("neo4jEdges.csv"), "utf-8"));
            br = new BufferedReader(new FileReader(csvFile));

            String fileHead = "node1,weight,node2";
            writer.append(fileHead);

            while ((line = br.readLine()) != null) {
                String v1 = line.substring(0,line.indexOf(":\t")).trim().replaceAll(",", "").replaceAll("\"", "");
                String[] edges = line.substring(line.indexOf(":\t") + 1).split(",");
                if (edges.length > 0) {
                    for(String s: edges) {
                        StringBuilder sb = new StringBuilder();
                        if (s.lastIndexOf(" ") >= 0) {
                            String v2 = s.substring(0, s.lastIndexOf(" ")).trim().replaceAll(",", "").replaceAll("\"", "");
                            String nodeString1 = v1 + v2;
                            String nodeString2 = v2 + v1;
                            if (!v1.equals(v2) && !nodes.contains(nodeString1) && !nodes.contains(nodeString2)) {
                                String val = s.substring(s.lastIndexOf(" ") + 1).trim();
                                sb.append(v1);
                                sb.append(',');
                                sb.append(val);
                                sb.append(',');
                                sb.append(v2);
                                writer.append("\n");
                                writer.append(sb.toString());
                                nodes.add(nodeString1);
                                nodes.add(nodeString2);

                            }
                        }

                    }
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
