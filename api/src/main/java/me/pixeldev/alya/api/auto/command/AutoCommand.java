package me.pixeldev.alya.api.auto.command;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoCommand {

	Property property() default Property.COMMAND;

	enum Property {
		AUTHORIZER("Authorizer"),
		MODULE("annotated.part.Module"),
		COMMAND("annotated.CommandClass");

		private final String name;

		Property(String name) {
			this.name = name;
		}

		public TypeMirror getType(ProcessingEnvironment processingEnv) {
			TypeElement element = processingEnv.getElementUtils()
					.getTypeElement("me.fixeddev.commandflow." + name);

			if (element == null) {
				return null;
			}

			return element.asType();
		}
	}
}
