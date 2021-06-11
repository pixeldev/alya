package me.pixeldev.alya.storage.gson.adapt;

import com.google.gson.*;

import java.lang.reflect.Type;

public class AbstractionTypeAdapter<T>
    implements JsonSerializer<T>, JsonDeserializer<T> {

  private final String className;

  public AbstractionTypeAdapter(Class<T> clazz) {
    className = clazz.getName();
  }

  @Override
  public JsonElement serialize(T object,
                               Type type,
                               JsonSerializationContext jsonSerializationContext) {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("class", className);
    jsonObject.add("value", jsonSerializationContext.serialize(object));
    return jsonObject;
  }

  @SuppressWarnings("unchecked")
  @Override
  public T deserialize(JsonElement jsonElement,
                       Type type,
                       JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    JsonObject jsonObject = jsonElement.getAsJsonObject();
    JsonPrimitive primitive = (JsonPrimitive) jsonObject.get("class");
    String className = primitive.getAsString();
    try {
      Class<? extends T> clazz =
          (Class<? extends T>) Class.forName(className);

      return jsonDeserializationContext.deserialize(
          jsonObject.get("value"), clazz
      );
    } catch (ClassNotFoundException e) {
      throw new JsonParseException(e.getMessage());
    }
  }
}
