package ui;

import model.Hobby;
import model.HobbyList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.stream.IntStream;
// Makes a GUI that can be used to navigate HobbyList

public class HobbyListGUI extends JFrame implements ActionListener {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 300;

    private JPanel buttonPanel;
    private JList<String> hobbyListPanel;
    private JPanel hobbyOptionsPanel;
    private JLabel hobbyTitle;
    private DefaultListModel allHobbies;
    private JTextField hoursToAddField;
    private JLabel hourMessages;

    private static String JSON_STORE = "./data/hobbyList.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private HobbyList hobbyList;
    JTextField dialogField;

    //
    //
    //
    public HobbyListGUI() {
        super("Milestone Tracker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);

        initialize();
        setUpButtons();
        //updateHobbyListArea();// maybe take this out

        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);
    }

    //
    //
    //
    private void initialize() {
        hobbyList = new HobbyList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        hobbyTitle = new JLabel();
        hobbyListPanel = new JList();
        hobbyOptionsPanel = new JPanel();
        allHobbies = new DefaultListModel();
        hobbyOptionsPanel.setLayout(new GridLayout(6, 1, 10, 5));
        buttonPanel = new JPanel(new GridLayout(0, 1, 10, 5));
        hobbyList.setName("new list");
        hobbyTitle.setFont(hobbyTitle.getFont().deriveFont(16.0f));
        setHobbyListTitle();
        makeNewHobbyOptionsPanel();
        add(hobbyTitle, BorderLayout.PAGE_START);
        add(buttonPanel, BorderLayout.LINE_END);

    }

