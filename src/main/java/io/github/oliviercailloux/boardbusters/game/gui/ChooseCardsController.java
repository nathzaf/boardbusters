package io.github.oliviercailloux.boardbusters.game.gui;

import io.github.oliviercailloux.boardbusters.card.Card;
import io.github.oliviercailloux.boardbusters.game.GameManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The ChooseCardsController class handles the card selection in the GUI.
 */
public class ChooseCardsController {

    @FXML
    private HBox playerCardsHBox;

    @FXML
    private Text playerNameText;

    @FXML
    private Text ageText;

    @FXML
    private Text turnText;

    @FXML
    private HBox playerResourcesHBox;

    @FXML
    private VBox playerWonderVBox;

    @FXML
    private HBox playerVictoryPointsHBox;

    @FXML
    private HBox playerTreasureHBox;

    @FXML
    private HBox playerMilitaryMightHBox;

    @FXML
    private HBox playerBuiltCardsHBox;

    @FXML
    private Text builtCardsTitle;

    /**
     * Fills in the playerCardsHBox with the cards in the player's hand, and fills the headers with player's information.
     *
     * @param gameManager the GameManager object
     */
    public void displayCards(GameManager gameManager){
        ControllerUtils.displayHeaders(gameManager, playerNameText, ageText, turnText);
        ControllerUtils.displayPlayerProperties(gameManager.getCurrentPlayer(), playerWonderVBox, playerVictoryPointsHBox,
                playerTreasureHBox, playerMilitaryMightHBox, playerResourcesHBox, playerBuiltCardsHBox, builtCardsTitle);
        for(Card playerCard : gameManager.getCurrentPlayer().getInHandCards()){
            VBox cardVbox = new VBox();
            ImageView cardImageView = new ImageView(ControllerUtils.CARD_IMG_MAP.get(playerCard.getCardType()));
            ControllerUtils.displayCard(playerCard, cardVbox, cardImageView);
            cardImageView.setOnMouseClicked((MouseEvent e) -> {
                try{
                    chooseCard(gameManager, playerCard);
                }catch(IOException ex){
                    System.err.println(ex.getMessage());
                }
            });
            playerCardsHBox.getChildren().add(cardVbox);
        }
    }

    /**
     * Chooses the specified card and displays the available actions view for the card.
     *
     * @param gameManager the GameManager object
     * @param chosenCard  the chosen card
     * @throws IOException if an I/O error occurs
     */
    private void chooseCard(GameManager gameManager, Card chosenCard) throws IOException{
        gameManager.getCurrentPlayer().setChosenCard(chosenCard);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChooseCardAction.fxml"));
        Parent root = loader.load();

        ChooseCardActionController chooseCardActionController = loader.getController();
        chooseCardActionController.displayActions(gameManager);

        Stage stage = (Stage) playerCardsHBox.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
