package io.github.oliviercailloux.boardbusters.game.gui;

import io.github.oliviercailloux.boardbusters.game.GameManager;
import io.github.oliviercailloux.boardbusters.player.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.List;

/**
 * This class is the controller for the end game screen.
 */
public class EndGameController {

    @FXML
    private VBox titleVBox;

    @FXML
    private VBox rankingVBox;

    /**
     * Fills in the rankingVBox with the final ranking of players.
     *
     * @param gameManager the game manager instance
     */
    public void displayRanking(GameManager gameManager){
        ControllerUtils.playSoundFromGame("victory_sound.mp3");

        List<Player> finalRanking = gameManager.endGame();
        displayTitle(finalRanking.get(0));

        Text ranking = new Text("Here is the ranking:");
        ranking.setFont(Font.font("Verdana", 20));
        ranking.setTextAlignment(TextAlignment.CENTER);
        rankingVBox.getChildren().add(ranking);
        Text playerRank = new Text(gameManager.displayRanking(finalRanking));
        playerRank.setFont(Font.font("Verdana", 20));
        playerRank.setTextAlignment(TextAlignment.CENTER);
        rankingVBox.getChildren().add(playerRank);
    }

    /**
     * Closes the current window.
     */
    public void closeWindow(ActionEvent actionEvent){
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Fills in the titleVBox with the title and the winning player's name on the end game screen.
     *
     * @param player the winning player
     */
    private void displayTitle(Player player){
        Text endGame = new Text("End of game");
        endGame.setFont(Font.font("Verdana", 30));
        endGame.setTextAlignment(TextAlignment.CENTER);
        titleVBox.getChildren().add(endGame);

        HBox timeForBattleHBox = new HBox();
        timeForBattleHBox.setAlignment(Pos.CENTER);
        timeForBattleHBox.setSpacing(10);

        Text timeForBattleText = new Text(player.getName() + " won the game!");
        timeForBattleText.setFont(Font.font("Verdana bold", 30));
        timeForBattleText.setTextAlignment(TextAlignment.CENTER);
        titleVBox.getChildren().add(timeForBattleText);
    }
}
