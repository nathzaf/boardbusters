package io.github.oliviercailloux.boardbusters.effect.guild;

import io.github.oliviercailloux.boardbusters.card.CardType;
import io.github.oliviercailloux.boardbusters.effect.Effect;
import io.github.oliviercailloux.boardbusters.effect.EffectType;
import io.github.oliviercailloux.boardbusters.player.Player;

/**
 * Effect that make the player earning victory points
 * according to the number of onWhichTypeOfCard his neighbour have
 * Note that it is only according to its neighbour and not himself
 */
public class EffectGuildEarnVictoryPoints extends Effect {

    private final CardType onWhichTypeOfCard;

    private final int amountOfVictoryPointsForEachCard;

    public EffectGuildEarnVictoryPoints(CardType onWhichTypeOfCard, int amountOfVictoryPointsForEachCard){
        super(EffectType.GUILD_EARN_VICTORY_POINTS);
        if(onWhichTypeOfCard == null) throw new NullPointerException();
        if(!(amountOfVictoryPointsForEachCard == 1 || amountOfVictoryPointsForEachCard == 2))
            throw new IllegalArgumentException("Gain of victory points of this effect can only be 1 or 2");
        this.onWhichTypeOfCard = onWhichTypeOfCard;
        this.amountOfVictoryPointsForEachCard = amountOfVictoryPointsForEachCard;
    }

    @Override
    public void applyEffect(Player player){
        if(player == null)
            throw new IllegalArgumentException("The player must not be null.");
        int victoryPointsGains = amountOfVictoryPointsForEachCard * (player.getLeftNeighbour().getBuiltCardsPerType().count(onWhichTypeOfCard) + player.getRightNeighbour().getBuiltCardsPerType().count(onWhichTypeOfCard));
        player.increaseVictoryPoints(victoryPointsGains);
    }

    @Override
    public String toString(){
        return "Gaining " + amountOfVictoryPointsForEachCard + "victory points \nfor each of your neighbours number of \n" + this.onWhichTypeOfCard.getLabel();
    }
}
