package backend.academy.fractal.model.function.variation;

import backend.academy.fractal.model.function.Transition;

public interface Variation extends Transition {
    static Variation getByName(String name) {
        return switch (name.toLowerCase()) {
            case "spherical" -> new Spherical();
            case "handkerchief" -> new Handkerchief();
            case "fisheye" -> new Fisheye();
            case "exponential" -> new Exponential();
            case "diamond" -> new Diamond();
            default -> throw new UnsupportedOperationException("Unsupported variation name: " + name);
        };
    }
}
