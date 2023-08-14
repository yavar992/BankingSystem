package myWallets.myWallets.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ProtectDataSerializer extends JsonSerializer {
    @Override
    public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String pii = value.toString().replaceAll("\\w(?=\\w{4})", "x");
        jsonGenerator.writeString(pii);
    }
}
