package com.garryiv.crawler.processor;

import com.garryiv.crawler.keywords.KeywordService;
import com.garryiv.crawler.model.InputSite;
import com.garryiv.crawler.model.OutputSite;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@ConfigurationProperties(prefix = "site-processor")
public class SiteProcessorImpl implements SiteProcessor {
    private int threadPoolSize = 5;

    private ExecutorService executor;

    private KeywordService keywordService;

    public SiteProcessorImpl(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @Override
    public CompletableFuture<OutputSite> process(InputSite site) {
        Assert.notNull(executor);
        return CompletableFuture.supplyAsync(() -> processSite(site), executor);
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

    @PostConstruct
    public void init() {
        executor = Executors.newFixedThreadPool(threadPoolSize);
    }

    @PreDestroy
    public void shutdown() throws InterruptedException {
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }
}
