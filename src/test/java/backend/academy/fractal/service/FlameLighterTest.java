package backend.academy.fractal.service;

import backend.academy.fractal.model.Color;
import backend.academy.fractal.model.Dot;
import backend.academy.fractal.model.InfluenceDot;
import backend.academy.fractal.model.Pixel;
import backend.academy.fractal.model.PixelMatrix;
import backend.academy.fractal.util.MathUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class FlameLighterTest {
    @Test
    @DisplayName("Correctness of lighting a pixel")
    void light() {
        FlameLighter lighter = new FlameLighter(1, 2);

        lighter.light(new InfluenceDot(new Dot(0.25, 0.75), new Color(200, 200, 200)));

        assertThat(lighter.pixelMatrix().getMaxToggleAmount()).isEqualTo(1);
        assertThat(lighter.pixelMatrix().getColorAt(1, 0)).isEqualTo(new Color(200, 200, 200));
    }

    @Test
    void normalize() {
        PixelMatrix pixelMatrix = new PixelMatrix(1, 2);
        pixelMatrix.add(new Dot(-1, -1), new Color(200, 200, 200));
        pixelMatrix.transform(pixel -> new Pixel(pixel.color(), pixel.toggleAmount() * 10));
        pixelMatrix.add(new Dot(1, -1), new Color(100, 100, 100));
        pixelMatrix.transform(pixel -> new Pixel(pixel.color(), pixel.toggleAmount() * 10));
        Color resColor0 = pixelMatrix.getColorAt(0, 0);
        Color resColor1 = pixelMatrix.getColorAt(1, 0);
        double mult = Math.pow(0.5, 1.0 / 2.2);
        resColor1 = new Color(
            MathUtils.round(resColor1.red() * mult),
            MathUtils.round(resColor1.green() * mult),
            MathUtils.round(resColor1.blue() * mult));
        FlameLighter lighter = new FlameLighter(pixelMatrix);

        lighter.normalize();

        assertThat(lighter.pixelMatrix().getColorAt(0, 0)).isEqualTo(resColor0);
        assertThat(lighter.pixelMatrix().getColorAt(1, 0)).isEqualTo(resColor1);
    }
}
