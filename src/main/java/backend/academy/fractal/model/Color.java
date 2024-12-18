package backend.academy.fractal.model;

public record Color(int red, int green, int blue) {
    public static final Color BLACK = new Color(0, 0, 0);

    public Color flowColor(Color color) {
        return new Color((red + color.red) / 2, (green + color.green) / 2, (blue + color.blue) / 2);
    }
}
