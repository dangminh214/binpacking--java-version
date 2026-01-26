package model.binpacking;

import java.util.ArrayList;
import java.util.List;
import model.algorithm.AbstractSolution;

public class AlgSolution extends AbstractSolution<Box> {

    List<Box> items;
    private int numbRect;

    public AlgSolution(int numbRect) {
        super();
        this.items = new ArrayList<>();
        this.numbRect = numbRect;
    }

    public int getNumbRect() {
        return numbRect;
    }

    public double getRuntime() {
        return this.runtime;
    }

    public List<Box> getItems() {
        return items;
    }

    public void setItems(List<Box> items) {
        this.items = items;
    }

    public void printStats() {
        System.out.println(
            "Number of items (boxes): " +
                items.size() +
                " for " +
                numbRect +
                " rectangles"
        );

        System.out.println("Run time: " + getRuntime() + "ms");
    }
}
