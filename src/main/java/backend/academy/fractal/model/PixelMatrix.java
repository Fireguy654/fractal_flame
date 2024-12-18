package backend.academy.fractal.model;

import backend.academy.fractal.util.MathUtils;
import java.util.function.UnaryOperator;
import lombok.Getter;

public class PixelMatrix {
    @Getter
    private final int height;
    @Getter
    private final int width;
    private final Pixel[][] pixels;

    private static final UnaryOperator<Pixel> INITIALIZE = (ignored) -> new Pixel();

    public PixelMatrix(int height, int width) {
        this.height = height;
        this.width = width;
        pixels = new Pixel[height][width];
        transform(INITIALIZE);
    }

    public void add(Dot point, Color inflColor) {
        if (outOfBorders(point.x()) || outOfBorders(point.y())) {
            return;
        }
        int x = getInd(point.x(), width);
        int y = getInd(point.y(), height);
        pixels[y][x].addInfluence(inflColor);
    }

    public final void transform(UnaryOperator<Pixel> transformer) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[y][x] = transformer.apply(pixels[y][x]);
            }
        }
    }

    public Color getColorAt(int x, int y) {
        return pixels[y][x].color();
    }

    public long getMaxToggleAmount() {
        long res = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                res = Math.max(res, pixels[y][x].toggleAmount());
            }
        }
        return res;
    }

    private boolean outOfBorders(double val) {
        return !(val >= MathUtils.DOT_LOWER_BORDER) || !(val <= MathUtils.DOT_UPPER_BORDER);
    }

    private int getInd(double val, int length) {
        return Math.min(length - 1, MathUtils.round((val - MathUtils.DOT_LOWER_BORDER)
            / (MathUtils.DOT_UPPER_BORDER - MathUtils.DOT_LOWER_BORDER) * length));
    }
}
