package com.garryiv.crawler.keywords;

import com.garryiv.crawler.model.InputSite;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Mock implementation of keyword service
 */
public class MockKeywordService implements KeywordService {
    @Override
    public String resolveKeywords(InputSite site) {
        return Stream.of(site.getName().split("[^a-zA-Z0-9]"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(","));
    }
}
