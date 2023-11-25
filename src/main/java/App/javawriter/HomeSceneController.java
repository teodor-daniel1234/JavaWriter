package App.javawriter;

import Logic.ColorPickerLogic;
import Logic.TextAreaLogic;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.io.IOException;

public class HomeSceneController {
    private ColorPickerLogic colorPickerLogic = new ColorPickerLogic();
    private TextAreaLogic textAreaLogic = new TextAreaLogic();
    @FXML
    public TextArea textArea;
    @FXML
    public Label charactersCounter;
    @FXML
    public Label stringCounter;
    @FXML
    public Button changeToUppercase;
    @FXML
    public Button changeToLowercase;
    @FXML
    public ColorPicker colorPicker;
    @FXML
    public Button changeToSaveScene;
    @FXML
    public Button changeToLoadScene;

    public void setTextAreaContent(String text) {
        textArea.setText(text);
    }

    public void updateCounters() {
        textArea.textProperty().addListener((event) -> {
            charactersCounter.setText("Characters: " + textAreaLogic.returnCharactersCount(textArea.getText()));
            stringCounter.setText("Words: " + textAreaLogic.returnWordsCount(textArea.getText()));
            textAreaLogic.setDefaultLabelText(textArea, stringCounter, charactersCounter);
        });

    }

    public void handletoUpperCase(ActionEvent actionEvent) {
        String selectedText = textArea.getSelectedText();
        if (selectedText != null && !selectedText.isEmpty()) {
            textAreaLogic.setToUpperCaseSelectedText(selectedText, textArea);
        }
    }

    public void handleToLowerCase(ActionEvent actionEvent) {
        String selectedText = textArea.getSelectedText();
        if (selectedText != null && !selectedText.isEmpty()) {
            textAreaLogic.setToLowerCaseSelectedText(selectedText, textArea);
        }

    }

    @FXML
    private void handleColorPicker(ActionEvent actionEvent) {
        textArea.setStyle("-fx-text-fill: #" + colorPickerLogic.colorToHex(colorPicker.getValue()) + ";");
    }


    public void handleToSaveScene(ActionEvent actionEvent) {
        //to do create scene hanlder class.
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("save-scene-view.fxml"));
            Parent root = fxmlLoader.load();
            SaveSceneController saveSceneController = fxmlLoader.getController();
            Stage newStage = new Stage();
            saveSceneController.setTextAreaBuffer(textArea.getText());

            newStage.setTitle("Save");
            newStage.setScene(new Scene(root, 400, 300));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleToLoadScene(ActionEvent actionEvent) {
        try {
            String loadSceneViewName = "load-scene-view.fxml";
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(loadSceneViewName));
            Parent root = fxmlLoader.load();
            LoadSceneController loadSceneController = fxmlLoader.getController();

            Stage newStage = new Stage();
            newStage.setTitle("Load");
            newStage.setScene(new Scene(root, 400, 300));
            newStage.show();

            newStage.setOnCloseRequest(event -> {
                //needs a revamp as setText uses just string.
                String buffer = loadSceneController.getWords();
                textArea.setText(buffer);
                System.out.println("Buffer " + buffer);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


