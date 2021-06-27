package me.pixeldev.alya.api.auto;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static javax.tools.Diagnostic.Kind.ERROR;

@SupportedAnnotationTypes("me.pixeldev.alya.api.auto.AutoListener")
public class AutoListenerAnnotationProcessor extends AbstractProcessor {

	private static final Set<Modifier> NOT_ABLE_MODIFIERS;

	static {
		NOT_ABLE_MODIFIERS = new HashSet<>();
		NOT_ABLE_MODIFIERS.add(Modifier.STATIC);
		NOT_ABLE_MODIFIERS.add(Modifier.ABSTRACT);
		NOT_ABLE_MODIFIERS.add(Modifier.PRIVATE);
		NOT_ABLE_MODIFIERS.add(Modifier.PROTECTED);
	}

	private final AutoListenerWriter autoListenerWriter;

	public AutoListenerAnnotationProcessor(AutoListenerWriter autoListenerWriter) {
		this.autoListenerWriter = autoListenerWriter;
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
												 RoundEnvironment roundEnvironment) {

		Set<? extends Element> listeners = roundEnvironment
				.getElementsAnnotatedWith(AutoListener.class);

		TypeMirror listener = autoListenerWriter.getListenerType();

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
			autoListenerWriter.write(listeners);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	private Messager messager() {
		return processingEnv.getMessager();
	}

	private boolean isType(TypeMirror type, TypeMirror typeToCompare) {
		return processingEnv.getTypeUtils().isSubtype(type, typeToCompare);
	}

}