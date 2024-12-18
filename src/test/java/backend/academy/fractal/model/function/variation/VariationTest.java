package backend.academy.fractal.model.function.variation;

import backend.academy.fractal.model.Dot;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class VariationTest {
    private static final double EPS = 1e-6;
    private static final Percentage EPS_PERC = Percentage.withPercentage(EPS / 100);

    @Test
    @DisplayName("Correctness of getting variation by its name")
    void getByName() {
        String name = "SphERical";

        Variation variation = Variation.getByName(name);

        assertThat(variation).isExactlyInstanceOf(Spherical.class);
    }

    @Test
    @DisplayName("Throwing an exeption if varation of given name isn't supported")
    void incorrectGetByName() {
        assertThatCode(() -> Variation.getByName("abc"))
            .isExactlyInstanceOf(UnsupportedOperationException.class).hasMessage("Unsupported variation name: abc");
    }

    @Test
    @DisplayName("Correctness of diamond variation")
    void diamond() {
        Dot dot = new Dot(3 * Math.PI, 4 * Math.PI);
        Variation var = new Diamond();

        Dot res = var.apply(dot);

        assertThat(res.x()).isCloseTo(-3.0 / 5.0, EPS_PERC);
        assertThat(Math.abs(res.y())).isLessThan(EPS);
    }

    @Test
    @DisplayName("Correctness of exponential variation")
    void exponential() {
        Dot dot = new Dot(1, 2);
        Variation var = new Exponential();

        Dot res = var.apply(dot);

        assertThat(res.x()).isCloseTo(1, EPS_PERC);
        assertThat(Math.abs(res.y())).isLessThan(EPS);
    }

    @Test
    @DisplayName("Correctness of fisheye variation")
    void fisheye() {
        Dot dot = new Dot(3, 4);
        Variation var = new Fisheye();

        Dot res = var.apply(dot);

        assertThat(res.x()).isCloseTo(4.0 / 3.0, EPS_PERC);
        assertThat(res.y()).isCloseTo(1.0, EPS_PERC);
    }

    @Test
    @DisplayName("Correctness of handkerchief variation")
    void handkerchief() {
        Dot dot = new Dot(3 * Math.PI, 4 * Math.PI);
        Variation var = new Handkerchief();

        Dot res = var.apply(dot);

        assertThat(res.x()).isCloseTo(-3.0 * Math.PI, EPS_PERC);
        assertThat(res.y()).isCloseTo(-4.0 * Math.PI, EPS_PERC);
    }

    @Test
    @DisplayName("Correctness of spherical variation")
    void spherical() {
        Dot dot = new Dot(3, 4);
        Variation var = new Spherical();

        Dot res = var.apply(dot);

        assertThat(res.x()).isCloseTo(3.0 / 25, EPS_PERC);
        assertThat(res.y()).isCloseTo(4.0 / 25, EPS_PERC);
    }
}
