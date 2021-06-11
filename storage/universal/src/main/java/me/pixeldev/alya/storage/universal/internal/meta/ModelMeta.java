package me.pixeldev.alya.storage.universal.internal.meta;

import me.pixeldev.alya.jdk.reflect.MethodProvider;
import me.pixeldev.alya.storage.universal.Model;

import java.lang.reflect.Method;

public class ModelMeta<T extends Model> {

  private final Method prePersistMethod;
  private final Class<T> type;

  public ModelMeta(Class<T> type) {
    this.type = type;
    prePersistMethod = MethodProvider.getMethodByAnnotation(
        type,
        PrePersist.class
    ).orElse(null);
  }

  public Class<T> getType() {
    return type;
  }

  public Method getPrePersistMethod() {
    return prePersistMethod;
  }

}