package com.garryiv.crawler.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garryiv.crawler.model.InputSite;
import com.garryiv.crawler.model.SitesCollection;

import java.nio.file.Path;
import java.util.Arrays;

/**
 * Reads collection from json files. File
 */
public class JsonCollectionReader {
    private ObjectMapper mapper = new ObjectMapper();

    public SitesCollection<InputSite> read(String collectionId, Path path) {
        try {
            InputSite[] sites = mapper.readValue(path.toFile(), InputSite[].class);
            return new SitesCollection<>(collectionId, Arrays.asList(sites));
        } catch (Exception e) {
            throw new CollectionReaderException("Can't read " + collectionId + " from " + path, e);
        }
    }
}
