package me.pixeldev.alya.api.auto.command;

import me.pixeldev.alya.api.auto.DefaultAnnotationProcessor;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.util.Set;

import static javax.tools.Diagnostic.Kind.ERROR;

public abstract class AutoCommandAnnotationProcessor extends DefaultAnnotationProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
												 RoundEnvironment roundEnvironment) {
		Set<? extends Element> commandProperties = roundEnvironment
				.getElementsAnnotatedWith(AutoCommand.class);

		commandProperties.removeIf(
				element -> {
					Set<Modifier> modifiers = element.getModifiers();

					for (Modifier modifier : modifiers) {
						if (!NOT_ABLE_MODIFIERS.contains(modifier)) {
							continue;
						}

						messager().printMessage(ERROR, "Cannot create an instance of this class.");

						return true;
					}

					TypeMirror type = element.getAnnotation(AutoCommand.class).property().getType(processingEnv);

					if (!isType(element.asType(), type)) {
						messager().printMessage(ERROR, "Element annotated with @AutoCommand is not available type!");

						return true;
					}

					return false;
				});

		if (commandProperties.size() == 0) {
			return false;
		}

		try {
			write(commandProperties);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}
}
