package org.geometryService.services.checker.plot;

@FunctionalInterface
public interface CheckerFunction {
    boolean check(float x, float y, float r);
}
