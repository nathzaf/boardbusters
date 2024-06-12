package io.github.oliviercailloux.boardbusters.resources;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.Arrays;

public enum Resources {

    STONE,

    CLAY,

    WOOD,

    ORE,

    GLASS,

    TEXTILE,

    PAPYRUS;

    /**
     * Initialize a Multiset of resources
     *
     * @param resources a vararg containing the resources to be in that Multiset
     * @return a new Multiset containing resources
     */
    public static Multiset<Resources> of(Resources... resources){
        return HashMultiset.create(Arrays.asList(resources));
    }
}
