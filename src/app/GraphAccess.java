package app;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.neo4j.driver.v1.*;

import java.util.List;

import static org.neo4j.driver.v1.Values.parameters;

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

    public String access(List<String> aliases) {
        try(Session session = driver.session()) {
            String response = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    StatementResult result = tx.run( "MATCH (n:Course)-[w:Edge]->(p) WHERE n.aliases CONTAINS 'CIS-120'  RETURN PROPERTIES(p), w.weight AS weight");
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