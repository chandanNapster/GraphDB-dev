import org.neo4j.driver.v1.*;

import java.util.ArrayList;
import java.util.List;

import static org.neo4j.driver.v1.Values.parameters;


public class SampleQueries {

    Driver driver;
    private List<String> NodeList;


    public void addNode(String name)
    {
        // Sessions are lightweight and disposable connection wrappers.
        try (Session session = driver.session())
        {
            // Wrapping Cypher in an explicit transaction provides atomicity
            // and makes handling errors much easier.
            try (Transaction tx = session.beginTransaction())
            {
                tx.run("MERGE (a:Person {name: {x}})", parameters("x", name));
                tx.success();  // Mark this write as successful.
            }
        }
    }

    public void getNode(){
        try(Session session = driver.session()){
            //try(Transaction tx = session.beginTransaction()){
                StatementResult result = session.run("MATCH (a:Person) " +
                        "RETURN a.name AS name");

                while(result.hasNext()){
                    Record record = result.next();
                    NodeList.add(record.get("name").asString());
                }
           // }


        }
        for (String nodes: NodeList) {
            System.out.println(nodes);
        }
    }

    public void addEdge(String node1, String node2){

        String name1 = null;
        String name2 = null;

        for (String node: NodeList) {
            if(node.equalsIgnoreCase(node1)){
                name1 = node;
            }
            else if(node.equalsIgnoreCase(node2)){
                name2 = node;
            }
        }
        try (Session session = driver.session())
        {
            // Wrapping Cypher in an explicit transaction provides atomicity
            // and makes handling errors much easier.
            try (Transaction tx = session.beginTransaction())
            {
                tx.run("MATCH (a:Person {name: $person_1})" +
                        "MATCH (b:Person{name:$person_2})" +
                        "MERGE (a)-[:KNOWS]->(b)",
                        parameters("person_1",name1,
                                                "person_2",name2));
                tx.success();  // Mark this write as successful.
            }
        }


    }

    public void printPeople(String initial)
    {
        try (Session session = driver.session())
        {
            // Auto-commit transactions are a quick and easy way to wrap a read.
            StatementResult result = session.run(
                    "MATCH (a:Person) WHERE a.name STARTS WITH {x} RETURN a.name AS name",
                    parameters("x", initial));
            // Each Cypher execution returns a stream of records.
            while (result.hasNext())
            {
                Record record = result.next();
                // Values can be extracted from a record by index or name.
                System.out.println(record.get("name").asString());
            }
        }
    }

    public void deleteRecords(String name){
        String result;
        try(Session session = driver.session()){
            session.run("MATCH (x) WHERE x.name = {y} DETACH DELETE x", parameters("y",name));
            result = "Deleted";
        }
        System.out.println(result);
   }

}
