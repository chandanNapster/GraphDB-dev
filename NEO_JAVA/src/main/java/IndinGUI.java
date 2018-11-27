import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IndinGUI extends JPanel implements ActionListener, MouseListener ,ItemListener{
    private final int PANEL_WIDTH = 400;
    private final int PANEL_HEIGHT = 800;
    private DrawingCanvas drawingCanvas;
    private JButton addNode,
                    addEdge,
                    deleteDB,
                    printResults,
                    createDatabase,
                    refresh;
                    //startDB,
                    //displayEdgeWeights;
    private JPanel  buttonPanel,
                    listPanel,
                    edgeListPanel,
                    drawingArea;
    private JList list, edgeList;
    private TestDBClientApplication test;
    private ArrayList<String> week;
    private String weeks[];
    private MenuBar menu;
    private boolean drawNodes = false;
    private boolean drawNodeHack = false;
    private boolean drawEdge = false;
    private boolean drawEdgeHack = false;
    private Node n1;
    private ArrayList<Node> ListNode;
    private JRadioButton nodeRadioButton, edgeRadioButton;
    //private Node node;
    private ArrayList<Edge> ListEdge;
    private JTextArea Area;
    private JTextField Match;

    public IndinGUI(){
        super(new BorderLayout());
        ListNode = new ArrayList();
        ListEdge = new ArrayList();
        //allNodes = new ArrayList();
        //ListNode.add()
        test = new TestDBClientApplication("bolt://localhost:7687",
                                        "neo4j",
                                        "chandan");

        drawingArea = new JPanel();
        drawingCanvas = new DrawingCanvas();
        drawingCanvas.addMouseListener(this);
        drawingArea.setLayout(new BorderLayout());
        drawingArea.add(drawingCanvas, BorderLayout.CENTER);
        nodeRadioButton = new JRadioButton("DrawNode");
        edgeRadioButton = new JRadioButton("DrawEdge");
        //nodeRadioButton.addMouseListener(this);
            nodeRadioButton.addItemListener(this);
            edgeRadioButton.addItemListener(this);
        //
        drawingArea.add(nodeRadioButton, BorderLayout.SOUTH);
        drawingArea.add(edgeRadioButton,BorderLayout.NORTH);

        buttonPanel = new JPanel();
        addNode = new JButton("ADD NODE");
        addNode.addActionListener(this);
        addEdge = new JButton("ADD EDGE");
        addEdge.addActionListener(this);

        deleteDB = new JButton("DELETE DB");
        deleteDB.addActionListener(this);
        printResults = new JButton("PrintOnConsole");
        printResults.addActionListener(this);
        createDatabase = new JButton("CREATE DB");
        createDatabase.addActionListener(this);
        refresh = new JButton("REFRESH");
        refresh.addActionListener(this);
        //displayEdgeWeights = new JButton("Edge Weights");
        //displayEdgeWeights.addActionListener(this);

        listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());
        edgeListPanel = new JPanel();
        list = new JList();
        list.setModel(getNodesData());
        Area = new JTextArea("NAME | API_CLIENT | CHANNEL | IMP_TYPE");
        Area.setPreferredSize(new Dimension(300,20));

        Match = new JTextField("ENTER SOMETHING");
        drawingCanvas.add(Match, BorderLayout.SOUTH);


//        for(Node node:getNodesData()){
//
//        }


        edgeList = new JList();
        edgeList.setModel(getEdgeData());


        list.setBackground(Color.PINK);

        listPanel.add(list,BorderLayout.CENTER);
        listPanel.add(Area,BorderLayout.NORTH);
        edgeListPanel.add(edgeList);
        menu = new MenuBar();

        buttonPanel.add(addNode);
        buttonPanel.add(addEdge);
//        buttonPanel.add(closeDBConnection);
        buttonPanel.add(deleteDB);
        buttonPanel.add(printResults);
        buttonPanel.add(createDatabase);
        buttonPanel.add(refresh);
       // buttonPanel.add(nodeRadioButton);
     //   buttonPanel.add(displayEdgeWeights);
        listPanel.setBackground(Color.GRAY);

        add(listPanel, BorderLayout.EAST);
        add(edgeListPanel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.SOUTH);
        add(menu,BorderLayout.NORTH);
        add(drawingArea, BorderLayout.CENTER);
        //startProcess = false;

        System.out.println("The String is: " + Match.getSelectedText());


    }






    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == addNode){
            System.out.println("node button");
            test.makeNode();
        }

        else if(source == addEdge){
            System.out.println("edge button");
        }

