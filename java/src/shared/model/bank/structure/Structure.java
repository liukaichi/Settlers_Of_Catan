package shared.model.bank.structure;

import shared.definitions.StructureType;

/**
 * This class represents a Structure in the Catan game
 */
public class Structure {
    private StructureType type;
    private int amountBuilt, amountUnbuilt;

    public StructureType getType() {
        return type;
    }

    public int getAmountBuilt() {
        return amountBuilt;
    }

    public int getAmountUnbuilt() {
        return amountUnbuilt;
    }

    /**
     * Increments amountBuilt by specified amount
     * @param addAmount -- amount to increment by
     */
    public void addBuilt(int addAmount) {

    }

    /**
     * Decrements amountBuilt by specified amount
     * @param subAmount -- amount to decrement by
     */
    public void subBuilt(int subAmount) {

    }

    /**
     * Increments amountUnbuilt by specified amount
     * @param addAmount -- amount to increment by
     */
    public void addUnbuilt(int addAmount) {

    }

    /**
     * Decrements amountUnbuilt by specified amount
     * @param subAmount -- amount to decrement by
     */
    public void subUnbuilt(int subAmount) {

    }

}