    //
    //
    //
    private void setUpButtons() {
        JButton newListButton = new JButton("make a new HobbyList");
        newListButton.setActionCommand("new");
        newListButton.addActionListener(this);
        JButton loadButton = new JButton("load");
        loadButton.setActionCommand("load");
        loadButton.addActionListener(this);
        JButton renameButton = new JButton("rename HobbyList");
        renameButton.setActionCommand("rename");
        renameButton.addActionListener(this);
        JButton addHobbyButton = new JButton("add a Hobby");
        addHobbyButton.setActionCommand("add");
        addHobbyButton.addActionListener(this);
        JButton saveButton = new JButton("save current HobbyList to file");
        saveButton.setActionCommand("save");
        saveButton.addActionListener(this);
        buttonPanel.add(newListButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(renameButton);
        buttonPanel.add(addHobbyButton);
        buttonPanel.add(saveButton);
    }

    //
    //
    //
    private void updateHobbyListArea() {
        allHobbies.removeAllElements();
        for (int i = 0; i < hobbyList.length(); i++) {
            allHobbies.addElement(hobbyList.getByIndex(i).getName() + " - Total Hours: "
                    + hobbyList.getByIndex(i).getTotalProgress());
        }
        hobbyListPanel.setModel(allHobbies);
        add(hobbyListPanel, BorderLayout.LINE_START);
        hobbyListPanel.repaint();
        hobbyListPanel.revalidate();
        hobbyListPanel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    hobbyOptionsPanel.setEnabled(true);
                    hobbyOptionsPanel.setVisible(true);
                    revalidate();
                }
            }
        });
        setHobbyListTitle();
        add(hobbyOptionsPanel, BorderLayout.CENTER);
        hobbyOptionsPanel.setVisible(false);
        hobbyOptionsPanel.setEnabled(false);
    }

    //
    //
    //
    private void makeNewHobbyOptionsPanel() {
        JPanel addTimePanel = new JPanel(new FlowLayout());
        JButton addTimeButton = new JButton("Add hours");
        addTimeButton.setActionCommand("addTime");
        addTimeButton.addActionListener(this);
        hoursToAddField = new JTextField(10);
        addTimePanel.add(addTimeButton);
        addTimePanel.add(hoursToAddField);
        JButton getLogButton = new JButton("Progression log");
        getLogButton.setActionCommand("progLog");
        getLogButton.addActionListener(this);
        JButton addMilestoneButton = new JButton("Add a Milestone");
        addMilestoneButton.setActionCommand("addMilestone");
        addMilestoneButton.addActionListener(this);
        JButton getMilestoneLogButton = new JButton("Milestone log");
        getMilestoneLogButton.setActionCommand("milestoneLog");
        getMilestoneLogButton.addActionListener(this);
        JButton chartButton = new JButton("Get progress chart");
        chartButton.setActionCommand("chart");
        chartButton.addActionListener(this);
        hourMessages = new JLabel("put your hours above");
        hourMessages.setFont(hourMessages.getFont().deriveFont(16.0f));
        addMiddlePanelButtons(addTimePanel, getLogButton, addMilestoneButton, getMilestoneLogButton, chartButton);
    }

    //
    //
    //
    private void addMiddlePanelButtons(JPanel addTimePanel, JButton getLogButton, JButton addMilestoneButton,
                                       JButton getMilestoneLogButton, JButton chartButton) {
        hobbyOptionsPanel.add(addTimePanel);
        hobbyOptionsPanel.add(hourMessages);
        hobbyOptionsPanel.add(getLogButton);
        hobbyOptionsPanel.add(addMilestoneButton);
        hobbyOptionsPanel.add(getMilestoneLogButton);
        hobbyOptionsPanel.add(chartButton);
    }


    //
    //
    //
    public static void main(String[] args) {
        new HobbyListGUI();
    }

    //
    //
    //
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("load")) {
            saveLoadDialogBox("Load");
            remove(hobbyOptionsPanel);
        } else if (e.getActionCommand().equals("rename")) {
            renameDialogBox();
        } else if (e.getActionCommand().equals("save")) {
            saveLoadDialogBox("Save");
        } else if (e.getActionCommand().equals("new")) {
            createNewList();
        } else if (e.getActionCommand().equals("add")) {
            addHobbyDialogBox();
        } else if (e.getActionCommand().equals("addTime")) {
            addTime();
        } else if (e.getActionCommand().equals("progLog")) {
            openLog("Progression");
        } else if (e.getActionCommand().equals("addMilestone")) {
            addMilestone();
        } else if (e.getActionCommand().equals("milestoneLog")) {
            openLog("Milestone");
        } else if (e.getActionCommand().equals("chart")) {
            makeChartFrame();
        }
    }

    //
    //
    //
    private void makeChartFrame() {
        Hobby currentHobby = hobbyList.getByIndex(hobbyListPanel.getSelectedIndex());
        currentHobby.makeChart();
    }

    //
    //
    //
    private void openLog(String operation) {
        JFrame newFrame = new JFrame(operation + "Log");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(400, 400);
        newFrame.setLayout(new GridLayout(0, 1));
        JTextArea displayLog = new JTextArea(getFormattedLogs(operation));
        displayLog.setEditable(false);
        JScrollPane scrPane = new JScrollPane(displayLog);
        newFrame.add(scrPane);
        newFrame.setVisible(true);
    }

    //
    //
    //
    private String getFormattedLogs(String operation) {
        Hobby currentHobby = hobbyList.getByIndex(hobbyListPanel.getSelectedIndex());
        LinkedList<String> log;
        if (operation == "Progression") {
            log = currentHobby.getLog();
        } else {
            log = currentHobby.getMilestoneLog();
        }
        String formattedLog = "";
        for (String entry : log) {
            formattedLog += entry + "\n";
        }
        if (formattedLog == "") {
            formattedLog = "Nothing to see here... yet";
        }
        return formattedLog;
    }

    private void addMilestone() {

    }

    //
    //
    //
    private void createNewList() {
        hobbyList = new HobbyList();
        hobbyList.setName("empty list");
        hobbyListPanel.setListData(new String[0]);
        updateHobbyListArea();
    }

    //
    //
    //
    private void addTime() {
        try {
            Hobby currentHobby = hobbyList.getByIndex(hobbyListPanel.getSelectedIndex());
            currentHobby.addTime(Integer.parseInt(hoursToAddField.getText()));
            updateHobbyListArea();
            hobbyOptionsPanel.setEnabled(true);
            hobbyOptionsPanel.setVisible(true);
            hourMessages.setText(hoursToAddField.getText() + " hours added to "
                    + currentHobby.getName());
            hoursToAddField.setText("");
        } catch (NumberFormatException exception) {
            hourMessages.setText("that is not a number");
        } catch (IndexOutOfBoundsException exception) {
            hourMessages.setText("select a hobby to add hours to it");
        }
    }

    //
    //
    //
    private void addHobbyDialogBox() {
        JFrame newFrame = new JFrame("Add Hobby to List");
        dialogField = new JTextField(15);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        newFrame.setSize(300, 100);
        newFrame.setLayout(new FlowLayout());
        JButton button = new JButton("Add Hobby");
        // adding local button for new window
        button.addActionListener(e -> {
            hobbyList.addHobby(dialogField.getText());
            updateHobbyListArea();
            newFrame.dispose();
        });
        JLabel hobbyName = new JLabel("Enter Hobby name: ");
        newFrame.add(hobbyName);
        newFrame.add(dialogField);
        newFrame.add(button);
        newFrame.setVisible(true);
    }


    //
    //
    //
    private void saveLoadDialogBox(String operation) {
        hobbyListPanel.setListData(new String[0]);
        JFrame newFrame = new JFrame(operation + " HobbyList");
        dialogField = new JTextField(15);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        newFrame.setSize(300, 100);
        newFrame.setLayout(new FlowLayout());
        JButton button = new JButton(operation + " List");
        dialogField.setText(JSON_STORE);
        // adding local button for new window
        button.addActionListener(e -> {
            if (operation == "Save") {
                saveHobbyList(dialogField.getText());
            } else if (operation == "Load") {
                loadHobbyList(dialogField.getText());
                updateHobbyListArea();
            }
            revalidate();
            newFrame.dispose();
        });
        JLabel fileLocation = new JLabel("Enter " + operation + " location: ");
        newFrame.add(fileLocation);
        newFrame.add(dialogField);
        newFrame.add(button);
        newFrame.setVisible(true);
    }

    //
    //
    //
    private void renameDialogBox() {
        JFrame newFrame = new JFrame("Renaming HobbyList");
        dialogField = new JTextField(8);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        newFrame.setSize(300, 100);
        newFrame.setLayout(new FlowLayout());
        JButton renameButton = new JButton("Rename List");
        // adding local button for new window
        renameButton.addActionListener(e -> {
            hobbyList.setName(dialogField.getText());
            setHobbyListTitle();
            revalidate();
            newFrame.dispose();
        });
        newFrame.add(dialogField);
        newFrame.add(renameButton);


        newFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: loads hobbyList from file
    private void loadHobbyList(String location) {
        jsonWriter = new JsonWriter(location);
        jsonReader = new JsonReader(location);
        JSON_STORE = location;
        try {
            hobbyList = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + location);
        } catch (ParseException e) {
            System.out.println("Exception from parse dateString");
        }
    }

    // MODIFIES: this
    // EFFECTS: saves the hobbyList to file
    private void saveHobbyList(String location) {
        jsonWriter = new JsonWriter(location);
        jsonReader = new JsonReader(location);
        JSON_STORE = location;
        try {
            jsonWriter.open();
            jsonWriter.write(hobbyList);
            jsonWriter.close();
            System.out.println("Saved " + hobbyList.getName() + " to " + location);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + location);
        }
    }

    //
    //
    //
    private void setHobbyListTitle() {
        hobbyTitle.setText("Current Hobbies in List: " + hobbyList.getName());
    }

    //todo: add visual graph of total progress and time
    //      DONE set up middle region of JFrame
    //      DONE button to make new hobbyList
    //      DONE button to add a hobby
    //      DONE Figure out how to click on a hobby in the side list
    //      DONE Match index of chosen line in list with index of hobby in hobbyList to update it
    //      click hobby on list then shows new buttons in middle
    //              - DONE open milestone log button
    //              - DONE open progress list log button
    //              - open dialog box to add a milestone
    //              - DONE text field at bottom to add hours
    //              - button to open a graph of hours and date
}


