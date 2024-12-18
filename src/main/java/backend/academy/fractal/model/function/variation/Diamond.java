package backend.academy.fractal.model.function.variation;

import backend.academy.fractal.model.Dot;
import backend.academy.fractal.util.MathUtils;

public class Diamond implements Variation {
    @Override
    public Dot apply(Dot dot) {
        double vertAngle = MathUtils.vertAngle(dot);
        double norm = MathUtils.rad(dot);
        return new Dot(Math.sin(vertAngle) * Math.cos(norm), Math.cos(vertAngle) * Math.sin(norm));
    }
}
