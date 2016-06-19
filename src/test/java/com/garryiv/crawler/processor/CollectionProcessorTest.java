package com.garryiv.crawler.processor;

import com.garryiv.crawler.model.InputSite;
import com.garryiv.crawler.model.OutputSite;
import com.garryiv.crawler.model.SitesCollection;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CollectionProcessorTest extends Assert {

    private CollectionProcessor collectionProcessor = new CollectionProcessor(input -> {
        OutputSite output = new OutputSite();
        output.setName(input.getName());
        output.setKeywords(input.getName());
        return CompletableFuture.completedFuture(output);
    });

    @Test
    public void process() throws Exception {
        List<SitesCollection<InputSite>> input = generate();
        List<SitesCollection<OutputSite>> output = collectionProcessor.process(input).get();

        assertNotNull(output);
        assertEquals(input.size(), output.size());

        for (SitesCollection<OutputSite> collection : output) {
            for (OutputSite site : collection.getSites()) {
                assertEquals(site.getName(), site.getKeywords());
            }
        }
    }

    private List<SitesCollection<InputSite>> generate() {
        List<SitesCollection<InputSite>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<InputSite> sites = new ArrayList<>();
            for (int j = 0; j < 100; j++) {
                InputSite site = new InputSite();
                site.setId(i * 1000 + j);
                site.setScore(111.);
                site.setName("example.com/input" + site.getId());
                sites.add(site);
            }
            list.add(new SitesCollection<>("input.json", sites));
        }
        return list;
    }

}