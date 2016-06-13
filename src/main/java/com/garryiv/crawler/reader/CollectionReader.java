package com.garryiv.crawler.reader;

import com.garryiv.crawler.model.InputSite;
import com.garryiv.crawler.model.SitesCollection;

import java.nio.file.Path;

/**
 * Reads site collection from file
 */
public interface CollectionReader {
    SitesCollection<InputSite> read(String collectionId, Path path);
}
