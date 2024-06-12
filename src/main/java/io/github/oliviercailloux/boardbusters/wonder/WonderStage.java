package io.github.oliviercailloux.boardbusters.wonder;

import com.google.common.collect.Multiset;
import io.github.oliviercailloux.boardbusters.effect.Effect;
import io.github.oliviercailloux.boardbusters.resources.Resources;

public class WonderStage {

    private final Multiset<Resources> costs;

    private final Effect effect;
    
    /**
     * Creates a new WonderStage with the specified costs and effect.
     *
     * @param costs  the resources required to build the stage
     * @param effect the effect associated with the stage
     */
    public WonderStage(Multiset<Resources> costs, Effect effect){
        this.costs = costs;
        this.effect = effect;
    }

    public Multiset<Resources> getCost(){
        return costs;
    }

    public Effect getEffect(){
        return effect;
    }


}

