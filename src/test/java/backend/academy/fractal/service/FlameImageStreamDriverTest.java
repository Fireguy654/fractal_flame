package backend.academy.fractal.service;

import backend.academy.fractal.model.Color;
import backend.academy.fractal.model.Dot;
import backend.academy.fractal.model.PixelMatrix;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class FlameImageStreamDriverTest {
    @Test
    @DisplayName("Correctness of getting a simple png picture")
    void paint() {
        assertThatCode(() -> {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PixelMatrix pixelMatrix = new PixelMatrix(1, 2);
            pixelMatrix.add(new Dot(0, 1), new Color(255, 0, 0));
            FlameGraphicalDriver driver = new FlameImageStreamDriver("png", outputStream);

            driver.paint(pixelMatrix);

            assertThat(outputStream.toByteArray()).isEqualTo(
                new byte[]{-119, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 2, 0, 0, 0, 1, 8, 2,
                    0, 0, 0, 123, 64, -24, -35, 0, 0, 0, 15, 73, 68, 65, 84, 120, 94, 99, 96, 96, 96, -8, -49, -64, 0,
                    0, 3, 4, 1, 0, 79, -31, 104, -81, 0, 0, 0, 0, 73, 69, 78, 68, -82, 66, 96, -126});
        }).doesNotThrowAnyException();
    }
}
