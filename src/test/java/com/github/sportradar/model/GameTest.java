package com.github.sportradar.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Smoke tests for a Game model
 */
class GameTest {

    @Test
    void constructingGameShouldHaveCorrectTeams() {
        var game = generateMockGame();

        assertEquals("Home", game.getHomeTeam().name());
        assertEquals("Away", game.getAwayTeam().name());
    }

    @Test
    void constructingGameShouldHaveUuid() {
        var game = generateMockGame();

        assertNotNull(game.getUuid());
    }

    @Test
    void constructingGameShouldHaveStartDate() {
        var game = generateMockGame();

        assertNotNull(game.getStartedAt());
    }

    @Test
    void newGameShouldHaveNullFinishedDate() {
        var game = generateMockGame();

        assertNull(game.getFinishedAt());
    }


    private Game generateMockGame() {
        return new Game(new Team("Home"), new Team("Away"));
    }
}