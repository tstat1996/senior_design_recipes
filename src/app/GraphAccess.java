package app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.neo4j.driver.v1.*;

import java.util.List;

public class GraphAccess implements AutoCloseable
{
    private final Driver driver;

    public GraphAccess( String uri, String user, String password )
    {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
    }

    @Override
    public void close() throws Exception
    {
        driver.close();
    }

    public String buildMatch(String[] courses, int diff, int cQual, int pQUal) {
        StringBuilder sb = new StringBuilder();
        sb.append("MATCH (n:Course)-[w:Edge]->(p) WHERE (");
        for (String s : courses) {
            sb.append("n.aliases CONTAINS '" + s + "' OR ");
        }
        sb.delete(sb.length() - 4, sb.length());
        sb.append(") AND (toFloat(p.professorQuality) >= " + (pQUal - 1) + " AND toFloat(p.professorQuality) <= " + pQUal + ")");
        sb.append(" AND (toFloat(p.courseQuality) >= " + (cQual - 1) + " AND toFloat(p.courseQuality) <= " + cQual + ")");
        sb.append(" AND (toFloat(p.difficulty) >= " + (diff - 1) + " AND toFloat(p.difficulty) <= " + diff + ")");
        sb.append(" RETURN PROPERTIES(p), w.weight AS weight");
        return sb.toString();
    }

    public String access(String courses, String diff, String courseQual, String profQual) {
        try(Session session = driver.session()) {
            String[] coursesLiked = courses.split(" ");
            Integer difficulty = Integer.parseInt(diff);
            Integer courseQuality = Integer.parseInt(courseQual);
            Integer profQuality = Integer.parseInt(profQual);

            String statement = buildMatch(coursesLiked, difficulty, courseQuality, profQuality);
            String response = session.writeTransaction( new TransactionWork<String>() {
                @Override
                public String execute( Transaction tx )
                {
                    StatementResult result = tx.run( statement);
                    List<Record> records = result.list();
                    JSONArray array = new JSONArray();
                    for (Record r : records) {
                        JSONObject ob = new JSONObject();
                        Value v = r.get(0);
                        ob.put("difficulty", v.get("difficulty"));
                        ob.put("professorQuality", v.get("professorQuality"));
                        ob.put("courseQuality", v.get("courseQuality"));
                        ob.put("description", v.get("description"));
                        ob.put("name", v.get("name"));
                        ob.put("aliases", v.get("aliases"));
                        array.add(ob);
                    }
                    return array.toString();
                }
            } );
            return response;
        }
    }
}