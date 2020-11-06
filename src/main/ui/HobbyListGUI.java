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
import java.util.ArrayList;
// Makes a GUI that can be used to navigate HobbyList

public class HobbyListGUI extends JFrame implements ActionListener {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 300;

    private JPanel buttonPanel;
    private JList<String> hobbyListPanel;
    private JLabel hobbyTitle;

    private static final String JSON_STORE = "./data/hobbyList.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private HobbyList hobbyList;
    JTextField field = new JTextField(8);

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
        hobbyList.setName("new list");
        hobbyTitle.setFont(hobbyTitle.getFont().deriveFont(16.0f));
        hobbyTitle.setText("Current Hobbies in List: " + hobbyList.getName());
        add(hobbyTitle, BorderLayout.NORTH);
    }

    private void setUpButtons() {
        JButton newListButton = new JButton("Make a new HobbyList");
        JButton loadButton = new JButton("load");
        loadButton.setActionCommand("load");
        loadButton.addActionListener(this);
        JButton renameButton = new JButton("rename HobbyList");
        renameButton.setActionCommand("rename");
        renameButton.addActionListener(this);
        JButton addHobbyButton = new JButton("add a Hobby");
        JButton saveButton = new JButton("save current HobbyList to file");
        buttonPanel.add(newListButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(renameButton);
        buttonPanel.add(addHobbyButton);
        buttonPanel.add(saveButton);

    }

    //
    //
    //
    private void makeHobbyListArea() {
        ArrayList<String> allHobbies = new ArrayList<>();
        for (int i = 0; i < hobbyList.length(); i++) {
            allHobbies.add(hobbyList.getByIndex(i).getName() + " - Total Hours: "
                    + hobbyList.getByIndex(i).getTotalProgress());
        }
        hobbyListPanel = new JList(allHobbies.toArray());
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
        } else if (e.getActionCommand().equals("rename")) {
            renameDialogBox();
        }
    }

    private void renameDialogBox() {
        JFrame newFrame = new JFrame("Renaming HobbyList");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        newFrame.setSize(300,100);
        newFrame.setLayout(new FlowLayout());
        JButton renameButton = new JButton("Rename List");
        // adding local button for new window
        renameButton.addActionListener(e -> {
            hobbyList.setName(field.getText());
            hobbyTitle.setText("Current Hobbies in List: " + hobbyList.getName());
            revalidate();
            newFrame.dispose();
        });
        newFrame.add(field);
        newFrame.add(renameButton);
        newFrame.setVisible(true);
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

    //todo: add visual graph of total progress and time
    //      set up middle region
}


