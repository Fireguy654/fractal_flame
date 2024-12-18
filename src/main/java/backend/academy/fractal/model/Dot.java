package backend.academy.fractal.model;

public record Dot(double x, double y) {
    public static final Dot ZERO = new Dot(0, 0);

    public Dot multiply(double d) {
        return new Dot(x * d, y * d);
    }

    public Dot add(Dot dot) {
        return new Dot(x + dot.x, y + dot.y);
    }
}
