import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.exceptions.DatabaseException;

import java.util.ArrayList;

import static org.neo4j.driver.v1.Values.parameters;

public class TestDBClientApplication implements AutoCloseable {

    private Driver driver1;
    private ArrayList<Node> nodeList;
    private ArrayList<Edge> edgeList;

    public TestDBClientApplication(String uri, String user, String password1){
        try {
            driver1 = GraphDatabase.driver(uri, AuthTokens.basic(user, password1));

        }
        catch (DatabaseException e){
            System.out.println("Database Exception1" + e.getMessage());
        }
    }

    private StatementResult addNode(final Transaction tx,
                                    final String nodeLabel,
                                    final String name,
                                    final String nodeProperty){
        String NodeValue = nodeLabel;
        return (tx.run("CREATE (a:NodeValue{name:$name, Property:$Property})",
                parameters("NodeLabel", nodeLabel,
                        "name", name,
                        "Property", nodeProperty)));
    }




    private StatementResult delete(final Transaction tx){

        return tx.run("MATCH (x) DETACH DELETE x");
    }

    public void makeNode(){

        String nodeLabel = "PERSON";
        String name = "Alicson";
        try(Session session = driver1.session(AccessMode.WRITE)) {
            session.writeTransaction(tx -> addNode(tx,
                    nodeLabel,
                    name,
                    "Software Developer"));
            session.writeTransaction(tx -> addNode(tx,
                    "PERSON",
                    "Bobbby",
                    "Software Tester"));
        }
    }

    public void deleteAllNodes(){
        try(Session session = driver1.session(AccessMode.WRITE)){
            session.writeTransaction(tx -> delete(tx));
        }
    }

    public void createDatabase(){
        try(Session session = driver1.session(AccessMode.WRITE)){
            session.writeTransaction(tx -> createDB(tx));
        }
    }


    public ArrayList<Node> returnNodes(){
        try(Session session = driver1.session(AccessMode.READ)){
            StatementResult result = session.run("MATCH (x) " +
                                                "RETURN x.name AS name, " +
                                                        "x.apiClient AS api, " +
                                                        "x.channel AS channel, "+
                                                        "x.implementationType AS implementationType "+
                                                "ORDER BY name, api");

            nodeList = new ArrayList();

            while(result.hasNext()){
                Record record = result.next();
                nodeList.add(new Node(record.get("name").asString(),
                                        record.get("api").asString(),
                                        record.get("channel").asString(),
                                        record.get("implementationType").asString()));
            }

        }
        return nodeList;
    }

    public ArrayList<Edge> returnEdgeWeights(){
        try(Session session = driver1.session(AccessMode.READ)){
            StatementResult result = session.run("MATCH (a)-[r]->(b) " +
                                                                "RETURN DISTINCT r.value AS value");
            edgeList = new ArrayList<>();

            while(result.hasNext()){
                Record record = result.next();
                edgeList.add( new Edge(record.get("value").asInt()));
            }
        }
        return edgeList;
    }


