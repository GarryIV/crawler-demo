package com.garryiv.crawler.reader;

import com.garryiv.crawler.model.InputSite;
import com.garryiv.crawler.model.SitesCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(DirectoryReader.class);

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
        logger.info("Starting to read a directory: {}", input);

        List<SitesCollection<InputSite>> collections = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(input)) {
            for (Path path : stream) {
                String collectionId = path.getFileName().toString();
                SitesCollection<InputSite> collection = collectionReader.read(collectionId, path);
                collections.add(collection);

                logger.info("Collection: {} size: {}", collectionId, collection.getSites().size());
            }
        } catch (IOException e) {
            throw new CollectionReaderException("Can't read " + input, e);
        }

        logger.info("Directory has been read, {} collections have been found", collections.size());
        return collections;
    }
}
