package backend.academy.fractal.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicReference;
import static org.assertj.core.api.Assertions.assertThat;

class PixelTest {
    @Test
    @DisplayName("Correctness of one thread adding influence")
    void addInfluence() {
        Pixel pixel = new Pixel(new Color(10, 0, 100), 10);

        pixel.addInfluence(new Color(20, 5, 200));

        assertThat(pixel.color()).isEqualTo(new Color(15, 2, 150));
        assertThat(pixel.toggleAmount()).isEqualTo(11);
    }

    @Test
    @DisplayName("Correctness of multi thread adding influence")
    void addMultipleInfluence() {
        Pixel pixel = new Pixel();
        AtomicReference<Color> colorA = new AtomicReference<>();
        AtomicReference<Color> colorB = new AtomicReference<>();
        Thread a = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                pixel.addInfluence(new Color(200, 200, 200));
            }
            colorA.set(pixel.color());
        });
        Thread b = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                pixel.addInfluence(new Color(199, 100, 100));
            }
            colorB.set(pixel.color());
        });

        a.start();
        b.start();
        try {
            a.join();
            b.join();
        } catch (InterruptedException e) {}

        assertThat(pixel.toggleAmount()).isEqualTo(15);
        assertThat(pixel.color().red()).isEqualTo(199);
        assertThat(pixel.color()).isIn(colorA.get(), colorB.get());
    }

    @Test
    void color() {
        Pixel pixel = new Pixel();
        Color cA = new Color(20, 5, 200);
        Color cB = new Color(200, 105, 100);
        Thread a = new Thread(() -> pixel.addInfluence(cA));
        Thread b = new Thread(() -> pixel.addInfluence(cB));

        a.start();
        b.start();
        Color res = pixel.color();
        try {
            a.join();
            b.join();
        } catch (InterruptedException e) {}

        assertThat(res).isIn(Color.BLACK, cA, cB, cA.flowColor(cB));
        assertThat(pixel.color()).isEqualTo(cA.flowColor(cB));
    }

    @Test
    void toggleAmount() {
        Pixel pixel = new Pixel();
        Thread a = new Thread(() -> pixel.addInfluence(Color.BLACK));
        Thread b = new Thread(() -> pixel.addInfluence(Color.BLACK));

        a.start();
        b.start();
        long amount = pixel.toggleAmount();
        try {
            a.join();
            b.join();
        } catch (InterruptedException e) {}

        assertThat(amount).isIn(0L, 1L, 2L);
        assertThat(pixel.toggleAmount()).isEqualTo(2);
    }
}
