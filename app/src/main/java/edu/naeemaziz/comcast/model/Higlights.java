package edu.naeemaziz.comcast.model;

import com.google.gson.annotations.SerializedName;

public class Higlights {

    @SerializedName("title")
    private String title;

    @SerializedName("teamA")
    private String teamA;

    @SerializedName("teamB")
    private String teamB;

    @SerializedName("teamAImageUrl")
    private String teamAImageUrl;

    @SerializedName("teamBImageUrl")
    private String teamBImageUrl;

    @SerializedName("highlightsUrl")
    private String highlightsUrl;

    @SerializedName("data")
    private String date;

    @SerializedName("time")
    private String time;

    @SerializedName("location")
    private String location;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeamA() {
        return teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public void setTeamB(String teamB) {
        this.teamB = teamB;
    }

    public String getTeamAImageUrl() {
        return teamAImageUrl;
    }

    public void setTeamAImageUrl(String teamAImageUrl) {
        this.teamAImageUrl = teamAImageUrl;
    }

    public String getTeamBImageUrl() {
        return teamBImageUrl;
    }

    public void setTeamBImageUrl(String teamBImageUrl) {
        this.teamBImageUrl = teamBImageUrl;
    }

    public String getHighlightsUrl() {
        return highlightsUrl;
    }

    public void setHighlightsUrl(String highlightsUrl) {
        this.highlightsUrl = highlightsUrl;
    }

    public String getData() {
        return date;
    }

    public void setData(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
