package ui;

import controller.TestFramework;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.binpacking.Box;
import model.binpacking.BinRectangle;

import java.util.List;
import java.util.function.UnaryOperator;

public class MainController {

    public Button runButton;
    public Label runtimeLabel;
    @FXML private TextField rectanglesNumberField;
    @FXML private TextField minWField;
    @FXML private TextField maxWField;
    @FXML private TextField minHField;
    @FXML private TextField maxHField;
    @FXML private TextField boxLField;
    @FXML private Pane solutionPane;

    @FXML
    public void initialize() {
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
            tf.runGreedy(); // Run algorithm

            List<Box> boxesToDraw = getBoxes(tf);

            // Get runtime string (assuming TestFramework has getSolution().getFormattedRunTime())
            String runtime = tf.getSolution().getFormattedRunTime();

            Platform.runLater(() -> {
                drawBoxes(boxesToDraw, runtime);
                runtimeLabel.setText("Runtime: " + runtime); // update left sidebar
            });
        }).start();
    }

    private static List<Box> getBoxes(TestFramework tf) {
        List<Box> allBoxes = tf.getSolution().getItems();

        // Pick first 2 + last 2 boxes
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
        return boxesToDraw;
    }

    private int parseField(TextField field, int defaultValue) {
        try {
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private void drawBoxes(List<Box> boxes, String runtime) {
        solutionPane.getChildren().clear();

        double scale = 5.0; // bigger scale for larger boxes
        double spacingX = 50;
        double spacingY = 50;

        // 2x2 grid
        int cols = 2;
        double startX = 20;
        double startY = 20;

        for (int i = 0; i < boxes.size(); i++) {
            Box box = boxes.get(i);

            // Compute position in 2x2 grid
            int col = i % cols;
            int row = i / cols;
            double offsetX = startX + col * (box.getLength() * scale + spacingX);
            double offsetY = startY + row * (box.getLength() * scale + spacingY);

            // Box color
            Color boxColor = Color.LIGHTGRAY;

            // Draw box boundary
            Rectangle boxRect = new Rectangle(box.getLength() * scale, box.getLength() * scale);
            boxRect.setFill(boxColor);
            boxRect.setStroke(Color.BLACK);
            boxRect.setX(offsetX);
            boxRect.setY(offsetY);
            solutionPane.getChildren().add(boxRect);

            // Draw rectangles inside box
            for (BinRectangle rect : box.getRectangles()) {
                double rx = offsetX + rect.getPosition().getX() * scale;
                double ry = offsetY + rect.getPosition().getY() * scale;
                double rw = rect.getWidth() * scale;
                double rh = rect.getHeight() * scale;

                Rectangle r = new Rectangle(rw, rh);
                if (rect.getIsRotated()) {
                    r.setFill(Color.RED);
                } else {
                    r.setFill(Color.BLUE);
                }
                r.setStroke(Color.BLACK);
                r.setX(rx);
                r.setY(ry);
                solutionPane.getChildren().add(r);

                // Add rectangle ID text
                Text idText = new Text(String.valueOf(rect.getId()));
                idText.setX(rx + rw / 4);
                idText.setY(ry + rh / 2);
                idText.setFill(Color.WHITE);
                solutionPane.getChildren().add(idText);
            }
        }

        // Display runtime at bottom
        Text runtimeText = new Text("Runtime: " + runtime);
        runtimeText.setX(20);
        runtimeText.setY(solutionPane.getHeight() - 20);
        runtimeText.setFill(Color.BLACK);
        solutionPane.getChildren().add(runtimeText);
    }
}