//        else if (source == closeDBConnection){
//            try {
//                test.close();
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
//        }
        else if (source == deleteDB){
            test.deleteAllNodes();
        }
        else if (source == printResults){
            test.printResults();
        }
        else if (source == createDatabase){
            test.createDatabase();
        }

        else if (source == refresh){
            //getNodesData();
            //drawingCanvas.repaint();
            System.out.println("Refresh button pressed!!!!!");
            list.setModel(getNodesData());
            edgeList.setModel(getEdgeData());
            //list.clearSelection();

        }


    }


    private DefaultListModel getNodesData() {
        int size = test.returnNodes().size();

        ArrayList<Node> listN = test.returnNodes();

//        for(Node node: listN){
//            System.out.println(node.toString());
//        }

        DefaultListModel<Node> model = new DefaultListModel<>();
        for(int i =0; i < size; i++){
            //listN.add(test.returnNodes());
            model.addElement(test.returnNodes().get(i));
        }

        return model;
    }

    private DefaultListModel getEdgeData() {
        int size = test.returnEdgeWeights().size();

        DefaultListModel<Edge> model = new DefaultListModel<>();
        for(int i = 0; i < size; i++){
            model.addElement(test.returnEdgeWeights().get(i));
        }
        return model;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       if(drawNodes){
           System.out.println("Can draw a node");
           ListNode.add(new Node(e.getX(),e.getY()));

           drawNodeHack = true;
       }
        drawingCanvas.repaint();

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(drawEdge){
            System.out.println("Can draw an Edge");
            ListEdge.add(new Edge(e.getX(), e.getY(), e.getX(), e.getY()));
            drawEdgeHack = true;
        }
        drawingCanvas.repaint();

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object source = e.getSource();
        if(source == nodeRadioButton){
            if(nodeRadioButton.isSelected()) {
                System.out.println(nodeRadioButton.isSelected());
                drawNodes = true;
            }
            else{drawNodes = false;}
        }

        else if (source == edgeRadioButton){
            if(edgeRadioButton.isSelected()) {
                System.out.println(edgeRadioButton.isSelected());
                drawEdge = true;
            }
            else{drawEdge = false;}
        }
    }


    private class DrawingCanvas extends JPanel{
        public DrawingCanvas(){
            setPreferredSize(new Dimension(PANEL_WIDTH/2,PANEL_HEIGHT/2));
            setBackground(Color.lightGray);

        }

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            System.out.println("IN paintComponenet");

            if(drawNodeHack) {
                int size = ListNode.size();
                System.out.println("The size is:" + size );
                for(Node node: ListNode){
                    node.draw(g);
                }
            }
            else if(drawEdgeHack){
                for(Edge edge : ListEdge){
                    edge.draw(g);
                }
            }

        }
    }




    private class MenuBar extends JMenuBar{
        public MenuBar(){
            super();
            JMenu fileDrop = new JMenu("File");
            fileDrop.setMnemonic(KeyEvent.VK_F);
            fileDrop.add(new AbstractAction("Exit") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        test.close();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    System.exit(0);
                }
            });
            add(fileDrop);
        }
    }

    public static void main(String[] args) throws Exception {

        IndinGUI iGUI = new IndinGUI();
        JFrame frame = new JFrame("INDIN GUI");
        frame.setDefaultCloseOperation(0);
        frame.getContentPane().add(iGUI);
        frame.pack();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(new Point((d.width/2) - (frame.getWidth()/2),(d.height/2) - frame.getHeight()/2));
        frame.setVisible(true);

    }
}





//String str = "Chandan";
//System.out.println("Print i" + i);


// SmallExample example = new SmallExample("bolt://localhost:7687", "neo4j", "chandan");

//SmallExample s = example;

//example.addPerson(ref str);
//        example.addNode("Alice");
////        example.addNode("Bob");
////        example.addNode("Bob1");
////        example.addNode("Bob2");
////        example.addNode("Bob3");
////        example.getNode();
////        example.addEdge("Alice","Bob1");
////        //example.printPeople("A");
//////        example.deleteRecords("Bob11");
//        example.makeGraph();
//        example.close();
//
//        SampleQueries s = new SampleQueries();
//        s.printPeople("A");

//
//        TestDBClientApplication test = new TestDBClientApplication("bolt://localhost:7687",
//                                                                    "neo4j",
//                                                                    "chandan",
//                                                                    "chandan");
//        test.close();
