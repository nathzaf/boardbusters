package io.github.oliviercailloux.boardbusters.player;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import io.github.oliviercailloux.boardbusters.card.Card;
import io.github.oliviercailloux.boardbusters.card.CardScientificStructures;
import io.github.oliviercailloux.boardbusters.card.CardType;
import io.github.oliviercailloux.boardbusters.effect.Effect;
import io.github.oliviercailloux.boardbusters.effect.EffectType;
import io.github.oliviercailloux.boardbusters.effect.resources.EffectScientificSymbol;
import io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints.EffectEarnCoinAndVictoryPointFromCards;
import io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints.EffectEarnCoinAndVictoryPointFromStages;
import io.github.oliviercailloux.boardbusters.resources.Resources;
import io.github.oliviercailloux.boardbusters.resources.ScientificSymbol;
import io.github.oliviercailloux.boardbusters.wonder.Wonder;
import io.github.oliviercailloux.boardbusters.wonder.WonderStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class Player {

    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(Player.class);

    private final String name;

    private final Wonder wonder;

    private List<Card> inHandCards;

    private final List<Card> builtCards;

    private final Multiset<Effect> effectsToApplyAtEndOfGame;

    private final Multiset<CardType> builtCardsPerType;

    private Card chosenCard;

    private int victoryPoints;

    private int treasure;

    private int militaryMight;

    private final Multiset<Resources> resources;

    private Multiset<Resources> cardResources;

    private Player leftNeighbour;

    private Player rightNeighbour;

    private final Set<Resources> leftNeighbourBenefits;

    private final Set<Resources> rightNeighbourBenefits;

    private final Multiset<ScientificSymbol> scientificSymbols;

    /**
     * Creates a new player with the given name and wonder.
     *
     * @param name   the name of the player
     * @param wonder the wonder associated with the player
     * @throws NullPointerException     if either name or wonder is null
     * @throws IllegalArgumentException if the name is empty
     */
    public Player(String name, Wonder wonder){
        if(name == null || wonder == null) throw new NullPointerException();
        if(name.isEmpty()) throw new IllegalArgumentException("Name can't be empty.");
        this.name = name;
        this.wonder = wonder;
        this.treasure = 3;
        this.militaryMight = 0;
        this.inHandCards = new ArrayList<>();
        this.builtCards = new ArrayList<>();
        this.victoryPoints = 0;
        this.effectsToApplyAtEndOfGame = HashMultiset.create();
        this.resources = HashMultiset.create();
        this.cardResources = HashMultiset.create();
        this.scientificSymbols = HashMultiset.create();
        this.leftNeighbourBenefits = new HashSet<>();
        this.rightNeighbourBenefits = new HashSet<>();
        this.builtCardsPerType = HashMultiset.create();
        this.militaryMight = 0;
    }

    /**
     * Check if the chosen card or the next stage is buildable, either with the player's own resources or by buying from a single neighbor.
     *
     * @param forCard indicates if it's for a card or for a stage
     * @return true if it can be built, false otherwise
     */
    public boolean canBuild(boolean forCard){
        if(forCard){
            if(isChainedToBuiltCard(chosenCard))
                return true;
            else if(builtCards.contains(chosenCard)) // a same card cannot be build twice
                return false;
        }else if(wonder.isComplete()) return false;
        Multiset<Resources> resourcesBag = forCard ? chosenCard.getCost() :
                wonder.getStages().get(wonder.getCurrentStage()).getCost();
        for(Resources resource : resourcesBag.elementSet()){
            int neededResource = resourcesBag.count(resource);
            int playerResource = resources.count(resource) + cardResources.count(resource);
            int leftNeighbourResource = leftNeighbour.getCardResources().count(resource);
            int rightNeighbourResource = rightNeighbour.getCardResources().count(resource);
            if(playerResource + rightNeighbourResource < neededResource && playerResource + leftNeighbourResource < neededResource)
                return false;
        }
        return treasure >= 2 * requiredBuyingResources(forCard) || canBuildAlone(forCard);
    }

    /**
     * Calculates the buy need from which neighbour to fulfill the required resources.
     *
     * @param forCard indicates if it's for a card or for a stage
     * @return an Optional containing the BuyNeed object if buying from a neighbour is required,
     * or an empty Optional if no neighbour buying is necessary or possible.
     */
    public Optional<BuyNeed> calculateBuyNeedFromWhichNeighbour(boolean forCard){
        if(forCard){
            if(isChainedToBuiltCard(chosenCard))
                return Optional.empty();
        }
        Multiset<Resources> resourcesBag = forCard ? chosenCard.getCost() :
                wonder.getStages().get(wonder.getCurrentStage()).getCost();
        for(Resources resource : resourcesBag.elementSet()){
            int neededResource = resourcesBag.count(resource);
            int playerResource = resources.count(resource) + cardResources.count(resource);
            int leftNeighbourResource = leftNeighbour.getCardResources().count(resource);
            int rightNeighbourResource = rightNeighbour.getCardResources().count(resource);
            if(playerResource >= neededResource) return Optional.empty();
            if(playerResource + leftNeighbourResource >= neededResource)
                return Optional.of(new BuyNeed(leftNeighbour, (neededResource - playerResource) * 2));
            else if(playerResource + rightNeighbourResource >= neededResource){
                return Optional.of(new BuyNeed(rightNeighbour, (neededResource - playerResource) * 2));
            }
        }
        return Optional.empty();
    }

    /**
     * Check if the chosen card or the next stage is buildable using only the player's resources (without buying).
     *
     * @param forCard indicates if it's for a card or for a stage
     * @return true if it can be built, false otherwise
     */
    private boolean canBuildAlone(boolean forCard){
        Multiset<Resources> resourcesBag = forCard ? chosenCard.getCost() :
                wonder.getStages().get(wonder.getCurrentStage()).getCost();
        for(Resources resource : resourcesBag.elementSet()){
            int neededResource = resourcesBag.count(resource);
            int playerResource = resources.count(resource) + cardResources.count(resource);
            if(playerResource < neededResource) return false;
        }
        return true;
    }

    /**
     * Attempt to build the chosen card. Increments numberOfBuiltCards
     *
     * @throws IllegalStateException If the player is unable to build the chosen card.
     */
    public void buildCard(){
        if(!canBuild(true))
            throw new IllegalStateException("Unable to build the card.");
        if(!isChainedToBuiltCard(chosenCard)) // Build is free if it's a chained card
            processCost(true);

        LOGGER.info("{} built {} (free by chains : {}).", name, chosenCard.getName(), isChainedToBuiltCard(chosenCard));
        // Processing effect of the card
        if(chosenCard.getEffect().getEffectType().isAtEnd()){
            effectsToApplyAtEndOfGame.add(chosenCard.getEffect());
        }else if(chosenCard.getEffect().getEffectType().isApplyAndAtEnd()){
            chosenCard.getEffect().applyEffect(this);
            effectsToApplyAtEndOfGame.add(chosenCard.getEffect());
        }else if(chosenCard instanceof CardScientificStructures){
            scientificSymbols.add(((EffectScientificSymbol) chosenCard.getEffect()).getScientificSymbol());
        }else{
            chosenCard.getEffect().applyEffect(this);
            LOGGER.info("Card {} effect applied to {} : {}", chosenCard.getName(), name, chosenCard.getEffect().toString());
        }
        inHandCards.remove(chosenCard);
        builtCards.add(chosenCard);
        builtCardsPerType.add(chosenCard.getCardType());
    }

    /**
     * Increases the player's treasure by the specified amount.
     *
     * @param treasureGains the amount of treasure to increase
     * @throws IllegalArgumentException if treasureGains is negative
     */
    public void increaseTreasure(int treasureGains){
        if(treasureGains < 0)
            throw new IllegalArgumentException("Gained coins can't be negative.");
        this.treasure += treasureGains;
        LOGGER.info("{}'s treasure increased by {}, now {}.", name, treasureGains, treasure);
    }

    /**
     * Increases the player's victory points by the specified amount.
     *
     * @param victoryPointGains the amount of victory points to increase
     * @throws IllegalArgumentException if victoryPointGains is negative
     */
    public void increaseVictoryPoints(int victoryPointGains){
        if(victoryPointGains < 0)
            throw new IllegalArgumentException("Gained victory points can't be negative.");
        this.victoryPoints += victoryPointGains;
        LOGGER.info("{}'s victory points increased by {}, now {}.", name, victoryPointGains, victoryPoints);
    }

    /**
     * Discard the current in-hand card. Adds 3 to the player's treasure and removes the card from their hand.
     */
    public void discardCard(){
        inHandCards.remove(chosenCard);
        LOGGER.info("{} discarded {}.", name, chosenCard.getName());
        increaseTreasure(3);
    }

    /**
     * Process buying a specified resource from a neighbor.
     * The player buy from the left neighbor first, then from the right neighbor if the left one can't afford.
     * The player's treasure is reduced by 2 for each bought resource.
     *
     * @param resource                  The resource to be bought.
     * @param requiredNeighbourResource The quantity of the resource to be bought.
     * @throws IllegalStateException if the neighbors do not have enough of the specified resource.
     * @throws NullPointerException  if resource is null
     */
    private void buyResourceFromNeighbour(Resources resource, int requiredNeighbourResource){
        if(resource == null) throw new NullPointerException();
        Player resourceProvidingNeighbour;

        if(leftNeighbour.getCardResources().count(resource) >= requiredNeighbourResource)
            resourceProvidingNeighbour = leftNeighbour;
        else if(rightNeighbour.getCardResources().count(resource) >= requiredNeighbourResource)
            resourceProvidingNeighbour = rightNeighbour;
        else
            throw new IllegalStateException("None of both neighbour have the required resources.");
        treasure -= 2 * requiredNeighbourResource;
        resourceProvidingNeighbour.setTreasure(resourceProvidingNeighbour.getTreasure() + 2 * requiredNeighbourResource);
        LOGGER.info("{} bought resources to {} for {} coins.", name, resourceProvidingNeighbour.getName(), requiredNeighbourResource * 2);
    }

    /**
     * Process the cost of the chosen card or the next stage.
     * Take into account the player's resources, both personal and from cards.
     * If necessary, buys resources from the neighbors.
     *
     * @param forCard indicates if it's for a card or for a stage
     */
    private void processCost(boolean forCard){
        Multiset<Resources> resourcesBag = forCard ? chosenCard.getCost() :
                wonder.getStages().get(wonder.getCurrentStage()).getCost();
        for(Resources resource : resourcesBag.elementSet()){
            int neededResource = resourcesBag.count(resource);
            int playerResource = resources.count(resource) + cardResources.count(resource);
            if(playerResource < neededResource)
                buyResourceFromNeighbour(resource, neededResource - playerResource);
        }
    }

    /**
     * Return the total quantity of resources that the player would need to buy to build the card or a wonder's stage.
     *
     * @param forCard indicates if it's for a card or for a stage
     * @return The total quantity of resources that would need to be bought.
     */
    private int requiredBuyingResources(boolean forCard){
        int requiredResources = 0;
        Multiset<Resources> resourcesBag = forCard ? chosenCard.getCost() :
                wonder.getStages().get(wonder.getCurrentStage()).getCost();
        for(Resources resource : resourcesBag.elementSet()){
            int neededResource = resourcesBag.count(resource);
            int playerResource = resources.count(resource) + cardResources.count(resource);
            if(playerResource < neededResource)
                requiredResources += neededResource - playerResource;
        }
        return requiredResources;
    }

    /**
     * Build the next stage of the wonder.
     *
     * @throws IllegalStateException if it's not buildable
     */
    public void buildStage(){
        if(!canBuild(false))
            throw new IllegalStateException("Unable to build the stage.");
        processCost(false);
        wonder.incrementCurrentStage();
        LOGGER.info("{} built the stage {} of its wonder.", name, wonder.getCurrentStage());
        applyStageEffect();
        inHandCards.remove(chosenCard);
    }

    public boolean isChainedToBuiltCard(Card card){
        for(Card builtCard : builtCards) if(builtCard.isChainedTo(card)) return true;
        return false;
    }

    /**
     * Apply the effect of the build stage of the wonder.
     */
    private void applyStageEffect(){
        WonderStage builtStage = wonder.getStages().get(wonder.getCurrentStage() - 1);
        builtStage.getEffect().applyEffect(this);
        LOGGER.info("{}'s stage effect applied : {}", name, wonder.getStages().get(wonder.getCurrentStage() - 1).getEffect().toString());
    }

    /**
     * This method adds commercial benefits after the commercial benefits effect.
     * It separates two cases, each testing if this.commercialBenefits already contains benefits with a neighbour.
     *
     * @param onLeftNeighbour   the left neighbour
     * @param onRightNeighbour  the right neighbour
     * @param onWhichResources: resources on which the player will gain benefits with the corresponding neighbours
     * @throws IllegalArgumentException if both onLeftNeighbour and onRightNeighbour are false, or if onWhichResources is empty
     * @throws NullPointerException     if onWhichResources is null
     */
    public void addCommercialBenefits(boolean onLeftNeighbour, boolean onRightNeighbour, Set<Resources> onWhichResources){
        if(onWhichResources == null)
            throw new NullPointerException("onWhichResources can't be null");
        if(onWhichResources.isEmpty())
            throw new IllegalArgumentException("onWhichResources can't be empty");
        if(!onLeftNeighbour && !onRightNeighbour)
            throw new IllegalArgumentException("At least one of both neighbours must be true");

        if(onLeftNeighbour) leftNeighbourBenefits.addAll(onWhichResources);
        if(onRightNeighbour) rightNeighbourBenefits.addAll(onWhichResources);
    }

    /**
     * Proceeds to the battle between the player and their neighbors for a given age.
     *
     * @param age The age for which the battle is taking place.
     * @throws IllegalArgumentException if the age is not between 1 and 3
     */
    public StringBuilder processBattle(int age){
        if(age < 1 || age > 3)
            throw new IllegalArgumentException("The age must be between 1 and 3.");
        StringBuilder battleStringBuilder = new StringBuilder();
        processBattle(age, leftNeighbour, battleStringBuilder);
        processBattle(age, rightNeighbour, battleStringBuilder);
        battleStringBuilder.append(name)
                .append(" has now ")
                .append(victoryPoints)
                .append(" victory points.");
        LOGGER.info(battleStringBuilder.toString());
        return battleStringBuilder;
    }

    /**
     * Process the battle between the player and one neighbour.
     * Compare the military might of the player and the neighbor, and updates victory points accordingly.
     * Display the outcome of the battle.
     *
     * @param age       The age of the game.
     * @param neighbour The neighbour to fight.
     * @throws NullPointerException if neighbour is null
     */
    private void processBattle(int age, Player neighbour, StringBuilder battleStringBuilder){
        if(neighbour == null)
            throw new NullPointerException();
        displayBattle(neighbour, battleStringBuilder);
        if(militaryMight > neighbour.getMilitaryMight()){
            victoryPoints = victoryPoints + winBattleValue(age);
            displayWinner(age, battleStringBuilder);
        }else if(militaryMight < neighbour.getMilitaryMight()){
            victoryPoints = Math.max(0, victoryPoints - winBattleValue(age));
            displayLoser(battleStringBuilder);
        }else
            battleStringBuilder.append("Draw!")
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
    }

    /**
     * Display the announcement of a battle between two players.
     *
     * @param neighbour The player's opponent in the battle.
     * @throws NullPointerException if neighbour is null
     */
    private void displayBattle(Player neighbour, StringBuilder battleStringBuilder){
        if(neighbour == null)
            throw new NullPointerException();
        battleStringBuilder.append(name)
                .append(" (")
                .append(militaryMight)
                .append(")")
                .append(" is now fighting ")
                .append(neighbour.getName())
                .append(" (")
                .append(neighbour.getMilitaryMight())
                .append(")")
                .append(System.lineSeparator());
    }

    /**
     * Display the announcement that the player has won the battle, along with the associated earning of victory points and the player's new point total.
     *
     * @param age The age of the game.
     * @throws IllegalArgumentException if the age is not between 1 and 3
     */
    private void displayWinner(int age, StringBuilder battleStringBuilder){
        if(age < 1 || age > 3)
            throw new IllegalArgumentException("The age must be between 1 and 3.");
        battleStringBuilder.append(name)
                .append(" won the battle!")
                .append(System.lineSeparator())
                .append(System.lineSeparator());
    }

    /**
     * Announce that the player lost the battle, with associated loss of victory points and his new amount
     */
    private void displayLoser(StringBuilder battleStringBuilder){
        battleStringBuilder.append(name)
                .append(" got defeated.")
                .append(System.lineSeparator())
                .append(System.lineSeparator());
    }

    /**
     * Calculate and returns the value of victory points earned in case of victory based on the specified age.
     *
     * @param age The age of the game.
     * @return The value of victory points earned.
     * @throws IllegalArgumentException if the age is not between 1 and 3
     */
    private static int winBattleValue(int age){
        return switch(age){
            case 1 -> 1;
            case 2 -> 3;
            case 3 -> 5;
            default -> throw new IllegalArgumentException("The age must be between 1 and 3.");
        };
    }

    /**
     * Process Victory Points gains mechanism according to nbr of scientific cards and number
     * of different symbols
     */
    public void addVictoryPointsAccordingToScientificCards(){
        int victoryPointsGains = 0;
        int tabletCount = 0;
        int compassCount = 0;
        int gearWheelCount = 0;

        tabletCount += scientificSymbols.count(ScientificSymbol.TABLET);
        compassCount += scientificSymbols.count(ScientificSymbol.COMPASS);
        gearWheelCount += scientificSymbols.count(ScientificSymbol.GEAR_WHEEL);

        victoryPointsGains += Math.pow(tabletCount, 2);
        victoryPointsGains += Math.pow(compassCount, 2);
        victoryPointsGains += Math.pow(gearWheelCount, 2);

        if(tabletCount != 0 && compassCount != 0 && gearWheelCount != 0) victoryPointsGains += 7;

        increaseTreasure(victoryPointsGains);
    }

    /**
     * Apply effects that give victory points at the end of the game
     */
    public void addVictoryPointAccordingToEffectAtEndOfGame(){

        for(Effect effect : effectsToApplyAtEndOfGame){
            if(effect.getEffectType().equals(EffectType.EARN_COIN_AND_VICTORY_POINT_FROM_STAGES)){
                ((EffectEarnCoinAndVictoryPointFromStages) effect).applyEffectForVictoryPoints(this);
            }else if(effect.getEffectType().equals(EffectType.EARN_COIN_AND_VICTORY_POINT_FROM_CARDS)){
                ((EffectEarnCoinAndVictoryPointFromCards) effect).applyEffectForVictoryPoints(this);
            }else effect.applyEffect(this);
        }

    }

    /**
     * Displays the resources of the player, including personal resources and resources from cards.
     *
     * @return a string representation of the player's resources
     */
    public String displayPlayerResources(){
        StringBuilder playerResource = new StringBuilder(name + "'s resource [total (personal+card)]:" + System.lineSeparator());
        for(Resources resource : Resources.values()){
            playerResource.append(resource.name())
                    .append(": ")
                    .append(resources.count(resource) + cardResources.count(resource))
                    .append(" (")
                    .append(resources.count(resource))
                    .append("+")
                    .append(cardResources.count(resource))
                    .append(")")
                    .append(System.lineSeparator());
        }
        playerResource.append(System.lineSeparator());
        for(ScientificSymbol scientificSymbol : ScientificSymbol.values()){
            playerResource.append(scientificSymbol.name())
                    .append(": ")
                    .append(scientificSymbols.count(scientificSymbol))
                    .append(System.lineSeparator());
        }
        return playerResource.toString();
    }

    public Wonder getWonder(){
        return wonder;
    }

    public String getName(){
        return name;
    }

    public List<Card> getInHandCards(){
        return inHandCards;
    }

    public void setInHandCards(List<Card> inHandCards){
        this.inHandCards = inHandCards;
    }

    public int getVictoryPoints(){
        return victoryPoints;
    }

    public void setVictoryPoints(int victoryPoints){
        this.victoryPoints = victoryPoints;
    }

    public int getMilitaryMight(){
        return militaryMight;
    }

    public void setMilitaryMight(int militaryMight){
        this.militaryMight = militaryMight;
    }

    public Multiset<Resources> getResources(){
        return resources;
    }

    public Multiset<ScientificSymbol> getScientificSymbols(){
        return this.scientificSymbols;
    }

    public int getTreasure(){
        return this.treasure;
    }

    public Card getChosenCard(){
        return chosenCard;
    }

    public void setChosenCard(Card card){
        if(!inHandCards.contains(card)) throw new IllegalArgumentException("The chosen card is not in hand");
        chosenCard = card;
        LOGGER.info("{} chose {} : {}", name, chosenCard.getName(), chosenCard.displayCardDetails());
    }

    public void setTreasure(int treasure){
        this.treasure = treasure;
    }

    public void setLeftNeighbour(Player leftNeighbour){
        this.leftNeighbour = leftNeighbour;
    }

    public Player getLeftNeighbour(){
        return this.leftNeighbour;
    }

    public void setRightNeighbour(Player rightNeighbour){
        this.rightNeighbour = rightNeighbour;
    }

    public Player getRightNeighbour(){
        return this.rightNeighbour;
    }

    public Set<Resources> getLeftNeighbourBenefits(){
        return leftNeighbourBenefits;
    }

    public Set<Resources> getRightNeighbourBenefits(){
        return rightNeighbourBenefits;
    }

    public Multiset<Resources> getCardResources(){
        return cardResources;
    }

    public void setCardResources(Multiset<Resources> cardResources){
        this.cardResources = cardResources;
    }

    public List<Card> getBuiltCards(){
        return builtCards;
    }

    public Multiset<CardType> getBuiltCardsPerType(){
        return builtCardsPerType;
    }

    public Multiset<Effect> getEffectToApplyAtEndOfGame(){
        return this.effectsToApplyAtEndOfGame;
    }
}
