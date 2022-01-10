package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Panel that displays a list of projects and details about one selected project
public class ProjectCollectionPanel extends JPanel {
    public static final int PROJECT_LIST_WIDTH = 100;
    public static final int PROJECT_LIST_HEIGHT = RecipeManagerApp.HEIGHT - RecipeManagerApp.TOOLBAR_HEIGHT * 3;
    public static final int PROJECT_VIEWER_WIDTH = RecipeManagerApp.WIDTH - PROJECT_LIST_WIDTH;
    public static final int PROJECT_VIEWER_HEIGHT = RecipeManagerApp.HEIGHT - RecipeManagerApp.TOOLBAR_HEIGHT * 4;
    public static final int TOOLBAR_WIDTH = PROJECT_VIEWER_WIDTH;
    public static final int TOOLBAR_HEIGHT = RecipeManagerApp.TOOLBAR_HEIGHT;

    private ArrayList<RecipeProject> projects;
    private Container projectList;
    private Container projectViewer;
    private static ProjectNameFrame projectNamePopup;
    private static Recipe recipeForNewProject;
    private static String nameForNewProject;

    // EFFECTS: sets projects to the given list of projects and sets up this panel
    public ProjectCollectionPanel(ArrayList<RecipeProject> projects) {
        this.projects = projects;
        projectList = new ProjectListPanel(projects,this);
        projectViewer = new ProjectViewerPanel();

        setup();
    }

    // MODIFIES: this
    // EFFECTS: sets up GUI elements
    private void setup() {
        setLayout(new FlowLayout());
        add(projectList);
        add(projectViewer);
        setVisible(true);
    }

    // REQUIRES: given project is in the list of projects
    // MODIFIES: this
    // EFFECTS: reconstructs the project viewer panel with the given project
    public void updateViewerWithProject(RecipeProject project) {
        remove(projectViewer);
        projectViewer = new ProjectViewerPanel(project,this);
        add(projectViewer);
        refresh();
    }

    // REQUIRES: name is not the name of an existing project and recipeForNewProject is not null
    // MODIFIES: this
    // EFFECTS: makes a new project with an empty recipe, with the given name
    public void makeNewProjectWithName(String name) {
        nameForNewProject = name;
        RecipeProject project = new RecipeProject(nameForNewProject,recipeForNewProject);
        projects.add(project);
        updateListPanel();
    }

    // MODIFIES: this
    // EFFECTS: constructs a new list panel with projects
    private void updateListPanel() {
        remove(projectList);
        remove(projectViewer);
        projectList = new ProjectListPanel(projects,this);
        add(projectList);
        add(projectViewer);
        refresh();
    }

    // MODIFIES: this
    // EFFECTS: starts a new project with the given recipe and prompts for a project name
    public void newProject(Recipe recipe) {
        recipeForNewProject = new Recipe(recipe.getIngredientList(),recipe.getInstructionList());
        projectNamePopup = new ProjectNameFrame(projects,this);
    }

    // MODIFIES: this
    // EFFECTS: validates and repaints this
    public void refresh() {
        validate();
        repaint();
    }
}
