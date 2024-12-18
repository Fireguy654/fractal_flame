package backend.academy.fractal.model.function;

import backend.academy.fractal.model.Dot;
import backend.academy.fractal.model.function.variation.Fisheye;
import backend.academy.fractal.model.function.variation.Spherical;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class TransitionTest {
    private static final double EPS = 1e-6;
    private static final Percentage EPS_PERC = Percentage.withPercentage(EPS / 100);

    @Test
    @DisplayName("Correctness of SYMMETRY colored transition")
    void symmetry() {
        ColoredTransition transition = ColoredTransition.SYMMETRY;
        Dot dot = new Dot(3, 4);

        Dot res = transition.func().apply(dot);

        assertThat(res.x()).isEqualTo(-3);
        assertThat(res.y()).isEqualTo(-4);
        assertThat(transition.color()).isNull();
    }

    @Test
    @DisplayName("Correctness of linear dot operator transition")
    void linear() {
        Transition transition = new LinearDotOperator(2, -1, 3, 3, 10, -20);
        Dot dot = new Dot(3, 4);

        Dot res = transition.apply(dot);

        assertThat(res.x()).isEqualTo(12);
        assertThat(res.y()).isEqualTo(1);
    }

    @Test
    @DisplayName("Correctness of SIFunction")
    void siFunction() {
        Transition transition = new SIFunction(
            List.of(new Fisheye(), new Spherical()),
            DoubleList.of(0.25, 0.75),
            new LinearDotOperator(1, 1, 1, 1, 1, 2)
        );
        Dot dot = new Dot(1, 1);

        Dot res = transition.apply(dot);

        assertThat(res.x()).isCloseTo(1.0 / 3.0 + 9.0 / 100.0, EPS_PERC);
        assertThat(res.y()).isCloseTo(1.0 / 4.0 + 3.0 / 25.0, EPS_PERC);
    }

    @Test
    @DisplayName("Correctness of returning zero when there is no variations")
    void returningZeroSIFunction() {
        Transition transition = new SIFunction(List.of(), DoubleList.of(), new LinearDotOperator(0, 0, 0, 0, 0, 0));
        Dot dot = new Dot(1, 1);

        Dot res = transition.apply(dot);

        assertThat(res.x()).isEqualTo(0);
        assertThat(res.y()).isEqualTo(0);
    }
}
