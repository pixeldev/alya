package me.pixeldev.alya.storage.universal.internal.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Cached {

	CacheType value() default CacheType.LOCAL;

	enum CacheType { LOCAL, REDIS }

}