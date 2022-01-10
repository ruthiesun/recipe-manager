package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

//Unit tests for InstructionList
public class InstructionListTest {
    private InstructionList instructionsConstructor1;
    private InstructionList instructionsConstructor2;

    @BeforeEach
    public void setup() {
        instructionsConstructor1 = new InstructionList();

        ArrayList<Instruction> i = new ArrayList<>();
        i.add(new Instruction("a"));
        i.add(new Instruction("b"));
        i.add(new Instruction("c"));
        instructionsConstructor2 = new InstructionList(i);
    }

    @Test
    public void testConstructor1() {
        assertEquals(instructionsConstructor1.getSize(), 0);
    }

    @Test
    public void testConstructor2() {
        assertEquals(instructionsConstructor2.getSize(), 3);
        assertEquals(instructionsConstructor2.getInstructions().get(0).getString(),"a");
        assertEquals(instructionsConstructor2.getInstructions().get(1).getString(),"b");
        assertEquals(instructionsConstructor2.getInstructions().get(2).getString(),"c");
    }

    @Test
    public void testDeleteInstructionFirstElement() {
        instructionsConstructor2.deleteInstruction(0);

        assertEquals(instructionsConstructor2.getSize(), 2);
        assertEquals(instructionsConstructor2.getInstructions().get(0).getString(),"b");
        assertEquals(instructionsConstructor2.getInstructions().get(1).getString(),"c");
    }

    @Test
    public void testDeleteInstructionMiddleElement() {
        instructionsConstructor2.deleteInstruction(1);

        assertEquals(instructionsConstructor2.getSize(), 2);
        assertEquals(instructionsConstructor2.getInstructions().get(0).getString(),"a");
        assertEquals(instructionsConstructor2.getInstructions().get(1).getString(),"c");
    }

    @Test
    public void testDeleteInstructionLastElement() {
        instructionsConstructor2.deleteInstruction(2);

        assertEquals(instructionsConstructor2.getSize(), 2);
        assertEquals(instructionsConstructor2.getInstructions().get(0).getString(),"a");
        assertEquals(instructionsConstructor2.getInstructions().get(1).getString(),"b");
    }

    @Test
    public void testDeleteInstructionMultiple() {
        instructionsConstructor2.deleteInstruction(0);
        instructionsConstructor2.deleteInstruction(0);

        assertEquals(instructionsConstructor2.getSize(), 1);
        assertEquals(instructionsConstructor2.getInstructions().get(0).getString(),"c");
    }

    @Test
    public void testInsertInstructionsEmpty() {
        InstructionList i1 = new InstructionList();

        instructionsConstructor2.insertInstructions(0, i1);

        assertEquals(instructionsConstructor2.getSize(), 3);
    }

    @Test
    public void testInsertInstructionsBeginning() {
        Instruction w = new Instruction("w");
        Instruction x = new Instruction("x");
        InstructionList i = new InstructionList();
        i.addInstruction(w);
        i.addInstruction(x);

        instructionsConstructor2.insertInstructions(0, i);
        assertEquals(instructionsConstructor2.getSize(),5);
        assertEquals(instructionsConstructor2.getInstructions().get(0).getString(),"w");
        assertEquals(instructionsConstructor2.getInstructions().get(1).getString(),"x");
    }

    @Test
    public void testInsertInstructionsMiddle() {
        Instruction w = new Instruction("w");
        Instruction x = new Instruction("x");
        InstructionList i = new InstructionList();
        i.addInstruction(w);
        i.addInstruction(x);

        instructionsConstructor2.insertInstructions(1, i);
        assertEquals(instructionsConstructor2.getSize(),5);
        assertEquals(instructionsConstructor2.getInstructions().get(1).getString(),"w");
        assertEquals(instructionsConstructor2.getInstructions().get(2).getString(),"x");
    }

    @Test
    public void testInsertInstructionsEnd() {
        Instruction w = new Instruction("w");
        Instruction x = new Instruction("x");
        InstructionList i = new InstructionList();
        i.addInstruction(w);
        i.addInstruction(x);

        instructionsConstructor2.insertInstructions(3, i);
        assertEquals(instructionsConstructor2.getSize(),5);
        assertEquals(instructionsConstructor2.getInstructions().get(3).getString(),"w");
        assertEquals(instructionsConstructor2.getInstructions().get(4).getString(),"x");
    }

