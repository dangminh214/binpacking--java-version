package ui;

import controller.TestFramework;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.binpacking.Box;
import model.binpacking.BinRectangle;

import java.util.List;
import java.util.function.UnaryOperator;

public class MainController {

    @FXML private TextField rectanglesNumberField;
    @FXML private TextField minWField;
    @FXML private TextField maxWField;
    @FXML private TextField minHField;
    @FXML private TextField maxHField;
    @FXML private TextField boxLField;
    @FXML private Pane solutionPane;

    @FXML
    public void initialize() {
        // Number-only filter
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            return newText.matches("\\d*") ? change : null;
        };

        rectanglesNumberField.setTextFormatter(new TextFormatter<>(filter));
        minWField.setTextFormatter(new TextFormatter<>(filter));
        maxWField.setTextFormatter(new TextFormatter<>(filter));
        minHField.setTextFormatter(new TextFormatter<>(filter));
        maxHField.setTextFormatter(new TextFormatter<>(filter));
        boxLField.setTextFormatter(new TextFormatter<>(filter));
    }

    @FXML
    public void handleRun() {
        solutionPane.getChildren().clear();

        int n = parseField(rectanglesNumberField, 100);
        int minW = parseField(minWField, 1);
        int maxW = parseField(maxWField, 50);
        int minH = parseField(minHField, 1);
        int maxH = parseField(maxHField, 50);
        int boxL = parseField(boxLField, 100);

        new Thread(() -> {
            TestFramework tf = new TestFramework(n, minW, maxW, minH, maxH, boxL);
            tf.generateInstances();
            tf.runGreedy();

            List<Box> allBoxes = tf.getSolution().getItems();

            // Pick first 2 and last 2 boxes
            List<Box> boxesToDraw;
            if (allBoxes.size() <= 4) {
                boxesToDraw = allBoxes;
            } else {
                boxesToDraw = List.of(
                        allBoxes.get(0),
                        allBoxes.get(1),
                        allBoxes.get(allBoxes.size() - 2),
                        allBoxes.get(allBoxes.size() - 1)
                );
            }

            Platform.runLater(() -> drawBoxes(boxesToDraw));
        }).start();
    }

    private int parseField(TextField field, int defaultValue) {
        try {
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private void drawBoxes(List<Box> boxes) {
        solutionPane.getChildren().clear();

        double startX = 20;
        double startY = 20;
        double scale = 2.0; // smaller scale to fit
        double spacing = 20;

        for (Box box : boxes) {
            // Each box has a base color
            Color boxColor = Color.color(Math.random(), Math.random(), Math.random(), 0.3);

            // Draw box boundary
            Rectangle boxRect = new Rectangle(box.getLength() * scale, box.getLength() * scale);
            boxRect.setFill(boxColor);
            boxRect.setStroke(Color.BLACK);
            boxRect.setX(startX);
            boxRect.setY(startY);
            solutionPane.getChildren().add(boxRect);

            // Draw each rectangle inside the box
            for (BinRectangle rect : box.getRectangles()) {
                double rx = startX + rect.getPosition().getX() * scale;
                double ry = startY + rect.getPosition().getY() * scale;
                double rw = rect.getWidth() * scale;
                double rh = rect.getHeight() * scale;

                Rectangle r = new Rectangle(rw, rh);
                // Color rotated rectangles differently
                if (rect.getIsRotated()) {
                    r.setFill(Color.RED);
                } else {
                    r.setFill(Color.BLUE);
                }
                r.setStroke(Color.BLACK);
                r.setX(rx);
                r.setY(ry);

                solutionPane.getChildren().add(r);
            }

            // Move startY for next box
            startY += box.getLength() * scale + spacing;
        }
    }
}
