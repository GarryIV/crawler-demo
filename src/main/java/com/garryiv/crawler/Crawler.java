package com.garryiv.crawler;

import com.garryiv.crawler.keywords.KeywordService;
import com.garryiv.crawler.keywords.MockKeywordService;
import com.garryiv.crawler.keywords.SimpleKeywordService;
import com.garryiv.crawler.model.InputSite;
import com.garryiv.crawler.model.OutputSite;
import com.garryiv.crawler.model.SitesCollection;
import com.garryiv.crawler.processor.Processor;
import com.garryiv.crawler.reader.*;
import com.garryiv.crawler.writer.CollectionWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.util.Assert;

import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Configuration
@EnableConfigurationProperties(CrawlerProperties.class)
public class Crawler implements ApplicationRunner {

    private static Logger logger = LoggerFactory.getLogger(Crawler.class);

    @Autowired
    private CrawlerProperties properties;

    @Autowired
    private DirectoryReader directoryReader;

    @Autowired
    private Processor processor;

    @Autowired
    private CollectionWriter collectionWriter;

    @Autowired
    private ConfigurableApplicationContext context;

    @Bean
    public CollectionReader collectionReader() {
        ExtensionBasedCollectionReader collectionReader = new ExtensionBasedCollectionReader();
        collectionReader.addMapping("json", new JsonCollectionReader());
        collectionReader.addMapping("csv", new CsvCollectionReader());
        return collectionReader;
    }

    @Bean
    public CollectionWriter collectionWriter() {
        return new CollectionWriter();
    }

    @Bean(name = "keywordService")
    @Profile("mock-keywords")
    public KeywordService mockKeywordService() {
        return new MockKeywordService();
    }

    @Bean(name = "keywordService")
    @Profile("simple-keywords")
    @Primary
    public KeywordService simpleKeywordService() {
        return new SimpleKeywordService();
    }

    @Bean
    public DirectoryReader directoryReader() {
        return new DirectoryReader(collectionReader());
    }

    @Bean
    public Processor processor(KeywordService keywordService) {
        return new Processor(keywordService);
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(Crawler.class)
                .bannerMode(Banner.Mode.OFF).run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String pathToDirectory = properties.getPathToDirectory();
        String outputFile = properties.getOutputFile();

        logger.info("Input: {}", pathToDirectory);
        logger.info("Output: {}", outputFile);

        Assert.hasText(pathToDirectory);
        Assert.hasText(outputFile);

        List<SitesCollection<InputSite>> collections = directoryReader.read(Paths.get(pathToDirectory));
        CompletableFuture<List<SitesCollection<OutputSite>>> res = processor.process(collections);
        res.thenAccept(sites -> collectionWriter.write(sites, Paths.get(outputFile))).get();

        context.close();
    }
}
