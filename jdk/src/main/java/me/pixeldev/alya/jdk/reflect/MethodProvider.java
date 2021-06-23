package me.pixeldev.alya.jdk.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Predicate;

public final class MethodProvider {

	private MethodProvider() {
		throw new UnsupportedOperationException("This class can't be initialized.");
	}

	public static void invoke(Method method, Object instance)
			throws InvocationTargetException, IllegalAccessException {
		boolean accessible = method.isAccessible();

		try {
			method.setAccessible(true);
			method.invoke(instance);
		} finally {
			method.setAccessible(accessible);
		}
	}

	public static Optional<Method> getMethodByAnnotation(Class<?> parent,
																											 Class<? extends Annotation> annotation) {
		return getMethod(parent, method -> method.isAnnotationPresent(annotation));
	}

	public static Optional<Method> getMethod(Class<?> parent,
																					 Predicate<Method> condition) {
		for (Method method : getMethods(parent)) {
			if (!condition.test(method)) {
				continue;
			}

			return Optional.of(method);
		}

		return Optional.empty();
	}

	public static Method[] getMethods(Class<?> parent) {
		return parent.getDeclaredMethods();
	}

}