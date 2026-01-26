package ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

public class MainController {

    @FXML
    private TextField rectanglesNumberField;
    @FXML
    private TextField minWField;
    @FXML
    private TextField maxWField;
    @FXML
    private TextField minHField;
    @FXML
    private TextField maxHField;
    @FXML
    private TextField boxLField;

    @FXML
    public void initialize() {
        // Filter for numbers only
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        };

        // Apply to all fields
        rectanglesNumberField.setTextFormatter(new TextFormatter<>(filter));
        minWField.setTextFormatter(new TextFormatter<>(filter));
        maxWField.setTextFormatter(new TextFormatter<>(filter));
        minHField.setTextFormatter(new TextFormatter<>(filter));
        maxHField.setTextFormatter(new TextFormatter<>(filter));
        boxLField.setTextFormatter(new TextFormatter<>(filter));
    }
}
