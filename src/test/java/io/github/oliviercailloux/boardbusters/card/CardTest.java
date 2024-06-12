package io.github.oliviercailloux.boardbusters.card;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import io.github.oliviercailloux.boardbusters.effect.military.EffectMilitaryMight;
import io.github.oliviercailloux.boardbusters.effect.resources.EffectProductResources;
import io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints.treasure.EffectEarnCoins;
import io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints.victorypoints.EffectVictoryPoints;
import io.github.oliviercailloux.boardbusters.player.Player;
import io.github.oliviercailloux.boardbusters.wonder.Wonder;
import io.github.oliviercailloux.boardbusters.wonder.WonderStage;
import io.github.oliviercailloux.boardbusters.resources.Resources;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.github.oliviercailloux.boardbusters.resources.Resources.*;
import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    public void testConstructor(){
        String name = "Card name";
        int minPlayers = 3;
        Multiset<Resources> cost = HashMultiset.create();
        cost.add(WOOD, 2);
        cost.add(STONE, 3);
        int age = 2;
        EffectProductResources effect = new EffectProductResources(Resources.of(WOOD), true);

        Card card = new CardManufacturedGoods(name, minPlayers, cost, age, effect);

        assertEquals(name, card.getName());
        assertEquals(minPlayers, card.getMinPlayers());
        assertEquals(cost, card.getCost());
        assertEquals(age, card.getAge());
        assertEquals(effect, card.getEffect());
    }

    @Test
    void wrongParametersCard(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new CardCommercialStructures("", 3,
                        HashMultiset.create(), 1,
                        new EffectProductResources(Resources.of(WOOD), true),
                        new ArrayList<>()));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new CardCommercialStructures("Card name", 3,
                        HashMultiset.create(), 0,
                        new EffectProductResources(Resources.of(WOOD), true),
                        new ArrayList<>()));
    }

    @Test
    public void testDisplayCardDetails(){
        String name = "Card name";
        int minPlayers = 3;
        Multiset<Resources> cost = HashMultiset.create();
        cost.add(WOOD, 2);
        cost.add(STONE, 3);
        int age = 2;
        EffectProductResources effect = new EffectProductResources(Resources.of(WOOD), true);

        Card card = new CardManufacturedGoods(name, minPlayers, cost, age, effect);

        // The problem here is that, depending on how we run the test,
        // the output is either expectedOutput1 or expectedOutput2.

        String expectedOutput1 = "Resources needed:" + System.lineSeparator() + "STONE: 3" +
                System.lineSeparator() + "WOOD: 2" + System.lineSeparator() + System.lineSeparator() +
                "Effect: " + System.lineSeparator() + "Produces: \nWOOD: 1\n\r\n";
        String expectedOutput2 = "Resources needed:" + System.lineSeparator() + "WOOD: 2" +
                System.lineSeparator() + "STONE: 3" + System.lineSeparator() + System.lineSeparator() +
                "Effect: " + System.lineSeparator() + "Produces: \nWOOD: 1\n\r\n";
        String actualOutput = card.displayCardDetails();
        assertTrue(actualOutput.equals(expectedOutput1) || actualOutput.equals(expectedOutput2));
    }

    @Test
    public void testChainedCards(){
        Multiset<Resources> cost1 = HashMultiset.create();
        cost1.add(WOOD, 0);
        cost1.add(STONE, 0);
        Multiset<Resources> cost2 = HashMultiset.create();
        cost2.add(WOOD, 10);
        cost2.add(STONE, 10);

        List<String> chainedCard1 = new ArrayList<>();
        chainedCard1.add("Card 2");
        List<String> chainedCard2 = new ArrayList<>();
        chainedCard2.add("Card 1");

        List<WonderStage> rhodesWonderStages = Arrays.asList(
                new WonderStage(Resources.of(ORE, ORE), new EffectMilitaryMight(2)),
                new WonderStage(Resources.of(ORE, ORE, ORE), new EffectVictoryPoints(2)),
                new WonderStage(Resources.of(ORE, ORE, ORE), new EffectEarnCoins(6))
        );
        Wonder rhodes = new Wonder("The Colossus of Rhodes", Resources.of(ORE), rhodesWonderStages);

        Card card1 = new CardCivilianStructures("Card 1", 3, cost1,
                1, new EffectVictoryPoints(1), chainedCard1);
        Card card2 = new CardCivilianStructures("Card 2", 3, cost2,
                1, new EffectVictoryPoints(1), chainedCard2);

        Player playerMain = new Player("Player name", rhodes);
        Player playerLeft = new Player("Player left", rhodes);
        Player playerRight = new Player("Player right", rhodes);
        playerMain.setLeftNeighbour(playerLeft);
        playerMain.setRightNeighbour(playerRight);

        playerMain.getInHandCards().add(card1);
        playerMain.getInHandCards().add(card2);

        playerMain.setChosenCard(card1);

        assertTrue(playerMain.canBuild(true));
    }

    @Test
    public void testNoChainedCards(){
        Multiset<Resources> cost1 = HashMultiset.create();
        Multiset<Resources> cost2 = HashMultiset.create();
        cost2.add(WOOD, 10);
        cost2.add(STONE, 10);

        List<String> chainedCard1 = new ArrayList<>();
        chainedCard1.add("Card 3");
        List<String> chainedCard2 = new ArrayList<>();
        chainedCard2.add("Card 1");

        List<WonderStage> rhodesWonderStages = Arrays.asList(
                new WonderStage(Resources.of(ORE, ORE), new EffectMilitaryMight(2)),
                new WonderStage(Resources.of(ORE, ORE, ORE), new EffectVictoryPoints(2)),
                new WonderStage(Resources.of(ORE, ORE, ORE, ORE), new EffectEarnCoins(6))
        );
        Wonder rhodes = new Wonder("The Colossus of Rhodes", Resources.of(ORE), rhodesWonderStages);

        Card card1 = new CardCivilianStructures("Card 1", 3, cost1,
                1, new EffectVictoryPoints(1), chainedCard1);
        Card card2 = new CardCivilianStructures("Card 2", 3, cost2,
                1, new EffectVictoryPoints(1), chainedCard2);

        Player playerMain = new Player("Player name", rhodes);
        Player playerLeft = new Player("Player left", rhodes);
        Player playerRight = new Player("Player right", rhodes);
        playerMain.setLeftNeighbour(playerLeft);
        playerMain.setRightNeighbour(playerRight);

        playerMain.getInHandCards().add(card1);
        playerMain.getInHandCards().add(card2);

        playerMain.setChosenCard(card2);
        assertFalse(playerMain.canBuild(true));
    }
}
