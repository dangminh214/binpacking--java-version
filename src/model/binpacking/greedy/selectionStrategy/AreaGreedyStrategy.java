package model.binpacking.greedy.selectionStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import model.algorithm.greedy.GreedySelection;
import model.binpacking.Rectangle;

public class AreaGreedyStrategy extends GreedySelection<Rectangle> {

    public AreaGreedyStrategy(ArrayList<Rectangle> rectangles) {
        super(rectangles);
    }

    @Override
    public ArrayList<Rectangle> orderItems(ArrayList<Rectangle> items) {
        items.sort(Comparator.comparingInt(Rectangle::getArea).reversed());
        return items;
    }
}
