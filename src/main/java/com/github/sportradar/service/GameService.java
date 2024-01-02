package com.github.sportradar.service;

import com.github.sportradar.model.Game;
import com.github.sportradar.model.Team;

import java.util.UUID;

public interface GameService {

    Game startGame(Team host, Team away);

    Game updateScore(UUID gameUuid, Integer homeScore, Integer awayScore);

    void finishGame(UUID gameUuid);
}
