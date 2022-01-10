package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

// Represents a single recipe instruction
public class Instruction implements Writable {
    private String instruction;

    // EFFECTS: instruction is set to newInstruction
    public Instruction(String newInstruction) {
        instruction = newInstruction;
    }

    // EFFECTS: returns instruction
    public String getString() {
        return instruction;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("instruction",instruction);

        return json;
    }

    // EFFECTS: returns true if this and given object have the same instruction strings;
    //          false otherwise
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Instruction that = (Instruction) o;
        return Objects.equals(instruction, that.instruction);
    }

    // EFFECTS: returns hashcode based on the instruction string
    @Override
    public int hashCode() {
        return Objects.hash(instruction);
    }
}
