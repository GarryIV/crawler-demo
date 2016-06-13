package com.garryiv.crawler.reader;

import com.garryiv.crawler.model.InputSite;
import com.garryiv.crawler.model.SitesCollection;

import java.nio.file.Path;

/**
 * Reads site collection from file
 */
public interface CollectionReader {

    /**
     * Read collection
     * @param collectionId collection id
     * @param filePath collection file location
     * @return collection
     */
    SitesCollection<InputSite> read(String collectionId, Path filePath);
}
