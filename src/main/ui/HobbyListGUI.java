package ui;

import model.HobbyList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
// Makes a GUI that can be used to navigate HobbyList

public class HobbyListGUI extends JFrame implements ActionListener {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 300;

    private JPanel buttonPanel;
    private JPanel hobbyListPanel;
    private JLabel hobbyTitle;

    private static final String JSON_STORE = "./data/hobbyList.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private HobbyList hobbyList;

    public HobbyListGUI() {
        super("Milestone Tracker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        buttonPanel = new JPanel(new GridLayout(0, 1, 10, 5));
        add(buttonPanel, BorderLayout.LINE_END);

        initialize();
        setUpButtons();
        makeHobbyListArea();


        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);
    }

    private void initialize() {
        hobbyList = new HobbyList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        hobbyTitle = new JLabel();
        hobbyList.setName("default");
        hobbyTitle.setFont(hobbyTitle.getFont().deriveFont(16.0f));
        hobbyTitle.setText("Current Hobbies in List: " + hobbyList.getName());
        System.out.println(hobbyList.getName());
        add(hobbyTitle, BorderLayout.NORTH);
    }

    private void setUpButtons() {
        JButton newListButton = new JButton("Make a new HobbyList");
        JButton loadButton = new JButton("load");
        loadButton.setActionCommand("load");
        loadButton.addActionListener(this);
        JButton renameButton = new JButton("rename HobbyList");
        JButton addHobbyButton = new JButton("add a Hobby");
        buttonPanel.add(newListButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(renameButton);
        buttonPanel.add(addHobbyButton);

    }

    //
    //
    //
    private void makeHobbyListArea() {
        hobbyListPanel = new JPanel();
        hobbyListPanel.setLayout(new BoxLayout(hobbyListPanel, BoxLayout.Y_AXIS));
        String[] hobbyNames = new String[hobbyList.length()];

        for (int i = 0; i < hobbyList.length(); i++) {
            hobbyNames[i] = hobbyList.getByIndex(i).getName()
                    + " - Total Hours :" + hobbyList.getByIndex(i).getTotalProgress();
            JList<String> hobbyJList = new JList(hobbyNames);
            hobbyListPanel.add(hobbyJList, BorderLayout.WEST);
        }
        add(hobbyListPanel, BorderLayout.WEST);
        hobbyListPanel.revalidate();
        hobbyListPanel.repaint();
    }


    //
    //
    //

    public static void main(String[] args) {
        new HobbyListGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("load")) {
            loadHobbyList();
            makeHobbyListArea();
            hobbyTitle.setText("Current Hobbies in List: " + hobbyList.getName());
            revalidate();

        }
    }

    // MODIFIES: this
    // EFFECTS: loads hobbyList from file
    private void loadHobbyList() {
        try {
            hobbyList = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (ParseException e) {
            System.out.println("Exception from parse dateString");
        }
    }
}


