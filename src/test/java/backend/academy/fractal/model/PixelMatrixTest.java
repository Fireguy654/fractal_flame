package backend.academy.fractal.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class PixelMatrixTest {
    @Test
    @DisplayName("Correctness of adding a dot")
    void add() {
        PixelMatrix pixelMatrix = new PixelMatrix(1, 2);

        pixelMatrix.add(new Dot(0.75, 0.75), new Color(200, 200, 200));
        pixelMatrix.add(new Dot(0.75, 0.25), new Color(100, 100, 100));

        assertThat(pixelMatrix.getMaxToggleAmount()).isEqualTo(2);
        assertThat(pixelMatrix.getColorAt(0, 0)).isEqualTo(Color.BLACK);
        assertThat(pixelMatrix.getColorAt(1, 0)).isEqualTo(new Color(150, 150, 150));
    }

    @Test
    @DisplayName("Correctness of transformation of pixels")
    void transform() {
        PixelMatrix pixelMatrix = new PixelMatrix(1, 2);
        pixelMatrix.add(new Dot(0.75, 0.75), new Color(100, 100, 100));
        Color all = new Color(200, 200, 200);

        pixelMatrix.transform(pixel -> {
            pixel.addInfluence(all);
            return new Pixel(pixel.color(), 50 + pixel.toggleAmount());
        });

        assertThat(pixelMatrix.getColorAt(0, 0)).isEqualTo(all);
        assertThat(pixelMatrix.getColorAt(1, 0)).isEqualTo(new Color(150, 150, 150));
        assertThat(pixelMatrix.getMaxToggleAmount()).isEqualTo(52);
    }
}
