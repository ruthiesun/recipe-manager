package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

//Unit tests for Instruction
public class InstructionTest {
    private Instruction instruction;

    @BeforeEach
    public void setup() {
        instruction = new Instruction("a");
    }

    @Test
    public void testConstructor() {
        assertEquals(instruction.getString(),"a");
    }

    @Test
    public void testGetString() {
        assertEquals(instruction.getString(),"a");
    }

    @Test
    public void testToJson() {
        JSONObject jsonObject = instruction.toJson();

        assertEquals ("a",jsonObject.getString("instruction"));
    }

    @Test
    public void testEqualsSameObject() {
        Instruction instruction2 = instruction;
        boolean isEqual = instruction.equals(instruction2);

        assertTrue(isEqual);
    }

    @Test
    public void testEqualsNull() {
        Instruction instruction2 = null;
        boolean isEqual = instruction.equals(instruction2);

        assertFalse(isEqual);
    }

    @Test
    public void testEqualsDiffClass() {
        int instruction2 = 0;
        boolean isEqual = instruction.equals(instruction2);

        assertFalse(isEqual);
    }

    @Test
    public void testEqualsDiffFields() {
        Instruction instruction2 = new Instruction("b");
        boolean isEqual = instruction.equals(instruction2);

        assertFalse(isEqual);
    }

    @Test
    public void testEqualsSameFields() {
        Instruction instruction2 = new Instruction("a");
        boolean isEqual = instruction.equals(instruction2);

        assertTrue(isEqual);
    }

    @Test
    public void testHashCode() {
        int hash = instruction.hashCode();

        assertEquals(Objects.hash(instruction.getString()),hash);
    }
}
