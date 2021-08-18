package me.pixeldev.alya.api.auto.listener;

import me.pixeldev.alya.api.auto.DefaultAnnotationProcessor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static javax.tools.Diagnostic.Kind.ERROR;

public abstract class AutoListenerAnnotationProcessor extends DefaultAnnotationProcessor {

	protected abstract TypeMirror getListenerType();

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
												 RoundEnvironment roundEnvironment) {

		Set<? extends Element> listeners = roundEnvironment
				.getElementsAnnotatedWith(AutoListener.class);

		TypeMirror listener = getListenerType();

		listeners.removeIf(
				element -> {
					Set<Modifier> modifiers = element.getModifiers();

					for (Modifier modifier : modifiers) {
						if (!NOT_ABLE_MODIFIERS.contains(modifier)) {
							continue;
						}

						messager().printMessage(ERROR, "Cannot create an instance of this class.");

						return true;
					}

					if (!isType(element.asType(), listener)) {
						messager().printMessage(ERROR, "Element annotated with @AutoListener is not type of Listener!");

						return true;
					}

					return false;
				});

		if (listeners.size() == 0) {
			return false;
		}

		try {
			write(listeners);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}
}