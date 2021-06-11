package me.pixeldev.alya.storage.universal.service.type;

import me.pixeldev.alya.storage.universal.Model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalModelService<T extends Model> extends AbstractLocalModelService<T> {

  private final Map<String, T> cache;

  public LocalModelService() {
    this.cache = new ConcurrentHashMap<>();
  }

  @Override
  protected Map<String, T> cache() {
    return cache;
  }

}