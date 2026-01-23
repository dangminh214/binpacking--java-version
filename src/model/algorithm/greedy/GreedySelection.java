package model.algorithm.greedy;

import model.core.SelectionStrategy;

import java.util.ArrayList;

public abstract class GreedySelection<I> extends SelectionStrategy<I> {
    public ArrayList<I> items;

    public GreedySelection(ArrayList<I> items) {
        this.items = items;
    }

    public abstract ArrayList<I> orderItems(ArrayList<I> items);
}
