package io.github.oliviercailloux.boardbusters.game.gui;

import com.google.common.collect.ImmutableMap;
import io.github.oliviercailloux.boardbusters.card.Card;
import io.github.oliviercailloux.boardbusters.card.CardType;
import io.github.oliviercailloux.boardbusters.game.GameManager;
import io.github.oliviercailloux.boardbusters.main.FileOpener;
import io.github.oliviercailloux.boardbusters.player.Player;
import io.github.oliviercailloux.boardbusters.player.PlayerAction;
import io.github.oliviercailloux.boardbusters.resources.Resources;
import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The ControllerUtils class provides utility methods for the GUI controllers.
 */
public class ControllerUtils {

    static final ImmutableMap<CardType, Image> CARD_IMG_MAP = initImgCardMap();

    static final ImmutableMap<Resources, Image> RESOURCES_IMAGE_MAP = initImgResourcesMap();

    static final ImmutableMap<PlayerAction, Image> ACTION_IMAGE_MAP = initImgActionMap();

    /**
     * Fills in the cardVBox with the playerCard details.
     *
     * @param playerCard    the card to display
     * @param cardVbox      the VBox container
     * @param cardImageView the ImageView for the card image
     */
    static void displayCard(Card playerCard, VBox cardVbox, ImageView cardImageView){
        Text cardTitle = new Text(playerCard.getName());
        cardTitle.setFont(Font.font("Verdana", 20));
        Text cardDetails = new Text(playerCard.displayCardDetails());
        cardDetails.setFont(Font.font("Verdana", 10));
        cardDetails.setTextAlignment(TextAlignment.CENTER);
        cardVbox.setAlignment(Pos.CENTER);
        cardVbox.setMaxHeight(200);
        cardVbox.getChildren().add(cardTitle);
        cardImageView.setPreserveRatio(true);
        cardImageView.setFitWidth(150);
        cardImageView.setPickOnBounds(true);
        Tooltip.install(cardImageView, new Tooltip(playerCard.getCardType().getLabel()));
        cardVbox.getChildren().add(cardImageView);
        cardVbox.getChildren().add(cardDetails);
    }

    /**
     * Fills in the headers Text with the player's name, current age, and current turn.
     *
     * @param gameManager    the game manager object
     * @param playerNameText the Text element for the player's name
     * @param ageText        the Text element for the current age
     * @param turnText       the Text element for the current turn
     */
    static void displayHeaders(GameManager gameManager, Text playerNameText, Text ageText, Text turnText){
        playerNameText.setText(playerNameText.getText() + gameManager.getCurrentPlayer().getName());
        ageText.setText(ageText.getText() + gameManager.getCurrentAge() + "/3");
        turnText.setText(turnText.getText() + gameManager.getCurrentTurn() + "/6");
    }

    /**
     * Fills in all the player's information HBox with the player's wonder, victory points, treasure, military might, resources, and built cards.
     *
     * @param player                  the player object
     * @param playerWonderVBox        the VBox container for the player's wonder
     * @param playerVictoryPointsHBox the HBox container for the player's victory points
     * @param playerTreasureHBox      the HBox container for the player's treasure
     * @param playerMilitaryMightHBox the HBox container for the player's military might
     * @param playerResourcesHBox     the HBox container for the player's resources
     * @param playerBuiltCardsHBox    the HBox container for the player's built cards
     * @param builtCardsTitle         the Text element for the built cards title
     */
    static void displayPlayerProperties(Player player, VBox playerWonderVBox, HBox playerVictoryPointsHBox, HBox playerTreasureHBox,
                                        HBox playerMilitaryMightHBox, HBox playerResourcesHBox, HBox playerBuiltCardsHBox, Text builtCardsTitle){
        displayPlayerResources(player, playerResourcesHBox);
        displayPlayerBuiltCards(player, playerBuiltCardsHBox, builtCardsTitle);
        Text playerWonderLabel = new Text("Wonder:");
        playerWonderLabel.setFont(Font.font("Verdana", 15));
        Text playerWonderName = new Text(player.getWonder().getName());
        playerWonderName.setFont(Font.font("Verdana", 15));
        playerWonderName.setUnderline(true);


        HBox playerWonderHBox = new HBox();
        playerWonderHBox.setSpacing(5);
        String playerWonderDetailsBuilder = "Stage " + player.getWonder().getCurrentStage() +
                "/" +
                player.getWonder().getNumberOfStages();
        Text playerWonderDetails = new Text(playerWonderDetailsBuilder);
        playerWonderDetails.setFont(Font.font("Verdana", 15));

        playerWonderHBox.getChildren().add(playerWonderLabel);
        playerWonderHBox.getChildren().add(playerWonderName);

        playerWonderVBox.getChildren().add(playerWonderHBox);
        playerWonderVBox.getChildren().add(playerWonderDetails);

        StringBuilder playerWonderDetailsTooltipBuilder = new StringBuilder(player.getWonder().displayProducedResources() + System.lineSeparator());

        if(!player.getWonder().isComplete()){
            playerWonderDetailsTooltipBuilder.append(System.lineSeparator())
                    .append("For next stage :")
                    .append(System.lineSeparator());
            for(Resources resources : player.getWonder().getStages().get(player.getWonder().getCurrentStage()).getCost().elementSet()){
                playerWonderDetailsTooltipBuilder.append(resources)
                        .append(": ")
                        .append(player.getWonder().getStages().get(player.getWonder().getCurrentStage()).getCost().count(resources))
                        .append(System.lineSeparator());
            }
            playerWonderDetailsTooltipBuilder.append(System.lineSeparator())
                    .append("Next stage effect: ")
                    .append(player.getWonder().getStages().get(player.getWonder().getCurrentStage()).getEffect().toString());
        }

        playerWonderName.setPickOnBounds(true);
        Tooltip.install(playerWonderName, new Tooltip(playerWonderDetailsTooltipBuilder.toString()));

        Text playerVictoryPoints = new Text("Victory Points: " + player.getVictoryPoints());
        playerVictoryPoints.setFont(Font.font("Verdana", 15));
        playerVictoryPointsHBox.getChildren().add(playerVictoryPoints);

        Text playerTreasure = new Text("Treasure: " + player.getTreasure());
        playerTreasure.setFont(Font.font("Verdana", 15));
        playerTreasureHBox.getChildren().add(playerTreasure);

        Text playerMilitaryMight = new Text("Military Might: " + player.getMilitaryMight());
        playerMilitaryMight.setFont(Font.font("Verdana", 15));
        playerMilitaryMightHBox.getChildren().add(playerMilitaryMight);
    }

