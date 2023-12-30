package com.github.sportradar.service;

import com.github.sportradar.model.Score;
import com.github.sportradar.model.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ScoreBoardServiceTest {

    ScoreBoardService scoreBoardService = new ScoreBoardService();

    @Test
    void createGameShouldHaveZero_ZeroScore() {
        var startedGame = scoreBoardService.startGame(new Team("Team1"), new Team("Team2"));

        assertEquals(0, startedGame.getScore().getHomeScore());
        assertEquals(0, startedGame.getScore().getAwayScore());
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
}