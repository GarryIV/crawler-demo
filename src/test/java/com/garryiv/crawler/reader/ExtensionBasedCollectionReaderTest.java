package com.garryiv.crawler.reader;

import com.garryiv.crawler.model.InputSite;
import com.garryiv.crawler.model.SitesCollection;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Collections;

public class ExtensionBasedCollectionReaderTest extends Assert {

    private static final String CSV = "csv";
    private static final String JSON = "json";

    @Test
    public void test() {
        ExtensionBasedCollectionReader reader = new ExtensionBasedCollectionReader();
        reader.addMapping(CSV, (id, path) -> new SitesCollection<>(CSV, Collections.emptyList()));
        reader.addMapping(JSON, (id, path) -> new SitesCollection<>(JSON, Collections.emptyList()));

        SitesCollection<InputSite> collection;

        collection = reader.read(CSV, Paths.get("input.csv"));
        assertEquals(CSV, collection.getCollectionId());

        collection = reader.read(JSON, Paths.get("input.json"));
        assertEquals(JSON, collection.getCollectionId());

        collection = reader.read(JSON, Paths.get("input.csv.json"));
        assertEquals(JSON, collection.getCollectionId());

        try {
            reader.read("xml", Paths.get("input.xml"));
            fail("CollectionReaderException is expected because there's no xml reader");
        } catch (CollectionReaderException e) {
            assertNotNull(e.getMessage());
        }

        try {
            reader.read("without_extension", Paths.get("input_without_extension"));
            fail("CollectionReaderException is expected because the path has no extension");
        } catch (CollectionReaderException e) {
            assertNotNull(e.getMessage());
        }
    }
}