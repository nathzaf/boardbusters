package io.github.oliviercailloux.boardbusters.card;

public enum CardType {

    CIVILIAN_STRUCTURES("Civilian Structures"),

    COMMERCIAL_STRUCTURES("Commercial Structures"),

    GUILDS("Guilds"),

    MANUFACTURED_GOODS("Manufactured Goods"),

    MILITARY_STRUCTURE("Military Structures"),

    RAW_MATERIALS("Raw Materials"),

    SCIENTIFIC_STRUCTURES("Scientific Structures");

    private final String label;

    CardType(String label){
        this.label = label;
    }

    public String getLabel(){
        return label;
    }
}
