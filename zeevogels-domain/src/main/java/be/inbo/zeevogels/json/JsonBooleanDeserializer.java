package be.inbo.zeevogels.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class JsonBooleanDeserializer extends JsonDeserializer<Boolean> {

	@Override
	public Boolean deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException,
			JsonProcessingException {
		switch (arg0.getValueAsString()) {
		case "true":
			return Boolean.TRUE;

		case "false":
			return Boolean.FALSE;
		default:
			return null;
		}
	}

}
