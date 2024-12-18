package backend.academy.fractal.util;

import backend.academy.fractal.model.Dot;
import backend.academy.fractal.model.function.LinearDotOperator;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class FlameRandomTest {
    private static final double EPS = 1e-6;
    private static final Percentage EPS_PERC = Percentage.withPercentage(EPS / 100);

    @Test
    @DisplayName("Correctness of getting a random dot")
    void getDot() {
        FlameRandom flameRandom = new FlameRandom();

        Dot res = flameRandom.getDot();

        assertThat(res.x()).isLessThanOrEqualTo(1.0).isGreaterThanOrEqualTo(-1.0);
        assertThat(res.y()).isLessThanOrEqualTo(1.0).isGreaterThanOrEqualTo(-1.0);
    }

    @Test
    @DisplayName("Correcting of getting a random linear operator")
    void getLinearOperator() {
        FlameRandom flameRandom = new FlameRandom();
        Dot dot = new Dot(1, 1);

        LinearDotOperator res = flameRandom.getLinearOperator();
        Dot resDot = res.apply(dot);

        assertThat(resDot.x()).isLessThanOrEqualTo(3.0).isGreaterThanOrEqualTo(-3.0);
        assertThat(resDot.y()).isLessThanOrEqualTo(3.0).isGreaterThanOrEqualTo(-3.0);
    }

    @Test
    @DisplayName("Correctness of getting probabilities of given size")
    void getProbs() {
        FlameRandom flameRandom = new FlameRandom();

        DoubleList probs = flameRandom.getProbs(5);

        assertThat(probs.size()).isEqualTo(5);
        assertThat(probs.toDoubleArray()).matches(array -> Arrays.stream(array).noneMatch(num -> num < 0));
        assertThat(probs.doubleStream().sum()).isCloseTo(1.0, EPS_PERC);
    }

    @Test
    @DisplayName("Correctness of getting indices with corresponding probabilities")
    void getIndWithProbs() {
        FlameRandom flameRandom = new FlameRandom();
        DoubleList probs = flameRandom.getProbs(5);
        int[] cnts = new int[probs.size()];

        for (int i = 0; i < 1000000; i++) {
            ++cnts[flameRandom.getIndWithProbs(probs)];
        }

        assertSoftly(softly -> {
            for (int i = 0; i < cnts.length; i++) {
                softly.assertThat((double) cnts[i] / 1000000).isCloseTo(probs.getDouble(i), Percentage.withPercentage(5));
            }
        });
    }
}
