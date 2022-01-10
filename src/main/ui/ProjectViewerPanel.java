package ui;

import model.Recipe;
import model.RecipeProject;
import model.WorkingRecipe;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

// Panel with an overview of a project
public class ProjectViewerPanel extends JPanel implements ActionListener, TreeSelectionListener {
    public static final int TOOLBAR_WIDTH = ProjectCollectionPanel.TOOLBAR_WIDTH;
    public static final int TOOLBAR_HEIGHT = ProjectCollectionPanel.TOOLBAR_HEIGHT;
    public static final int VIEWER_WIDTH = ProjectCollectionPanel.PROJECT_VIEWER_WIDTH;
    public static final int VIEWER_HEIGHT = ProjectCollectionPanel.PROJECT_VIEWER_HEIGHT;

    private RecipeProject project;
    private Map<DefaultMutableTreeNode,Recipe> recipeMap;
    private Map<Recipe,RecipeViewerFrame> recipeViewerMap;
    private JTree recipeTree;
    private JButton finalDraftShortcut;
    private JToolBar toolbar;
    private JPanel viewer;
    private ProjectCollectionPanel projectCollectionPanel;
    private RecipeViewerFrame recipeViewerFrame;

    // EFFECTS: constructs this and runs setup procedure
    public ProjectViewerPanel() {
        setup();
    }

    // REQUIRES: collection panel is the calling collection panel object
    // EFFECTS: sets projects and collection panel to the given projects and collection panel
    public ProjectViewerPanel(RecipeProject project, ProjectCollectionPanel projectCollectionPanel) {
        this.project = project;
        this.projectCollectionPanel = projectCollectionPanel;
        recipeViewerMap = new HashMap<>();
        setup();
        loadViewer();
        loadToolbar();
    }

    // MODIFIES: this
    // EFFECTS: sets up the GUI
    private void setup() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setupViewer();
        setupToolbar();
        add(viewer);
        add(toolbar);
    }

    // MODIFIES: this
    // EFFECTS: sets up a blank viewer panel
    private void setupViewer() {
        viewer = new JPanel();
        viewer.setLayout(new FlowLayout());
        viewer.setPreferredSize(new Dimension(VIEWER_WIDTH,VIEWER_HEIGHT));
    }

    // MODIFIES: this
    // EFFECTS: sets up the toolbar
    private void setupToolbar() {
        finalDraftShortcut = new JButton("View final draft");
        finalDraftShortcut.addActionListener(this);
        toolbar = new JToolBar();
        toolbar.setLayout(new FlowLayout());
        toolbar.setPreferredSize(new Dimension(TOOLBAR_WIDTH,TOOLBAR_HEIGHT));
    }

    // MODIFIES: this
    // EFFECTS: loads project elements into the viewer panel
    public void loadViewer() {
        viewer.add(makeRecipeTree());
    }

    // MODIFIES: this
    // EFFECTS: makes a JTree that represents each recipe and sets the node icons; returns this JTree
    private JTree makeRecipeTree() {
        recipeMap = new HashMap<>();
        recipeTree = new JTree(makeRecipeBranch(project.getWorkingRecipe(),1,0));
        recipeTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        recipeTree.addTreeSelectionListener(this);

        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) recipeTree.getCellRenderer();
        renderer.setLeafIcon(new ImageIcon("./data/bowl.png"));
        renderer.setClosedIcon(new ImageIcon("./data/bowl.png"));
        renderer.setOpenIcon(new ImageIcon("./data/bowl.png"));
        return recipeTree;
    }

    // MODIFIES: this
    // EFFECTS: recursively assigns a unique numerical label for each node and maps each recipe to a node
    //          returns a node representing the given working recipe and downstream recipe drafts
    private MutableTreeNode makeRecipeBranch(WorkingRecipe wr, int level, int version) {
        String nodeLabel = level + "." + version;
        DefaultMutableTreeNode recipeNode = new DefaultMutableTreeNode(nodeLabel);
        recipeMap.put(recipeNode,wr.getCurrentRecipe());

        level++;
        version = 0;
        for (WorkingRecipe nextWr : wr.getNewRecipes()) {
            recipeNode.add(makeRecipeBranch(nextWr,level,version));
            version++;
        }
        return recipeNode;
    }

    // MODIFIES: this
    // EFFECTS: adds toolbar to the panel; if the recipe has a final draft, adds button for final draft to the toolbar
    public void loadToolbar() {
        if (project.isRecipeFinished()) {
            toolbar.add(finalDraftShortcut);
        }
    }

    // REQUIRES: action event source is a JButton
    // EFFECTS: executes selected button action
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton clickedButton = (JButton) actionEvent.getSource();
        if (clickedButton == finalDraftShortcut) {
            openRecipe(project.getFinalRecipe());
        }
    }

    // MODIFIES: this
    // EFFECTS: if given recipe object has an open window already, brings that window to the front
    //          otherwise, opens a new window for the recipe and maps the recipe to the window
    private void openRecipe(Recipe recipe) {
        if (recipeViewerMap.containsKey(recipe)) {
            JFrame viewer = recipeViewerMap.get(recipe);
            viewer.setVisible(true);
        } else {
            recipeViewerFrame = new RecipeViewerFrame(recipe, this);
            recipeViewerMap.put(recipe,recipeViewerFrame);
        }
    }

    // REQUIRES: JTree selection model allows single selection
    // MODIFIES: this
    // EFFECTS: opens the recipe selected from the tree
    // This method references code from Oracle's JTree tutorial
    // Link: https://docs.oracle.com/javase/tutorial/uiswing/components/tree.html
    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) recipeTree.getLastSelectedPathComponent();
        if (node == null) {
            refresh();
            return;
        }
        openRecipe(recipeMap.get(node));
    }

    // REQUIRES: given recipe is in the project's collection of drafts
    // MODIFIES: this
    // EFFECTS: sets final recipe of project to given recipe
    public void setFinal(Recipe recipe) {
        project.assignFinalRecipe(recipe);
        refresh();
    }

    // REQUIRES: given recipe is in the project's collection of drafts
    // MODIFIES: this
    // EFFECTS: finds the matching recipe object in the project's drafts and creates a new draft branch with it
    public void newDraft(Recipe recipe) {
        WorkingRecipe wr = project.findMatchingObjectInWorkingRecipes(project.getWorkingRecipe(),recipe);
        project.makeNewDraft(wr);
        //wr.makeNewDraft();
        refresh();
    }

    // EFFECTS: creates a new project with the given project
    public void newProject(Recipe recipe) {
        projectCollectionPanel.newProject(recipe);
    }

    // MODIFIES: this
    // EFFECTS: validates and repaints this; validates and repaints the project collection panel
    private void refresh() {
        removeAll();
        setup();
        loadViewer();
        loadToolbar();
        validate();
        repaint();
        projectCollectionPanel.validate();
        projectCollectionPanel.repaint();
    }
}
