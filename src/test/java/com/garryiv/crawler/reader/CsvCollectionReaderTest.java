package com.garryiv.crawler.reader;

import com.garryiv.crawler.model.InputSite;
import com.garryiv.crawler.model.SitesCollection;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CsvCollectionReaderTest extends Assert {

    @Test
    public void read() {
        CollectionReader reader = new CsvCollectionReader();
        Path path = Paths.get("input/input1.csv");
        SitesCollection<InputSite> collection = reader.read(path.getFileName().toString(), path);

        assertEquals("input1.csv", collection.getCollectionId());
        assertEquals(3, collection.getSites().size());

        InputSite site0 = collection.getSites().get(0);
        assertEquals(12000, site0.getId());
        assertEquals("example.com/csv1", site0.getName());
        assertEquals(true, site0.isMobile());
        assertEquals(454., site0.getScore(), 0.);

        InputSite site1 = collection.getSites().get(1);
        assertEquals(12001, site1.getId());
        assertEquals("example.com/csv2", site1.getName());
        assertEquals(true, site1.isMobile());
        assertEquals(128, site1.getScore(), 0.);

        InputSite site2 = collection.getSites().get(2);
        assertEquals(12002, site2.getId());
        assertEquals("example.com/csv3", site2.getName());
        assertEquals(false, site2.isMobile());
        assertEquals(522, site2.getScore(), 0.);
    }
}