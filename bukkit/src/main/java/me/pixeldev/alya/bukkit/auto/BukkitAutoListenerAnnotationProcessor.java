package me.pixeldev.alya.bukkit.auto;

import me.pixeldev.alya.api.auto.listener.AutoListenerAnnotationProcessor;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@SupportedAnnotationTypes("me.pixeldev.alya.api.auto.listener.AutoListener")
public class BukkitAutoListenerAnnotationProcessor
		extends AutoListenerAnnotationProcessor {

	@Override
	public void write(final Set<? extends Element> elements) throws IOException {
		String packageName = "me.pixeldev.alya.bukkit.loader";
		String className = "AutoListenerLoader";
		JavaFileObject listenerRegisterFile = processingEnv.getFiler().createSourceFile(className);

		try (final PrintWriter out = new PrintWriter(listenerRegisterFile.openWriter())) {
			writeDefaults(out, elements, packageName, className);
			out.println();
			out.println("  @javax.inject.Inject private org.bukkit.plugin.Plugin plugin; ");
			out.println();
			out.println("  @Override");
			out.println("  public void load() {");
			out.println("    org.bukkit.plugin.PluginManager pluginManager = org.bukkit.Bukkit.getPluginManager();");

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
