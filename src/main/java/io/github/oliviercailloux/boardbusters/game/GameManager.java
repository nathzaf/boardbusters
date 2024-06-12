package io.github.oliviercailloux.boardbusters.game;

import io.github.oliviercailloux.boardbusters.card.Card;
import io.github.oliviercailloux.boardbusters.player.Player;
import io.github.oliviercailloux.boardbusters.wonder.Wonder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class GameManager {

    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class);

    private final Set<Player> players;

    private final List<Card> fullDeck;

    private final Set<Wonder> wonders;

    private final int numberOfPlayers;

    private int currentAge;

    private int currentTurn;

    private Player currentPlayer;

    private Iterator<Player> playerIterator;

    /**
     * Constructs a GameManager object with the specified number of players.
     *
     * @param numberOfPlayers the number of players in the game
     * @throws IllegalArgumentException if the number of players is not between 3 and 7
     */
    public GameManager(Integer numberOfPlayers){
        if(numberOfPlayers < 3 || numberOfPlayers > 7)
            throw new IllegalArgumentException("Number of players must be between 3 and 7.");
        this.numberOfPlayers = numberOfPlayers;
        this.players = new LinkedHashSet<>();
        this.fullDeck = new ArrayList<>(ObjectsInitializer.generateCards());
        this.wonders = new LinkedHashSet<>(ObjectsInitializer.generateWonders());
        LOGGER.info("Created a game with {} players.", numberOfPlayers);
    }

    /**
     * Sets up the game by initializing the wonders and players.
     *
     * @param playersNameList the list of names for the players
     * @throws IllegalStateException if there are not enough wonders for the players
     */
    public void setupGame(List<String> playersNameList){
        if(wonders.size() < numberOfPlayers)
            throw new IllegalStateException("There must be at least as many wonders as there are players.");

        Iterator<Wonder> wonderIterator = wonders.iterator();
        for(int i = 0; i < numberOfPlayers; i++){
            Wonder wonder = wonderIterator.next();
            players.add(new Player(playersNameList.get(i), wonder));
            LOGGER.info("Player {} : {} with {} Wonder: {}", i + 1, playersNameList.get(i), wonder.getName(), wonder.displayProducedResources());
        }

        List<Player> playerList = new ArrayList<>(this.players);
        for(int i = 0; i < numberOfPlayers; i++){
            playerList.get(i).setLeftNeighbour(playerList.get(i == 0 ? numberOfPlayers - 1 : i - 1));
            playerList.get(i).setRightNeighbour(playerList.get(i == numberOfPlayers - 1 ? 0 : i + 1));
            playerList.get(i).setCardResources(playerList.get(i).getWonder().getProducedResources());
            LOGGER.info("{}'s left neighbour set to {} and right neighbour set to {}.", playerList.get(i).getName(),
                    playerList.get(i).getLeftNeighbour().getName(), playerList.get(i).getRightNeighbour().getName());
        }
    }

    /**
     * Starts the game.
     */
    public void startGame(){
        currentAge = 1;
        currentTurn = 1;
        List<Card> currAgeDeck = new ArrayList<>(fullDeck.stream().filter(c -> c.getAge() == currentAge).toList());
        distributeCardsToPlayers(currAgeDeck);
        playerIterator = players.iterator();
        currentPlayer = playerIterator.next();
        LOGGER.info("Game started, age : {}/3, turn : {}/6.", currentAge, currentTurn);
        LOGGER.info("Current player : {}", currentPlayer.displayPlayerResources());
    }

    /**
     * Ends the current player's turn and advances to the next player or the next turn/age.
     *
     * @return the EndTurnEvent indicating the result of ending the turn
     */
    public EndTurnEvent endPlayerTurn(){
        if(playerIterator.hasNext()){
            currentPlayer = playerIterator.next();
            LOGGER.info("Next player's turn, age : {}/3, turn : {}/6.", currentAge, currentTurn);
            LOGGER.info("Current player : {}", currentPlayer.displayPlayerResources());
            return EndTurnEvent.NEXT_PLAYER;
        }else{
            playerIterator = players.iterator();
            currentPlayer = playerIterator.next();
            if(currentTurn < 6){
                currentTurn += 1;
                passCardsToNextPlayer();
                LOGGER.info("Next turn, age : {}/3, turn : {}/6.", currentAge, currentTurn);
                LOGGER.info("Current player : {}", currentPlayer.displayPlayerResources());
                return EndTurnEvent.END_TURN;
            }else{
                currentTurn = 1;
                if(currentAge < 3){
                    currentAge += 1;
                    List<Card> currAgeDeck = new ArrayList<>(fullDeck.stream().filter(c -> c.getAge() == currentAge).toList());
                    distributeCardsToPlayers(currAgeDeck);
                    LOGGER.info("Next age, age : {}/3, turn : {}/6.", currentAge, currentTurn);
                    LOGGER.info("Current player : {}", currentPlayer.displayPlayerResources());
                    return EndTurnEvent.END_AGE;
                }else{
                    LOGGER.info("End of game, age : {}/3, turn : {}/6.", currentAge, currentTurn);
                    return EndTurnEvent.END_GAME;
                }
            }
        }
    }

    /**
     * Ends the game and calculates the final ranking of players based on their victory points.
     *
     * @return the list of players in the final ranking
     */
    public List<Player> endGame(){
        // Count victory points after the game ends and Determine the winner based on the victory points
        for(Player player : players){
            player.addVictoryPointAccordingToEffectAtEndOfGame();
            player.addVictoryPointsAccordingToScientificCards();
            player.setVictoryPoints(player.getVictoryPoints() + player.getMilitaryMight() + player.getTreasure() / 3);
        }
        List<Player> rankingList = players.stream().sorted(Comparator.comparing(Player::getVictoryPoints).reversed()).toList();
        LOGGER.info("Game has ended, here is the ranking:\n{}", displayRanking(rankingList));
        return rankingList;
    }

    /**
     * Returns a string representation of the final ranking of players.
     *
     * @param finalRanking the list of players in the final ranking
     * @return a string representation of the final ranking
     */
    public String displayRanking(List<Player> finalRanking){
        StringBuilder ranking = new StringBuilder();
        for(int i = 1; i <= finalRanking.size(); i++){
            ranking.append(i)
                    .append(" - ")
                    .append(finalRanking.get(i - 1).getName())
                    .append(" with ")
                    .append(finalRanking.get(i - 1).getVictoryPoints())
                    .append(" victory points.")
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
        }
        return ranking.toString();
    }

    /**
     * Distributes cards to all players from the given deck.
     *
     * @param currAgeDeck the deck of cards to distribute
     * @throws NullPointerException     if currAgeDeck is null
     * @throws IllegalArgumentException if currAgeDeck is empty
     */
    private void distributeCardsToPlayers(List<Card> currAgeDeck){
        if(currAgeDeck == null)
            throw new NullPointerException("Age deck can't be null");
        if(currAgeDeck.isEmpty())
            throw new IllegalArgumentException("Age deck can't be empty.");
        for(Player player : players){
            List<Card> hand = new ArrayList<>();
            for(int i = 0; i < 7 && !currAgeDeck.isEmpty(); i++){
                hand.add(currAgeDeck.remove(currAgeDeck.size() - 1));
            }
            player.setInHandCards(hand);
        }
        LOGGER.info("Cards distributed.");
    }

    /**
     * Passes the remaining cards in each player's hand to the next player.
     */
    private void passCardsToNextPlayer(){
        List<Player> playerList = new ArrayList<>(players);
        List<Card> tempHand = playerList.get(numberOfPlayers - 1).getInHandCards();

        for(int i = numberOfPlayers - 1; i > 0; i--)
            playerList.get(i).setInHandCards(playerList.get(i - 1).getInHandCards());

        playerList.get(0).setInHandCards(tempHand);// Assign last player's hand to the first player
        LOGGER.info("In hand card swapped.");
    }


    public int getNumberOfPlayers(){
        return this.numberOfPlayers;
    }

    public Set<Wonder> getWonders(){
        return this.wonders;
    }

    public Set<Player> getPlayers(){
        return this.players;
    }

    public List<Card> getFullDeck(){
        return this.fullDeck;
    }

    public int getCurrentAge(){
        return currentAge;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public int getCurrentTurn(){
        return currentTurn;
    }
}

