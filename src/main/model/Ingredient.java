package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

// Represents a single ingredient in a recipe
public class Ingredient implements Writable {
    private float quantity;
    private String unit;
    private String name;

    // REQUIRES: newQuantity is positive
    // EFFECTS: sets quantity to newQuantity, sets unit to newUnit, sets name to newName
    public Ingredient(float newQuantity, String newUnit, String newName) {
        quantity = newQuantity;
        unit = newUnit;
        name = newName;
    }

    public float getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public String getName() {
        return name;
    }

    // EFFECTS: returns a string with the ingredient's quantity, unit, and name
    public String getString() {
        return (quantity + " " + unit + " " + name);
    }

     //EFFECTS: returns true if this and given object are both ingredients with the same quantity, unit, and name;
     //         false otherwise
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ingredient that = (Ingredient) o;

        boolean equalQuantities = Float.compare(that.quantity, quantity) == 0;
        boolean equalUnits = Objects.equals(unit, that.unit);
        boolean equalNames = Objects.equals(name, that.name);
        return equalQuantities && equalUnits && equalNames;
    }

    // EFFECTS: returns hashcode based on the ingredient's quantity, unit, and name
    @Override
    public int hashCode() {
        return Objects.hash(quantity, unit, name);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("quantity",quantity);
        json.put("unit",unit);
        json.put("name",name);

        return json;
    }
}
