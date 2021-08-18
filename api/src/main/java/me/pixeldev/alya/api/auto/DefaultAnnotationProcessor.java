package me.pixeldev.alya.api.auto;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public abstract class DefaultAnnotationProcessor extends AbstractProcessor {

	protected static final Set<Modifier> NOT_ABLE_MODIFIERS;

	static {
		NOT_ABLE_MODIFIERS = new HashSet<>();
		NOT_ABLE_MODIFIERS.add(Modifier.STATIC);
		NOT_ABLE_MODIFIERS.add(Modifier.ABSTRACT);
		NOT_ABLE_MODIFIERS.add(Modifier.PRIVATE);
		NOT_ABLE_MODIFIERS.add(Modifier.PROTECTED);
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
