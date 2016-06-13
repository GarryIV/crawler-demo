package com.garryiv.crawler.reader;

import com.garryiv.crawler.model.InputSite;
import com.garryiv.crawler.model.SitesCollection;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Reads collection from CSV file.
 */
public class CsvCollectionReader implements CollectionReader {
    private static final CSVFormat FORMAT = CSVFormat.DEFAULT
            .withHeader(Record.class)
            .withSkipHeaderRecord();

    @Override
    public SitesCollection<InputSite> read(String collectionId, Path path) {
        try (Reader in = new FileReader(path.toFile())){
            Iterable<CSVRecord> records = FORMAT.parse(in);
            ArrayList<InputSite> sites = new ArrayList<>();
            for (CSVRecord record : records) {
                sites.add(readSite(record));
            }
            return new SitesCollection<>(collectionId, sites);
        } catch (Exception e) {
            throw new CollectionReaderException("Can't read " + collectionId + " from " + path, e);
        }
    }

    private InputSite readSite(CSVRecord record) {
        InputSite site = new InputSite();
        site.setId(Long.parseLong(record.get(Record.ID)));
        site.setName(record.get(Record.NAME));
        site.setMobile(Boolean.parseBoolean(record.get(Record.IS_MOBILE)));
        site.setScore(Double.parseDouble(record.get(Record.SCORE)));
        return site;
    }

    private enum Record {
        ID,
        NAME,
        IS_MOBILE,
        SCORE
    }
}
