package com.garryiv.crawler.processor;

import com.garryiv.crawler.model.InputSite;
import com.garryiv.crawler.model.OutputSite;
import com.garryiv.crawler.model.SitesCollection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ProcessorTest extends Assert {

    private Processor processor;

    @Before
    public void init() {
        processor = new Processor(InputSite::getName);
    }

    @After
    public void clean() throws InterruptedException {
        processor.shutdown();
    }

    @Test(timeout = 10000)
    public void process() throws Exception {

        List<SitesCollection<InputSite>> input = generate();

        List<SitesCollection<OutputSite>> output = processor.process(input).get();

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