    /**
     * Fills in the playerBuiltCardsHBox with player's built cards by type and the total number of each type.
     *
     * @param player               the player object
     * @param playerBuiltCardsHBox the HBox container for the player's built cards
     * @param builtCardsTitle      the Text element for the built cards title
     */
    private static void displayPlayerBuiltCards(Player player, HBox playerBuiltCardsHBox, Text builtCardsTitle){
        VBox playerBuiltCardsVBox = new VBox();
        playerBuiltCardsVBox.setSpacing(20);
        playerBuiltCardsVBox.setAlignment(Pos.CENTER);
        HBox playerBuiltCardsHBoxL1 = new HBox();
        playerBuiltCardsHBoxL1.setSpacing(25);
        playerBuiltCardsHBoxL1.setAlignment(Pos.CENTER);
        HBox playerBuiltCardsHBoxL2 = new HBox();
        playerBuiltCardsHBoxL2.setSpacing(25);
        playerBuiltCardsHBoxL2.setAlignment(Pos.CENTER);
        playerBuiltCardsVBox.getChildren().add(playerBuiltCardsHBoxL1);
        playerBuiltCardsVBox.getChildren().add(playerBuiltCardsHBoxL2);
        playerBuiltCardsHBox.getChildren().add(playerBuiltCardsVBox);
        List<CardType> l1CardType = List.of(CardType.CIVILIAN_STRUCTURES, CardType.COMMERCIAL_STRUCTURES, CardType.GUILDS, CardType.MANUFACTURED_GOODS);
        for(CardType cardType : CardType.values()){
            String cardTypeAndCountBuilder = cardType.getLabel() + ": " +
                    player.getBuiltCardsPerType().count(cardType);

            Text cardTypeAndCount = new Text(cardTypeAndCountBuilder);
            cardTypeAndCount.setFont(Font.font("Verdana", 15));
            if(l1CardType.contains(cardType))
                playerBuiltCardsHBoxL1.getChildren().add(cardTypeAndCount);
            else
                playerBuiltCardsHBoxL2.getChildren().add(cardTypeAndCount);
        }

        StringBuilder builtCardsList = new StringBuilder();
        if(player.getBuiltCards().isEmpty()){
            builtCardsList.append("No built cards yet.");
        }else{
            for(Card builtCard : player.getBuiltCards()){
                builtCardsList.append("- ")
                        .append(builtCard.getName())
                        .append(" (")
                        .append(builtCard.getCardType().getLabel())
                        .append(")")
                        .append(System.lineSeparator());
            }
        }
        builtCardsTitle.setPickOnBounds(true);
        Tooltip.install(builtCardsTitle, new Tooltip(builtCardsList.toString()));
    }

    /**
     * Fills in the playerResourcesHBox with the player's resources by type and the total number of each type.
     *
     * @param player              the player object
     * @param playerResourcesHBox the HBox container for the player's resources
     */
    private static void displayPlayerResources(Player player, HBox playerResourcesHBox){
        playerResourcesHBox.setSpacing(50);
        for(Resources resources : Resources.values()){
            VBox resourceVBox = new VBox();
            resourceVBox.setSpacing(15);
            resourceVBox.setAlignment(Pos.CENTER);
            ImageView resourceImage = new ImageView(RESOURCES_IMAGE_MAP.get(resources));
            resourceImage.setPreserveRatio(true);
            resourceImage.setFitWidth((35));
            resourceImage.setPickOnBounds(true);
            Tooltip.install(resourceImage, new Tooltip(resources.name()));

            Text playerResources = new Text(String.valueOf(player.getResources().count(resources) + player.getCardResources().count(resources)));
            playerResources.setFont(Font.font("Verdana", 15));
            playerResources.setTextAlignment(TextAlignment.CENTER);
            playerResources.setPickOnBounds(true);

            String playerResourcesDetails = "Personal resources: " +
                    player.getResources().count(resources) +
                    System.lineSeparator() +
                    "Card resources: " +
                    player.getCardResources().count(resources);

            Tooltip.install(playerResources, new Tooltip(playerResourcesDetails));

            resourceVBox.getChildren().add(resourceImage);
            resourceVBox.getChildren().add(playerResources);

            playerResourcesHBox.getChildren().add(resourceVBox);
        }
    }

