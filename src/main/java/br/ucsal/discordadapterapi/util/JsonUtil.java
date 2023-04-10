package br.ucsal.discordadapterapi.util;

import java.util.Collection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	
	public JsonUtil() {
		throw new UnsupportedOperationException("Classe utilitaria!");
	}
	
	private static ObjectMapper obterObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper;
	}
	
	public static String toJson(Object o) throws JsonProcessingException {
		ObjectMapper objectMapper = obterObjectMapper();
		return objectMapper.writeValueAsString(o);
	}
	
	public static <T> T fromJson(String json, Class<T> valueType) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = obterObjectMapper();
		return objectMapper.readValue(json, valueType);
	}
	
	public static <T, C extends Collection<?>> C fromJson(String json, Class<C> collectionClass, Class<T> elementClass) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = obterObjectMapper();
		JavaType type = objectMapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
		return objectMapper.readValue(json, type);
	}

}
