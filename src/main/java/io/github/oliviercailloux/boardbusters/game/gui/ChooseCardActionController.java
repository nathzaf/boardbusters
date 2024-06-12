package io.github.oliviercailloux.boardbusters.game.gui;

import io.github.oliviercailloux.boardbusters.card.Card;
import io.github.oliviercailloux.boardbusters.game.EndTurnEvent;
import io.github.oliviercailloux.boardbusters.game.GameManager;
import io.github.oliviercailloux.boardbusters.player.BuyNeed;
import io.github.oliviercailloux.boardbusters.player.Player;
import io.github.oliviercailloux.boardbusters.player.PlayerAction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 * The ChooseCardActionController class handles the actions available for the chosen card in the GUI.
 */
public class ChooseCardActionController {

    private Parent root;

    private Scene scene;

    private Stage stage;

    @FXML
    private VBox choosenCardVBox;

    @FXML
    private HBox actionsHBox;

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

    private GameManager gameManager;

    private Player player;

    /**
     * Fills in the actionHBox with the available actions for the chosen card.
     *
     * @param gameManager the GameManager object
     */
    public void displayActions(GameManager gameManager){
        this.gameManager = gameManager;
        player = gameManager.getCurrentPlayer();
        Card chosenCard = gameManager.getCurrentPlayer().getChosenCard();

        ControllerUtils.displayHeaders(gameManager, playerNameText, ageText, turnText);
        ControllerUtils.displayPlayerProperties(gameManager.getCurrentPlayer(), playerWonderVBox, playerVictoryPointsHBox,
                playerTreasureHBox, playerMilitaryMightHBox, playerResourcesHBox, playerBuiltCardsHBox, builtCardsTitle);

        ImageView cardImageView = new ImageView(ControllerUtils.CARD_IMG_MAP.get(chosenCard.getCardType()));
        ControllerUtils.displayCard(chosenCard, choosenCardVBox, cardImageView);

        for(PlayerAction action : PlayerAction.values()){
            VBox actionVBox = new VBox();
            actionVBox.setSpacing(10);
            actionVBox.setAlignment(Pos.CENTER);
            if(action.equals(PlayerAction.BUILD_CARD) && !player.canBuild(true)) continue;
            if(action.equals(PlayerAction.BUILD_STAGE) && !player.canBuild(false)) continue;
            ImageView actionImageView = new ImageView(ControllerUtils.ACTION_IMAGE_MAP.get(action));
            actionImageView.setPreserveRatio(true);
            actionImageView.setFitWidth(150);
            actionImageView.setPickOnBounds(true);
            Tooltip.install(actionImageView, new Tooltip(action.getLabel()));
            actionImageView.setOnMouseClicked((MouseEvent mouseEvent) -> {
                try{
                    switch(action){
                        case RETURN_TO_SELECTION -> returnToSelection();
                        case BUILD_CARD -> buildCard();
                        case BUILD_STAGE -> buildStage();
                        case DISCARD -> discard();
                    }

                }catch(IOException e){
                    System.err.println(e.getMessage());
                }
            });
            actionVBox.getChildren().add(actionImageView);

            if(action.equals(PlayerAction.BUILD_CARD) || action.equals(PlayerAction.BUILD_STAGE)){
                Optional<BuyNeed> optBuyNeed;
                boolean chainedCard = false;
                if(action.equals(PlayerAction.BUILD_CARD)){
                    optBuyNeed = player.calculateBuyNeedFromWhichNeighbour(true);
                    chainedCard = player.isChainedToBuiltCard(chosenCard);
                }else
                    optBuyNeed = player.calculateBuyNeedFromWhichNeighbour(false);

                if(chainedCard){
                    Text freeByChainingText = new Text("Free construction (chains).");
                    freeByChainingText.setTextAlignment(TextAlignment.CENTER);
                    freeByChainingText.setFont(Font.font("Verdana", 12));
                    actionVBox.getChildren().add(freeByChainingText);
                }else if(optBuyNeed.isPresent()){
                    BuyNeed buyNeed = optBuyNeed.get();
                    String buyNeedBuilder = "You need to buy resources" + System.lineSeparator() +
                            "for " +
                            buyNeed.getCoins() +
                            " coins to " +
                            buyNeed.getPlayer().getName();
                    Text buyNeedText = new Text(buyNeedBuilder);
                    buyNeedText.setTextAlignment(TextAlignment.CENTER);
                    buyNeedText.setFont(Font.font("Verdana", 12));
                    actionVBox.getChildren().add(buyNeedText);
                }
            }
            actionsHBox.setSpacing(50);
            actionsHBox.getChildren().add(actionVBox);
        }
    }

    /**
     * Returns to the card selection screen.
     *
     * @throws IOException if an I/O error occurs
     */
    private void returnToSelection() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChooseCards.fxml"));
        root = loader.load();

        ChooseCardsController chooseCardsController = loader.getController();
        chooseCardsController.displayCards(gameManager);

        stage = (Stage) actionsHBox.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Builds the selected card.
     *
     * @throws IOException if an I/O error occurs
     */
    private void buildCard() throws IOException{
        ControllerUtils.playSoundFromGame("build_sound.mp3");
        player.buildCard();
        endTurn();
    }

    /**
     * Builds the next stage of the player's wonder.
     *
     * @throws IOException if an I/O error occurs
     */
    private void buildStage() throws IOException{
        ControllerUtils.playSoundFromGame("buildstage_sound.mp3");
        player.buildStage();
        endTurn();
    }

    /**
     * Discards the selected card.
     *
     * @throws IOException if an I/O error occurs
     */
    private void discard() throws IOException{
        ControllerUtils.playSoundFromGame("discard_sound.mp3");
        player.discardCard();
        endTurn();
    }

    /**
     * Ends the current turn.
     *
     * @throws IOException if an I/O error occurs
     */
    private void endTurn() throws IOException{
        EndTurnEvent endTurnEvent = gameManager.endPlayerTurn();
        FXMLLoader loader;
        switch(endTurnEvent){
            case NEXT_PLAYER, END_TURN -> {
                loader = new FXMLLoader(getClass().getResource("ChooseCards.fxml"));
                root = loader.load();
                ChooseCardsController chooseCardsController = loader.getController();
                chooseCardsController.displayCards(gameManager);
            }
            case END_AGE -> {
                loader = new FXMLLoader(getClass().getResource("Battles.fxml"));
                root = loader.load();
                BattleController battleController = loader.getController();
                battleController.displayBattles(gameManager);
            }
            case END_GAME -> {
                loader = new FXMLLoader(getClass().getResource("EndGame.fxml"));
                root = loader.load();
                EndGameController endGameController = loader.getController();
                endGameController.displayRanking(gameManager);
            }
        }

        stage = (Stage) actionsHBox.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
