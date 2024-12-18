package backend.academy.fractal.model;

import com.beust.jcommander.JCommander;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FractalCliParamsTest {
    @Test
    @DisplayName("Correctness of parsing")
    void parsing() {
        String[] args = new String[]{"var", "--format", "jpg", "--width", "1024", "--height", "1024",
                "--iter", "100", "--symmetry", "--path", "file.jpg", "--threads", "8", "othervar"};
        FractalCliParams fractalCliParams = new FractalCliParams();

        JCommander.newBuilder().addObject(fractalCliParams).build().parse(args);

        assertThat(fractalCliParams.path()).isEqualTo("file.jpg");
        assertThat(fractalCliParams.threads()).isEqualTo(8);
        assertThat(fractalCliParams.format()).isEqualTo("jpg");
        assertThat(fractalCliParams.width()).isEqualTo(1024);
        assertThat(fractalCliParams.height()).isEqualTo(1024);
        assertThat(fractalCliParams.iter()).isEqualTo(100);
        assertThat(fractalCliParams.symmetry()).isTrue();
        assertThat(fractalCliParams.vars()).containsExactly("var", "othervar");
    }
}
