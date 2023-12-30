package com.github.sportradar.model;

import java.util.UUID;

public class Score {
    private final UUID gameUuid;

    private Integer homeScore;

    private Integer awayScore;

    public Score(UUID gameUuid, Integer homeScore, Integer awayScore) {
        this.gameUuid = gameUuid;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public void updateScore(Integer homeScore, Integer awayScore) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public Integer getAwayScore() {
        return awayScore;
    }
}
