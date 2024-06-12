package io.github.oliviercailloux.boardbusters.game.gui;

import io.github.oliviercailloux.boardbusters.game.GameManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * The ChoosePlayersNameController class handles the input of player names in the GUI.
 */
public class ChoosePlayersNameController {

    @FXML
    private HBox playerNamesHBox;

    private GameManager gameManager;

    /**
     * Fills in the playerNamesHBox with the text fields to enter player names.
     *
     * @param gameManager the game manager object
     */
    public void displayPlayerNamesField(GameManager gameManager){
        this.gameManager = gameManager;
        List<TextField> playersNameInputs = new ArrayList<>();
        for(int i = 0; i < gameManager.getNumberOfPlayers(); i++){
            TextField currTextField = new TextField();
            currTextField.setPrefSize(240, 50);
            currTextField.setPromptText("Player " + (i + 1) + "'s name");
            playersNameInputs.add(currTextField);
            playerNamesHBox.getChildren().add(playersNameInputs.get(i));
        }
    }

    /**
     * Submits the player names and starts the game if the names are valid.
     * If not valid, displays an alert.
     *
     * @throws IOException if an I/O error occurs
     */
    public void submitPlayersName(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error in player's names");
        alert.setHeaderText("Error");
        alert.setContentText("There are blank names !");
        boolean valid = true;
        List<String> playersName = new ArrayList<>();
        for(Node nameFieldNode : playerNamesHBox.getChildren()){
            String currentName = ((TextField) nameFieldNode).getText();
            if(currentName.isBlank()){
                alert.show();
                valid = false;
                break;
            }
            playersName.add(currentName);
        }

        if(valid){
            gameManager.setupGame(playersName);
            gameManager.startGame();

            URL musicFile = getClass().getResource("main_theme2.mp3");
            MusicPlayer musicPlayer = MusicPlayer.getInstance();
            musicPlayer.setMusic(musicFile.toString());
            musicPlayer.playMusic();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChooseCards.fxml"));
            Parent root = loader.load();

            ChooseCardsController chooseCardsController = loader.getController();
            chooseCardsController.displayCards(gameManager);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}
