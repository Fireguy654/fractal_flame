package backend.academy.fractal.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ColorTest {
    @Test
    @DisplayName("Correctness of flowing a color")
    void flowColor() {
        Color color = new Color(100, 50, 35);
        Color otherC = new Color(150, 250, 65);

        Color res = color.flowColor(otherC);

        assertThat(res).isEqualTo(new Color(125, 150, 50));
    }
}
