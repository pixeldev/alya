package me.pixeldev.alya.api.auto.listener;

import me.pixeldev.alya.api.auto.DefaultAnnotationProcessor;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import java.util.Set;

import static javax.tools.Diagnostic.Kind.ERROR;

public abstract class AutoListenerAnnotationProcessor extends DefaultAnnotationProcessor {

	protected abstract TypeMirror getListenerType();

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
												 RoundEnvironment roundEnvironment) {

		TypeMirror listener = getListenerType();

		return internalProcess(
				roundEnvironment, AutoListener.class,
				element -> {
					if (!isType(element.asType(), listener)) {
						messager().printMessage(ERROR, "Element annotated with @AutoListener is not type of Listener!");

						return true;
					}
					return false;
				}
		);
	}
}