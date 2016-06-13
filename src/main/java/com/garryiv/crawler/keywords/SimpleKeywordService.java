package com.garryiv.crawler.keywords;

import com.garryiv.crawler.model.InputSite;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Makes http request and extract keywords using Jsoup.
 */
public class SimpleKeywordService implements KeywordService {

    private static Logger logger = LoggerFactory.getLogger(SimpleKeywordService.class);

    @Override
    public String resolveKeywords(InputSite site) {
        try {
            return extractKeywords(Jsoup.parse(toURL(site), 10000));
        } catch (IOException | KeywordServiceException e) {
            logger.info("Can't resolve keywords from {} - {}", site, e.getMessage());
            return "";
        }
    }

    private String extractKeywords(Document document) {
        return Stream.of(document.text().split("[^\\p{L}\\p{N}]"))
            .filter(s -> s.length() > 2)
            .sorted()
            .collect(Collectors.joining(","));
    }

    private URL toURL(InputSite site) {
        String url = site.getName();
        if (!url.startsWith("http://") || !url.startsWith("https://")) {
            url = "http://" + url;
        }
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new KeywordServiceException("Bad url " + url, e);
        }
    }
}
