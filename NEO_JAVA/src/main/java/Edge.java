import java.awt.*;

public class Edge {

    private int value;
    private int getX1, getX2, getY1, getY2;

    public Edge(int value){
        this.value = value;
    }

    public Edge(int getX1, int getX2, int getY1, int getY2){
        this.getX1 = getX1;
        this.getX2 = getX2;
        this.getY1 = getY1;
        this.getY2 = getY2;
    }

    public String toString(){
        return "Weight" + "->" + value;
    }



    public void draw(Graphics g){
        g.setColor(Color.RED);
        g.fillOval(getX1, getY1, 10, 10);
        //g.fillArc(10,10,10,10,10,10);
        //g.drawLine(getX1, getY1, getX2, getY2);
    }

}
