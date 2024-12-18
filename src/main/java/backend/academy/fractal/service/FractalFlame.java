package backend.academy.fractal.service;

import backend.academy.fractal.model.Color;
import backend.academy.fractal.model.Dot;
import backend.academy.fractal.model.InfluenceDot;
import backend.academy.fractal.model.function.ColoredTransition;
import backend.academy.fractal.model.function.LinearDotOperator;
import backend.academy.fractal.model.function.SIFunction;
import backend.academy.fractal.model.function.variation.Variation;
import backend.academy.fractal.util.FlameRandom;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FractalFlame {
    private final FlameGraphicalDriver driver;
    private final FlameRandom random;

    private static final int FUNCTIONS_AMOUNT = 5;
    private static final List<Color> FUNCTION_COLORS = List.of(
        new Color(100, 13, 95),
        new Color(217, 22, 86),
        new Color(235, 91, 0),
        new Color(255, 178, 0),
        new Color(0, 11, 88)
    );

    private static final int DOT_AMOUNT = 100;
    private static final int SKIP_AMOUNT = 20;

    public void flame(int height, int width, int iterAmount, boolean symmetry, List<Variation> vars, int threadAmount) {
        FlameLighter lighter = new FlameLighter(height, width);
        FlameGenerator generator = getGenerator(vars, symmetry);

        if (threadAmount > 0) {
            try (ExecutorService executor = Executors.newFixedThreadPool(threadAmount)) {
                for (int dotInd = 0; dotInd < DOT_AMOUNT; ++dotInd) {
                    executor.execute(() -> dotWork(lighter, generator, iterAmount));
                }
            }
        } else {
            for (int dotInd = 0; dotInd < DOT_AMOUNT; ++dotInd) {
                dotWork(lighter, generator, iterAmount);
            }
        }
        lighter.normalize();

        try {
            driver.paint(lighter.pixelMatrix());
        } catch (IOException e) {
            throw new IllegalStateException("Couldn't paint flame", e);
        }
    }

    private void dotWork(FlameLighter lighter, FlameGenerator generator, int iterAmount) {
        Color prevColor = Color.BLACK;
        Dot cur = random.getDot();
        for (int iter = 0; iter < iterAmount; iter++) {
            InfluenceDot inflDot = generator.getNextLight(cur);
            if (inflDot.color() == null) {
                inflDot = new InfluenceDot(inflDot.coords(), prevColor);
            }
            if (iter > SKIP_AMOUNT) {
                lighter.light(inflDot);
            }
            prevColor = inflDot.color();
            cur = inflDot.coords();
        }
    }

    @SuppressWarnings("MagicNumber")
    private FlameGenerator getGenerator(List<Variation> vars, boolean symmetry) {
        List<ColoredTransition> functions = new ArrayList<>(FUNCTIONS_AMOUNT);
        for (int i = 0; i < FUNCTIONS_AMOUNT; i++) {
            DoubleList weights = random.getProbs(vars.size());
            LinearDotOperator linearOperator = random.getLinearOperator();
            functions.add(new ColoredTransition(new SIFunction(vars, weights, linearOperator), FUNCTION_COLORS.get(i)));
        }

        DoubleList probabilites = random.getProbs(FUNCTIONS_AMOUNT);

        if (symmetry) {
            functions.add(ColoredTransition.SYMMETRY);
            for (int i = 0; i < FUNCTIONS_AMOUNT; i++) {
                probabilites.set(i, probabilites.getDouble(i) / 2);
            }
            probabilites.add(0.5);
        }

        return new FlameGenerator(random, functions, probabilites);
    }
}
