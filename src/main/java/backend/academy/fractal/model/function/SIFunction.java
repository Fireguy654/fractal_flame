package backend.academy.fractal.model.function;

import backend.academy.fractal.model.Dot;
import backend.academy.fractal.model.function.variation.Variation;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.List;

public class SIFunction implements Transition {
    private final List<Variation> vars;
    private final DoubleList weights;
    private final LinearDotOperator transition;

    public SIFunction(List<Variation> vars, DoubleList weights, LinearDotOperator transition) {
        this.vars = vars;
        this.weights = weights;
        this.transition = transition;
    }

    @Override
    public Dot apply(Dot dot) {
        Dot transitedDot = transition.apply(dot);

        Dot res = Dot.ZERO;
        for (int i = 0; i < vars.size(); ++i) {
            res = res.add(vars.get(i).apply(transitedDot).multiply(weights.getDouble(i)));
        }
        return res;
    }
}
