package backend.academy.fractal.util;

import backend.academy.fractal.model.Dot;
import backend.academy.fractal.model.function.LinearDotOperator;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleUnaryOperator;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FlameRandom {
    private static final DoubleUnaryOperator SQUARE = x -> x * x;

    public Dot getDot() {
        return new Dot(getBiUnitUniform(), getBiUnitUniform());
    }

    @SuppressFBWarnings
    public LinearDotOperator getLinearOperator() {
        double[][] a;
        do {
            a = new double[][] {
                {getBiUnitUniform(), getBiUnitUniform()},
                {getBiUnitUniform(), getBiUnitUniform()}
            };
        } while (
            squareSum(a[0][0], a[1][0]) >= 1
                || squareSum(a[0][1], a[1][1]) >= 1
                || squareSum(a[0][0], a[0][1], a[1][0], a[1][1])
                >= 1 + squareSum(a[0][0] * a[1][1] - a[0][1] * a[1][0])
        );

        double[] b = new double[]{getBiUnitUniform(), getBiUnitUniform()};

        return new LinearDotOperator(a[0][0], a[0][1], a[1][0], a[1][1], b[0], b[1]);
    }

    @SuppressFBWarnings
    public DoubleList getProbs(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be positive");
        }

        if (size == 1) {
            return DoubleList.of(1);
        }

        double[] borders = DoubleStream.concat(
                DoubleStream.concat(
                        DoubleStream.of(0),
                        ThreadLocalRandom.current().doubles().limit(size - 1).sorted()
                ),
                DoubleStream.of(1)
        ).toArray();
        DoubleList res = new DoubleArrayList(size);
        for (int i = 1; i <= size; ++i) {
            res.add(borders[i] - borders[i - 1]);
        }
        return res;
    }

    @SuppressFBWarnings
    public int getIndWithProbs(DoubleList probs) {
        double res = ThreadLocalRandom.current().nextDouble();
        for (int i = 0; i < probs.size(); ++i) {
            res -= probs.getDouble(i);
            if (res < 0) {
                return i;
            }
        }
        throw new IllegalArgumentException("Sum of probabilities is lower than one");
    }

    @SuppressFBWarnings
    @SuppressWarnings("MagicNumber")
    private double getBiUnitUniform() {
        return (ThreadLocalRandom.current().nextDouble() - 0.5) * 2;
    }

    private double squareSum(double... nums) {
        return Arrays.stream(nums).map(SQUARE).sum();
    }
}
