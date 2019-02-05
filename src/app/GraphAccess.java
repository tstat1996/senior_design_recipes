package app;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;

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

    public void printGreeting( final String message )
    {
        try ( Session session = driver.session() )
        {
            String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    StatementResult result = tx.run( "CREATE (a:Greeting) " +
                                    "SET a.message = $message " +
                                    "RETURN a.message + ', from node ' + id(a)",
                            parameters( "message", message ) );
                    return result.single().get( 0 ).asString();
                }
            } );
            System.out.println( greeting );
        }
    }

    public void access(List<String> aliases) {
        try(Session session = driver.session()) {
            String reponse = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    StatementResult result = tx.run( "MATCH (n:Course)-[w:Edge]->(p) WHERE n.aliases CONTAINS 'CIS-120'  RETURN PROPERTIES(p), w.weight AS weight");
                    System.out.print(result.list().get(0).get(0));
                    return result.list().get(0).get(0).toString();
                }
            } );

            JSONArray array = new JSONArray();
            JSONParser parser = new JSONParser();
            try {
                System.out.print("HERE");
                JSONObject ob = (JSONObject) parser.parse(reponse);
                System.out.println(ob.toJSONString());
            } catch (Exception e) {
                System.out.println(e.getStackTrace()[0].toString());

            }
            //System.out.print(reponse);
        }
    }

    public static void main( String... args ) throws Exception
    {
        try ( GraphAccess greeter = new GraphAccess( "bolt://localhost:7687", "sam", "sam" ) )
        {
            greeter.access( null );
        }
    }
}