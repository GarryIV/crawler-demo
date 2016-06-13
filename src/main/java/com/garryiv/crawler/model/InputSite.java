package com.garryiv.crawler.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Stores data from input collections
 */
public class InputSite {

    @JsonProperty("site_id")
    private long id;

    @JsonProperty("name")
    private String url;

    private boolean mobile;

    private double score;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "InputSite{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", mobile=" + mobile +
                ", score=" + score +
                '}';
    }
}
