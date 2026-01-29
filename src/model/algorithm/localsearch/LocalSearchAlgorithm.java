package model.algorithm.localsearch;

import java.lang.reflect.Array;
import java.util.ArrayList;
import model.algorithm.AbstractSolution;
import model.core.Item;

import model.core.Item;

public class LocalSearchAlgorithm<I extends Item, C, S extends AbstractSolution<C>> {
    private int numBox = 0;
    private double occupiedRate = 0.0;
    private ArrayList<S> solutions = new ArrayList<S>();
    private S currentSolution;
    public LocalSearchAlgorithm(S initialSolution) {
        currentSolution = initialSolution;
    }

    public S getCurrentSolution() {
        return this.currentSolution;
    }

    public double costFunction(S solution) {
        double WEIGHT = 1000;
        return solution.getItems() * WEIGHT + this.occupiedRate;
    }

    public void runLocalSearch() {
        for (int i = 0; i < this.solutions.size(); i++) {
            if (costFunction(this.solutions.get(i + 1)) < costFunction((this.solutions.get(i))))  {
                this.currentSolution = this.solutions.get(i);
            }
        }
    }
}
