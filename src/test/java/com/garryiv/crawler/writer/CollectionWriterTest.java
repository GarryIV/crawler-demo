package com.garryiv.crawler.writer;

import com.garryiv.crawler.model.OutputSite;
import com.garryiv.crawler.model.SitesCollection;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CollectionWriterTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void write() throws IOException {
        List<SitesCollection<OutputSite>> collections = prepareData();
        Path filePath = folder.newFile().toPath();

        CollectionWriter writer = new CollectionWriter();
        writer.write(collections, filePath);

        assertTrue(Files.exists(filePath));

        List<String> lines = Files.readAllLines(filePath);
        assertEquals(1, lines.size());
    }

    private List<SitesCollection<OutputSite>> prepareData() {
        ArrayList<OutputSite> sites = new ArrayList<>();
        sites.add(prepareSite(111));
        sites.add(prepareSite(222));
        SitesCollection<OutputSite> collection = new SitesCollection<>("collection1", sites);
        return Collections.singletonList(collection);
    }

    private OutputSite prepareSite(int id) {
        OutputSite site = new OutputSite();
        site.setId(id);
        site.setName("name.com/path/" + id);
        site.setKeywords("kw1,kw2");
        site.setMobile(false);
        site.setScore(999.);
        return site;
    }
}