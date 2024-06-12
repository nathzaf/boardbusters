package io.github.oliviercailloux.boardbusters.game;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import io.github.oliviercailloux.boardbusters.card.*;
import io.github.oliviercailloux.boardbusters.effect.commercial.EffectCommercialBenefits;
import io.github.oliviercailloux.boardbusters.effect.guild.*;
import io.github.oliviercailloux.boardbusters.effect.military.EffectMilitaryMight;
import io.github.oliviercailloux.boardbusters.effect.resources.EffectProductResources;
import io.github.oliviercailloux.boardbusters.effect.resources.EffectScientificSymbol;
import io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints.EffectEarnCoinAndVictoryPointFromCards;
import io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints.EffectEarnCoinAndVictoryPointFromStages;
import io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints.treasure.EffectEarnCoins;
import io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints.treasure.EffectEarnCoinsFromTypeOfCard;
import io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints.victorypoints.EffectVictoryPoints;
import io.github.oliviercailloux.boardbusters.resources.Resources;
import io.github.oliviercailloux.boardbusters.resources.ScientificSymbol;
import io.github.oliviercailloux.boardbusters.wonder.Wonder;
import io.github.oliviercailloux.boardbusters.wonder.WonderStage;

import java.util.*;

import static io.github.oliviercailloux.boardbusters.resources.Resources.*;


public class ObjectsInitializer {

    /**
     * Initialize a deck with all cards of the game in order to extract
     * the useful ones later.
     */
    static List<Card> generateCards(){
        List<Card> cardList = new ArrayList<>();
        cardList.addAll(createFirstAgeDeck());
        cardList.addAll(createSecondAgeDeck());
        cardList.addAll(createThirdAgeDeck());
        return cardList;
    }

