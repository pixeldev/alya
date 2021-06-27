package me.pixeldev.alya.api.auto;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.util.Set;

public interface AutoListenerWriter {

	void write(Set<? extends Element> elements) throws IOException;

	TypeMirror getListenerType();

}