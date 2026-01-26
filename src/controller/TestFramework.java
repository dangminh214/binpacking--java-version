package controller;

import java.util.ArrayList;
import model.algorithm.greedy.GreedyAlgorithm;
import model.binpacking.AlgSolution;
import model.binpacking.Box;
import model.binpacking.Rectangle;
import model.binpacking.greedy.BottomLeftPlacer;
import model.binpacking.greedy.selectionStrategy.AreaGreedyStrategy;

public class TestFramework {

    private int numberInstances;
    private int minW;
    private int maxW;
    private int minH;
    private int maxH;
    private ArrayList<Rectangle> rectangles;
    private int boxL;
    private AlgSolution solution;

    public TestFramework(
        int numberInstances,
        int minW,
        int maxW,
        int minH,
        int maxH,
        int boxL
    ) {
        if (
            numberInstances <= 0 ||
            minW <= 0 ||
            maxW <= 0 ||
            minH <= 0 ||
            maxH <= 0 ||
            boxL <= 0
        ) {
            throw new IllegalArgumentException(
                "All inputs must be positive integers."
            );
        }

        if (minW > maxW) throw new IllegalArgumentException(
            "minW cannot be greater than maxW."
        );
        if (minH > maxH) throw new IllegalArgumentException(
            "minH cannot be greater than maxH."
        );

        // Ensure boxL can fit the min/max rectangle sizes
        if (boxL < minW || boxL < minH) {
            throw new IllegalArgumentException(
                "Box length must be at least as big as minW and minH."
            );
        }
        if (boxL < maxW || boxL < maxH) {
            throw new IllegalArgumentException(
                "Box length cannot be smaller than maxW or maxH."
            );
        }

        this.numberInstances = numberInstances;
        this.minW = minW;
        this.maxW = maxW;
        this.minH = minH;
        this.maxH = maxH;
        this.rectangles = new ArrayList<Rectangle>();
        this.boxL = boxL;
        this.solution = null;
    }

    public AlgSolution getSolution() {
        return this.solution;
    }

    public void generateInstances() {
        for (int i = 0; i < numberInstances; i++) {
            int width = (int) (Math.random() * (maxW - minW + 1)) + minW;
            int height = (int) (Math.random() * (maxH - minH + 1)) + minH;

            Rectangle rect = new Rectangle(i, width, height);

            this.rectangles.add(rect);
        }

        System.out.println(
            "Generated " + this.rectangles.size() + " in test-framework"
        );
    }

    public void runGreedy() {
        AreaGreedyStrategy selection = new AreaGreedyStrategy(this.rectangles);
        BottomLeftPlacer placer = new BottomLeftPlacer(this.boxL);
        this.solution = new AlgSolution(this.numberInstances);

        GreedyAlgorithm<Rectangle, Box, AlgSolution> alg =
            new GreedyAlgorithm<>(solution, selection, placer);

        AlgSolution sol = alg.solve();

        System.out.println("Solution boxes: " + sol.getItems().size());
        System.out.println("Runtime:" + sol.getFormattedRunTime());
        System.out.println("\nBoxes with rectangles:");

        for (int i = 0; i < sol.getItems().size(); i++) {
            Box box = sol.getItems().get(i);

            System.out.println(
                "\nBox " + i + ": " + box.getRectangles().size() + " rectangles"
            );

            for (Rectangle rect : box.getRectangles()) {
                System.out.println(
                    "  Rectangle " +
                        rect.getId() +
                        ": " +
                        "Size: " +
                        "(" +
                        rect.getWidth() +
                        "x" +
                        rect.getHeight() +
                        ")" +
                        " at (" +
                        rect.getPosition().getX() +
                        ", " +
                        rect.getPosition().getY() +
                        ")"
                );
            }
        }

        sol.printStats();
    }
}
