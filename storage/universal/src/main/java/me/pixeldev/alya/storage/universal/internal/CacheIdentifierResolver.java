package me.pixeldev.alya.storage.universal.internal;

import me.pixeldev.alya.jdk.reflect.FieldProvider;
import me.pixeldev.alya.storage.universal.Model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CacheIdentifierResolver<T extends Model> {

  private final Map<String, String> resolver;
  private final Field cacheIdentifierField;

  public CacheIdentifierResolver(Field cacheIdentifierField) {
    this.cacheIdentifierField = cacheIdentifierField;

    resolver = new HashMap<>();
  }

  public void removeResolver(String cacheIdentifier) {
    resolver.remove(cacheIdentifier);
  }

  public void removeResolver(T model) {
    removeResolver(invoke(model));
  }

  public void addResolver(String cacheIdentifier, String modelId) {
    resolver.put(cacheIdentifier, modelId);
  }

  public void addResolver(T model) {
    addResolver(invoke(model), model.getId());
  }

  public String resolve(String cacheIdentifier) {
    return resolver.get(cacheIdentifier);
  }

  public String resolve(T model) {
    return resolve(invoke(model));
  }

  private String invoke(T model) {
    Object cacheIdentifierInstance;

    try {
      cacheIdentifierInstance = FieldProvider.invoke(cacheIdentifierField, model);
    } catch (IllegalAccessException e) {
      return null;
    }

    return cacheIdentifierInstance.toString();
  }

}