package io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import io.github.oliviercailloux.boardbusters.card.Card;
import io.github.oliviercailloux.boardbusters.card.CardManufacturedGoods;
import io.github.oliviercailloux.boardbusters.card.CardType;
import io.github.oliviercailloux.boardbusters.effect.military.EffectMilitaryMight;
import io.github.oliviercailloux.boardbusters.effect.resources.EffectProductResources;
import io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints.treasure.EffectEarnCoins;
import io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints.treasure.EffectEarnCoinsFromTypeOfCard;
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

public class EffectEarnCoinsFromTypeOfCardTest {
    final Multiset<Resources> defaultCost = HashMultiset.create();
    List<WonderStage> rhodesStages = Arrays.asList(
            new WonderStage(Resources.of(ORE, ORE), new EffectMilitaryMight(2)),
            new WonderStage(Resources.of(ORE, ORE, ORE), new EffectVictoryPoints(2)),
            new WonderStage(Resources.of(ORE, ORE, ORE, ORE), new EffectEarnCoins(6))
    );
    Wonder rhodes = new Wonder("The Colossus of Rhodes", Resources.of(ORE), rhodesStages);

    @Test
    void testApplyCoinGains(){
        Player playerMain = new Player("Player name", rhodes);
        Player playerLeft = new Player("Player left", rhodes);
        Player playerRight = new Player("Player right", rhodes);
        playerMain.setLeftNeighbour(playerLeft);
        playerMain.setRightNeighbour(playerRight);

        CardManufacturedGoods addedCard = new CardManufacturedGoods("Glassworks", 3, HashMultiset.create(), 1, new EffectProductResources(Resources.of(GLASS), true));

        List<Card> cardToAddMain = new ArrayList<>();
        cardToAddMain.add(addedCard);
        List<Card> cardToAddLeft = new ArrayList<>();
        cardToAddLeft.add(addedCard);

        playerMain.setInHandCards(cardToAddMain);
        playerMain.setChosenCard(addedCard);
        playerMain.buildCard();
        playerLeft.setInHandCards(cardToAddLeft);
        playerLeft.setChosenCard(addedCard);
        playerLeft.buildCard();

        EffectEarnCoinsFromTypeOfCard coinsGainsEffect = new EffectEarnCoinsFromTypeOfCard(CardType.MANUFACTURED_GOODS, 2);

        coinsGainsEffect.applyEffect(playerMain); // +2 *2 because curr player and one of its neighbour have one manufacturedgoods

        assertEquals(3 + 4, playerMain.getTreasure());

        String expectedOutput = """
                Earn coin according to you
                and your neighbours number of
                Manufactured Goods""";
        String actualOutput = coinsGainsEffect.toString();

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testExceptions(){
        assertThrows(IllegalArgumentException.class, () -> new EffectEarnCoinsFromTypeOfCard(CardType.MILITARY_STRUCTURE, 2));

        assertThrows(IllegalArgumentException.class, () -> new EffectEarnCoinsFromTypeOfCard(CardType.MILITARY_STRUCTURE, 3));
    }


}
