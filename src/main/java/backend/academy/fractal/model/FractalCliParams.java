package backend.academy.fractal.model;

import com.beust.jcommander.Parameter;
import java.util.List;
import lombok.Getter;

@Getter
public class FractalCliParams {
    @Parameter(description = "Variation names: diamond, exponential, fisheye, handkerchief, spherical")
    private List<String> vars;

    @SuppressWarnings("MagicNumber")
    @Parameter(names = "--width", description = "Width of the image")
    private int width = 4096;

    @SuppressWarnings("MagicNumber")
    @Parameter(names = "--height", description = "Height of the image")
    private int height = 2160;

    @SuppressWarnings("MagicNumber")
    @Parameter(names = "--iter", description = "Amount of iterations of fractal flame")
    private int iter = 1000000;

    @Parameter(names = "--threads", description = "Amount of threads creating fractal flame")
    private int threads = 1;

    @Parameter(names = "--path", description = "Destination path")
    private String path = "res.png";

    @Parameter(names = "--format", description = "Image format name")
    private String format = "png";

    @Parameter(names = "--symmetry", description = "Symmetrical flame")
    private boolean symmetry = false;
}
