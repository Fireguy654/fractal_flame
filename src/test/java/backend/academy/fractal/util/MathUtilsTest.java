package backend.academy.fractal.util;

import backend.academy.fractal.model.Dot;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class MathUtilsTest {
    private static final double EPS = 1e-6;
    private static final Percentage EPS_PERC = Percentage.withPercentage(EPS / 100);

    @Test
    @DisplayName("Correctness of getting square of l2 norm of dot")
    void squareRad() {
        Dot dot = new Dot(3, 4);

        double res = MathUtils.squareRad(dot);

        assertThat(res).isCloseTo(25, EPS_PERC);
    }

    @Test
    @DisplayName("Correctness of getting l2 norm of dot")
    void rad() {
        Dot dot = new Dot(3, 4);

        double res = MathUtils.rad(dot);

        assertThat(res).isCloseTo(5, EPS_PERC);
    }

    @Test
    @DisplayName("Correctness of getting vertical angle of dot")
    void vertAngle() {
        Dot dot = new Dot(Math.sqrt(3), 1);

        double res = MathUtils.vertAngle(dot);

        assertThat(res).isCloseTo(Math.PI / 3, EPS_PERC);
    }

    @Test
    @DisplayName("Correctness of rounding a number to upper integer")
    void roundUp() {
        double num = 1.5;

        int result = MathUtils.round(num);

        assertThat(result).isEqualTo(2);
    }

    @Test
    @DisplayName("Correctness of rounding a number to lower integer")
    void roundDown() {
        double num = 3.49;

        int result = MathUtils.round(num);

        assertThat(result).isEqualTo(3);
    }

    @Test
    @DisplayName("Correctness of throwing an exception if number is too big")
    void throwExceptionIfNumberIsTooBig() {
        assertThatCode(() -> MathUtils.round(1e18))
            .isExactlyInstanceOf(ArithmeticException.class).hasMessage("integer overflow");
    }
}