    private StatementResult createDB(Transaction tx){

        return (tx.run("\n" +
                "CREATE  (T1:Hybrid{implementationType:\"Tightly\",name:\"HT:1\", apiClient:\"Java\", channel:\"Modbus\"}),\n" +
                "\t\t(T2:OnDevice{implementationType:\"Tightly\", name:\"OT:1\", apiClient:\"Java\", channel:\"Modbus\"}),\n" +
                "        (L1:Hybrid{implementationType:\"Loosed\", name:\"HL:1\", apiClient:\"Apache Milo\", channel:\"OPC-UA\"}),\n" +
                "        (L2:Hybrid{implementationType:\"Loosed\", name:\"HL:2\", apiClient:\"Apache Paho\", channel:\"MQTT\", broker:\"Eclipse Mosquito\"}),\n" +
                "        (L3:OnDevice{implementationType:\"Loosed\", name:\"OL:1\",apiClient:\"Apache Milo\", channel:\"OPC-UA\"}),\n" +
                "        (L4:OnDevice{implementationType:\"Loosed\", name:\"OL:2\",apiClient:\"Apache Paho\", channel:\"MQTT\", broker:\"Eclipse Mosquito\"})\n" +
                "\n" +
                "CREATE\t(Monitoring:Function{name:\"Monitoring\"}),\n" +
                "        (Control:Function{name:\"Control\"}),\n" +
                "        (Simulation:Function{name :\"Simulation\"}),\n" +
                "        (Energy:Domain{name:\"Energy\"}),\n" +
                "        (FactoryAutomation:Domain{name:\"Factory Automation\"}),\n" +
                "        (BuildingAutomation:Domain{name:\"Building Automation\"}),\n" +
                "        (CapacityToHostAgents:PerformanceEfficiency{name:\"Capacity To Host Agents\"}),\n" +
                "        (TimeBehaviour:PerformanceEfficiency{name:\"Time Behaviour\"}),\n" +
                "        (Reusability:Maintenance{name:\"Reusability\"})\n" +
                "\n" +
                "\n" +
                "\n" +
                "CREATE  (T1)-[v1:WEIGHT_4{value:4}]->(Monitoring),\n" +
                "\t\t(T1)-[v2:WEIGHT_3{value:3}]->(Control),\n" +
                "        (T1)-[v3:WEIGHT_2{value:2}]->(Simulation),\n" +
                "        (T1)-[v4:WEIGHT_3{value:3}]->(Energy),\n" +
                "        (T1)-[v5:WEIGHT_4{value:4}]->(FactoryAutomation),\n" +
                "        (T1)-[v6:WEIGHT_3{value:3}]->(BuildingAutomation),\n" +
                "        (T1)-[v7:WEIGHT_1{value:1}]->(CapacityToHostAgents),\n" +
                "        (T1)-[v8:WEIGHT_4{value:4}]->(TimeBehaviour),\n" +
                "        (T1)-[v9:WEIGHT_3{value:3}]->(Reusability),\n" +
                "       \n" +
                "        (T2)-[v10:WEIGHT_4{value:4}]->(Monitoring),\n" +
                "\t\t(T2)-[v11:WEIGHT_5{value:5}]->(Control),\n" +
                "        (T2)-[v12:WEIGHT_1{value:1}]->(Simulation),\n" +
                "        (T2)-[v13:WEIGHT_3{value:3}]->(Energy),\n" +
                "        (T2)-[v14:WEIGHT_4{value:4}]->(FactoryAutomation),\n" +
                "        (T2)-[v15:WEIGHT_3{value:3}]->(BuildingAutomation),\n" +
                "        (T2)-[v16:WEIGHT_5{value:5}]->(CapacityToHostAgents),\n" +
                "        (T2)-[v17:WEIGHT_5{value:5}]->(TimeBehaviour),\n" +
                "        (T2)-[v18:WEIGHT_2{value:2}]->(Reusability),\n" +
                "        \n" +
                "        (L1)-[v19:WEIGHT_5{value:5}]->(Monitoring),\n" +
                "\t\t(L1)-[v20:WEIGHT_4{value:4}]->(Control),\n" +
                "        (L1)-[v21:WEIGHT_5{value:5}]->(Simulation),\n" +
                "        (L1)-[v22:WEIGHT_4{value:4}]->(Energy),\n" +
                "        (L1)-[v23:WEIGHT_5{value:5}]->(FactoryAutomation),\n" +
                "        (L1)-[v24:WEIGHT_4{value:4}]->(BuildingAutomation),\n" +
                "        (L1)-[v25:WEIGHT_1{value:1}]->(CapacityToHostAgents),\n" +
                "        (L1)-[v26:WEIGHT_4{value:4}]->(TimeBehaviour),\n" +
                "        (L1)-[v27:WEIGHT_5{value:5}]->(Reusability),\n" +
                "        \n" +
                "        (L2)-[v28:WEIGHT_5{value:5}]->(Monitoring),\n" +
                "\t\t(L2)-[v29:WEIGHT_3{value:3}]->(Control),\n" +
                "        (L2)-[v30:WEIGHT_5{value:5}]->(Simulation),\n" +
                "        (L2)-[v31:WEIGHT_3{value:3}]->(Energy),\n" +
                "        (L2)-[v32:WEIGHT_4{value:4}]->(FactoryAutomation),\n" +
                "        (L2)-[v33:WEIGHT_5{value:5}]->(BuildingAutomation),\n" +
                "        (L2)-[v34:WEIGHT_1{value:1}]->(CapacityToHostAgents),\n" +
                "        (L2)-[v35:WEIGHT_3{value:3}]->(TimeBehaviour),\n" +
                "        (L2)-[v36:WEIGHT_5{value:5}]->(Reusability),\n" +
                "        \n" +
                "        (L3)-[v37:WEIGHT_4{value:4}]->(Monitoring),\n" +
                "\t\t(L3)-[v38:WEIGHT_5{value:5}]->(Control),\n" +
                "        (L3)-[v39:WEIGHT_3{value:3}]->(Simulation),\n" +
                "        (L3)-[v40:WEIGHT_3{value:3}]->(Energy),\n" +
                "        (L3)-[v41:WEIGHT_4{value:4}]->(FactoryAutomation),\n" +
                "        (L3)-[v42:WEIGHT_4{value:4}]->(BuildingAutomation),\n" +
                "        (L3)-[v43:WEIGHT_5{value:5}]->(CapacityToHostAgents),\n" +
                "        (L3)-[v44:WEIGHT_4{value:4}]->(TimeBehaviour),\n" +
                "        (L3)-[v45:WEIGHT_5{value:5}]->(Reusability),\n" +
                "        \n" +
                "        (L4)-[v46:WEIGHT_4{value:4}]->(Monitoring),\n" +
                "\t\t(L4)-[v47:WEIGHT_3{value:3}]->(Control),\n" +
                "        (L4)-[v48:WEIGHT_3{value:3}]->(Simulation),\n" +
                "        (L4)-[v49:WEIGHT_2{value:2}]->(Energy),\n" +
                "        (L4)-[v50:WEIGHT_4{value:4}]->(FactoryAutomation),\n" +
                "        (L4)-[v51:WEIGHT_4{value:4}]->(BuildingAutomation),\n" +
                "        (L4)-[v52:WEIGHT_5{value:5}]->(CapacityToHostAgents),\n" +
                "        (L4)-[v53:WEIGHT_4{value:4}]->(TimeBehaviour),\n" +
                "        (L4)-[v54:WEIGHT_5{value:5}]->(Reusability)"));
    }


    public void printResults(){
        try(Session session = driver1.session()){
            StatementResult result = session.run("MATCH (x) RETURN x.name AS name");

            while(result.hasNext()){
                Record record = result.next();
                System.out.println(record.get("name").asString());
            }
        }
    }

    @Override
    public void close() throws Exception {
        driver1.close();

    }
}