    static private List<Card> createFirstAgeDeck(){
        final Multiset<Resources> costDefault = HashMultiset.create();
        List<Card> cardList = new ArrayList<>();


        /////////////////////////////////////////// FIRST AGE CARDS ///////////////////////////////////////////


        // Raw Material Cards
        // first age cards

        // Raw Material Cards
        // 3+ players
        cardList.add(new CardRawMaterials("Lumber Yard", 3, costDefault, 1, new EffectProductResources(Resources.of(WOOD), true)));
        cardList.add(new CardRawMaterials("Stone Pit", 3, costDefault, 1, new EffectProductResources(Resources.of(STONE), true)));
        cardList.add(new CardRawMaterials("Clay Pool", 3, costDefault, 1, new EffectProductResources(Resources.of(CLAY), true)));
        // clay pit is "fosse argileuse", when processing it , you can choose between 2 raw materials to produce
        // cardList.add(new CardRawMaterials("Clay Pit", 3, costDefault, 1, new EffectProductResources(Map.of(Resources.CLAY + ORE, 1))));
        // cardList.add(new CardRawMaterials("Forestry Exploitation", 3, costDefault, 1, new EffectProductResources(Map.of(Resources.STONE + WOOD, 1))));

        cardList.add(new CardRawMaterials("Ore Vein", 3, costDefault, 1, new EffectProductResources(Resources.of(ORE), true)));

        // 4+ players
        cardList.add(new CardRawMaterials("Lumber Yard", 4, costDefault, 1, new EffectProductResources(Resources.of(WOOD), true)));
        // idem
        // cardList.add(new CardRawMaterials("Excavation", 4, costDefault, 1, new EffectProductResources(Map.of(Resources.STONE + CLAY, 1))));
        cardList.add(new CardRawMaterials("Ore Vein", 4, costDefault, 1, new EffectProductResources(Resources.of(ORE), true)));

        // 5+ players
        cardList.add(new CardRawMaterials("Stone Pit", 5, costDefault, 1, new EffectProductResources(Resources.of(STONE), true)));
        cardList.add(new CardRawMaterials("Clay Pool", 5, costDefault, 1, new EffectProductResources(Resources.of(CLAY), true)));
        // idem
        // cardList.add(new CardRawMaterials("Deposit", 5, costDefault, 1, new EffectProductResources(Map.of(Resources.WOOD + ORE, 1))));

        // 6+ players
        // idem
        // cardList.add(new CardRawMaterials("Fallow Land", 6, costDefault, 1, new EffectProductResources(Map.of(Resources.STONE + ORE, 1))));
        // cardList.add(new CardRawMaterials("Mine", 6, costDefault, 1, new EffectProductResources(Map.of(Resources.WOOD + CLAY, 1))));


        // Manufactured Goods Cards

        // 3+
        cardList.add(new CardManufacturedGoods("Glassworks", 3, costDefault, 1, new EffectProductResources(Resources.of(GLASS), true)));
        cardList.add(new CardManufacturedGoods("Press", 3, costDefault, 1, new EffectProductResources(Resources.of(PAPYRUS), true)));
        cardList.add(new CardManufacturedGoods("Loom", 3, costDefault, 1, new EffectProductResources(Resources.of(TEXTILE), true)));

        // 6+
        cardList.add(new CardManufacturedGoods("Glassworks", 6, costDefault, 1, new EffectProductResources(Resources.of(GLASS), true)));
        cardList.add(new CardManufacturedGoods("Press", 6, costDefault, 1, new EffectProductResources(Resources.of(PAPYRUS), true)));
        cardList.add(new CardManufacturedGoods("Loom", 6, costDefault, 1, new EffectProductResources(Resources.of(TEXTILE), true)));

        // Civilian Structures
        // 3+
        List<String> bathsChainedCards = new ArrayList<>();
        bathsChainedCards.add("Aqueduct");
        cardList.add(new CardCivilianStructures("Baths", 3, Resources.of(STONE), 1, new EffectVictoryPoints(3), bathsChainedCards));
        List<String> theaterChainedCards = new ArrayList<>();
        theaterChainedCards.add("Statue");
        cardList.add(new CardCivilianStructures("Theater", 3, costDefault, 1, new EffectVictoryPoints(2), theaterChainedCards));
        List<String> altarChainedCards = new ArrayList<>();
        altarChainedCards.add("Temple");
        cardList.add(new CardCivilianStructures("Altar", 3, costDefault, 1, new EffectVictoryPoints(2), altarChainedCards));

        // 4+
        cardList.add(new CardCivilianStructures("Pawnbroker", 4, costDefault, 1, new EffectVictoryPoints(3), new ArrayList<>()));

        // 5+
        List<String> altarChainedCards2 = new ArrayList<>();
        altarChainedCards.add("Temple");
        cardList.add(new CardCivilianStructures("Altar", 5, costDefault, 1, new EffectVictoryPoints(2), altarChainedCards2));

        // 6+
        List<String> theaterChainedCards2 = new ArrayList<>();
        theaterChainedCards.add("Statue");
        cardList.add(new CardCivilianStructures("Theater", 6, costDefault, 1, new EffectVictoryPoints(2), theaterChainedCards2));

        // 7+
        cardList.add(new CardCivilianStructures("Pawnbroker", 7, costDefault, 1, new EffectVictoryPoints(3), new ArrayList<>()));
        List<String> bathsChainedCards2 = new ArrayList<>();
        bathsChainedCards.add("Aqueduct");
        cardList.add(new CardCivilianStructures("Baths", 7, Resources.of(STONE), 1, new EffectVictoryPoints(3), bathsChainedCards2));

        // Scientific Structures
        // 3+
        List<String> pharmacyChainedCards = new ArrayList<>();
        pharmacyChainedCards.add("Clinic");
        pharmacyChainedCards.add("Horses Stables");
        cardList.add(new CardScientificStructures("Pharmacy", 3, Resources.of(TEXTILE), 1, new EffectScientificSymbol(ScientificSymbol.COMPASS), pharmacyChainedCards));
        List<String> workshopChainedCards = new ArrayList<>();
        workshopChainedCards.add("Laboratory");
        workshopChainedCards.add("Shooting Ranges");
        cardList.add(new CardScientificStructures("Workshop", 3, Resources.of(TEXTILE), 1, new EffectScientificSymbol(ScientificSymbol.GEAR_WHEEL), workshopChainedCards));

        List<String> scriptoriumChainedCards = new ArrayList<>();
        scriptoriumChainedCards.add("Statue Court");
        scriptoriumChainedCards.add("Library");
        cardList.add(new CardScientificStructures("Scriptorium", 3, Resources.of(PAPYRUS), 1, new EffectScientificSymbol(ScientificSymbol.TABLET), scriptoriumChainedCards));

        // 4+
        List<String> scriptoriumChainedCards2 = new ArrayList<>();
        scriptoriumChainedCards2.add("Statue Court");
        scriptoriumChainedCards2.add("Library");
        cardList.add(new CardScientificStructures("Scriptorium", 4, Resources.of(PAPYRUS), 1, new EffectScientificSymbol(ScientificSymbol.TABLET), scriptoriumChainedCards2));

        // 5+
        List<String> pharmacyChainedCards2 = new ArrayList<>();
        pharmacyChainedCards.add("Clinic");
        pharmacyChainedCards.add("Horses Stables");
        cardList.add(new CardScientificStructures("Pharmacy", 5, Resources.of(TEXTILE), 1, new EffectScientificSymbol(ScientificSymbol.COMPASS), pharmacyChainedCards2));

        // 7+
        List<String> workshopChainedCards2 = new ArrayList<>();
        workshopChainedCards.add("Laboratory");
        workshopChainedCards.add("Shooting Ranges");
        cardList.add(new CardScientificStructures("Workshop", 7, Resources.of(TEXTILE), 1, new EffectScientificSymbol(ScientificSymbol.GEAR_WHEEL), workshopChainedCards2));

        // Commercial Structures
        // 3+
        List<String> westernCounterChainedCards = new ArrayList<>();
        workshopChainedCards.add("Forum");
        cardList.add(new CardCommercialStructures("Western counter", 3, costDefault, 1, new EffectCommercialBenefits(true, false, Set.of(STONE, CLAY, WOOD, ORE)), westernCounterChainedCards));

        // 4+
        cardList.add(new CardCommercialStructures("Tavern", 4, costDefault, 1, new EffectEarnCoins(5), new ArrayList<>()));

        // 5+
        cardList.add(new CardCommercialStructures("Tavern", 5, costDefault, 1, new EffectEarnCoins(5), new ArrayList<>()));


        // Military Structures
        // 3+
        cardList.add(new CardMilitaryStructures("Stockade", 3, Resources.of(WOOD), 1, new EffectMilitaryMight(1), new ArrayList<>()));
        cardList.add(new CardMilitaryStructures("Barracks", 3, Resources.of(ORE), 1, new EffectMilitaryMight(1), new ArrayList<>()));

        cardList.add(new CardMilitaryStructures("Guard Tower", 3, Resources.of(CLAY), 1, new EffectMilitaryMight(1), new ArrayList<>()));

        // 4+
        cardList.add(new CardMilitaryStructures("Guard Tower", 4, Resources.of(CLAY), 1, new EffectMilitaryMight(1), new ArrayList<>()));

        // 5+
        cardList.add(new CardMilitaryStructures("Barracks", 5, Resources.of(ORE), 1, new EffectMilitaryMight(1), new ArrayList<>()));

        // 7+
        cardList.add(new CardMilitaryStructures("Stockade", 7, Resources.of(WOOD), 1, new EffectMilitaryMight(1), new ArrayList<>()));
        cardList.add(new CardMilitaryStructures("Stockade", 7, Resources.of(WOOD), 1, new EffectMilitaryMight(1), new ArrayList<>()));

        Collections.shuffle(cardList);
        return cardList;
    }

