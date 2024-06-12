package io.github.oliviercailloux.boardbusters.wonder;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import io.github.oliviercailloux.boardbusters.effect.military.EffectMilitaryMight;
import io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints.victorypoints.EffectVictoryPoints;
import io.github.oliviercailloux.boardbusters.player.Player;
import io.github.oliviercailloux.boardbusters.resources.Resources;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.github.oliviercailloux.boardbusters.resources.Resources.ORE;
import static io.github.oliviercailloux.boardbusters.resources.Resources.WOOD;
import static org.junit.jupiter.api.Assertions.*;

class WonderTest {

    @Test
    void isCompleteWithNoStages(){
        Wonder wonder = new Wonder("Wonder Name", Resources.of(ORE), Collections.emptyList());
        assertTrue(wonder.isComplete());
    }

    @Test
    void isNotCompleteWithStages(){
        List<WonderStage> wonderStages = List.of(
                new WonderStage(HashMultiset.create(), new EffectMilitaryMight(2)),
                new WonderStage(HashMultiset.create(), new EffectMilitaryMight(2))
        );
        Wonder wonder = new Wonder("Wonder Name", Resources.of(ORE), wonderStages);
        assertFalse(wonder.isComplete());
    }

    @Test
    void isCompleteWithStages(){
        List<WonderStage> wonderStages = List.of(
                new WonderStage(HashMultiset.create(), new EffectMilitaryMight(2)),
                new WonderStage(HashMultiset.create(), new EffectMilitaryMight(2))
        );
        Wonder wonder = new Wonder("Wonder Name", Resources.of(ORE), wonderStages);
        Player player = new Player("Player name", wonder);

        player.buildStage();
        player.buildStage();
        assertTrue(wonder.isComplete());
    }

    @Test
    void cannotBuildNextStageWithCompleteWonder(){
        List<WonderStage> wonderStages = List.of(new WonderStage(HashMultiset.create(), new EffectMilitaryMight(2)));
        Wonder wonder = new Wonder("Wonder Name", Resources.of(ORE), wonderStages);
        Player player = new Player("Player name", wonder);

        player.buildStage();
        assertFalse(player.canBuild(false));
    }

    @Test
    void cannotBuildNextStageWithInsufficientResources(){
        List<WonderStage> wonderStages = List.of(new WonderStage(Resources.of(WOOD, WOOD, WOOD), new EffectMilitaryMight(2)));
        Wonder wonder = new Wonder("Wonder Name", Resources.of(ORE), wonderStages);

        Player playerMain = new Player("Player name", wonder);
        Player playerLeft = new Player("Player left", wonder);
        Player playerRight = new Player("Player right", wonder);
        playerMain.setLeftNeighbour(playerLeft);
        playerMain.setRightNeighbour(playerRight);

        assertFalse(playerMain.canBuild(false));
    }

    @Test
    void canBuildNextStageWithSufficientResources(){
        List<WonderStage> wonderStages = List.of(new WonderStage(Resources.of(WOOD, WOOD, WOOD), new EffectMilitaryMight(2)));
        Wonder wonder = new Wonder("Wonder Name", Resources.of(ORE), wonderStages);

        Player playerMain = new Player("Player name", wonder);
        Player playerLeft = new Player("Player left", wonder);
        Player playerRight = new Player("Player right", wonder);
        playerMain.setLeftNeighbour(playerLeft);
        playerMain.setRightNeighbour(playerRight);

        Multiset<Resources> playerResources = HashMultiset.create();
        playerResources.add(Resources.WOOD, 4);
        playerMain.getResources().addAll(playerResources);

        assertTrue(playerMain.canBuild(false));
    }

    @Test
    void buildAndApplyEffectTest(){
        List<WonderStage> wonderStages = new ArrayList<>();
        wonderStages.add(new WonderStage(HashMultiset.create(), new EffectMilitaryMight(2)));
        wonderStages.add(new WonderStage(HashMultiset.create(), new EffectVictoryPoints(3)));

        Wonder wonder = new Wonder("Wonder Name", Resources.of(ORE), wonderStages);
        Player player = new Player("Player name", wonder);

        int playerMilitaryMight = player.getMilitaryMight();
        int playerVictoryPoints = player.getVictoryPoints();

        player.buildStage();
        player.buildStage();

        assertEquals(playerMilitaryMight + 2, player.getMilitaryMight());
        assertEquals(playerVictoryPoints + 3, player.getVictoryPoints());
    }
}
