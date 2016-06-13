package com.garryiv.crawler;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class CrawlerProperties {

    private String pathToDirectory;
    private String outputFile;

    public String getPathToDirectory() {
        return pathToDirectory;
    }

    public void setPathToDirectory(String pathToDirectory) {
        this.pathToDirectory = pathToDirectory;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }
}
