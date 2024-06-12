package io.github.oliviercailloux.boardbusters.card;

import com.google.common.collect.Multiset;
import io.github.oliviercailloux.boardbusters.effect.Effect;
import io.github.oliviercailloux.boardbusters.resources.Resources;

/**
 * These structures allow players to score points according to specific criteria.
 */
public class CardGuilds extends Card {

    public CardGuilds(String name, int minPlayers, Multiset<Resources> cost, int age, Effect effect){
        super(name, minPlayers, cost, age, effect, CardType.GUILDS);
    }
}
