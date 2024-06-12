package io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints.treasure;

import io.github.oliviercailloux.boardbusters.card.CardType;
import io.github.oliviercailloux.boardbusters.effect.Effect;
import io.github.oliviercailloux.boardbusters.effect.EffectType;
import io.github.oliviercailloux.boardbusters.player.Player;

/**
 * @author pneveu
 * This class represent the effect of commercial cards that make the player win
 * a certain amount of coins, according to the number of a specific type of card, he and its neighbour have
 */
public class EffectEarnCoinsFromTypeOfCard extends Effect {

    private final CardType onWhichTypeOfCard;

    private final int amountOfGainsForEachCard;

    /**
     * Constructor of an effect that make the player earning coins
     * according to the number of onWhichTypeOfCard that he and his neighbour have.
     *
     * @throws IllegalArgumentException if the gain of coin is different from 1 or 2
     * @throws IllegalArgumentException if the type of card is not a manufactured or a raw material card
     */
    public EffectEarnCoinsFromTypeOfCard(CardType onWhichTypeOfCard, int amountOfGainsForEachCard){
        super(EffectType.EARN_COIN_FROM_TYPE_OF_CARD);
        if(onWhichTypeOfCard == null) throw new NullPointerException();
        if(!(amountOfGainsForEachCard == 1 || amountOfGainsForEachCard == 2))
            throw new IllegalArgumentException("Gain of coins of this effect can only be 1 or 2");
        if(!(onWhichTypeOfCard.equals(CardType.RAW_MATERIALS) || (onWhichTypeOfCard.equals(CardType.MANUFACTURED_GOODS)))){
            throw new IllegalArgumentException("This effect only applies on Manufactured Goods or Raw Materials");
        }
        this.onWhichTypeOfCard = onWhichTypeOfCard;
        this.amountOfGainsForEachCard = amountOfGainsForEachCard;
    }

    public void applyEffect(Player player){
        int coinGains = amountOfGainsForEachCard * (player.getBuiltCardsPerType().count(onWhichTypeOfCard) + player.getLeftNeighbour().getBuiltCardsPerType().count(onWhichTypeOfCard) + player.getRightNeighbour().getBuiltCardsPerType().count(onWhichTypeOfCard));
        player.increaseTreasure(coinGains);
    }

    @Override
    public String toString(){
        return "Earn coin according to you\nand your neighbours number of\n" + this.onWhichTypeOfCard.getLabel();
    }
}
