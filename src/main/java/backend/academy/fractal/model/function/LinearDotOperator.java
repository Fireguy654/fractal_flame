package backend.academy.fractal.model.function;

import backend.academy.fractal.model.Dot;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class LinearDotOperator implements Transition {
    private final double aXToX;
    private final double aYToX;
    private final double aXToY;
    private final double aYToY;
    private final double shiftX;
    private final double shiftY;

    @Override
    public Dot apply(Dot dot) {
        return new Dot(aXToX * dot.x() + aYToX * dot.y() + shiftX, aXToY * dot.x() + aYToY * dot.y() + shiftY);
    }
}
