package ui;

import model.RecipeProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

// Panel that displays a list of projects
public class ProjectListPanel extends JPanel {
    public static final int WIDTH = ProjectCollectionPanel.PROJECT_LIST_WIDTH;
    public static final int HEIGHT = ProjectCollectionPanel.PROJECT_LIST_HEIGHT;
    private ArrayList<RecipeProject> projects;
    private ProjectCollectionPanel projectCollectionPanel;

    private MouseListener mouseListener = new MouseAdapter() {
        // REQUIRES: source of click was a JList
        // MODIFIES: this
        // EFFECTS: updates project viewer with the project that was selected from the list
        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                JList<String> selection = (JList<String>) e.getSource();
                int selectedProjectIndex = selection.getSelectedIndex();
                RecipeProject selectedProject = projects.get(selectedProjectIndex);
                projectCollectionPanel.updateViewerWithProject(selectedProject);
            } catch (ArrayIndexOutOfBoundsException exception) {
                //do nothing
            }
        }
    };

    // REQUIRES: collection panel is the calling collection panel object
    // EFFECTS: sets projects and collection panel to the given projects collection panel
    public ProjectListPanel(ArrayList<RecipeProject> projects,ProjectCollectionPanel projectCollectionPanel) {
        this.projects = projects;
        this.projectCollectionPanel = projectCollectionPanel;
        setup();
    }

    // MODIFIES: this
    // EFFECTS: sets up the GUI
    private void setup() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        loadProjectList();
    }

    // MODIFIES: this
    // EFFECTS: constructs the scrollable list of projects
    private void loadProjectList() {
        String[] names = getProjectNames().toArray(new String[0]);
        JList<String> list = new JList<>(names);

        list.addMouseListener(mouseListener);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane listScroller = new JScrollPane(
                list,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        listScroller.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        listScroller.setAlignmentX(RIGHT_ALIGNMENT);

        JLabel label = new JLabel("Projects");

        add(label,BorderLayout.NORTH);
        add(listScroller,BorderLayout.CENTER);
    }

    // EFFECTS: returns a list of all project names
    private ArrayList<String> getProjectNames() {
        ArrayList<String> names = new ArrayList<>();
        for (RecipeProject project : projects) {
            names.add(project.getName());
        }
        return names;
    }
}
