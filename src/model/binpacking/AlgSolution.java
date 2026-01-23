package model.binpacking;

import model.algorithm.AbstractSolution;

import java.util.ArrayList;
import java.util.List;

public class AlgSolution extends AbstractSolution<Box> {
    List<Box> items;

    public AlgSolution() {
        super();
        this.items= new ArrayList<>();;
    }
}
