import java.awt.*;

public class Node {
    private String name;
    private String api;
    private String channel;
    private String implementationType;
    private int getX, getY;

    public Node(String name, String api, String channel, String implementationType){
        this.name = name;
        if(api == "null"){ this.api = "EMPTY";}
        else{this.api = api;}
        if(channel == "null"){this.channel = "EMPTY";}
        else {this.channel = channel;}
        if(implementationType == "null"){this.implementationType = "EMPTY";}
        else{this.implementationType = implementationType;}
    }



    public Node(int getX, int getY){
        this.getX = getX;
        this.getY = getY;
    }

    public String toString(){
        return name + "|" + api +"|"+ channel +"|" + implementationType + "|" + getX + "|" + getY;
    }


    public void draw(Graphics g){
        g.setColor(Color.ORANGE);
        g.fillOval(getX,getY,20,20);
    }






}