    static private List<Card> createSecondAgeDeck(){
        List<Card> cardList = new ArrayList<>();
        final Multiset<Resources> costDefault = HashMultiset.create();

        /////////////////////////////////////////// SECOND AGE CARDS ///////////////////////////////////////////


        // Raw Material Cards
        // 3+
        cardList.add(new CardRawMaterials("Sawmill", 3, costDefault, 2, new EffectProductResources(Resources.of(WOOD, WOOD), true)));
        cardList.add(new CardRawMaterials("Quarry", 3, costDefault, 2, new EffectProductResources(Resources.of(STONE, STONE), true)));
        cardList.add(new CardRawMaterials("Brickyard", 3, costDefault, 2, new EffectProductResources(Resources.of(CLAY, CLAY), true)));
        cardList.add(new CardRawMaterials("Foundry", 3, costDefault, 2, new EffectProductResources(Resources.of(ORE, ORE), true)));

        // 4+ players
        cardList.add(new CardRawMaterials("Sawmill", 4, costDefault, 2, new EffectProductResources(Resources.of(WOOD, WOOD), true)));
        cardList.add(new CardRawMaterials("Quarry", 4, costDefault, 2, new EffectProductResources(Resources.of(STONE, STONE), true)));
        cardList.add(new CardRawMaterials("Brickyard", 4, costDefault, 2, new EffectProductResources(Resources.of(CLAY, CLAY), true)));
        cardList.add(new CardRawMaterials("Foundry", 4, costDefault, 2, new EffectProductResources(Resources.of(ORE, ORE), true)));


        // Manufactured Goods Cards
        // 3+
        cardList.add(new CardManufacturedGoods("Glassworks", 3, costDefault, 2, new EffectProductResources(Resources.of(GLASS), true)));
        cardList.add(new CardManufacturedGoods("Press", 3, costDefault, 2, new EffectProductResources(Resources.of(PAPYRUS), true)));
        cardList.add(new CardManufacturedGoods("Loom", 3, costDefault, 2, new EffectProductResources(Resources.of(TEXTILE), true)));

        // 5+
        cardList.add(new CardManufacturedGoods("Glassworks", 5, costDefault, 2, new EffectProductResources(Resources.of(GLASS), true)));
        cardList.add(new CardManufacturedGoods("Press", 5, costDefault, 2, new EffectProductResources(Resources.of(PAPYRUS), true)));
        cardList.add(new CardManufacturedGoods("Loom", 5, costDefault, 2, new EffectProductResources(Resources.of(TEXTILE), true)));

        // Civilian Structures
        // 3+
        List<String> aqueductChainedCards = new ArrayList<>();
        cardList.add(new CardCivilianStructures("Aqueduct", 3, costDefault, 2, new EffectVictoryPoints(5), aqueductChainedCards));

        List<String> statueChainedCards = new ArrayList<>();
        cardList.add(new CardCivilianStructures("Statue", 3, costDefault, 2, new EffectVictoryPoints(4), statueChainedCards));

        List<String> courthouseChainedCards = new ArrayList<>();
        cardList.add(new CardCivilianStructures("Courthouse", 3, costDefault, 2, new EffectVictoryPoints(4), courthouseChainedCards));

        List<String> templeChainedCards = new ArrayList<>();
        cardList.add(new CardCivilianStructures("Temple", 3, costDefault, 2, new EffectVictoryPoints(4), templeChainedCards));

        // 5+
        cardList.add(new CardCivilianStructures("Courthouse", 5, costDefault, 2, new EffectVictoryPoints(4), courthouseChainedCards));

        // 6+
        cardList.add(new CardCivilianStructures("Temple", 6, costDefault, 2, new EffectVictoryPoints(4), templeChainedCards));

        // 7+
        cardList.add(new CardCivilianStructures("Aqueduct", 7, costDefault, 2, new EffectVictoryPoints(5), aqueductChainedCards));
        cardList.add(new CardCivilianStructures("Statue", 7, costDefault, 2, new EffectVictoryPoints(4), statueChainedCards));

        // Scientific Structures
        List<String> clinicChainedCards2 = new ArrayList<>();
        clinicChainedCards2.add("Lodge");
        clinicChainedCards2.add("Arena");

        List<String> laboratoryChainedCards = new ArrayList<>();
        laboratoryChainedCards.add("Obeservatory");
        laboratoryChainedCards.add("Siege workshop");

        List<String> libraryChainedCards = new ArrayList<>();
        libraryChainedCards.add("University");
        libraryChainedCards.add("Senate");

        List<String> schoolChainedCards = new ArrayList<>();
        schoolChainedCards.add("Academy");
        schoolChainedCards.add("Study");

        // 3+
        cardList.add(new CardScientificStructures("Dispensary", 3, costDefault, 2, new EffectScientificSymbol(ScientificSymbol.COMPASS), clinicChainedCards2));
        cardList.add(new CardScientificStructures("Laboratory", 3, costDefault, 2, new EffectScientificSymbol(ScientificSymbol.GEAR_WHEEL), laboratoryChainedCards));
        cardList.add(new CardScientificStructures("Library", 3, costDefault, 2, new EffectScientificSymbol(ScientificSymbol.TABLET), libraryChainedCards));
        cardList.add(new CardScientificStructures("School", 3, costDefault, 2, new EffectScientificSymbol(ScientificSymbol.TABLET), schoolChainedCards));

        //4+
        cardList.add(new CardScientificStructures("Dispensary", 4, costDefault, 2, new EffectScientificSymbol(ScientificSymbol.COMPASS), clinicChainedCards2));

        // 5+
        cardList.add(new CardScientificStructures("Laboratory", 5, costDefault, 2, new EffectScientificSymbol(ScientificSymbol.GEAR_WHEEL), laboratoryChainedCards));

        // 6+
        cardList.add(new CardScientificStructures("Library", 6, costDefault, 2, new EffectScientificSymbol(ScientificSymbol.TABLET), libraryChainedCards));

        // 7+
        cardList.add(new CardScientificStructures("School", 7, costDefault, 2, new EffectScientificSymbol(ScientificSymbol.TABLET), schoolChainedCards));

        // Commercial Structures
        List<String> vinyardChainedCards = new ArrayList<>();

        List<String> bazarChainedCards = new ArrayList<>();

        // 3+
        cardList.add(new CardCommercialStructures("Vinyard", 3, costDefault, 2, new EffectEarnCoinsFromTypeOfCard(CardType.RAW_MATERIALS, 1), vinyardChainedCards));
//        cardList.add(new CardCommercialStructures("Caravansery", 3, costDefault, 2, new effecttoimplement(CardRawMaterials.class, 1), vinyardChainedCards));
//        cardList.add(new CardCommercialStructures("Forum", 3, costDefault, 2, new EffectEarnCoinsFromTypeOfCard(CardRawMaterials.class, 1), vinyardChainedCards));

        //4+
        cardList.add(new CardCommercialStructures("Bazar", 4, costDefault, 2, new EffectEarnCoinsFromTypeOfCard(CardType.MANUFACTURED_GOODS, 2), bazarChainedCards));

        // 5+
//      cardList.add(new CardCommercialStructures("Caravansery", 5, costDefault, 2, new effecttoimplement(CardRawMaterials.class, 1), vinyardChainedCards));

        // 6+
        cardList.add(new CardCommercialStructures("Vinyard", 6, costDefault, 2, new EffectEarnCoinsFromTypeOfCard(CardType.RAW_MATERIALS, 1), vinyardChainedCards));
//      cardList.add(new CardCommercialStructures("Caravansery", 6, costDefault, 2, new effecttoimplement(CardRawMaterials.class, 1), vinyardChainedCards));
//      cardList.add(new CardCommercialStructures("Forum", 6, costDefault, 2, new EffectEarnCoinsFromTypeOfCard(CardRawMaterials.class, 1), vinyardChainedCards));

        // 7+
        cardList.add(new CardCommercialStructures("Bazar", 7, costDefault, 2, new EffectEarnCoinsFromTypeOfCard(CardType.MANUFACTURED_GOODS, 2), bazarChainedCards));
//      cardList.add(new CardCommercialStructures("Forum", 7, costDefault, 2, new EffectEarnCoinsFromTypeOfCard(CardRawMaterials.class, 1), vinyardChainedCards));


        // Military Structures
        List<String> stablesChainedCards = new ArrayList<>();

        List<String> archeryRangeChainedCards = new ArrayList<>();

        List<String> wallsChainedCards = new ArrayList<>();
        wallsChainedCards.add("Fortifications");

        List<String> trainingGroundChainedCards = new ArrayList<>();
        trainingGroundChainedCards.add("Circus");

        // 3+
        cardList.add(new CardMilitaryStructures("Stalbes", 3, costDefault, 2, new EffectMilitaryMight(2), stablesChainedCards));
        cardList.add(new CardMilitaryStructures("Archery range", 3, costDefault, 2, new EffectMilitaryMight(2), archeryRangeChainedCards));
        cardList.add(new CardMilitaryStructures("Walls", 3, costDefault, 2, new EffectMilitaryMight(2), wallsChainedCards));

        // 4+
        cardList.add(new CardMilitaryStructures("Training ground", 4, costDefault, 2, new EffectMilitaryMight(2), trainingGroundChainedCards));

        // 5+
        cardList.add(new CardMilitaryStructures("Stalbes", 5, costDefault, 2, new EffectMilitaryMight(2), stablesChainedCards));

        // 6+ 
        cardList.add(new CardMilitaryStructures("Archery range", 6, costDefault, 2, new EffectMilitaryMight(2), archeryRangeChainedCards));
        cardList.add(new CardMilitaryStructures("Training ground", 6, costDefault, 2, new EffectMilitaryMight(2), trainingGroundChainedCards));

        // 7+
        cardList.add(new CardMilitaryStructures("Walls", 7, costDefault, 2, new EffectMilitaryMight(2), wallsChainedCards));
        cardList.add(new CardMilitaryStructures("Training ground", 7, costDefault, 2, new EffectMilitaryMight(2), trainingGroundChainedCards));

        Collections.shuffle(cardList);
        return cardList;
    }

