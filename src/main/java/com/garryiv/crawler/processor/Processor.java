package com.garryiv.crawler.processor;

import com.garryiv.crawler.keywords.KeywordService;
import com.garryiv.crawler.model.InputSite;
import com.garryiv.crawler.model.OutputSite;
import com.garryiv.crawler.model.SitesCollection;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Process sites collection
 */
public class Processor {

    private ExecutorService keywordsExtractorExecutor = Executors.newFixedThreadPool(5);

    private KeywordService keywordService;

    public Processor(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    public CompletableFuture<List<SitesCollection<OutputSite>>> process(List<SitesCollection<InputSite>> collections) {
        List<CompletableFuture<SitesCollection<OutputSite>>> all = collections.stream()
                .map(this::processCollection)
                .collect(Collectors.toList());

        return combine(all);
    }

    private CompletableFuture<SitesCollection<OutputSite>> processCollection(SitesCollection<InputSite> collection) {
        List<CompletableFuture<OutputSite>> list = collection.getSites().stream()
                .map(this::processSiteAsync)
                .collect(Collectors.toList());

        return combine(list).thenApply(sites -> new SitesCollection<>(collection.getCollectionId(), sites));
    }

    private CompletableFuture<OutputSite> processSiteAsync(InputSite site) {
        return CompletableFuture.supplyAsync(() -> processSite(site), keywordsExtractorExecutor);
    }

    private OutputSite processSite(InputSite inputSite) {
        OutputSite res = new OutputSite();
        res.setId(inputSite.getId());
        res.setName(inputSite.getName());
        res.setMobile(inputSite.isMobile());
        res.setScore(inputSite.getScore());
        res.setKeywords(keywordService.resolveKeywords(inputSite));
        return res;
    }


    @PreDestroy
    public void shutdown() throws InterruptedException {
        keywordsExtractorExecutor.shutdown();
        keywordsExtractorExecutor.awaitTermination(1, TimeUnit.MINUTES);
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
