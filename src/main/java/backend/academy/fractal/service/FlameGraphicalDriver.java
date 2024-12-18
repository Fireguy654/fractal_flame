package backend.academy.fractal.service;

import backend.academy.fractal.model.PixelMatrix;
import java.io.IOException;

public interface FlameGraphicalDriver extends AutoCloseable {
    void paint(PixelMatrix pixelMatrix) throws IOException;

    @Override
    void close() throws IOException;
}
