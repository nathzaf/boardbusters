package io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import io.github.oliviercailloux.boardbusters.card.Card;
import io.github.oliviercailloux.boardbusters.card.CardManufacturedGoods;
import io.github.oliviercailloux.boardbusters.card.CardType;
import io.github.oliviercailloux.boardbusters.effect.military.EffectMilitaryMight;
import io.github.oliviercailloux.boardbusters.effect.resources.EffectProductResources;
import io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints.treasure.EffectEarnCoins;
import io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints.victorypoints.EffectVictoryPoints;
import io.github.oliviercailloux.boardbusters.player.Player;
import io.github.oliviercailloux.boardbusters.wonder.Wonder;
import io.github.oliviercailloux.boardbusters.wonder.WonderStage;
import io.github.oliviercailloux.boardbusters.resources.Resources;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.github.oliviercailloux.boardbusters.resources.Resources.GLASS;
import static io.github.oliviercailloux.boardbusters.resources.Resources.ORE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EffectEarnCoinAndVictoryPointTest {
    final Multiset<Resources> defaultCost = HashMultiset.create();
    List<WonderStage> rhodesStages = Arrays.asList(
            new WonderStage(Resources.of(ORE, ORE), new EffectMilitaryMight(2)),
            new WonderStage(Resources.of(ORE, ORE, ORE), new EffectVictoryPoints(2)),
            new WonderStage(Resources.of(ORE, ORE, ORE, ORE), new EffectEarnCoins(6))
    );
    Wonder rhodes = new Wonder("The Colossus of Rhodes", Resources.of(ORE), rhodesStages);

    @Test
    void testApplyCoinGainsFromStages(){
        Player playerMain = new Player("Player name", rhodes);

        playerMain.getWonder().incrementCurrentStage();

        EffectEarnCoinAndVictoryPointFromStages coinsGainsEffect = new EffectEarnCoinAndVictoryPointFromStages(1, 2);

        coinsGainsEffect.applyEffect(playerMain); // +2 *2 cause 2 stages built

        assertEquals(3 + 2, playerMain.getTreasure());

        String expectedOutput = """
                Earn 2 coins for each stage you have built.
                Earn as well 1 victory points
                for each at the end of the game""";
        String actualOutput = coinsGainsEffect.toString();

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testApplyCoinGainsFromCards(){
        Player playerMain = new Player("Player name", rhodes);

        CardManufacturedGoods addedCard = new CardManufacturedGoods("Glassworks", 3, HashMultiset.create(), 1, new EffectProductResources(Resources.of(GLASS), true));

        List<Card> cardToAdd = new ArrayList<>();
        cardToAdd.add(addedCard);

        playerMain.setInHandCards(cardToAdd);
        playerMain.setChosenCard(addedCard);
        playerMain.buildCard();
        playerMain.getWonder().incrementCurrentStage();
        playerMain.getWonder().incrementCurrentStage();

        EffectEarnCoinAndVictoryPointFromCards coinsGainsEffect = new EffectEarnCoinAndVictoryPointFromCards(CardType.MANUFACTURED_GOODS, 1, 2);

        coinsGainsEffect.applyEffect(playerMain); // +2 *1 because one manufacture goods card

        assertEquals(3 + 2, playerMain.getTreasure());

        String expectedOutput = """
                Earn 2 coins for each card of type
                Manufactured Goods you have built.
                Earn as well 1 victory points
                for each at the end of the game""";
        String actualOutput = coinsGainsEffect.toString();

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testFromStagesExceptions(){
        assertThrows(IllegalArgumentException.class, () -> new EffectEarnCoinAndVictoryPointFromStages(-1, 2));

        assertThrows(IllegalArgumentException.class, () -> new EffectEarnCoinAndVictoryPointFromStages(1, -2));

        assertThrows(IllegalArgumentException.class, () -> new EffectEarnCoinAndVictoryPointFromStages(-1, -2));
    }

    @Test
    void testFromCardExceptions(){
        assertThrows(IllegalArgumentException.class, () -> new EffectEarnCoinAndVictoryPointFromCards(CardType.MANUFACTURED_GOODS, -1, 2));

        assertThrows(IllegalArgumentException.class, () -> new EffectEarnCoinAndVictoryPointFromCards(CardType.MANUFACTURED_GOODS, 1, -2));

    }
}
