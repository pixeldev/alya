package me.pixeldev.alya.storage.universal.internal.meta;

import me.pixeldev.alya.jdk.reflect.FieldProvider;
import me.pixeldev.alya.jdk.reflect.MethodProvider;
import me.pixeldev.alya.storage.universal.Model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

public class ModelMeta<T extends Model> {

  private final Method prePersistMethod;
  private final Field cacheIdentifierField;

  public ModelMeta(Class<T> type) {
    Optional<Field> cacheIdentifierFieldOptional = FieldProvider.getFieldByAnnotation(
        type,
        CacheIdentifier.class
    );

    if (!cacheIdentifierFieldOptional.isPresent()) {
      throw new IllegalArgumentException("Model doesn't have cache identifier annotation.");
    }

    prePersistMethod = MethodProvider.getMethodByAnnotation(
        type,
        PrePersist.class
    ).orElse(null);

    cacheIdentifierField = cacheIdentifierFieldOptional.get();
  }

  public Field getCacheIdentifierField() {
    return cacheIdentifierField;
  }

  public Method getPrePersistMethod() {
    return prePersistMethod;
  }

}