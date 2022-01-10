package ui;

import model.logging.Event;
import model.logging.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// The main window of the recipe manager application
public class RecipeManagerApp extends JFrame implements ActionListener {
    public static final int WIDTH = 300;
    public static final int HEIGHT = 600;
    public static final int TOOLBAR_HEIGHT = 30;

    private static final String JSON_STORE = "./data/recipeProjects.json";
    protected ArrayList<RecipeProject> projects;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JButton newButton;
    private JButton saveButton;
    private JButton loadButton;
    private ProjectCollectionPanel projectCollectionPanel;

    // EFFECTS: opens the main window, sets up GUI elements, and starts running the app
    public RecipeManagerApp() {
        super("Sous-Chef");
        projects = new ArrayList<>();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        setup();
    }

    // MODIFIES: this
    // EFFECTS: sets up GUI elements
    private void setup() {
        setLayout(new BorderLayout());
        // The following code was adapted from stackoverflow
        // Link: https://stackoverflow.com/questions/16372241/run-function-on-jframe-close
        // Start
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                exitProcedure();
            }
        });
        // End

        setLocationRelativeTo(null);

        loadMainMenu();
        loadProjectCollection();
        pack();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates the main menu toolbar and adds it to the window
    private void loadMainMenu() {
        JToolBar menu = new JToolBar();
        menu.setPreferredSize(new Dimension(WIDTH,TOOLBAR_HEIGHT));
        newButton = new JButton("New project");
        saveButton = new JButton("Save projects");
        loadButton = new JButton("Load projects");
        newButton.addActionListener(this);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
        menu.add(newButton);
        menu.add(saveButton);
        menu.add(loadButton);
        add(menu,BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: creates a list of the user's projects and adds it to the window
    private void loadProjectCollection() {
        projectCollectionPanel = new ProjectCollectionPanel(projects);
        add(projectCollectionPanel,BorderLayout.LINE_START);
    }

    // EFFECTS: saves the projects to file
    // This method references code from JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    private void saveProjects() {
        try {
            jsonWriter.open();
            jsonWriter.writeProjects(projects);
            jsonWriter.close();
            System.out.println("Saved all projects");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads projects from file
    // This method references code from JsonSerializationDemo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    private void loadProjects() {
        try {
            projects = jsonReader.read();
            System.out.println("Loaded projects from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // REQUIRES: source of action event is a JButton
    // MODIFIES: this
    // EFFECTS: executes selected main menu toolbar task
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton clickedButton = (JButton) actionEvent.getSource();
        if (clickedButton == newButton) {
            projectCollectionPanel.newProject(new Recipe());
            add(projectCollectionPanel,BorderLayout.LINE_START);
        } else if (clickedButton == saveButton) {
            saveProjects();
        } else if (clickedButton == loadButton) {
            loadProjects();
            remove(projectCollectionPanel);
            loadProjectCollection();
            refresh();
        }
    }

    // MODIFIES: this
    // EFFECTS: validates and repaints this
    public void refresh() {
        validate();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: prints event log and closes the program
    // The following method was adapted from stackoverflow
    // Link: https://stackoverflow.com/questions/16372241/run-function-on-jframe-close
    private void exitProcedure() {
        this.dispose();
        EventLog log = EventLog.getInstance();
        for (Event next : log) {
            System.out.println(next.toString());
        }
        System.exit(0);
    }
}
