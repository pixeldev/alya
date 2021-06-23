package me.pixeldev.alya.jdk.concurrent.observer;

public interface Observer<T> {

	void onNext(T model);

	default void onError(Throwable error) {
		error.printStackTrace();
	}

	default void onComplete() {

	}

	default void onSubscribe(Subscription<T> subscription) {

	}

}