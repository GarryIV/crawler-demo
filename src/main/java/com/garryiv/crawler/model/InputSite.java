package com.garryiv.crawler.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Stores data from input collections
 */
public class InputSite {

    /***
     * Site id
     */
    @JsonProperty("site_id")
    private long id;

    /**
     * Site name (url)
     */
    private String name;

    /**
     * Process as mobile site
     */
    private boolean mobile;

    /**
     * Score of the site
     */
    private double score;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                ", name='" + name + '\'' +
                ", mobile=" + mobile +
                ", score=" + score +
                '}';
    }
}
