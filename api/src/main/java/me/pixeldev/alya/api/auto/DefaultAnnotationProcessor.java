package me.pixeldev.alya.api.auto;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import static javax.tools.Diagnostic.Kind.ERROR;

public abstract class DefaultAnnotationProcessor extends AbstractProcessor {

	protected static final Set<Modifier> NOT_ABLE_MODIFIERS;

	static {
		NOT_ABLE_MODIFIERS = new HashSet<>();
		NOT_ABLE_MODIFIERS.add(Modifier.STATIC);
		NOT_ABLE_MODIFIERS.add(Modifier.ABSTRACT);
		NOT_ABLE_MODIFIERS.add(Modifier.PRIVATE);
		NOT_ABLE_MODIFIERS.add(Modifier.PROTECTED);
	}

	protected boolean internalProcess(RoundEnvironment roundEnvironment,
																		Class<? extends Annotation> annotation,
																		Predicate<Element> removeIf) {


		Set<? extends Element> elements = roundEnvironment
				.getElementsAnnotatedWith(annotation);

		elements.removeIf(
				element -> {
					Set<Modifier> modifiers = element.getModifiers();

					for (Modifier modifier : modifiers) {
						if (!NOT_ABLE_MODIFIERS.contains(modifier)) {
							continue;
						}

						messager().printMessage(ERROR, "Cannot create an instance of this class.");

						return true;
					}

					return removeIf.test(element);
				});

		if (elements.size() == 0) {
			return false;
		}

		try {
			write(elements);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	protected abstract void write(Set<? extends Element> elements) throws IOException;

	protected Messager messager() {
		return processingEnv.getMessager();
	}

	protected void writeDefaults(
			PrintWriter out,
			Set<? extends Element> elements,
			String packageName,
			String className
	) {
		out.println("package " + packageName + ";");
		out.println();

		out.println("public class " + className + " implements me.pixeldev.alya.api.loader.Loader {");
		out.println();

		elements.forEach(element -> out.println(
				"  @javax.inject.Inject private "
						+ element
						+ " "
						+ element.getSimpleName().toString().toLowerCase()
						+ ";"
		));
	}

	protected boolean isType(TypeMirror type, TypeMirror typeToCompare) {
		return processingEnv.getTypeUtils().isSubtype(type, typeToCompare);
	}

}
