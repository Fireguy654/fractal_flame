package backend.academy.fractal.service;

import backend.academy.fractal.model.Dot;
import backend.academy.fractal.model.InfluenceDot;
import backend.academy.fractal.model.function.ColoredTransition;
import backend.academy.fractal.util.FlameRandom;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.List;

public class FlameGenerator {
    private final FlameRandom random;
    private final List<ColoredTransition> funcs;
    private final DoubleList probs;

    public FlameGenerator(FlameRandom random, List<ColoredTransition> funcs, DoubleList probs) {
        this.random = random;
        this.funcs = funcs;
        this.probs = probs;
    }

    public InfluenceDot getNextLight(Dot current) {
        int ind = random.getIndWithProbs(probs);
        Dot res = funcs.get(ind).func().apply(current);
        return new InfluenceDot(res, funcs.get(ind).color());
    }
}
