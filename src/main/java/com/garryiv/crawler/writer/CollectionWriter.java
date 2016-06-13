package com.garryiv.crawler.writer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.garryiv.crawler.model.OutputSite;
import com.garryiv.crawler.model.SitesCollection;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

public class CollectionWriter {
    private ObjectMapper mapper = new ObjectMapper()
            .configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false)
            .configure(SerializationFeature.CLOSE_CLOSEABLE, false)
            .configure(SerializationFeature.FLUSH_AFTER_WRITE_VALUE, true);

    private String lineSeparator = System.lineSeparator();

    public void write(Collection<SitesCollection<OutputSite>> collections, Path filePath) {
        makeParentDirectory(filePath);

        try (Writer writer = new OutputStreamWriter(Files.newOutputStream(filePath), StandardCharsets.UTF_8)) {
            for (SitesCollection<OutputSite> collection : collections) {
                mapper.writeValue(writer, collection);
                writer.write(lineSeparator);
            }
        } catch (Exception e) {
            throw new CollectionWriterException("Can't write file " + filePath);
        }
    }

    private void makeParentDirectory(Path filePath) {
        try {
            Files.createDirectories(filePath.getParent());
        } catch (IOException e) {
            throw new CollectionWriterException("Can't configure parent for " + filePath);
        }
    }
}
