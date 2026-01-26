package ui;

import controller.TestFramework;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.binpacking.Box;
import model.binpacking.BinRectangle;

import java.util.List;
import java.util.function.UnaryOperator;

public class MainController {

    public Label boxesLabel;       // number of boxes
    public Label rectanglesLabel;  // number of rectangles used
    public Button runButton;
    public Label runtimeLabel;
    @FXML private ComboBox<String> algorithmCombo;
    @FXML private ComboBox<String> neighborhoodCombo;
    @FXML private ComboBox<String> selectionCombo;
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

        algorithmCombo.setItems(FXCollections.observableArrayList(
                "Greedy", "Local Search"
        ));
        algorithmCombo.getSelectionModel().selectFirst();

        selectionCombo.setItems(FXCollections.observableArrayList(
                "Area-based", "Height-based"
        ));
        selectionCombo.getSelectionModel().selectFirst();

        neighborhoodCombo.setItems(FXCollections.observableArrayList(
                "Geometry-based", "Partially Overlapped", "Rule-based"
        ));
        neighborhoodCombo.getSelectionModel().selectFirst();

        algorithmCombo.valueProperty().addListener((obs, oldVal, newVal) -> {
            updateAlgorithmUI();
        });

        // apply correct state on first render
        updateAlgorithmUI();

    }


    private void updateAlgorithmUI() {
        boolean isLocal = "Local Search".equals(algorithmCombo.getValue());

        selectionCombo.setDisable(isLocal);     // disable for Local Search
        neighborhoodCombo.setDisable(!isLocal); // enable only for Local Search
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

        String algorithm = algorithmCombo.getValue();
        String neighborhood = neighborhoodCombo.getValue();
        String selectionStrategy = selectionCombo.getValue();

        new Thread(() -> {
            TestFramework tf = new TestFramework(n, minW, maxW, minH, maxH, boxL);
            tf.generateInstances();

            if ("Greedy".equals(algorithm)) {

                // default if user didn't choose
                String greedyStrategy = selectionStrategy != null
                        ? selectionStrategy
                        : "Area-based";

                tf.runGreedy(greedyStrategy);

            } else if ("Local Search".equals(algorithm)) {

                // safety defaults
                String neigh = neighborhood != null ? neighborhood : "Geometry-based";
                String select = selectionStrategy != null ? selectionStrategy : "Area-based";

                // tf.runLocalSearch(neigh, select);
            }



            List<Box> boxesToDraw = getBoxes(tf);

            // Get runtime string (assuming TestFramework has getSolution().getFormattedRunTime())
            String runtime = tf.getSolution().getFormattedRunTime();

            Platform.runLater(() -> {
                drawBoxes(boxesToDraw, runtime);
                runtimeLabel.setText("Runtime: " + runtime); // update left sidebar
                int totalBoxes = tf.getSolution().getItems().size();
                boxesLabel.setText("Used Boxes: " + totalBoxes);

                int totalRectangles = tf.getSolution().getItems().stream()
                        .mapToInt(b -> b.getRectangles().size())
                        .sum();

                rectanglesLabel.setText("Rectangles: " + totalRectangles);
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

        double scale = 4.0; // bigger scale for larger boxes
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

            // Add box ID text above the box
            Text boxIdText = new Text("Box: " + box.getId());
            boxIdText.setX(offsetX + 5);
            boxIdText.setY(offsetY - 5); // slightly above the box
            boxIdText.setFill(Color.BLACK);
            solutionPane.getChildren().add(boxIdText);

            // Draw rectangles inside box
            for (BinRectangle rect : box.getRectangles()) {
                double rx = offsetX + rect.getPosition().getX() * scale;
                double boxSize = box.getLength() * scale;

                double ry = offsetY
                        + boxSize
                        - (rect.getPosition().getY() + rect.getHeight()) * scale;

                double rw = rect.getWidth() * scale;
                double rh = rect.getHeight() * scale;

                Rectangle r = new Rectangle(rw, rh);

                r.setX(rx);
                r.setY(ry);

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
    }
}
