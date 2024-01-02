package com.github.sportradar.service;

import com.github.sportradar.model.Game;
import com.github.sportradar.model.Team;
import org.junit.jupiter.api.Test;

import java.util.MissingResourceException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void updateScoreWithInvalidGameShouldThrowException() {
        assertThrows(MissingResourceException.class, () -> scoreBoardService.updateScore(UUID.randomUUID(), 2, 2));
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

    @Test
    void getRunningGameWorks() {
        var first = scoreBoardService.startGame(new Team("Team1"), new Team("Team2"));
        var second = scoreBoardService.startGame(new Team("Team3"), new Team("Team4"));

        var game = scoreBoardService.getRunningGame(first.getUuid());

        assertEquals(first.getUuid(), game.getUuid());
        assertEquals(first.getStartedAt(), game.getStartedAt());
        assertEquals(first.getScore(), game.getScore());
        assertNotEquals(second.getUuid(), game.getUuid());
    }

    @Test
    void finishGameShouldHaveFinishedDate() {
        var game = scoreBoardService.startGame(new Team("Team1"), new Team("Team2"));

        scoreBoardService.finishGame(game.getUuid());

        assertNotNull(game.getFinishedAt());
    }

    @Test
    void finishGameShouldRemoveTheGameFromScoreBoard() {
        var game = scoreBoardService.startGame(new Team("Team1"), new Team("Team2"));

        scoreBoardService.finishGame(game.getUuid());

        assertThrows(MissingResourceException.class, () -> {
            scoreBoardService.getRunningGame(game.getUuid());
        });
    }

    @Test
    void updateScoreOfFinishedGameShouldThrowException() {
        var game = scoreBoardService.startGame(new Team("Team1"), new Team("Team2"));
        scoreBoardService.finishGame(game.getUuid());

        assertThrows(MissingResourceException.class, () -> {
            scoreBoardService.updateScore(game.getUuid(), 1, 0);
        });
    }

    @Test
    void getRunningGamesShouldOrderedByTotalScore() {
        var game1 = scoreBoardService.startGame(new Team("Team1"), new Team("Team2"));
        var game2 = scoreBoardService.startGame(new Team("Team1"), new Team("Team2"));
        var game3 = scoreBoardService.startGame(new Team("Team1"), new Team("Team2"));

        scoreBoardService.updateScore(game1.getUuid(), 1, 0);
        scoreBoardService.updateScore(game2.getUuid(), 1, 2);
        scoreBoardService.updateScore(game3.getUuid(), 0, 2);

        assertThat(scoreBoardService.getScoreBoard())
                .extracting(Game::getUuid).containsExactly(game2.getUuid(), game3.getUuid(), game1.getUuid());
    }

    @Test
    void getRunningGamesShouldOrderByStartDateWhenScoreSumIsEqual() {
        var game1 = scoreBoardService.startGame(new Team("Team1"), new Team("Team2"));
        var game2 = scoreBoardService.startGame(new Team("Team1"), new Team("Team2"));
        var game3 = scoreBoardService.startGame(new Team("Team1"), new Team("Team2"));
        var game4 = scoreBoardService.startGame(new Team("Team1"), new Team("Team2"));

        scoreBoardService.updateScore(game1.getUuid(), 1, 0);
        scoreBoardService.updateScore(game2.getUuid(), 1, 2); //same score sum as game 4
        scoreBoardService.updateScore(game3.getUuid(), 0, 2);
        scoreBoardService.updateScore(game4.getUuid(), 2, 1); //same score sum as game 2

        assertThat(scoreBoardService.getScoreBoard())
                .extracting(Game::getUuid).containsExactly(game4.getUuid(), game2.getUuid(), game3.getUuid(), game1.getUuid());
    }

    @Test
    void testWithSampleTestDataShouldPass() {
        var game1 = scoreBoardService.startGame(new Team("Mexico"), new Team("Canada"));
        var game2 = scoreBoardService.startGame(new Team("Spain"), new Team("Brazil"));
        var game3 = scoreBoardService.startGame(new Team("Germany"), new Team("France"));
        var game4 = scoreBoardService.startGame(new Team("Uruguay"), new Team("Italy"));
        var game5 = scoreBoardService.startGame(new Team("Argentina"), new Team("Australia"));

        scoreBoardService.updateScore(game1.getUuid(), 0, 5);
        scoreBoardService.updateScore(game2.getUuid(), 10, 2);
        scoreBoardService.updateScore(game3.getUuid(), 2, 2);
        scoreBoardService.updateScore(game4.getUuid(), 6, 6);
        scoreBoardService.updateScore(game5.getUuid(), 3, 1);

        assertThat(scoreBoardService.getScoreBoard())
                .extracting(Game::getUuid)
                .containsExactly(game4.getUuid(), game2.getUuid(), game1.getUuid(), game5.getUuid(), game3.getUuid());
    }

}