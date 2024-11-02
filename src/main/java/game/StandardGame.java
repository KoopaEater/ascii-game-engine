package game;

import engine.GameLoop;
import engine.StandardGameLoop;

import java.util.concurrent.atomic.AtomicInteger;

public class StandardGame implements Game {
    public StandardGame() {
        AtomicInteger counter = new AtomicInteger(0);
        GameLoop gameLoop = new StandardGameLoop(2);

        gameLoop.tick(dt -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        gameLoop.fixedTick(() -> {
            System.out.println(counter);
            if (counter.incrementAndGet() > 10) {
                gameLoop.stop();
            }
        });
        gameLoop.start();
    }
}
