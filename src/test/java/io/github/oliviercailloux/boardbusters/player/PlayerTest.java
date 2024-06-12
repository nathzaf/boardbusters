package io.github.oliviercailloux.boardbusters.player;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import io.github.oliviercailloux.boardbusters.card.Card;
import io.github.oliviercailloux.boardbusters.card.CardManufacturedGoods;
import io.github.oliviercailloux.boardbusters.card.CardType;
import io.github.oliviercailloux.boardbusters.effect.military.EffectMilitaryMight;
import io.github.oliviercailloux.boardbusters.effect.resources.EffectProductResources;
import io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints.treasure.EffectEarnCoins;
import io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints.victorypoints.EffectVictoryPoints;
import io.github.oliviercailloux.boardbusters.resources.Resources;
import io.github.oliviercailloux.boardbusters.wonder.Wonder;
import io.github.oliviercailloux.boardbusters.wonder.WonderStage;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.github.oliviercailloux.boardbusters.resources.Resources.ORE;
import static io.github.oliviercailloux.boardbusters.resources.Resources.WOOD;
import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {
    // Create the wonder
    final Multiset<Resources> defaultCost = HashMultiset.create();
    List<WonderStage> rhodesStages = Arrays.asList(
            new WonderStage(Resources.of(ORE, ORE), new EffectMilitaryMight(2)),
            new WonderStage(Resources.of(ORE, ORE, ORE), new EffectVictoryPoints(2)),
            new WonderStage(Resources.of(ORE, ORE, ORE, ORE), new EffectEarnCoins(6))
    );
    Wonder rhodes = new Wonder("The Colossus of Rhodes", Resources.of(ORE), rhodesStages);


    @Test
    public void testPickCardInvalidCard(){
        Player player = new Player("Player name", rhodes);
        Card card1 = new CardManufacturedGoods("Card 1", 3, defaultCost, 2, new EffectProductResources(Resources.of(WOOD), true));
        Card card2 = new CardManufacturedGoods("Card 2", 3, defaultCost, 2, new EffectProductResources(Resources.of(WOOD), true));
        player.getInHandCards().add(card1);

        assertThrows(IllegalArgumentException.class, () -> player.setChosenCard(card2));
    }

    @Test
    public void testCanBuildCard(){
        Player playerMain = new Player("Player name", rhodes);
        Player playerLeft = new Player("Player left", rhodes);
        Player playerRight = new Player("Player right", rhodes);
        playerMain.setLeftNeighbour(playerLeft);
        playerMain.setRightNeighbour(playerRight);
        Card card = new CardManufacturedGoods("Card name", 3, HashMultiset.create(), 2, new EffectProductResources(Resources.of(WOOD, WOOD, WOOD), true));
        playerMain.getInHandCards().add(card);
        playerMain.setChosenCard(card);
        assertTrue(playerMain.canBuild(true));
    }

    @Test
    public void testAlreadyBuiltCard(){
        Player playerMain = new Player("Player name", rhodes);
        Card card1 = new CardManufacturedGoods("Card name", 3, HashMultiset.create(), 2, new EffectProductResources(Resources.of(WOOD, WOOD, WOOD), true));
        Card card2 = new CardManufacturedGoods("Card name", 3, HashMultiset.create(), 2, new EffectProductResources(Resources.of(WOOD, WOOD, WOOD), true));
        playerMain.getBuiltCards().add(card1);
        playerMain.getInHandCards().add(card2);
        playerMain.setChosenCard(card2);
        assertFalse(playerMain.canBuild(true));
    }

    @Test
    public void testCanBuildCardInsufficientResources(){
        Multiset<Resources> resourcesBag = HashMultiset.create();
        resourcesBag.addAll(Arrays.asList(Resources.values()));
        resourcesBag.addAll(Arrays.asList(Resources.values()));

        Player playerMain = new Player("Player name", rhodes);
        Player playerLeft = new Player("Player left", rhodes);
        Player playerRight = new Player("Player right", rhodes);
        playerMain.setLeftNeighbour(playerLeft);
        playerMain.setRightNeighbour(playerRight);
        Card card = new CardManufacturedGoods("Card name", 3, resourcesBag, 2, new EffectProductResources(Resources.of(WOOD, WOOD, WOOD), true));
        playerMain.getInHandCards().add(card);
        playerMain.setChosenCard(card);
        assertFalse(playerMain.canBuild(true));
    }

    @Test
    public void testNumberOfBuildCard(){
        Multiset<Resources> resourcesBag = HashMultiset.create();

        Player player = new Player("Player name", rhodes);

        assertEquals(0, player.getBuiltCardsPerType().count(CardType.MANUFACTURED_GOODS));

        CardManufacturedGoods addedCard = new CardManufacturedGoods("Glassworks", 3, resourcesBag, 1, new EffectProductResources(Resources.of(WOOD, WOOD, WOOD), true));

        List<Card> cardToAdd = new ArrayList<>();
        cardToAdd.add(addedCard);

        player.setInHandCards(cardToAdd);
        player.setChosenCard(addedCard);
        player.buildCard();
        assertEquals(1, player.getBuiltCardsPerType().count(CardType.MANUFACTURED_GOODS));

    }

//    @Test
//    public void testBuildCard() {
//        Player playerMain = new Player("Player name", rhodes);
//        Player playerLeft = new Player("Player left", rhodes);
//        Player playerRight = new Player("Player right", rhodes);
//        playerMain.setLeftNeighbour(playerLeft);
//        playerMain.setRightNeighbour(playerRight);
//        Card card = new CardManufacturedGoods("Card name", 1, new HashMap<>(), 2, new EffectProductResources(Map.of(Resources.WOOD, 1)));
//        playerMain.getInHandCards().add(card);
//
//        playerMain.pickCard(card);
//        playerMain.buildCard();
//
//        assertTrue(playerMain.getBuiltCards().contains(card));
//    }

//    @Test
//    public void testBuildCardNotBuilt() {
//        Player playerMain = new Player("Player name", rhodes);
//        Player playerLeft = new Player("Player left", rhodes);
//        Player playerRight = new Player("Player right", rhodes);
//        playerMain.setLeftNeighbour(playerLeft);
//        playerMain.setRightNeighbour(playerRight);
//        Card card = new CardManufacturedGoods("Card name", 1, new HashMap<>(), 2, new EffectProductResources(Map.of(Resources.WOOD, 1)));
//        playerMain.getInHandCards().add(card);
//
//        playerMain.pickCard(card);
//
//        assertFalse(playerMain.getBuiltCards().contains(card));
//    }

    @Test
    public void testPlayCard(){
        Player playerMain = new Player("Player name", rhodes);
        playerMain.setTreasure(10);
        Card card = new CardManufacturedGoods("Card name", 3, HashMultiset.create(), 2, new EffectProductResources(Resources.of(WOOD), true));
        card.getCost().add(WOOD, 2);
        playerMain.getCardResources().add(WOOD, 1);

        Player playerLeft = new Player("Player left", rhodes);
        playerLeft.getCardResources().add(WOOD, 2);
        playerMain.setLeftNeighbour(playerLeft);

        Player playerRight = new Player("Player right", rhodes);
        playerMain.setRightNeighbour(playerRight);

        playerMain.getInHandCards().add(card);
        playerMain.setChosenCard(card);
        playerMain.buildCard();

        assertEquals(8, playerMain.getTreasure());
        assertEquals(2, playerMain.getCardResources().count(WOOD));
        assertEquals(0, playerMain.getResources().count(WOOD));
    }

}
