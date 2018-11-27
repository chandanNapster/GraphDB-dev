package Neo_Java_MVC;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class IND_Controller {
    private static IND_Model model;
    private static IND_View view;
    private String searchString;


    public IND_Controller(){
        /**
         * MODEL INITIALIZATION
         */
        model = new IND_Model("bolt://localhost:7687",
                "neo4j",
                "chandan");
        /**
         * VIEW INITIALIZATION
         */
        view = new IND_View();
        JFrame frame = new JFrame("INDIN GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(view);
        frame.pack();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(new Point((d.width/2) - (frame.getWidth()/2),(d.height/2) - frame.getHeight()/2));
        frame.setVisible(true);

        //IND_Controller i = new IND_Controller();
    }

    public void addListeners(){
        view.addIND_ViewListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == view.getButtonInfo()){
                    System.out.println("search");
                    System.out.println(view.JTextFieldString());
                    searchString = view.JTextFieldString();
                    model.searchPat(searchString);
                }

            }
        });
    }




    public static void main(String[] arg) throws Exception {
        IND_Controller controller = new IND_Controller();
       // System.out.println(model.getDriver());
        model.close();
        controller.addListeners();

    }
}
