package com.garryiv.crawler.writer;

import com.garryiv.crawler.CrawlerException;

/**
 * Exception while writing collection file
 */
public class CollectionWriterException extends CrawlerException {
    public CollectionWriterException(String message) {
        super(message);
    }

    public CollectionWriterException(String message, Throwable cause) {
        super(message, cause);
    }
}
