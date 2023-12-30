package com.github.sportradar.service;

import com.github.sportradar.model.Team;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ScoreBoardServiceTest {

    ScoreBoardService scoreBoardService = new ScoreBoardService();

    @Test
    void createGameShouldHaveZero_ZeroScore() {
        var startedGame = scoreBoardService.startGame(new Team("Team1"), new Team("Team2"));

        assertEquals(0, startedGame.getScore().homeScore());
        assertEquals(0, startedGame.getScore().awayScore());
        assertNotNull(startedGame.getStartedAt());
        assertNotNull(startedGame.getUuid());
    }

    @Test
    void createGameContainsCorrectTeamSlots() {
        var newGame = scoreBoardService.startGame(new Team("Home"), new Team("Away"));

        assertEquals("Home", newGame.getHomeTeam().name());
        assertEquals("Away", newGame.getAwayTeam().name());
    }

    @Test
    void startingGameShouldHaveNullFinishDate() {
        var startedGame = scoreBoardService.startGame(new Team("Team1"), new Team("Team2"));

        assertNull(startedGame.getFinishedAt());
    }

    @Test
    void updateGameScoreShouldWork() {
        var startedGame = scoreBoardService.startGame(new Team("Team1"), new Team("Team2"));

        var updatedGame = scoreBoardService.updateScore(startedGame.getUuid(), 1, 0);

        assertEquals(1, updatedGame.getScore().homeScore());
        assertEquals(0, updatedGame.getScore().awayScore());
        assertEquals(startedGame, updatedGame);
    }

    @Test
    void updateGameScoreOnlyUpdatedTheGivenGame() {
        var first = scoreBoardService.startGame(new Team("Team1"), new Team("Team2"));
        var second = scoreBoardService.startGame(new Team("Team3"), new Team("Team4"));

        scoreBoardService.updateScore(first.getUuid(), 2, 1);

        assertEquals(2, first.getScore().homeScore());
        assertEquals(1, first.getScore().awayScore());
        assertEquals(0, second.getScore().homeScore());
        assertEquals(0, second.getScore().awayScore());
    }
}