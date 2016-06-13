package com.garryiv.crawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CrawlerProperties.class)
public class Crawler implements ApplicationRunner {

    @Autowired
    private CrawlerProperties properties;

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(Crawler.class)
                .bannerMode(Banner.Mode.OFF).run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(properties.getPathToDirectory());
        System.out.println(properties.getOutputFile());
    }
}
