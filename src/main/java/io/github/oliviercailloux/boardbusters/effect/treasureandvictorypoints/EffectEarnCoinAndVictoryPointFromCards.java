package io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints;

import io.github.oliviercailloux.boardbusters.card.CardType;
import io.github.oliviercailloux.boardbusters.effect.Effect;
import io.github.oliviercailloux.boardbusters.effect.EffectType;
import io.github.oliviercailloux.boardbusters.player.Player;

/**
 * Effect that make the player earning coins (and victory points at the end of the game)
 * according to the number of card of a specific type he has built
 */
public class EffectEarnCoinAndVictoryPointFromCards extends Effect {

    private final CardType onWhichTypeOfCard;

    private final int victoryPointsForEach;

    private final int coinsForEach;

    public EffectEarnCoinAndVictoryPointFromCards(CardType onWhichTypeOfCard, int victoryPointsForEach, int coinsForEach){
        super(EffectType.EARN_COIN_AND_VICTORY_POINT_FROM_CARDS);
        if(victoryPointsForEach < 0 || coinsForEach < 0)
            throw new IllegalArgumentException("Amounts to earn must be positive");
        this.onWhichTypeOfCard = onWhichTypeOfCard;
        this.victoryPointsForEach = victoryPointsForEach;
        this.coinsForEach = coinsForEach;
    }

    @Override
    public void applyEffect(Player player){
        if(player == null)
            throw new IllegalArgumentException("The player must not be null.");
        int coinsGains = coinsForEach * player.getBuiltCardsPerType().count(onWhichTypeOfCard);
        player.increaseTreasure(coinsGains);
    }

    public void applyEffectForVictoryPoints(Player player){
        if(player == null)
            throw new IllegalArgumentException("The player must not be null.");
        int victoryPointsGains = victoryPointsForEach * player.getBuiltCardsPerType().count(onWhichTypeOfCard);
        player.increaseVictoryPoints(victoryPointsGains);
    }

    @Override
    public String toString(){
        return "Earn " + coinsForEach + " coins for each card of type\n" + this.onWhichTypeOfCard.getLabel() + " you have built.\nEarn as well " + victoryPointsForEach + " victory points\nfor each at the end of the game";
    }
}
