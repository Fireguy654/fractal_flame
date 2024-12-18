package backend.academy.fractal.util;

import backend.academy.fractal.model.Dot;

public class MathUtils {
    public static final double DOT_UPPER_BORDER = 1;
    public static final double DOT_LOWER_BORDER = -1;

    private MathUtils() {}

    public static double squareRad(Dot dot) {
        return dot.x() * dot.x() + dot.y() * dot.y();
    }

    public static double rad(Dot dot) {
        return Math.sqrt(squareRad(dot));
    }

    public static double vertAngle(Dot dot) {
        return Math.atan2(dot.x(), dot.y());
    }

    public static int round(double value) {
        return Math.toIntExact(Math.round(value));
    }
}
