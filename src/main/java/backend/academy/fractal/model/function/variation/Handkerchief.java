package backend.academy.fractal.model.function.variation;

import backend.academy.fractal.model.Dot;
import backend.academy.fractal.util.MathUtils;

public class Handkerchief implements Variation {
    @Override
    public Dot apply(Dot dot) {
        double norm = MathUtils.rad(dot);
        double vertAngle = MathUtils.vertAngle(dot);
        return new Dot(Math.sin(vertAngle + norm) * norm, Math.cos(vertAngle - norm) * norm);
    }
}
