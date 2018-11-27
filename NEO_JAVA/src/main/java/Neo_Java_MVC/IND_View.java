package Neo_Java_MVC;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class IND_View extends JPanel {

    private final int PANEL_WIDTH = 400;
    private final int PANEL_HEIGHT = 600;
    private final int SEARCH_FIELD_WIDTH = 200;
    private final int SEARCH_FIELD_HEIGHT = 20;
    private JPanel buttonPanel,
                    queryPanel,
                    listPanel;
    private MainPanel mainPanel;
    private JTextField queryField;
    private JButton searchPattern;
    private JList queryResults;
    private ScrollPane scrollPane;

    public IND_View(){
        super(new BorderLayout());
        queryField = new JTextField();
        queryField.setPreferredSize(new Dimension(SEARCH_FIELD_WIDTH, SEARCH_FIELD_HEIGHT));
        queryField.setFont(new Font("Verdana", Font.BOLD, 12));


        buttonPanel = new JPanel();
        searchPattern = new JButton("Search Pattern");

        buttonPanel.add(searchPattern);

        queryPanel = new JPanel();
        queryPanel.add(queryField);

        mainPanel = new MainPanel();
        mainPanel.setLayout(new BorderLayout());
        //mainPanel.add()

        add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
        add(queryPanel, BorderLayout.NORTH);

    }

    private class MainPanel extends JPanel{

        public MainPanel(){

            setPreferredSize(new Dimension(PANEL_WIDTH/2, PANEL_HEIGHT/2));
            setBackground(Color.CYAN);
            scrollPane = new ScrollPane();

            listPanel = new JPanel();
            listPanel.setPreferredSize(new Dimension(PANEL_WIDTH/2,PANEL_HEIGHT/2));
            queryResults = new JList();
            listPanel.add(queryResults);

            listPanel.setBackground(Color.PINK);
            listPanel.setVisible(true);
            add(listPanel, BorderLayout.CENTER);
        }
    }

    public String JTextFieldString(){
        return queryField.getText();
    }

    public void addIND_ViewListener(ActionListener listener){
        searchPattern.addActionListener(listener);
    }

    public JButton getButtonInfo(){
        return searchPattern;
    }



}