    @Test
    public void testInsertInstructionsMultiple() {
        Instruction w = new Instruction("w");
        Instruction x = new Instruction("x");
        Instruction y = new Instruction("y");
        Instruction z = new Instruction("z");
        InstructionList i1 = new InstructionList();
        InstructionList i2 = new InstructionList();
        i1.addInstruction(w);
        i1.addInstruction(x);
        i2.addInstruction(y);
        i2.addInstruction(z);

        instructionsConstructor2.insertInstructions(0, i2);
        instructionsConstructor2.insertInstructions(0, i1);
        assertEquals(instructionsConstructor2.getSize(),7);
        assertEquals(instructionsConstructor2.getInstructions().get(0).getString(),"w");
        assertEquals(instructionsConstructor2.getInstructions().get(1).getString(),"x");
        assertEquals(instructionsConstructor2.getInstructions().get(2).getString(),"y");
        assertEquals(instructionsConstructor2.getInstructions().get(3).getString(),"z");
    }

    @Test
    public void testAddInstruction() {
        Instruction x = new Instruction("x");

        instructionsConstructor2.addInstruction(x);

        assertEquals(instructionsConstructor2.getSize(),4);
        assertEquals(instructionsConstructor2.getInstructions().get(3).getString(),"x");
    }

    @Test
    public void testAddInstructionMultiple() {
        Instruction x = new Instruction("x");
        Instruction y = new Instruction("y");

        instructionsConstructor2.addInstruction(x);
        instructionsConstructor2.addInstruction(y);

        assertEquals(instructionsConstructor2.getSize(),5);
        assertEquals(instructionsConstructor2.getInstructions().get(3).getString(),"x");
        assertEquals(instructionsConstructor2.getInstructions().get(4).getString(),"y");
    }

    @Test
    public void testGetInstructions() {
        ArrayList<Instruction> i = instructionsConstructor2.getInstructions();

        assertEquals(i.size(),3);
        assertEquals(i.get(0).getString(), "a");
        assertEquals(i.get(1).getString(), "b");
        assertEquals(i.get(2).getString(), "c");
    }

    @Test
    public void testGetInstructionsCopy() {
        ArrayList<Instruction> i = instructionsConstructor2.getInstructionsCopy();

        assertEquals(i.size(),3);
        assertEquals(i.get(0).getString(), "a");
        assertEquals(i.get(1).getString(), "b");
        assertEquals(i.get(2).getString(), "c");
    }

    @Test
    public void testGetSize() {
        int size = instructionsConstructor2.getSize();

        assertEquals(size, 3);
    }

    @Test
    public void testEqualsSameObject() {
        InstructionList instructions = instructionsConstructor2;
        boolean isEqual = instructionsConstructor2.equals(instructions);

        assertTrue(isEqual);
    }

    @Test
    public void testEqualsNull() {
        InstructionList instructions = null;
        boolean isEqual = instructionsConstructor2.equals(instructions);

        assertFalse(isEqual);
    }

    @Test
    public void testEqualsDiffClass() {
        int ingredients = 0;
        boolean isEqual = instructionsConstructor2.equals(ingredients);

        assertFalse(isEqual);
    }

    @Test
    public void testEqualsDiffSizes() {
        boolean isEqual = instructionsConstructor2.equals(instructionsConstructor1);

        assertFalse(isEqual);
    }

    @Test
    public void testEqualsDiffFields() {
        ArrayList<Instruction> i = new ArrayList<>();
        i.add(new Instruction("d"));
        i.add(new Instruction("e"));
        i.add(new Instruction("f"));
        InstructionList instructions = new InstructionList(i);

        boolean isEqual = instructionsConstructor2.equals(instructions);

        assertFalse(isEqual);
    }

    @Test
    public void testEqualsSameFields() {
        ArrayList<Instruction> i = new ArrayList<>();
        i.add(new Instruction("a"));
        i.add(new Instruction("b"));
        i.add(new Instruction("c"));
        InstructionList instructions = new InstructionList(i);

        boolean isEqual = instructionsConstructor2.equals(instructions);

        assertTrue(isEqual);
    }

    @Test
    public void testHashCode() {
        int hash = instructionsConstructor2.hashCode();

        assertEquals(Objects.hash(instructionsConstructor2.getInstructions()),hash);
    }
}
