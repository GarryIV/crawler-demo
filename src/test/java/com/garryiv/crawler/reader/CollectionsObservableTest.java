package com.garryiv.crawler.reader;


import com.garryiv.crawler.keywords.KeywordService;
import com.garryiv.crawler.model.InputSite;
import com.garryiv.crawler.model.OutputSite;
import com.garryiv.crawler.model.SitesCollection;
import com.garryiv.crawler.writer.CollectionWriter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionsObservableTest extends Assert {

    private ExecutorService keywordsExtractorExecutor = Executors.newFixedThreadPool(5);
    private ExtensionBasedCollectionReader collectionReader;
    private CollectionWriter collectionWriter;
    private KeywordService keywordService;

    @Before
    public void init() {
        collectionReader = new ExtensionBasedCollectionReader();
        collectionReader.addMapping("json", new JsonCollectionReader());
        collectionReader.addMapping("csv", new CsvCollectionReader());

        collectionWriter = new CollectionWriter();

        keywordService = site -> Stream.of(site.getName().split("[^a-zA-Z0-9]"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(", "));
    }

    @Test(timeout = 10000)
    public void test() throws Exception {
        Path input = Paths.get("input");
        Path output = Paths.get("output/output.txt");

        CompletableFuture<Void> combined = process(input, output);

        combined.get();
    }

    private CompletableFuture<Void> process(Path input, Path output) throws IOException {
        List<SitesCollection<InputSite>> collections = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(input)) {
            for (Path path : stream) {
                SitesCollection<InputSite> collection = collectionReader.read(path.getFileName().toString(), path);
                collections.add(collection);
            }
        }

        List<CompletableFuture<SitesCollection<OutputSite>>> all = collections.stream()
                .map(this::process)
                .collect(Collectors.toList());

        return combine(all).thenAccept(sites -> collectionWriter.write(sites, output));
    }

    private CompletableFuture<SitesCollection<OutputSite>> process(SitesCollection<InputSite> collection) {
        List<CompletableFuture<OutputSite>> list = collection.getSites().stream()
                .map(inputSite -> CompletableFuture.supplyAsync(() -> processSite(inputSite), keywordsExtractorExecutor))
                .collect(Collectors.toList());

        return combine(list).thenApply(sites -> new SitesCollection<>(collection.getCollectionId(), sites));
    }

    private static <T> CompletableFuture<List<T>> combine(List<CompletableFuture<T>> futures) {
         return CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
                .thenApply(aVoid -> futures.stream()
                                .map(CompletableFuture::join)
                                .collect(Collectors.toList()));
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

}