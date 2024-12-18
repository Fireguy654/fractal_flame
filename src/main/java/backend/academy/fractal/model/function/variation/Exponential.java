package backend.academy.fractal.model.function.variation;

import backend.academy.fractal.model.Dot;

public class Exponential implements Variation {
    @Override
    public Dot apply(Dot dot) {
        double mult = Math.exp(dot.x() - 1);
        return new Dot(mult * Math.cos(Math.PI * dot.y()), mult * Math.sin(Math.PI * dot.y()));
    }
}
