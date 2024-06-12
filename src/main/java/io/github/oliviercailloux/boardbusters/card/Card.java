package io.github.oliviercailloux.boardbusters.card;

import com.google.common.collect.Multiset;
import io.github.oliviercailloux.boardbusters.effect.Effect;
import io.github.oliviercailloux.boardbusters.resources.Resources;

import java.util.Objects;

/**
 * A card represents structures.
 * There are 7 different type of structures, implemented by this class subclass
 */
public abstract class Card {

    private final String name;

    // Minimum number of player to be played
    private final int minPlayers;

    private final Multiset<Resources> cost;

    private final int age;

    private final Effect effect;

    private final CardType cardType;

    /**
     * Constructor of an abstract card.
     *
     * @param name       the name of the card, must not be empty
     * @param minPlayers the minimum number of players required to play the card
     * @param cost       the cost to use the card
     * @param age        the age of the card, must be between 1 and 3
     * @param effect     the effect of the card
     * @param cardType   the type of the card
     * @throws NullPointerException     if any of the parameters name, cost, or effect is null
     * @throws IllegalArgumentException if the name is empty, or if the age is not between 1 and 3,
     *                                  or if the minimum number of players is not between 3 and 7
     */
    public Card(String name, int minPlayers, Multiset<Resources> cost, int age, Effect effect, CardType cardType){
        if(name == null || cost == null || effect == null)
            throw new NullPointerException();
        if(age < 1 || age > 3)
            throw new IllegalArgumentException("The age must be between 1 and 3.");
        if(name.isEmpty())
            throw new IllegalArgumentException("Name cannot empty.");
        if(minPlayers < 3 || minPlayers > 7)
            throw new IllegalArgumentException("Numbers of player min must be between 3 and 7.");
        this.name = name;
        this.minPlayers = minPlayers;
        this.cost = cost;
        this.age = age;
        this.effect = effect;
        this.cardType = cardType;
    }

    /**
     * Returns a string representation of the card's details, including its cost and effect.
     *
     * @return a string representation of the card's details
     */
    public String displayCardDetails(){
        StringBuilder stringBuilder = new StringBuilder();
        displayCardCost(stringBuilder);
        stringBuilder.append("Effect: ")
                .append(System.lineSeparator())
                .append(getEffect().toString())
                .append(System.lineSeparator());
        return stringBuilder.toString();
    }

    /**
     * Displays the required cost to use the card.
     *
     * @param stringBuilder the StringBuilder to append the cost details to
     */
    public void displayCardCost(StringBuilder stringBuilder){
        if(cost.isEmpty())
            stringBuilder.append("No resources required.")
                    .append(System.lineSeparator());
        else{
            stringBuilder.append("Resources needed:")
                    .append(System.lineSeparator());
            for(Resources resource : cost.elementSet()){
                stringBuilder.append(resource.name())
                        .append(": ")
                        .append(cost.count(resource))
                        .append(System.lineSeparator());
            }
        }
        stringBuilder.append(System.lineSeparator());
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(name, card.name);
    }

    @Override
    public int hashCode(){
        return Objects.hash(name);
    }

    /**
     * Checks if the given card is chainable to this card.
     *
     * @param card: is the card to test if it is free to build thanks to the chain mechanism
     * @return True if it is chainable, False otherwise
     */
    public boolean isChainedTo(Card card){
        return false;
    }

    public String getName(){
        return name;
    }

    public int getMinPlayers(){
        return this.minPlayers;
    }

    public Multiset<Resources> getCost(){
        return cost;
    }

    public int getAge(){
        return age;
    }

    public Effect getEffect(){
        return this.effect;
    }

    public CardType getCardType(){
        return cardType;
    }
}
