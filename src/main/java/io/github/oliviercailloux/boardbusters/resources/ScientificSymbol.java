package io.github.oliviercailloux.boardbusters.resources;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.Arrays;

public enum ScientificSymbol {

    TABLET,

    COMPASS,

    GEAR_WHEEL;

    /**
     * Initialize a Multiset of scientific symbols
     *
     * @param scientificSymbols a vararg containing the scientific symbols to be in that Multiset
     * @return a new Multiset containing scientific symbols
     */
    public static Multiset<ScientificSymbol> of(ScientificSymbol... scientificSymbols){
        return HashMultiset.create(Arrays.asList(scientificSymbols));
    }
}
