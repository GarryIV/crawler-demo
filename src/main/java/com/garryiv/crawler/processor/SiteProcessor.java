package com.garryiv.crawler.processor;

import com.garryiv.crawler.model.InputSite;
import com.garryiv.crawler.model.OutputSite;

import java.util.concurrent.CompletableFuture;

public interface SiteProcessor {
    CompletableFuture<OutputSite> process(InputSite site);
}
