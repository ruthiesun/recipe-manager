package ui;

import model.RecipeProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Window that prompts for a project name
public class ProjectNameFrame extends JFrame implements ActionListener {
    private String name;
    private JTextField nameTextField;
    private JButton okButton;
    private JLabel instructions;
    private ProjectCollectionPanel projectCollectionPanel;
    private ArrayList<RecipeProject> projects;
    private final int textFieldColumns = 30;

    // REQUIRES: collection panel is the calling collection panel object
    // EFFECTS: sets projects and collection panel to the given projects and collection panel
    public ProjectNameFrame(ArrayList<RecipeProject> projects,ProjectCollectionPanel projectCollectionPanel) {
        this.projectCollectionPanel = projectCollectionPanel;
        this.projects = projects;
        setup();
    }

    // EFFECTS: sets up the GUI
    private void setup() {
        setName("Project Name");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loadInstructions();
        loadTextField();
        loadOkButton();
        add(instructions,BorderLayout.NORTH);
        add(nameTextField,BorderLayout.CENTER);
        add(okButton,BorderLayout.SOUTH);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates the instructions
    private void loadInstructions() {
        instructions = new JLabel("Enter the project name.");
    }

    // MODIFIES: this
    // EFFECTS: creates the "Ok" button
    private void loadOkButton() {
        okButton = new JButton("Ok");
        okButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: creates the empty text field
    private void loadTextField() {
        nameTextField = new JTextField(textFieldColumns);
    }

    // MODIFIES: this
    // EFFECTS: if the user clicked "Ok", then makes a new project with the name entered in the text field and closes
    //          the window if it is a valid name. Otherwise, setups up the window again
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == okButton) {
            name = nameTextField.getText();
            if (nameIsValid()) {
                projectCollectionPanel.makeNewProjectWithName(name);
                dispose();
            } else {
                remove(nameTextField);
                nameTextField = new JTextField(textFieldColumns);
                add(instructions, BorderLayout.NORTH);
                add(nameTextField, BorderLayout.CENTER);
                refresh();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: if name is an empty string, changes the instructions to say so and returns false
    //          if name has been used to name any other project, changes the instructions to say so and returns false
    //          else, returns true
    private boolean nameIsValid() {
        if (name.isEmpty()) {
            remove(instructions);
            instructions = new JLabel("Name must have at least 1 character.");
            return false;
        }

        for (RecipeProject project : projects) {
            if (project.getName().equals(name)) {
                remove(instructions);
                instructions = new JLabel("\"" + nameTextField.getText() + "\" is taken. Enter a unique name.");
                return false;
            }
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS: validates and repaints this
    public void refresh() {
        validate();
        repaint();
    }
}
