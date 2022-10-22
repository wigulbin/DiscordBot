package org.example.ffxivApi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Character {
    @JsonProperty("Avatar")
    private String avatar;
    @JsonProperty("FeastMatches")
    private int feastMatches;
    @JsonProperty("ID")
    private long id;
    @JsonProperty("Lang")
    private String lang;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Rank")
    private String rank;
    @JsonProperty("RankIcon")
    private String rankIcon;
    @JsonProperty("Server")
    private String server;

    public Character() {
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getFeastMatches() {
        return feastMatches;
    }

    public void setFeastMatches(int feastMatches) {
        this.feastMatches = feastMatches;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRankIcon() {
        return rankIcon;
    }

    public void setRankIcon(String rankIcon) {
        this.rankIcon = rankIcon;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
