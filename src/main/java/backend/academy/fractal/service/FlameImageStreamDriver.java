package backend.academy.fractal.service;

import backend.academy.fractal.model.Color;
import backend.academy.fractal.model.PixelMatrix;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FlameImageStreamDriver implements FlameGraphicalDriver {
    private final String formatName;
    private final OutputStream source;

    @Override
    public void paint(PixelMatrix pixelMatrix) throws IOException {
        int height = pixelMatrix.height();
        int width = pixelMatrix.width();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                image.setRGB(x, y, getColor(pixelMatrix.getColorAt(x, y)));
            }
        }

        ImageIO.write(image, formatName, source);
    }

    @SuppressWarnings("MagicNumber")
    private int getColor(Color color) {
        return (color.red() << 16) | (color.green() << 8) | color.blue();
    }

    @Override
    public void close() throws IOException {
        source.close();
    }
}
