package com.github.sportradar.model;


public record Score(Integer homeScore, Integer awayScore) {
    public Integer getScoreSum() {
        return homeScore + awayScore;
    }
}
