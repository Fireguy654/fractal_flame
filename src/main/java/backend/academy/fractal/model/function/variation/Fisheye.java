package backend.academy.fractal.model.function.variation;

import backend.academy.fractal.model.Dot;
import backend.academy.fractal.util.MathUtils;

public class Fisheye implements Variation {
    @Override
    public Dot apply(Dot dot) {
        double mult = 2 / (MathUtils.rad(dot) + 1);
        return new Dot(mult * dot.y(), mult * dot.x());
    }
}
