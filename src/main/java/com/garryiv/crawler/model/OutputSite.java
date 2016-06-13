package com.garryiv.crawler.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.garryiv.crawler.jackson.NumericBooleanSerializer;

public class OutputSite {
    private long id;
    private String name;
    @JsonSerialize(using = NumericBooleanSerializer.class)
    private boolean mobile;
    private String keywords;
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

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "OutputSite{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mobile=" + mobile +
                ", keywords=" + keywords +
                ", score=" + score +
                '}';
    }
}
