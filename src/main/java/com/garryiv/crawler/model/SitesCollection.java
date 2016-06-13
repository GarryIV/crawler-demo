package com.garryiv.crawler.model;

import java.util.List;

/**
 * Sites container with an identifier
 * @param <T> item type
 */
public class SitesCollection<T> {

    /**
     * Collection id
     */
    private String collectionId;

    /**
     * Sites
     */
    private List<T> sites;

    /**
     * Creates new collection
     * @param collectionId collection id
     * @param sites sites
     */
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