    static private List<Card> createThirdAgeDeck(){
        final Multiset<Resources> costDefault = HashMultiset.create();
        List<Card> cardList = new ArrayList<>();

        /////////////////////////////////////////// THIRD AGE CARDS ///////////////////////////////////////////
        List<String> emptyList = new ArrayList<>();

        // Civilian Structures
        // 3+
        cardList.add(new CardCivilianStructures("Pantheon", 3, costDefault, 3, new EffectVictoryPoints(7), emptyList));
        cardList.add(new CardCivilianStructures("Gardens", 3, costDefault, 3, new EffectVictoryPoints(5), emptyList));
        cardList.add(new CardCivilianStructures("Town hall", 3, costDefault, 3, new EffectVictoryPoints(6), emptyList));
        cardList.add(new CardCivilianStructures("Palace", 3, costDefault, 3, new EffectVictoryPoints(8), emptyList));
        cardList.add(new CardCivilianStructures("Senate", 3, costDefault, 3, new EffectVictoryPoints(6), emptyList));

        //4+
        cardList.add(new CardCivilianStructures("Gardens", 4, costDefault, 3, new EffectVictoryPoints(5), emptyList));

        // 5+
        cardList.add(new CardCivilianStructures("Senate", 5, costDefault, 3, new EffectVictoryPoints(6), emptyList));

        // 6+
        cardList.add(new CardCivilianStructures("Pantheon", 6, costDefault, 3, new EffectVictoryPoints(7), emptyList));
        cardList.add(new CardCivilianStructures("Town hall", 6, costDefault, 3, new EffectVictoryPoints(6), emptyList));

        // 7+
        cardList.add(new CardCivilianStructures("Palace", 7, costDefault, 3, new EffectVictoryPoints(8), emptyList));

        // Scientific Structures
        // 3+
        cardList.add(new CardScientificStructures("Lodge", 3, costDefault, 3, new EffectScientificSymbol(ScientificSymbol.COMPASS), emptyList));
        cardList.add(new CardScientificStructures("Academy", 3, costDefault, 3, new EffectScientificSymbol(ScientificSymbol.COMPASS), emptyList));
        cardList.add(new CardScientificStructures("Obervatory", 3, costDefault, 3, new EffectScientificSymbol(ScientificSymbol.GEAR_WHEEL), emptyList));
        cardList.add(new CardScientificStructures("Study", 3, costDefault, 3, new EffectScientificSymbol(ScientificSymbol.GEAR_WHEEL), emptyList));
        cardList.add(new CardScientificStructures("University", 3, costDefault, 3, new EffectScientificSymbol(ScientificSymbol.TABLET), emptyList));

        // 4+
        cardList.add(new CardScientificStructures("University", 4, costDefault, 3, new EffectScientificSymbol(ScientificSymbol.TABLET), emptyList));

        // 5+
        cardList.add(new CardScientificStructures("Study", 5, costDefault, 3, new EffectScientificSymbol(ScientificSymbol.GEAR_WHEEL), emptyList));

        // 6+
        cardList.add(new CardScientificStructures("Lodge", 6, costDefault, 3, new EffectScientificSymbol(ScientificSymbol.COMPASS), emptyList));

        // 7+
        cardList.add(new CardScientificStructures("Academy", 7, costDefault, 3, new EffectScientificSymbol(ScientificSymbol.COMPASS), emptyList));
        cardList.add(new CardScientificStructures("Obervatory", 7, costDefault, 3, new EffectScientificSymbol(ScientificSymbol.GEAR_WHEEL), emptyList));

        // Commercial Structures
        // 3+
        cardList.add(new CardCommercialStructures("Light house", 3, costDefault, 3, new EffectEarnCoinAndVictoryPointFromCards(CardType.COMMERCIAL_STRUCTURES, 1, 1), emptyList));
        cardList.add(new CardCommercialStructures("Haven", 3, costDefault, 3, new EffectEarnCoinAndVictoryPointFromCards(CardType.RAW_MATERIALS, 1, 1), emptyList));
        cardList.add(new CardCommercialStructures("Arena", 3, costDefault, 3, new EffectEarnCoinAndVictoryPointFromStages(1, 3), emptyList));

        // 4+
        cardList.add(new CardCommercialStructures("Haven", 4, costDefault, 3, new EffectEarnCoinAndVictoryPointFromCards(CardType.RAW_MATERIALS, 1, 1), emptyList));
        cardList.add(new CardCommercialStructures("Chamber of commerce", 4, costDefault, 3, new EffectEarnCoinAndVictoryPointFromCards(CardType.MANUFACTURED_GOODS, 2, 2), emptyList));

        //5+
        cardList.add(new CardCommercialStructures("Ludus", 5, costDefault, 3, new EffectEarnCoinAndVictoryPointFromCards(CardType.MILITARY_STRUCTURE, 1, 3), emptyList));
        cardList.add(new CardCommercialStructures("Arena", 5, costDefault, 3, new EffectEarnCoinAndVictoryPointFromStages(1, 3), emptyList));

        // 6+
        cardList.add(new CardCommercialStructures("Light house", 6, costDefault, 3, new EffectEarnCoinAndVictoryPointFromCards(CardType.COMMERCIAL_STRUCTURES, 1, 1), emptyList));
        cardList.add(new CardCommercialStructures("Chamber of commerce", 6, costDefault, 3, new EffectEarnCoinAndVictoryPointFromCards(CardType.MANUFACTURED_GOODS, 2, 2), emptyList));

        // 7+
        cardList.add(new CardCommercialStructures("Ludus", 7, costDefault, 3, new EffectEarnCoinAndVictoryPointFromCards(CardType.MILITARY_STRUCTURE, 1, 3), emptyList));

        // Military Structures
        // 3+
        cardList.add(new CardMilitaryStructures("Fortifications", 3, costDefault, 3, new EffectMilitaryMight(3), emptyList));
        cardList.add(new CardMilitaryStructures("Arsenal", 3, costDefault, 3, new EffectMilitaryMight(3), emptyList));
        cardList.add(new CardMilitaryStructures("Siege workshop", 3, costDefault, 3, new EffectMilitaryMight(3), emptyList));

        // 4+
        cardList.add(new CardMilitaryStructures("Castrum", 4, costDefault, 3, new EffectMilitaryMight(3), emptyList));
        cardList.add(new CardMilitaryStructures("Circus", 4, costDefault, 3, new EffectMilitaryMight(3), emptyList));

        // 5+
        cardList.add(new CardMilitaryStructures("Arsenal", 5, costDefault, 3, new EffectMilitaryMight(3), emptyList));
        cardList.add(new CardMilitaryStructures("Siege workshop", 5, costDefault, 3, new EffectMilitaryMight(3), emptyList));

        // 6+
        cardList.add(new CardMilitaryStructures("Circus", 6, costDefault, 3, new EffectMilitaryMight(3), emptyList));

        // 7+
        cardList.add(new CardMilitaryStructures("Castrum", 7, costDefault, 3, new EffectMilitaryMight(3), emptyList));
        cardList.add(new CardMilitaryStructures("Fortifications", 7, costDefault, 3, new EffectMilitaryMight(3), emptyList));

        // Guilds        
        // 3+
        cardList.add(new CardGuilds("Workers guild", 3, costDefault, 3, new EffectGuildEarnVictoryPoints(CardType.RAW_MATERIALS, 1)));
        cardList.add(new CardGuilds("Crafts mens guild", 3, costDefault, 3, new EffectGuildEarnVictoryPoints(CardType.MANUFACTURED_GOODS, 2)));
        cardList.add(new CardGuilds("Magistrates guild", 3, costDefault, 3, new EffectGuildEarnVictoryPoints(CardType.CIVILIAN_STRUCTURES, 1)));
        cardList.add(new CardGuilds("Traders guild", 3, costDefault, 3, new EffectGuildEarnVictoryPoints(CardType.COMMERCIAL_STRUCTURES, 1)));
        cardList.add(new CardGuilds("Spies guild", 3, costDefault, 3, new EffectGuildEarnVictoryPoints(CardType.MILITARY_STRUCTURE, 1)));
        cardList.add(new CardGuilds("Philosophers guild", 3, costDefault, 3, new EffectGuildEarnVictoryPoints(CardType.SCIENTIFIC_STRUCTURES, 1)));

        cardList.add(new CardGuilds("Builders guild", 3, costDefault, 3, new EffectGuildBuilders()));
        cardList.add(new CardGuilds("Decorators guild", 3, costDefault, 3, new EffectGuildDecorators()));
        cardList.add(new CardGuilds("Shipowners guild", 3, costDefault, 3, new EffectGuildShipowners()));
        cardList.add(new CardGuilds("Scientist guild", 3, costDefault, 3, new EffectGuildScientists()));

        Collections.shuffle(cardList);
        return cardList;
    }


