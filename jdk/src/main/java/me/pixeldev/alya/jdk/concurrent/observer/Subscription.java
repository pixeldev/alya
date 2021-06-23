package me.pixeldev.alya.jdk.concurrent.observer;

public class Subscription<T> {

	private final Observable<T> observable;
	private final Observer<T> observer;

	public Subscription(Observable<T> observable, Observer<T> observer) {
		this.observable = observable;
		this.observer = observer;
	}

	public Observable<T> getObservable() {
		return observable;
	}

	public Observer<T> getObserver() {
		return observer;
	}

}