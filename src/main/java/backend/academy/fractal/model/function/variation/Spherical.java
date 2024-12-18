package backend.academy.fractal.model.function.variation;

import backend.academy.fractal.model.Dot;
import backend.academy.fractal.util.MathUtils;

public class Spherical implements Variation {
    @Override
    public Dot apply(Dot dot) {
        double norm = MathUtils.squareRad(dot);
        return new Dot(dot.x() / norm, dot.y() / norm);
    }
}
