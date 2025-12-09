package org.geometryService.services.plot;

@FunctionalInterface
public interface CheckerFunction {
    boolean check(float x, float y, float r);
}
