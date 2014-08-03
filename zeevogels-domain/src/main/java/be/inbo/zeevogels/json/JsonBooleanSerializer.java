package be.inbo.zeevogels.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsonBooleanSerializer extends JsonSerializer<Boolean> {

	@Override
	public void serialize(Boolean arg0, JsonGenerator jsonGen, SerializerProvider arg2) throws IOException,
			JsonProcessingException {
		jsonGen.writeString("" + arg0);
	}

}
