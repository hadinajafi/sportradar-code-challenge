package com.github.sportradar.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public class Game {

    private final UUID uuid;

    private final Team homeTeam;

    private final Team awayTeam;

    private Score score;

    private final OffsetDateTime startedAt;

    private OffsetDateTime finishedAt;

    public Game(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.uuid = UUID.randomUUID();
        this.score = new Score(this.uuid, 0, 0);
        this.startedAt = OffsetDateTime.now();
    }

    public UUID getUuid() {
        return uuid;
    }

    public Score getScore() {
        return score;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public OffsetDateTime getStartedAt() {
        return startedAt;
    }

    public OffsetDateTime getFinishedAt() {
        return finishedAt;
    }
}
