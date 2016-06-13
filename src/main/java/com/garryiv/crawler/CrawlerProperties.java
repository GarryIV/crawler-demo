package com.garryiv.crawler;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link ConfigurationProperties} for a web crawler.
 */
@ConfigurationProperties
public class CrawlerProperties {

    /**
     * Input files (i.e. collections) location
     */
    private String pathToDirectory;

    /**
     * Output file location
     */
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

    @Override
    public String toString() {
        return "CrawlerProperties{" +
                "pathToDirectory='" + pathToDirectory + '\'' +
                ", outputFile='" + outputFile + '\'' +
                '}';
    }
}
