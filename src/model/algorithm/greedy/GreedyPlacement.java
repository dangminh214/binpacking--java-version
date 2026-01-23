package model.algorithm.greedy;
/**
 * check if it is possible to add an item into a solution
 * e.g. Bottom Left
 */
// If return true then add else skip

import model.algorithm.AbstractSolution;
import model.binpacking.AlgSolution;
import model.binpacking.Rectangle;
import model.binpacking.greedy.ToPlacePosition;
import model.core.Item;
import model.core.PlacementStrategy;

/**
 * check if it is possible to add an item into a solution
 * e.g. Bottom Left
 */
public abstract class GreedyPlacement<
        I extends Item,
        C,
        S extends AbstractSolution<C>> extends PlacementStrategy<I, S> {

    public boolean checkThenAdd(I item, S solution) {
        Placement toPlacePos = this.canPlace(item, solution);
        if (toPlacePos != null) {
            this.place(item, solution, toPlacePos);
            return true;
        }
        return false;
    }

    /**
     * check if a rectangle can be placed in a solution
     * @return Placement containing container id and position,
     *         or null if it is unplaceable
     */
    protected abstract Placement canPlace(I item, S solution);

    /**
     * insert an item into a solution
     * @param pos container id in the solution and coordinates to place the item
     */
    protected abstract void place(I item, S solution, Placement pos);

    protected abstract void place(Rectangle rect, AlgSolution algSol, ToPlacePosition toPlacePos);

    /**
     * Helper class representing a placement position
     */
    protected static class Placement {

        public final int cid;
        public final int x;
        public final int y;

        public Placement(int cid, int x, int y) {
            this.cid = cid;
            this.x = x;
            this.y = y;
        }
    }
}

