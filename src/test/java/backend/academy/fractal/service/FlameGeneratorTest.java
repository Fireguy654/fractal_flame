package backend.academy.fractal.service;

import backend.academy.fractal.model.Dot;
import backend.academy.fractal.model.InfluenceDot;
import backend.academy.fractal.model.function.ColoredTransition;
import backend.academy.fractal.model.function.Transition;
import backend.academy.fractal.util.FlameRandom;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.any;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class FlameGeneratorTest {
    @Test
    @DisplayName("Correctness of getting next dot")
    void getNextLight(@Mock FlameRandom flameRandom, @Mock ColoredTransition coloredTransition,
        @Mock Transition transition, @Mock Dot dot) {
        doReturn(0).when(flameRandom).getIndWithProbs(any());
        doReturn(transition).when(coloredTransition).func();
        doReturn(new Dot(50, 50)).when(transition).apply(any());
        FlameGenerator flameGenerator = new FlameGenerator(
            flameRandom,
            List.of(coloredTransition, coloredTransition),
            DoubleList.of(0.3, 0.7)
        );

        InfluenceDot res = flameGenerator.getNextLight(dot);

        assertThat(res.coords()).isEqualTo(new Dot(50, 50));
        assertThat(res.color()).isNull();
    }
}
