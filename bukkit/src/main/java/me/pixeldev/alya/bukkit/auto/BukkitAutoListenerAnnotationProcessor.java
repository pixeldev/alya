package me.pixeldev.alya.bukkit.auto;

import me.pixeldev.alya.api.auto.AutoListenerAnnotationProcessor;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@SupportedAnnotationTypes("me.pixeldev.alya.api.auto.AutoListener")
public class BukkitAutoListenerAnnotationProcessor
		extends AutoListenerAnnotationProcessor {

	@Override
	public void write(final Set<? extends Element> elements) throws IOException {
		String packageName = "me.pixeldev.alya.bukkit.loader";
		String className = "AutoListenerLoader";
		JavaFileObject listenerRegisterFile = processingEnv.getFiler().createSourceFile(className);

		try (final PrintWriter out = new PrintWriter(listenerRegisterFile.openWriter())) {
			out.println("package " + packageName + ";");
			out.println();

			out.println("public class " + className + " implements me.pixeldev.alya.api.loader.Loader {");
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
			out.println("    pluginManager.registerEvents(new team.unnamed.gui.core.GUIListeners(), plugin);");

			elements.forEach(listener -> out.println(
					"    pluginManager.registerEvents(" + listener.getSimpleName().toString().toLowerCase() + ", plugin);"
			));

			out.print("  }");
			out.println();
			out.println("}");
		}
	}

	@Override
	public TypeMirror getListenerType() {
		TypeElement element = processingEnv.getElementUtils()
				.getTypeElement("org.bukkit.event.Listener");

		if (element == null) {
			return null;
		}

		return element.asType();
	}

}