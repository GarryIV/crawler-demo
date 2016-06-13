package com.garryiv.crawler.model;

import java.util.List;

public class SitesCollection<T> {
    private String collectionId;
    private List<T> sites;

    public SitesCollection(String collectionId, List<T> sites) {
        this.collectionId = collectionId;
        this.sites = sites;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public List<T> getSites() {
        return sites;
    }
}
