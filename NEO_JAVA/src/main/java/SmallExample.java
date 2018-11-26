import org.neo4j.driver.v1.*;

import javax.naming.ServiceUnavailableException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.neo4j.driver.v1.Values.parameters;

public class SmallExample
{
    // Driver objects are thread-safe and are typically made available application-wide.
    private Driver driver1;
    private List<String> NodeList;

    public SmallExample(String uri, String user, String password)
    {
        driver1 = GraphDatabase.driver(uri, AuthTokens.basic(user, password),
                Config.build().withConnectionTimeout(15,TimeUnit.SECONDS).toConfig());
        NodeList = new ArrayList<>();
    }


    private StatementResult addNode(final Transaction tx,
                                     final String nodeLabel,
                                     final String name,
                                     final String nodeProperty){
    String NodeValue = nodeLabel;
    return (tx.run("MERGE (a:NodeValue{name:$name, Property:$Property})",
            parameters("NodeLabel", nodeLabel,
                    "name", name,
                    "Property", nodeProperty)));
}

    private StatementResult addEdge(final Transaction tx,
                                    final String node1,
                                    final String node2,
                                    final String edgeLabel,
                                    final String edgeProperty,
                                    final String edgeName){
        return (tx.run("MATCH (a{name : $node1})"+
                        "MATCH (b{name: $node2})"+
                        "MERGE (a)-[:edgeLabel{name:$edgeName, " +
                                "Property:$edgeProperty}]" +
                                "->(b)", parameters("node1", node1,
                                    "node2", node2,
                                    "edgeLabel", edgeLabel,
                                    "edgeProperty", edgeProperty,
                                    "edgeName", edgeName)));
    }

    public void makeGraph(){
        try(Session session = driver1.session(AccessMode.WRITE)) {
            session.writeTransaction(tx -> addNode(tx,
                    "PERSON",
                    "Alicson",
                    "Software Developer"));
            session.writeTransaction(tx -> addNode(tx,
                    "PERSON",
                    "Bobbby",
                    "Software Tester"));
            session.writeTransaction(tx -> addEdge(tx,
                    "Alicson",
                    "Bobbby",
                    "KNOWS",
                    "since:2003",
                    "knows"));
        }

    }


    public void close()
    {
        // Closing a driver immediately shuts down all open connections.
        driver1.close();
    }


}