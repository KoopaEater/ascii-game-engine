package game;

import engine.GameLoop;
import engine.StandardGameLoop;

import java.util.concurrent.atomic.AtomicInteger;

public class StandardGame implements Game {
    public StandardGame() {
        AtomicInteger counter = new AtomicInteger(0);
        GameLoop gameLoop = new StandardGameLoop(1);

//        gameLoop.setContinuousTick((deltaTime) -> {
//            System.out.println(deltaTime);
//        });

        gameLoop.setFixedTick(() -> {
            System.out.println(counter);
            if (counter.incrementAndGet() > 3) {
                gameLoop.pause();
            }
        });
        gameLoop.start();
    }
}
