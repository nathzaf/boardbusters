package io.github.oliviercailloux.boardbusters.effect.guild;

import io.github.oliviercailloux.boardbusters.card.CardType;
import io.github.oliviercailloux.boardbusters.effect.Effect;
import io.github.oliviercailloux.boardbusters.effect.EffectType;
import io.github.oliviercailloux.boardbusters.player.Player;

/**
 * This class represents the effect of guild shipowners.
 * It grants victory points to the player based on the number of raw material, manufactured goods,
 * and guild cards they have built.
 */
public class EffectGuildShipowners extends Effect {

    public EffectGuildShipowners(){
        super(EffectType.GUILD_SHIPOWNERS);
    }

    @Override
    public void applyEffect(Player player){
        if(player == null)
            throw new IllegalArgumentException("The player must not be null.");
        int victoryPointsGains = player.getBuiltCardsPerType().count(CardType.RAW_MATERIALS) + player.getBuiltCardsPerType().count(CardType.MANUFACTURED_GOODS) + player.getBuiltCardsPerType().count(CardType.GUILDS);
        player.increaseVictoryPoints(victoryPointsGains);
    }

    @Override
    public String toString(){
        return "Gaining 1 victory point for each of your raw material,\nmanufactured goods and scientific cards";
    }
}
