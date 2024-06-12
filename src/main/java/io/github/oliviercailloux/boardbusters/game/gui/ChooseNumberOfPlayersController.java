package io.github.oliviercailloux.boardbusters.game.gui;

import io.github.oliviercailloux.boardbusters.game.GameManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * The ChooseNumberOfPlayersController class handles the selection of the number of players in the GUI.
 */
public class ChooseNumberOfPlayersController implements Initializable {

    @FXML
    private Text nbOfPlayerText;

    @FXML
    private Button increaseNbOfPlayer;

    @FXML
    private Button decreaseNbOfPlayer;

    private MediaPlayer increaseSound;

    private MediaPlayer decreaseSound;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        nbOfPlayerText.setText(String.valueOf(3));
        decreaseNbOfPlayer.setDisable(true);
        URL increaseSoundFile = getClass().getResource("increase_sound.mp3");
        Media increaseMedia = new Media(increaseSoundFile.toString());
        increaseSound = new MediaPlayer(increaseMedia);

        URL decreaseSoundFile = getClass().getResource("decrease_sound.mp3");
        Media decreaseMedia = new Media(decreaseSoundFile.toString());
        decreaseSound = new MediaPlayer(decreaseMedia);
    }

    public void increaseNumberOfPlayer(ActionEvent event){
        Integer nbOfPlayer = Integer.parseInt(nbOfPlayerText.getText()) + 1;
        nbOfPlayerText.setText(String.valueOf(nbOfPlayer));
        increaseSound.stop();
        increaseSound.play();
        increaseNbOfPlayer.setDisable(nbOfPlayer == 7);
        decreaseNbOfPlayer.setDisable(nbOfPlayer == 3);
    }

    public void decreaseNumberOfPlayer(ActionEvent event){
        Integer nbOfPlayer = Integer.parseInt(nbOfPlayerText.getText()) - 1;
        nbOfPlayerText.setText(String.valueOf(nbOfPlayer));
        decreaseSound.stop();
        decreaseSound.play();
        increaseNbOfPlayer.setDisable(nbOfPlayer == 7);
        decreaseNbOfPlayer.setDisable(nbOfPlayer == 3);
    }

    public void createGame(ActionEvent event) throws IOException{
        GameManager gameManager = new GameManager(Integer.parseInt(nbOfPlayerText.getText()));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChoosePlayersName.fxml"));
        Parent root = loader.load();

        ChoosePlayersNameController choosePlayersNameController = loader.getController();
        choosePlayersNameController.displayPlayerNamesField(gameManager);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
