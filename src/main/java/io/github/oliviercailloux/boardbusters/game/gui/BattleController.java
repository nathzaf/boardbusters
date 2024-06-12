package io.github.oliviercailloux.boardbusters.game.gui;

import io.github.oliviercailloux.boardbusters.game.GameManager;
import io.github.oliviercailloux.boardbusters.main.FileOpener;
import io.github.oliviercailloux.boardbusters.player.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The BattleController class handles the battle screen in the GUI.
 */
public class BattleController {

    @FXML
    private VBox titleVBox;

    @FXML
    private HBox battlesHBox;

    private GameManager gameManager;

    /**
     * Fills in the battlesHBox with all player's battles.
     *
     * @param gameManager the GameManager object
     */
    public void displayBattles(GameManager gameManager){
        ControllerUtils.playSoundFromGame("battle_sound.mp3");

        this.gameManager = gameManager;
        displayTitle();
        for(Player player : gameManager.getPlayers()){
            VBox battleVBox = new VBox();
            battleVBox.setAlignment(Pos.CENTER);
            battleVBox.setSpacing(10);

            Text playerName = new Text(player.getName());
            playerName.setFont(Font.font("Verdana Bold", 16));
            battleVBox.getChildren().add(playerName);

            StringBuilder battleStringBuilder = player.processBattle(gameManager.getCurrentAge() - 1);
            Text battleText = new Text(battleStringBuilder.toString());
            battleText.setFont(Font.font("Verdana", 15));
            battleText.setTextAlignment(TextAlignment.CENTER);
            battleVBox.getChildren().add(battleText);

            battlesHBox.getChildren().add(battleVBox);
        }
    }

    /**
     * Switches to the next age screen.
     *
     * @throws IOException if an I/O error occurs
     */
    public void goToNextAge(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChooseCards.fxml"));
        Parent root = loader.load();

        ChooseCardsController chooseCardsController = loader.getController();
        chooseCardsController.displayCards(gameManager);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Fills in the titleVBox with the title of the battle screen.
     */
    private void displayTitle(){
        Text endAge = new Text("End of age " + (gameManager.getCurrentAge() - 1));
        endAge.setFont(Font.font("Verdana", 30));
        endAge.setTextAlignment(TextAlignment.CENTER);
        titleVBox.getChildren().add(endAge);

        HBox timeForBattleHBox = new HBox();
        timeForBattleHBox.setAlignment(Pos.CENTER);
        timeForBattleHBox.setSpacing(10);
        Image battleImg = FileOpener.openImageFromSourceGame("icon_battle.png");
        ImageView battleImgViewLeft = new ImageView(battleImg);
        battleImgViewLeft.setPreserveRatio(true);
        battleImgViewLeft.setFitWidth(50);
        ImageView battleImgViewRight = new ImageView(battleImg);
        battleImgViewRight.setPreserveRatio(true);
        battleImgViewRight.setFitWidth(50);
        Text timeForBattleText = new Text("Time for battle");
        timeForBattleText.setFont(Font.font("Verdana", 30));
        timeForBattleText.setTextAlignment(TextAlignment.CENTER);
        timeForBattleHBox.getChildren().add(battleImgViewLeft);
        timeForBattleHBox.getChildren().add(timeForBattleText);
        timeForBattleHBox.getChildren().add(battleImgViewRight);
        titleVBox.getChildren().add(timeForBattleHBox);
    }
}
