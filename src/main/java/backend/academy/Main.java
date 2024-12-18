package backend.academy;

import backend.academy.fractal.model.FractalCliParams;
import backend.academy.fractal.model.function.variation.Variation;
import backend.academy.fractal.service.FlameGraphicalDriver;
import backend.academy.fractal.service.FlameImageStreamDriver;
import backend.academy.fractal.service.FractalFlame;
import backend.academy.fractal.util.FlameRandom;
import com.beust.jcommander.JCommander;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

/**
 * Fractal flame algorithm
 * <h3>
 *     One-thread and Multi-thread results
 * </h3>
 * <h5>
 *     Configuration: '--threads 16 --symmetry spherical diamond handkerchief fisheye'
 * </h5>
 * <p>
 *     3.737982, 3.855608, 5.118864, 4.767027, 4.473881 seconds. Mean: 4.3906724 seconds
 * </p>
 * <h5>
 *     Configuration: '--threads 1 --symmetry spherical diamond handkerchief fisheye'
 * </h5>
 * <p>
 *     22.467509, 29.210432, 22.864363, 28.777159, 21.027713 seconds. Mean: 24.8694352 seconds
 * </p>
 */
@Log4j2
@UtilityClass
public class Main {
    public static void main(String[] args) {
        FractalCliParams params = new FractalCliParams();
        JCommander.newBuilder().addObject(params).build().parse(args);

        LocalDateTime before = LocalDateTime.now();
        try (FlameGraphicalDriver driver = new FlameImageStreamDriver(
            params.format(), Files.newOutputStream(Path.of(params.path()))
        )) {
            FractalFlame flame = new FractalFlame(driver, new FlameRandom());
            flame.flame(
                params.height(),
                params.width(),
                params.iter(),
                params.symmetry(),
                params.vars().stream().map(Variation::getByName).toList(),
                params.threads()
            );
        } catch (IOException | IllegalStateException e) {
            log.error(String.format("Couldn't work with file '%s'", params.path()), e);
        } catch (InvalidPathException e) {
            log.error(String.format("Invalid path '%s'", params.path()), e);
        }
        log.info(String.join(" ", args));
        log.info(Duration.between(before, LocalDateTime.now()));
    }
}
