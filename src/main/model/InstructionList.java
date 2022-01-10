package model;

import java.util.ArrayList;
import java.util.Objects;

// Represents a list of instructions in a recipe
public class InstructionList {
    private ArrayList<Instruction> instructions;

    // EFFECTS: instructions is set to a new empty list
    public InstructionList() {
        instructions = new ArrayList<>();
    }

    // EFFECTS: instructions is set to the given instruction list
    public InstructionList(ArrayList<Instruction> i) {
        instructions = i;
    }

    // REQUIRES: 0 <= i < length of instructions
    // MODIFIES: this
    // EFFECTS: deletes the ith instruction in instructions (0-based indexing)
    protected void deleteInstruction(int i) {
        instructions.remove(i);
    }

    // REQUIRES: 0 <= i <= length of instructions
    // MODIFIES: this
    // EFFECTS: adds newInstructions to the ith spot in instructions (0-based indexing)
    protected void insertInstructions(int i, InstructionList newInstructions) {
        instructions.addAll(i, newInstructions.getInstructions());
    }

    // MODIFIES: this
    // EFFECTS: adds instruction to the last spot in instructions
    public void addInstruction(Instruction instruction) {
        instructions.add(instruction);
    }

    public ArrayList<Instruction> getInstructions() {
        return instructions;
    }

    // EFFECTS: copies the elements from instructions into a new ArrayList, then returns the new copy
    public ArrayList<Instruction> getInstructionsCopy() {
        ArrayList<Instruction> copy = new ArrayList<>();
        for (Instruction instruction : instructions) {
            String text = instruction.getString();
            copy.add(new Instruction(text));
        }
        return copy;
    }

    // EFFECTS: returns the length of the instructions list
    public int getSize() {
        return instructions.size();
    }

    // EFFECTS: returns true if this and given object are both instruction lists with the same instructions;
    //          false otherwise
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InstructionList that = (InstructionList) o;
        if (instructions.size() != that.instructions.size()) {
            return false;
        }
        for (int i = 0; i < instructions.size(); i++) {
            if (!Objects.equals(instructions.get(i), that.instructions.get(i))) {
                return false;
            }
        }
        return true;
    }

    // EFFECTS: returns hashcode based on the list of instructions
    @Override
    public int hashCode() {
        return Objects.hash(instructions);
    }
}
