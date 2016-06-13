package com.garryiv.crawler.reader;

import com.garryiv.crawler.model.InputSite;
import com.garryiv.crawler.model.SitesCollection;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class DirectoryReaderTest extends Assert {

    @Test
    public void read() {
        CollectionReader collectionReader = (collectionId, filePath) ->
                new SitesCollection<>(collectionId, Collections.emptyList());

        DirectoryReader directoryReader = new DirectoryReader(collectionReader);

        List<SitesCollection<InputSite>> res = directoryReader.read(Paths.get("input"));
        assertEquals(2, res.size());

        Collections.sort(res, (o1, o2) -> o1.getCollectionId().compareTo(o2.getCollectionId()));
        assertEquals("input1.csv", res.get(0).getCollectionId());
        assertEquals("input2.json", res.get(1).getCollectionId());
    }
}