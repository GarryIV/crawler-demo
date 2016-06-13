package com.garryiv.crawler.reader;

import com.garryiv.crawler.model.InputSite;
import com.garryiv.crawler.model.SitesCollection;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class JsonCollectionReaderTest extends Assert {

    @Test
    public void read() {
        JsonCollectionReader reader = new JsonCollectionReader();
        Path path = Paths.get("input/input2.json");
        SitesCollection<InputSite> collection = reader.read(path.getFileName().toString(), path);

        assertEquals("input2.json", collection.getCollectionId());
        assertEquals(3, collection.getSites().size());

        InputSite site0 = collection.getSites().get(0);
        assertEquals(13000, site0.getId());
        assertEquals("example.com/json1", site0.getName());
        assertEquals(true, site0.isMobile());
        assertEquals(21., site0.getScore(), 0.);

        InputSite site1 = collection.getSites().get(1);
        assertEquals(13001, site1.getId());
        assertEquals("example.com/json2", site1.getName());
        assertEquals(false, site1.isMobile());
        assertEquals(97., site1.getScore(), 0.);
    }
}