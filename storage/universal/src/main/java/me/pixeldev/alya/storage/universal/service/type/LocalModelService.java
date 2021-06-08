package me.pixeldev.alya.storage.universal.service.type;

import me.pixeldev.alya.storage.universal.Model;
import me.pixeldev.alya.storage.universal.internal.meta.ModelMeta;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalModelService<T extends Model> extends AbstractLocalModelService<T> {

  private final Map<String, T> cache;

  public LocalModelService(ModelMeta<T> modelMeta) {
    super(modelMeta);

    this.cache = new ConcurrentHashMap<>();
  }

  @Override
  protected Map<String, T> cache() {
    return cache;
  }

}