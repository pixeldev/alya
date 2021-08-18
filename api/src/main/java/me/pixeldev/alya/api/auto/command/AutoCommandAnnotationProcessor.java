package me.pixeldev.alya.api.auto.command;

import me.pixeldev.alya.api.auto.DefaultAnnotationProcessor;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.Set;

import static javax.tools.Diagnostic.Kind.ERROR;

public abstract class AutoCommandAnnotationProcessor extends DefaultAnnotationProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
												 RoundEnvironment roundEnvironment) {

		return internalProcess(
				roundEnvironment, AutoCommand.class,
				element -> {
					TypeMirror type = element.getAnnotation(AutoCommand.class).property().getType(processingEnv);

					if (!isType(element.asType(), type)) {
						messager().printMessage(ERROR, "Element annotated with @AutoCommand is not available type!");

						return true;
					}
					return false;
				}
		);
	}
}