    static Set<Wonder> generateWonders(){
        List<Wonder> wonders = new ArrayList<>();

        List<WonderStage> rhodesStages = Arrays.asList(
                new WonderStage(Resources.of(WOOD, WOOD), new EffectMilitaryMight(2)),
                new WonderStage(Resources.of(CLAY, CLAY, CLAY), new EffectVictoryPoints(2)),
                new WonderStage(Resources.of(ORE, ORE, ORE, ORE), new EffectEarnCoins(6))
        );

        List<WonderStage> gizehStages = Arrays.asList(
                new WonderStage(Resources.of(STONE, STONE), new EffectVictoryPoints(3)),
                new WonderStage(Resources.of(WOOD, WOOD, WOOD), new EffectVictoryPoints(5)),
                new WonderStage(Resources.of(STONE, STONE, STONE, STONE), new EffectVictoryPoints(7))
        );

        List<WonderStage> babylonStages = Arrays.asList(
                new WonderStage(Resources.of(CLAY, CLAY), new EffectVictoryPoints(3)),
                new WonderStage(Resources.of(WOOD, WOOD, WOOD), new EffectVictoryPoints(5)), //change when we will make the EffectScientificSymbol
                new WonderStage(Resources.of(CLAY, CLAY, CLAY, CLAY), new EffectVictoryPoints(7))
        );

        List<WonderStage> alexandriaStages = Arrays.asList(
                new WonderStage(Resources.of(STONE, STONE), new EffectVictoryPoints(3)),
                new WonderStage(Resources.of(ORE, ORE, ORE), new EffectVictoryPoints(5)),
                new WonderStage(Resources.of(GLASS, GLASS, GLASS, GLASS), new EffectVictoryPoints(7))
        );

        List<WonderStage> olympiaStages = Arrays.asList(
                new WonderStage(Resources.of(WOOD, WOOD), new EffectVictoryPoints(3)),
                new WonderStage(Resources.of(STONE, STONE, STONE), new EffectVictoryPoints(5)),
                new WonderStage(Resources.of(ORE, ORE, ORE, ORE), new EffectVictoryPoints(7))
        );

        List<WonderStage> halicarnassusStages = Arrays.asList(
                new WonderStage(Resources.of(CLAY, CLAY), new EffectVictoryPoints(3)),
                new WonderStage(Resources.of(TEXTILE, TEXTILE), new EffectVictoryPoints(5)),
                new WonderStage(Resources.of(PAPYRUS, PAPYRUS, PAPYRUS, PAPYRUS), new EffectVictoryPoints(7))
        );

        List<WonderStage> ephesusStages = Arrays.asList(
                new WonderStage(Resources.of(STONE, STONE), new EffectVictoryPoints(3)),
                new WonderStage(Resources.of(WOOD, WOOD, WOOD), new EffectVictoryPoints(5)),
                new WonderStage(Resources.of(GLASS, GLASS, GLASS, GLASS), new EffectVictoryPoints(7))
        );

        wonders.add(new Wonder("The Colossus of Rhodes", Resources.of(ORE), rhodesStages));
        wonders.add(new Wonder("The Pyramid of Gizeh", Resources.of(STONE), gizehStages));
        wonders.add(new Wonder("The Hanging Gardens of Babylon", Resources.of(CLAY), babylonStages));
        wonders.add(new Wonder("The Lighthouse of Alexandria", Resources.of(GLASS), alexandriaStages));
        wonders.add(new Wonder("The Statue of Zeus at Olympia", Resources.of(WOOD), olympiaStages));
        wonders.add(new Wonder("The Mausoleum at Halicarnassus", Resources.of(CLAY), halicarnassusStages));
        wonders.add(new Wonder("The Temple of Artemis at Ephesus", Resources.of(STONE), ephesusStages));

        Collections.shuffle(wonders);
        return new LinkedHashSet<>(wonders);
    }
}
