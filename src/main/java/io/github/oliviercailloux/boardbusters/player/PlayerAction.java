package io.github.oliviercailloux.boardbusters.player;

public enum PlayerAction {

    RETURN_TO_SELECTION("Return to card selection"),

    BUILD_CARD("Build Card"),

    BUILD_STAGE("Build Stage"),

    DISCARD("Discard");

    private final String label;

    PlayerAction(String label){
        this.label = label;
    }

    public String getLabel(){
        return label;
    }
}
