package io.github.oliviercailloux.boardbusters.effect.resources;

import io.github.oliviercailloux.boardbusters.effect.Effect;
import io.github.oliviercailloux.boardbusters.effect.EffectType;
import io.github.oliviercailloux.boardbusters.player.Player;
import io.github.oliviercailloux.boardbusters.resources.ScientificSymbol;

public class EffectScientificSymbol extends Effect {

    private final ScientificSymbol scientificSymbol;

    public EffectScientificSymbol(ScientificSymbol scientificSymbol){
        super(EffectType.SCIENTIFIC_SYMBOLS);
        this.scientificSymbol = scientificSymbol;
    }

    @Override
    public void applyEffect(Player player){
        throw new IllegalStateException("Apply effect can't be called on EffectScientificStructures");
    }

    @Override
    public String toString(){
        return "Adds 1 " + scientificSymbol.name() + ".";
    }

    public ScientificSymbol getScientificSymbol(){
        return scientificSymbol;
    }
}
