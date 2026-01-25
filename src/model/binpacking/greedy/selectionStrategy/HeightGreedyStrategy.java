package model.binpacking.greedy.selectionStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import model.algorithm.greedy.GreedySelection;
import model.binpacking.Rectangle;

public class HeightGreedyStrategy extends GreedySelection<Rectangle> {

    public HeightGreedyStrategy(ArrayList<Rectangle> rectangles) {
        super(rectangles);
    }

    @Override
    public ArrayList<Rectangle> orderItems(ArrayList<Rectangle> items) {
        items.sort(Comparator.comparingInt(Rectangle::getHeight).reversed());
        return items;
    }
}
