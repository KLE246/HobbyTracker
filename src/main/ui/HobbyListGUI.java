package ui;

import exceptions.NegativeTimeException;
import model.Hobby;
import model.HobbyList;
import model.Milestone;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
// Makes a GUI that can be used to navigate a HobbyList

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


    // EFFECTS: creates the main frame and sets up the buttons that should be shown first
    public HobbyListGUI() {
        super("Milestone Tracker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);

        initialize();
        setUpButtons();

        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates instances of objects required and panels to be used
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

    // MODIFIES: this
    // EFFECTS: adds the buttons on the right side of the frame
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

    // MODIFIES: this
    // EFFECTS: shows the hobby list on the left side of the frame
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
        hobbyListPanel.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                hobbyOptionsPanel.setEnabled(true);
                hobbyOptionsPanel.setVisible(true);
                revalidate();
            }
        });
        setHobbyListTitle();
        add(hobbyOptionsPanel, BorderLayout.CENTER);
        hobbyOptionsPanel.setVisible(false);
        hobbyOptionsPanel.setEnabled(false);
    }

    // MODIFIES: this
    // EFFECTS: shows the buttons and options in the middle panel relating to a selected hobby
    private void makeNewHobbyOptionsPanel() {
        JPanel addTimePanel = new JPanel(new FlowLayout());
        JButton addTimeButton = new JButton("Add hours");
        addTimeButton.setActionCommand("addTime");
        addTimeButton.addActionListener(this);
        hoursToAddField = new JTextField(10);
        addTimePanel.add(addTimeButton);
        addTimePanel.add(hoursToAddField);
        JButton getLogButton = new JButton("Progression log");
        getLogButton.setActionCommand("progressionLog");
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

    // MODIFIES: this
    // EFFECTS: adds all the option buttons to the middle panel
    private void addMiddlePanelButtons(JPanel addTimePanel, JButton getLogButton, JButton addMilestoneButton,
                                       JButton getMilestoneLogButton, JButton chartButton) {
        hobbyOptionsPanel.add(addTimePanel);
        hobbyOptionsPanel.add(hourMessages);
        hobbyOptionsPanel.add(getLogButton);
        hobbyOptionsPanel.add(addMilestoneButton);
        hobbyOptionsPanel.add(getMilestoneLogButton);
        hobbyOptionsPanel.add(chartButton);
    }


//    // MODIFIES:
//    // EFFECTS: calls the GUI to open the frame
//    public static void main(String[] args) {
//        new HobbyListGUI();
//    }

    // MODIFIES: this, hobbyList
    // EFFECTS: calls methods depending on the button clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("load")) {
            saveLoadDialogBox("Load");
            remove(hobbyOptionsPanel);
        } else if (e.getActionCommand().equals("rename")) {
            renameDialogBox();
        } else if (e.getActionCommand().equals("save")) {
            saveLoadDialogBox("Save");
            remove(hobbyOptionsPanel);
        } else if (e.getActionCommand().equals("new")) {
            createNewList();
        } else if (e.getActionCommand().equals("add")) {
            addHobbyDialogBox();
        } else if (e.getActionCommand().equals("addTime")) {
            addTime();
        } else if (e.getActionCommand().equals("progressionLog")) {
            openLog("Progression");
        } else if (e.getActionCommand().equals("addMilestone")) {
            addMilestoneDialogBox();
        } else if (e.getActionCommand().equals("milestoneLog")) {
            openLog("Milestone");
        } else if (e.getActionCommand().equals("chart")) {
            makeChartFrame();
        }
    }


    // EFFECTS: opens new frame with the line chart displaying progress
    private void makeChartFrame() {
        JFrame newFrame = new JFrame("Progression");
        newFrame.setSize(700, 300);
        Hobby currentHobby = hobbyList.getByIndex(hobbyListPanel.getSelectedIndex());
        XYDataset dataset = currentHobby.makeDataset();
        JFreeChart lineChart = ChartFactory.createTimeSeriesChart(
                currentHobby.getName() + " Progress",
                "Time", "Progress Hours", dataset,
                true, true, false);
        ChartPanel chartPanel = new ChartPanel(lineChart);
        XYPlot plot = (XYPlot) lineChart.getPlot();

        DateAxis dateAxis = new DateAxis();
        dateAxis.setDateFormatOverride(new SimpleDateFormat("dd-MM-yyyy"));
        plot.setDomainAxis(dateAxis);

        chartPanel.setPreferredSize(new Dimension(600, 400));
        newFrame.add(chartPanel);
        newFrame.setVisible(true);
    }

    // MODIFIES:
    // EFFECTS: opens new frame with progress log of a hobby
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


    // EFFECTS: returns the progressList of hobby as formatted strings
    private String getFormattedLogs(String operation) {
        Hobby currentHobby = hobbyList.getByIndex(hobbyListPanel.getSelectedIndex());
        LinkedList<String> log;
        if (operation.equals("Progression")) {
            log = currentHobby.getLog();
        } else {
            log = currentHobby.getMilestoneLog();
        }
        StringBuilder formattedLog = new StringBuilder();
        for (String entry : log) {
            formattedLog.append(entry).append("\n");
        }
        if (formattedLog.toString().equals("")) {
            formattedLog = new StringBuilder("Nothing to see here... yet");
        }
        return formattedLog.toString();
    }


    // EFFECTS: opens dialog box to input and make a milestone
    private void addMilestoneDialogBox() {
        JFrame newFrame = new JFrame("Add Milestone");
        newFrame.setLayout(new GridLayout(4, 1, 10, 10));
        Hobby currentHobby = hobbyList.getByIndex(hobbyListPanel.getSelectedIndex());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        newFrame.setSize(200, 300);
        JPanel titlePanel = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("Give a title: ");
        JTextField titleField = new JTextField(10);
        titlePanel.add(titleLabel);
        titlePanel.add(titleField);
        JLabel descriptionLabel = new JLabel("<html>Give a description<br/>for this Milestone: </html>");
        JTextArea descriptionArea = new JTextArea();
        JButton button = new JButton("Confirm this Milestone");
        button.addActionListener(e -> {
            currentHobby.addMilestone(new Milestone(titleField.getText(), descriptionArea.getText(),
                    currentHobby.getTotalProgress()));
            newFrame.dispose();
        });
        newFrame.add(titlePanel);
        newFrame.add(descriptionLabel);
        newFrame.add(descriptionArea);
        newFrame.add(button);
        newFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: makes a new hobbyList to work on
    private void createNewList() {
        hobbyList = new HobbyList();
        hobbyList.setName("empty list");
        hobbyListPanel.setListData(new String[0]);
        updateHobbyListArea();
    }

    //
    // MODIFIES: hobbyList
    // EFFECTS: add time to the selected hobby in the list
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
        } catch (NegativeTimeException e) {
            hourMessages.setText("You progressed backwards?");
        } catch (NumberFormatException exception) {
            hourMessages.setText("that is not a number");
        } catch (IndexOutOfBoundsException exception) {
            hourMessages.setText("select a hobby to add hours to it");
        }
    }

    // MODIFIES: this
    // EFFECTS: add a hobby to the list and updates its panel
    private void addHobbyDialogBox() {
        JFrame newFrame = new JFrame("Add Hobby to List");
        dialogField = new JTextField(15);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        newFrame.setSize(300, 100);
        newFrame.setLayout(new FlowLayout());
        JButton button = new JButton("Add Hobby");
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

    // MODIFIES: this
    // EFFECTS: saves the current list to the set location
    private void saveLoadDialogBox(String operation) {
        hobbyListPanel.setListData(new String[0]);
        JFrame newFrame = new JFrame(operation + " HobbyList");
        dialogField = new JTextField(15);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        newFrame.setSize(300, 100);
        newFrame.setLayout(new FlowLayout());
        JButton button = new JButton(operation + " List");
        dialogField.setText(JSON_STORE);
        button.addActionListener(e -> {
            if (operation.equals("Save")) {
                saveHobbyList(dialogField.getText());
            } else if (operation.equals("Load")) {
                loadHobbyList(dialogField.getText());
            }
            updateHobbyListArea();
            revalidate();
            newFrame.dispose();
        });
        JLabel fileLocation = new JLabel("Enter " + operation + " location: ");
        newFrame.add(fileLocation);
        newFrame.add(dialogField);
        newFrame.add(button);
        newFrame.setVisible(true);
    }

    // MODIFIES: this, hobbyList
    // EFFECTS: changes the name of the hobbyList and updates it on the panel
    private void renameDialogBox() {
        JFrame newFrame = new JFrame("Renaming HobbyList");
        dialogField = new JTextField(8);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        newFrame.setSize(300, 100);
        newFrame.setLayout(new FlowLayout());
        JButton renameButton = new JButton("Rename List");
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

    // MODIFIES: this, hobbyList
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

    // MODIFIES: this
    // EFFECTS: displays the hobbyList title on the top corner
    private void setHobbyListTitle() {
        hobbyTitle.setText("Current Hobbies in List: " + hobbyList.getName());
    }
}


