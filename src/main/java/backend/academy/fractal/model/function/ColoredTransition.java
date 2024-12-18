package backend.academy.fractal.model.function;

import backend.academy.fractal.model.Color;

public record ColoredTransition(Transition func, Color color) {
    public static final ColoredTransition SYMMETRY = new ColoredTransition(
        new LinearDotOperator(-1, 0, 0, -1, 0, 0),
        null
    );
}
