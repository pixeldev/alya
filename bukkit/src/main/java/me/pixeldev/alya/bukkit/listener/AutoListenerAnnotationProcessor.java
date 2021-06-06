package me.pixeldev.alya.bukkit.listener;

import javax.annotation.processing.*;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import static javax.tools.Diagnostic.Kind.ERROR;
import static javax.tools.Diagnostic.Kind.NOTE;

@SupportedAnnotationTypes("me.pixeldev.alya.bukkit.listener.AutoListener")
public class AutoListenerAnnotationProcessor extends AbstractProcessor {

	private void write(final Set<? extends Element> elements) throws IOException {
		String packageName = "me.pixeldev.alya.bukkit.loader";
		String className = "AutoListenerLoader";
		JavaFileObject listenerRegisterFile = filer().createSourceFile(className);

		try (final PrintWriter out = new PrintWriter(listenerRegisterFile.openWriter())) {
			out.println("package " + packageName + ";");
			out.println();

			out.println("public class " + className + " implements me.pixeldev.alya.bukkit.loader.Loader {");
			out.println();

			out.println("  @javax.inject.Inject private org.bukkit.plugin.Plugin plugin; ");
			out.println();

			elements.forEach(listener -> out.println(
				"  @javax.inject.Inject private "
					+ listener
					+ " "
					+ listener.getSimpleName().toString().toLowerCase()
					+ ";"
			));

			out.println();
			out.println("  @Override");
			out.println("  public void load() {");
			out.println("    org.bukkit.plugin.PluginManager pluginManager = org.bukkit.Bukkit.getPluginManager();");
			out.println("    pluginManager.registerEvents(new me.pixeldev.alya.bukkit.gui.core.GUIListeners(), plugin);");

			elements.forEach(listener -> out.println(
				"    pluginManager.registerEvents(" + listener.getSimpleName().toString().toLowerCase() + ", plugin);"
			));

			out.print("  }");
			out.println();
			out.println("}");
		}
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
												 RoundEnvironment roundEnvironment) {

		final Set<? extends Element> listeners =
			roundEnvironment.getElementsAnnotatedWith(AutoListener.class);

		final TypeMirror listener = getTypeByClass();

		listeners.removeIf(
			element -> {
				if (element.getKind() != ElementKind.CLASS) {
					messager()
						.printMessage(
							NOTE,
							"Invalid element of type "
								+ element.getKind()
								+ " annotated with @AutoListener",
							element);
					return true;
				}

				if (element.getModifiers().contains(Modifier.STATIC)
					|| element.getModifiers().contains(Modifier.ABSTRACT)
					|| element.getModifiers().contains(Modifier.PRIVATE)
					|| element.getModifiers().contains(Modifier.PROTECTED)) {

					messager()
						.printMessage(
							ERROR, "Element annotated with " + "@AutoListener is not instanciable class!");
					return true;
				}

				if (!isType(element.asType(), listener)) {
					messager()
						.printMessage(
							ERROR,
							"Element annotated with @AutoListener is not type of org.bukkit.event.Listener!");
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

	private Messager messager() {
		return processingEnv.getMessager();
	}

	private Filer filer() {
		return processingEnv.getFiler();
	}

	private TypeMirror getTypeByClass() {
		TypeElement element = processingEnv.getElementUtils().getTypeElement("org.bukkit.event.Listener");
		if (element == null) {
			return null;
		}
		return element.asType();
	}

	private boolean isType(TypeMirror type, TypeMirror typeToCompare) {
		return processingEnv.getTypeUtils().isSubtype(type, typeToCompare);
	}

}