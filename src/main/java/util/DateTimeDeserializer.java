package util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;

public class DateTimeDeserializer extends StdDeserializer<LocalDateTime> {
    public DateTimeDeserializer() {
        super((JavaType) null);
    }

    public DateTimeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser,
                                     DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        String dateTime = jsonParser.getText();
        return LocalDateTime.parse(dateTime);
    }


}
