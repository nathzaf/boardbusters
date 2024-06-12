package io.github.oliviercailloux.boardbusters.effect;

public enum EffectType {

    COMMERCIAL_BENEFITS(false, false),

    EARN_COIN_AND_VICTORY_POINT_FROM_CARDS(false, true),

    EARN_COIN_AND_VICTORY_POINT_FROM_STAGES(false, true),

    EARN_COINS(false, false),

    EARN_COIN_FROM_TYPE_OF_CARD(false, false),

    GUILD_BUILDERS(true, false),

    GUILD_DECORATORS(true, false),

    GUILD_EARN_VICTORY_POINTS(true, false),

    GUILD_SCIENTISTS(false, false),

    GUILD_SHIPOWNERS(true, false),

    MILITARY_MIGHT(false, false),

    PRODUCT_RESOURCES(false, false),

    SCIENTIFIC_SYMBOLS(false, false),

    VICTORY_POINTS(false, false);

    private final boolean atEnd;

    private final boolean applyAndAtEnd;

    EffectType(boolean atEnd, boolean applyAndAtEnd){
        if(atEnd && applyAndAtEnd) throw new IllegalStateException("Both can't be true");
        this.atEnd = atEnd;
        this.applyAndAtEnd = applyAndAtEnd;
    }

    public boolean isAtEnd(){
        return atEnd;
    }

    public boolean isApplyAndAtEnd(){
        return applyAndAtEnd;
    }
}
