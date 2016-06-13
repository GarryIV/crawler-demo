package com.garryiv.crawler.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Serialize boolean as 0 and 1
 */
public class NumericBooleanSerializer extends JsonSerializer<Boolean> {
    @Override
    public void serialize(Boolean value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeNumber(value ? 1 : 0);
    }
}
