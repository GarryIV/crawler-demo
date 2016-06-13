package com.garryiv.crawler.keywords;

import com.garryiv.crawler.model.InputSite;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class SimpleKeywordServiceTest extends Assert {

    @Test
    @Ignore("Because of external resource usage")
    public void resolveKeywords() {
        KeywordService keywordService = new SimpleKeywordService();
        InputSite site = new InputSite();
        site.setName("github.com/");
        String keywords = keywordService.resolveKeywords(site);
        assertNotNull(keywords);
    }

}