package com.garryiv.crawler.reader;

import com.garryiv.crawler.model.InputSite;
import com.garryiv.crawler.model.SitesCollection;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Chooses CollectionReader by file extension.
 * This class is thread-safe after initial configuration (by calling addMapping).
 */
public class ExtensionBasedCollectionReader implements CollectionReader {

    private final Map<String, CollectionReader> byExtension = new HashMap<>();

    /**
     * Add extension mapping
     * @param extension file extension i.e. 'csv' or 'json'
     * @param reader associated collection reader
     */
    public void addMapping(String extension, CollectionReader reader) {
        byExtension.put(extension.toLowerCase(), reader);
    }

    @Override
    public SitesCollection<InputSite> read(String collectionId, Path path) {
        String extension = extension(path);

        CollectionReader reader = byExtension.get(extension);

        if (reader == null) {
            throw new CollectionReaderException("No reader is configured for extension " + extension + " path " + path);
        }

        return reader.read(collectionId, path);
    }

    private String extension(Path path) {
        String file = path.getFileName().toString();
        int n = file.lastIndexOf('.');
        if (n == -1) {
            throw new CollectionReaderException("Can't find extension for " + path);
        }
        return file.substring(n + 1, file.length()).toLowerCase();
    }
}
