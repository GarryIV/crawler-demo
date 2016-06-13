package com.garryiv.crawler.reader;

import com.garryiv.crawler.CrawlerException;

/**
 * Exception while reading collection file
 */
public class CollectionReaderException extends CrawlerException {
    public CollectionReaderException(String message) {
        super(message);
    }

    public CollectionReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
