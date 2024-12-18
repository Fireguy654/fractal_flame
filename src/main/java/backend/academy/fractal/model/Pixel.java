package backend.academy.fractal.model;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public final class Pixel {
    private final AtomicReference<Color> color;
    private final AtomicLong toggleAmount;

    public Pixel() {
        color = new AtomicReference<>(Color.BLACK);
        toggleAmount = new AtomicLong(0);
    }

    public Pixel(Color color, long toggleAmount) {
        this.color = new AtomicReference<>(color);
        this.toggleAmount = new AtomicLong(toggleAmount);
    }

    public void addInfluence(Color inflColor) {
        toggleAmount.getAndIncrement();

        Color curC;
        Color res;
        do {
            curC = color.get();
            if (curC == Color.BLACK) {
                res = inflColor;
            } else {
                res = curC.flowColor(inflColor);
            }
        } while (!color.compareAndSet(curC, res));
    }

    public Color color() {
        return color.get();
    }

    public long toggleAmount() {
        return toggleAmount.get();
    }
}
