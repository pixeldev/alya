package me.pixeldev.alya.bungee.auto;

import me.pixeldev.alya.api.auto.command.AutoCommand;
import me.pixeldev.alya.api.auto.command.AutoCommandAnnotationProcessor;

import javax.lang.model.element.Element;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

public class BungeeAutoCommandAnnotationProcessor extends AutoCommandAnnotationProcessor {
	@Override
	protected void write(Set<? extends Element> elements) throws IOException {
		String packageName = "me.pixeldev.alya.bungee.loader";
		String className = "AutoCommandLoader";
		JavaFileObject listenerRegisterFile = processingEnv.getFiler().createSourceFile(className);

		try (final PrintWriter out = new PrintWriter(listenerRegisterFile.openWriter())) {
			writeDefaults(out, elements, packageName, className);
			out.println();
			out.println("  @javax.inject.Inject private net.md_5.bungee.api.plugin.Plugin plugin; ");
			out.println();

			out.println("  @Override");
			out.println("  public void load() {");
			out.println("    me.fixeddev.commandflow.CommandManager commandManager = new me.fixeddev.commandflow.bungee.BungeeCommandManager(plugin);");
			out.println("    me.fixeddev.commandflow.annotated.part.PartInjector partInjector = new me.fixeddev.commandflow.annotated.part.SimplePartInjector();");
			out.println("    partInjector.install(new me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule());");
			out.println("    partInjector.install(new me.fixeddev.commandflow.bungee.factory.BungeeModule());");


			out.println("    me.fixeddev.commandflow.annotated.builder.AnnotatedCommandBuilder builder = new me.fixeddev.commandflow.annotated.builder.AnnotatedCommandBuilderImpl(partInjector);");

			elements.forEach(commandProperty -> {
				AutoCommand.Property property  = commandProperty.getAnnotation(AutoCommand.class).property();
				switch (property) {
					case MODULE -> out.println("    partInjector.install(" + commandProperty.getSimpleName().toString().toLowerCase() + ");");
					case TRANSLATOR_PROVIDER -> out.println("    commandManager.setTranslator(new me.fixeddev.commandflow.translator.DefaultTranslator(" + commandProperty.getSimpleName().toString().toLowerCase() + ");");
					case USAGE_BUILDER -> out.println("    commandManager.setUsageBuilder(" + commandProperty.getSimpleName().toString().toLowerCase() + ");");
					case AUTHORIZER -> out.println("    commandManager.setAuthorizer(" + commandProperty.getSimpleName().toString().toLowerCase() + ");");
				}
			});

			out.println();

			elements.forEach(commandProperty -> {
				AutoCommand.Property property  = commandProperty.getAnnotation(AutoCommand.class).property();
				if(property == AutoCommand.Property.COMMAND) {
					out.println("    commandManager.registerCommands(builder.fromClass(" + commandProperty.getSimpleName().toString().toLowerCase() + "));");
				}
			});

			out.print("  }");
			out.println();
			out.println("}");
		}
	}

}