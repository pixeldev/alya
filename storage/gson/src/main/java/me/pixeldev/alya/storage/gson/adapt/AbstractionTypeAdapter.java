package me.pixeldev.alya.storage.gson.adapt;

import com.google.gson.*;

import java.lang.reflect.Type;

public class AbstractionTypeAdapter<T>
		implements JsonSerializer<T>, JsonDeserializer<T> {

	private static final String CLASS_KEY = "class";
	private static final String VALUE_KEY = "value";

	@Override
	public JsonElement serialize(T object,
															 Type type,
															 JsonSerializationContext jsonSerializationContext) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(CLASS_KEY, object.getClass().getName());
		jsonObject.add(VALUE_KEY, jsonSerializationContext.serialize(object));
		return jsonObject;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T deserialize(JsonElement jsonElement,
											 Type type,
											 JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		JsonPrimitive primitive = (JsonPrimitive) jsonObject.get(CLASS_KEY);
		String className = primitive.getAsString();
		try {
			Class<? extends T> clazz =
					(Class<? extends T>) Class.forName(className);

			return jsonDeserializationContext.deserialize(
					jsonObject.get(VALUE_KEY), clazz
			);
		} catch (ClassNotFoundException e) {
			throw new JsonParseException(e.getMessage());
		}
	}
}
