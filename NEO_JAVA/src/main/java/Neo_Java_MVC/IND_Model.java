package Neo_Java_MVC;
import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.exceptions.DatabaseException;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;

import static org.neo4j.driver.v1.Values.parameters;

public class IND_Model implements AutoCloseable{

    private Driver driver;
    private ArrayList<Character> nodeEdgeVar;
    private String pString;
    private ArrayList<String> nodeData;


    public IND_Model(String uri, String user, String password){



        try {
            driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));

        }
        catch (DatabaseException e){
            System.out.println("Database Exception1" + e.getMessage());
        }

//        getNodeEdgeVar();
    }

    private void searchPattern(String pString){
        nodeData = new ArrayList();
        getNodeEdgeVar();
        try(Session session = driver.session(AccessMode.READ)) {
            StatementResult result = session.run("MATCH " + pString +
                                                    " RETURN DISTINCT " + nodeEdgeVar.get(0)+ ".name AS name" );
            while(result.hasNext()){
                Record record = result.next();
                nodeData.add(record.get("name").asString());
            }
        }

        catch(Exception e){
            System.out.println(e.getMessage());
        }

        for(String noded : nodeData){
            System.out.println(noded);
        }


    }

    public void searchPat(String pString){
        this.pString = pString;
      searchPattern(pString);
    }

//    public String getString(String patternString){
//        pString = patternString;
//        return patternString;
//    }

    private ArrayList<Character> getNodeEdgeVar(){

        //char[] ch =
        char[] ch = pString.toCharArray();
        nodeEdgeVar = new ArrayList();

        for(int i =0; i < ch.length; i++){
            if(ch[i] == '(' || ch[i] == ')' ||
                                ch[i] == '-' ||
                                ch[i] == '>' ||
                                ch[i] == '<' ||
                                ch[i] == '[' ||
                                ch[i] == ']'){
                continue;
            }
            else{
                nodeEdgeVar.add(ch[i]);
            }
        }

        for(Character ch1 : nodeEdgeVar){
            System.out.println(ch1);
        }

        return nodeEdgeVar;
    }


    @Override
    public void close() throws Exception {
        //driver.close();
    }
}
