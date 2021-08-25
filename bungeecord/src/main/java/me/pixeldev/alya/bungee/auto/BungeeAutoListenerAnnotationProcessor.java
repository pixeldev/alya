package me.pixeldev.alya.bungee.auto;

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
public class BungeeAutoListenerAnnotationProcessor
		extends AutoListenerAnnotationProcessor {

	@Override
	public void write(final Set<? extends Element> elements) throws IOException {
		String packageName = "me.pixeldev.alya.bungee.loader";
		String className = "AutoListenerLoader";
		JavaFileObject listenerRegisterFile = processingEnv.getFiler().createSourceFile(className);

		try (final PrintWriter out = new PrintWriter(listenerRegisterFile.openWriter())) {
			writeDefaults(out, elements, packageName, className);
			out.println();
			out.println("  @javax.inject.Inject private net.md_5.bungee.api.plugin.Plugin plugin; ");
			out.println();
			out.println("  @Override");
			out.println("  public void load() {");
			out.println(
					"    net.md_5.bungee.api.plugin.PluginManager pluginManager " +
					"= net.md_5.bungee.api.ProxyServer.getInstance().getPluginManager();"
			);

			elements.forEach(listener -> out.println(
					"    pluginManager.registerListener(plugin, " + listener.getSimpleName().toString().toLowerCase() + ");"
			));

			out.print("  }");
			out.println();
			out.println("}");
		}
	}

	@Override
	public TypeMirror getListenerType() {
		TypeElement element = processingEnv.getElementUtils()
				.getTypeElement("net.md_5.bungee.api.plugin.Listener");

		if (element == null) {
			return null;
		}

		return element.asType();
	}

}