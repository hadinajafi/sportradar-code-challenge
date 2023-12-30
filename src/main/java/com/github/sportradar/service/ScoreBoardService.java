package com.github.sportradar.service;

import com.github.sportradar.model.Game;
import com.github.sportradar.model.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ScoreBoardService {
    private final Map<UUID, Game> runningGames = new HashMap<>();


    public Game startGame(Team home, Team away) {
        var game = new Game(home, away);
        runningGames.put(game.getUuid(), game);
        return game;
    }
}
