package me.pixeldev.alya.jdk.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Optional;
import java.util.function.Predicate;

public final class FieldProvider {

	private FieldProvider() {
		throw new UnsupportedOperationException("This class can't be initialized.");
	}

	public static Object invoke(Field field, Object modelInstance) throws IllegalAccessException {
		boolean accessible = field.isAccessible();
		try {
			field.setAccessible(true);

			return field.get(modelInstance);
		} finally {
			field.setAccessible(accessible);
		}
	}

	public static void set(Field field,
												 Object classInstance,
												 Object value) throws IllegalAccessException {
		boolean accessible = field.isAccessible();

		try {
			field.setAccessible(true);
			field.set(classInstance, value);
		} finally {
			field.setAccessible(accessible);
		}
	}

	public static Optional<Field> getFieldByAnnotation(Class<?> parent,
																										 Class<? extends Annotation> annotation) {
		return getField(parent, field -> field.isAnnotationPresent(annotation));
	}

	public static Optional<Field> getField(Class<?> parent, Predicate<Field> condition) {
		for (Field field : parent.getDeclaredFields()) {
			if (!condition.test(field)) {
				continue;
			}

			return Optional.of(field);
		}

		return Optional.empty();
	}

}