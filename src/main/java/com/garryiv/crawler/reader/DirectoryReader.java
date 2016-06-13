package com.garryiv.crawler.reader;

import com.garryiv.crawler.model.InputSite;
import com.garryiv.crawler.model.SitesCollection;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Read directory content
 */
public class DirectoryReader {

    private CollectionReader collectionReader;

    public DirectoryReader(CollectionReader collectionReader) {
        this.collectionReader = collectionReader;
    }

    /**
     * Read directory
     * @param input input directory
     * @return parsed content
     */
    public List<SitesCollection<InputSite>> read(Path input)  {
        List<SitesCollection<InputSite>> collections = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(input)) {
            for (Path path : stream) {
                SitesCollection<InputSite> collection = collectionReader.read(path.getFileName().toString(), path);
                collections.add(collection);
            }
        } catch (IOException e) {
            throw new CollectionReaderException("Can't read " + input, e);
        }

        return collections;
    }
}
