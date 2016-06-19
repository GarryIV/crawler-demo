package com.garryiv.crawler.processor;

import com.garryiv.crawler.model.InputSite;
import com.garryiv.crawler.model.OutputSite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SiteProcessorImplTest {

    private SiteProcessorImpl siteProcessor;

    @Before
    public void initProcessor() {
        siteProcessor = new SiteProcessorImpl(InputSite::getName);
        siteProcessor.init();
    }

    @After
    public void shutdownProcessor() throws InterruptedException {
        siteProcessor.shutdown();
    }

    @Test
    public void process() throws Exception {
        InputSite input = new InputSite();
        input.setId(12);
        input.setName("name");
        input.setScore(22.);
        input.setMobile(true);

        OutputSite output = siteProcessor.process(input).get();

        assertNotNull(output);
        assertEquals(input.getId(), output.getId());
        assertEquals(input.getName(), output.getName());
        assertEquals(input.getName(), output.getKeywords());
        assertEquals(input.getScore(), output.getScore(), 0.);
        assertEquals(input.isMobile(), output.isMobile());
    }

}