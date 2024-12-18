package backend.academy.fractal.service;

import backend.academy.fractal.model.Color;
import backend.academy.fractal.model.InfluenceDot;
import backend.academy.fractal.model.Pixel;
import backend.academy.fractal.model.PixelMatrix;
import backend.academy.fractal.util.MathUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public class FlameLighter {
    private final PixelMatrix pixelMatrix;

    private static final double GAMMA = 2.2;

    public FlameLighter(int height, int width) {
        this.pixelMatrix = new PixelMatrix(height, width);
    }

    public void light(InfluenceDot dot) {
        pixelMatrix.add(dot.coords(), dot.color());
    }

    public void normalize() {
        double max = Math.log10(pixelMatrix.getMaxToggleAmount());
        pixelMatrix.transform((pixel) -> {
            if (pixel.toggleAmount() == 0) {
                return pixel;
            }
            double multiplier = Math.pow(Math.log10(pixel.toggleAmount()) / max, 1.0 / GAMMA);
            return new Pixel(new Color(
                MathUtils.round(pixel.color().red() * multiplier),
                MathUtils.round(pixel.color().green() * multiplier),
                MathUtils.round(pixel.color().blue() * multiplier)
            ), pixel.toggleAmount());
        });
    }
}