    /**
     * Plays a sound effect from the specified file.
     *
     * @param fileName the name of the sound effect file
     */
    public static void playSoundFromGame(String fileName){
        URL soundEffectFile = ControllerUtils.class.getResource(fileName);
        MusicPlayer musicPlayer = MusicPlayer.getInstance();
        musicPlayer.playSoundEffect(soundEffectFile.toString());
    }

    /**
     * Initializes the map of card images.
     *
     * @return the map of card images
     */
    private static ImmutableMap<CardType, Image> initImgCardMap(){
        Map<CardType, Image> imgMap = new LinkedHashMap<>();

        Image imgCivilianStructures
                = FileOpener.openImageFromSourceCard("card_CivilianStructures.png");
        imgMap.put(CardType.CIVILIAN_STRUCTURES, imgCivilianStructures);

        Image imgCommercialStructures
                = FileOpener.openImageFromSourceCard("card_CommercialStructures.png");
        imgMap.put(CardType.COMMERCIAL_STRUCTURES, imgCommercialStructures);

        Image imgManufacturedGoods
                = FileOpener.openImageFromSourceCard("card_ManufacturedGoods.png");
        imgMap.put(CardType.MANUFACTURED_GOODS, imgManufacturedGoods);

        Image imgMilitaryStructures
                = FileOpener.openImageFromSourceCard("card_MilitaryStructures.png");
        imgMap.put(CardType.MILITARY_STRUCTURE, imgMilitaryStructures);

        Image imgRawMaterials
                = FileOpener.openImageFromSourceCard("card_RawMaterials.png");
        imgMap.put(CardType.RAW_MATERIALS, imgRawMaterials);

        Image imgScientificStructures
                = FileOpener.openImageFromSourceCard("card_ScientificStructures.png");
        imgMap.put(CardType.SCIENTIFIC_STRUCTURES, imgScientificStructures);

        Image imgGuilds
                = FileOpener.openImageFromSourceCard("card_Guilds.png");
        imgMap.put(CardType.GUILDS, imgGuilds);

        return ImmutableMap.copyOf(imgMap);
    }

    /**
     * Initializes the map of resource images.
     *
     * @return the map of resource images
     */
    private static ImmutableMap<Resources, Image> initImgResourcesMap(){
        Map<Resources, Image> imgMap = new LinkedHashMap<>();

        Image imgStone
                = FileOpener.openImageFromSourceResources("resources_stone.png");
        imgMap.put(Resources.STONE, imgStone);

        Image imgClay
                = FileOpener.openImageFromSourceResources("resources_clay.png");
        imgMap.put(Resources.CLAY, imgClay);

        Image imgWood
                = FileOpener.openImageFromSourceResources("resources_wood.png");
        imgMap.put(Resources.WOOD, imgWood);

        Image imgOre
                = FileOpener.openImageFromSourceResources("resources_ore.png");
        imgMap.put(Resources.ORE, imgOre);

        Image imgGlass
                = FileOpener.openImageFromSourceResources("resources_glass.png");
        imgMap.put(Resources.GLASS, imgGlass);

        Image imgTextile
                = FileOpener.openImageFromSourceResources("resources_textile.png");
        imgMap.put(Resources.TEXTILE, imgTextile);

        Image imgPapyrus
                = FileOpener.openImageFromSourceResources("resources_papyrus.png");
        imgMap.put(Resources.PAPYRUS, imgPapyrus);

        return ImmutableMap.copyOf(imgMap);
    }

    /**
     * Initializes the map of action images.
     *
     * @return the map of action images
     */
    private static ImmutableMap<PlayerAction, Image> initImgActionMap(){
        Map<PlayerAction, Image> imgMap = new LinkedHashMap<>();

        Image imgActionBuildCard
                = FileOpener.openImageFromSourceGame("action_buildCard.png");
        imgMap.put(PlayerAction.BUILD_CARD, imgActionBuildCard);

        Image imgActionBuildStage
                = FileOpener.openImageFromSourceGame("action_buildStage.png");
        imgMap.put(PlayerAction.BUILD_STAGE, imgActionBuildStage);

        Image imgActionDiscard
                = FileOpener.openImageFromSourceGame("action_discard.png");
        imgMap.put(PlayerAction.DISCARD, imgActionDiscard);

        Image imgReturn
                = FileOpener.openImageFromSourceGame("action_return.png");
        imgMap.put(PlayerAction.RETURN_TO_SELECTION, imgReturn);

        return ImmutableMap.copyOf(imgMap);
    }
}
