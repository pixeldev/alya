package me.pixeldev.alya.storage.sql.meta;

import me.pixeldev.alya.storage.sql.connection.SQLProtocols;
import me.pixeldev.alya.storage.sql.identity.DataType;
import me.pixeldev.alya.storage.sql.identity.SQLConstraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLTable {

	String name();

	Element[] elements();

	String protocol() default SQLProtocols.MYSQL;

	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@interface Element {

		String column();

		DataType type();

		SQLConstraint[] constraints() default SQLConstraint.NOT_NULL;

	}

}