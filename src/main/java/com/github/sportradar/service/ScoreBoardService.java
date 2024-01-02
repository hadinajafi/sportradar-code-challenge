package com.github.sportradar.service;

import com.github.sportradar.model.Game;
import com.github.sportradar.model.Score;
import com.github.sportradar.model.Team;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.UUID;

public class ScoreBoardService implements GameService {
    private final Map<UUID, Game> runningGames = new HashMap<>();


    public Game startGame(Team home, Team away) {
        var game = new Game(home, away);
        runningGames.put(game.getUuid(), game);
        return game;
    }

    public Game updateScore(UUID gameUuid, Integer homeScore, Integer awayScore) {
        var game = runningGames.get(gameUuid);
        if (game == null)
            throw new MissingResourceException("Couldn't find the game with uuid: " + gameUuid, "Game", gameUuid.toString());
        game.setScore(new Score(homeScore, awayScore));
        return game;
    }

    public void finishGame(UUID gameUuid) {
        var game = runningGames.get(gameUuid);
        game.setFinishedAt(OffsetDateTime.now());
        runningGames.remove(gameUuid);
    }

    public Game getRunningGame(UUID uuid) {
        var runningGame = runningGames.get(uuid);
        if (runningGame == null)
            throw new MissingResourceException("Couldn't find the game with uuid: " + uuid, "Game", uuid.toString());
        return runningGame;
    }

    public List<Game> getScoreBoard() {
        return orderGames();
    }

    private List<Game> orderGames() {
        return runningGames.values().stream().sorted((g1, g2) -> {
            if (g1.getScore().getScoreSum() > g2.getScore().getScoreSum())
                return -1;
            else if (g1.getScore().getScoreSum() < g2.getScore().getScoreSum())
                return 1;
            else {
                if (g1.getStartedAt().isBefore(g2.getStartedAt()))
                    return 1;
                else
                    return -1;
            }
        }).toList();
    }
}
