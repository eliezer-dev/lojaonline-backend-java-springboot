package dev.eliezer.lojaonline.providers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CustomBigDecimalSerializer extends JsonSerializer<BigDecimal> {

    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        BigDecimal roundedValue = value.setScale(2, RoundingMode.HALF_UP);
        gen.writeNumber(roundedValue);
    }
}
