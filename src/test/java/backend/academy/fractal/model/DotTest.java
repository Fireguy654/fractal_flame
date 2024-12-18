package backend.academy.fractal.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class DotTest {
    @Test
    @DisplayName("Correctness of multiplying dot by constant")
    void multiply() {
        Dot dot = new Dot(3, 4);

        Dot res = dot.multiply(3);

        assertThat(res).isEqualTo(new Dot(9, 12));
    }

    @Test
    @DisplayName("Correctness of zero dot")
    void zero() {
        Dot dot = Dot.ZERO;

        assertThat(dot).isEqualTo(new Dot(0, 0));
    }

    @Test
    @DisplayName("Correctness of adding a dot to other dot")
    void add() {
        Dot dot = new Dot(3, 4);
        Dot otherDot = new Dot(5, 6);

        Dot res = dot.add(otherDot);

        assertThat(res).isEqualTo(new Dot(8, 10));
    }
}
