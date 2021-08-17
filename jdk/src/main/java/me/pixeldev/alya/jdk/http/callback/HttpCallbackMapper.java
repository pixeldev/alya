package me.pixeldev.alya.jdk.http.callback;

import java.io.Reader;

public interface HttpCallbackMapper<T> {

	T map(Reader reader, Class<T> type);

}