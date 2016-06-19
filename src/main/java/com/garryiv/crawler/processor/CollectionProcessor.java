package com.garryiv.crawler.processor;

import com.garryiv.crawler.model.InputSite;
import com.garryiv.crawler.model.OutputSite;
import com.garryiv.crawler.model.SitesCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Process sites collection
 */
public class CollectionProcessor {

    private static final Logger logger = LoggerFactory.getLogger(CollectionProcessor.class);

    private final SiteProcessor siteProcessor;

    public CollectionProcessor(SiteProcessor siteProcessor) {
        this.siteProcessor = siteProcessor;
    }

    public CompletableFuture<List<SitesCollection<OutputSite>>> process(List<SitesCollection<InputSite>> collections) {
        logger.info("Processing has been started");

        List<CompletableFuture<SitesCollection<OutputSite>>> all = collections.stream()
                .map(this::processCollection)
                .collect(Collectors.toList());

        CompletableFuture<List<SitesCollection<OutputSite>>> futureResult = combine(all);
        futureResult.thenAccept(result -> logger.info("Processing has been finished"));
        return futureResult;
    }

    private CompletableFuture<SitesCollection<OutputSite>> processCollection(SitesCollection<InputSite> collection) {
        List<CompletableFuture<OutputSite>> list = collection.getSites().stream()
                .map(siteProcessor::process)
                .collect(Collectors.toList());

        return combine(list)
                .thenApply(sites -> {
                    logger.info("Collection {} has been processed", collection.getCollectionId());
                    return new SitesCollection<>(collection.getCollectionId(), sites);
                });
    }

    /**
     * Combine list of completable futures into one.
     */
    private static <T> CompletableFuture<List<T>> combine(List<CompletableFuture<T>> futures) {
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
                .thenApply(
                        v -> futures.stream()
                                .map(CompletableFuture::join)
                                .collect(Collectors.toList())
                );
    }
}
