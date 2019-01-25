package graphbuilder;

import java.io.*;
import java.util.HashSet;

public class FilterNodes {

        public static void main(String[] args) {
            File csvFile = new File("./courses2.txt");
            BufferedReader br = null;
            Writer writer = null;
            String line = "";
            String cvsSplitBy = "\t\t";

            try {
                writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream("uniqueNodes.txt"), "utf-8"));
                br = new BufferedReader(new FileReader(csvFile));
                HashSet<String> ids = new HashSet<>();
                HashSet<String> names = new HashSet<>();
                HashSet<String> aliases = new HashSet<>();

                while ((line = br.readLine()) != null) {
                    String[] courseInfo = line.split(cvsSplitBy);
                    ids.add(courseInfo[2]);
                    aliases.add(courseInfo[5]);
                    names.add(courseInfo[0]);

//                    if (!ids.contains(courseInfo[2]) && !courseInfo[2].equals("id")) {
//                        StringBuilder sb = new StringBuilder();
//                        sb.append("CREATE (:Course{name:\"");
//                        sb.append(courseInfo[0].replaceAll("\"", "\'"));
//                        sb.append("\", ");
//                        sb.append("number:\"");
//                        sb.append(courseInfo[1]);
//                        sb.append("\", ");
//                        sb.append("id:\"");
//                        sb.append(courseInfo[2]);
//                        ids.add(courseInfo[2]);
//                        sb.append("\", ");
//                        sb.append("path:\"");
//                        sb.append(courseInfo[3]);
//                        sb.append("\", ");
//                        sb.append("description:\"");
//                        sb.append(courseInfo[4]);
//                        sb.append("\", ");
//                        sb.append("aliases:\"");
//                        sb.append(courseInfo[5].substring(1, courseInfo[5].length()-1));
//                        sb.append("\", ");
//                        sb.append("courseQuality:\"");
//                        sb.append(courseInfo[6]);
//                        sb.append("\", ");
//                        sb.append("professorQuality:\"");
//                        sb.append(courseInfo[7]);
//                        sb.append("\", ");
//                        sb.append("difficulty:\"");
//                        sb.append(courseInfo[8]);
//                        sb.append("\"})");
//                        writer.write(sb.toString() + '\n');
//                        ids.add(courseInfo[2]);
//                        aliases.add(courseInfo[5]);
//                        names.add(courseInfo[0]);
//                    }

                }

                System.out.println(ids.size());
                System.out.println(names.size());
                System.out.println(aliases.size());
                System.out.println(ids);
                System.out.println(names);
                System.out.println(aliases);

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
