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

import java.util.function.UnaryOperator;
import java.util.List;

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

    /**
     * Called when the "Run Algorithm" button is clicked.
     */
    @FXML
    public void handleRun() {
        // Clear previous solution
        solutionPane.getChildren().clear();

        // Read input values
        int n = parseField(rectanglesNumberField, 100); // default 100 if empty
        int minW = parseField(minWField, 1);
        int maxW = parseField(maxWField, 50);
        int minH = parseField(minHField, 1);
        int maxH = parseField(maxHField, 50);
        int boxL = parseField(boxLField, 100);

        // Run bin packing in background thread
        new Thread(() -> {
            TestFramework tf = new TestFramework(n, minW, maxW, minH, maxH, boxL);
            tf.generateInstances();
            tf.runGreedy(); // assumes solution stored in tf.getSolution()

            List<Box> solution = tf.getSolution().getItems();

            // Update UI on JavaFX thread
            Platform.runLater(() -> drawBoxes(solution, boxL));
        }).start();
    }

    private int parseField(TextField field, int defaultValue) {
        try {
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private void drawBoxes(List<Box> boxes, int boxLength) {
        double x = 10;
        double y = 10;
        double scale = 5.0; // scale down to fit Pane

        for (Box box : boxes) {
            Rectangle r = new Rectangle(box.getLength() * scale, box.getLength() * scale);
            r.setFill(Color.color(Math.random(), Math.random(), Math.random()));
            r.setStroke(Color.BLACK);
            r.setX(x);
            r.setY(y);

            solutionPane.getChildren().add(r);

            x += box.getLength() * scale + 5;
            if (x > solutionPane.getWidth() - 50) {
                x = 10;
                y += box.getLength() * scale + 5;
            }
        }
    }
}
