package com.garryiv.crawler.reader;

public class CollectionReaderException extends RuntimeException {
    public CollectionReaderException(String message) {
        super(message);
    }

    public CollectionReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
