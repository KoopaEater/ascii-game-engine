package engine;

import game.StandardGame;

public class StandardGameLoop implements GameLoop {
    private ContinuousTick continuousTick;
    private FixedTick fixedTick;
    private boolean running;
    private long lastTickTime;
    private double millisPerFixedTick;
    private double elapsedFixedTime;

    public StandardGameLoop(int fixedTickRate) {
        elapsedFixedTime = 0;
        millisPerFixedTick = 1000.0 / fixedTickRate;
    }

    private void run() {
        long elapsedTime = System.currentTimeMillis() - lastTickTime;
        lastTickTime = System.currentTimeMillis();

        elapsedFixedTime += elapsedTime;
        while (elapsedFixedTime > millisPerFixedTick) {
            fixedTick.tick();
            elapsedFixedTime -= elapsedFixedTime;
        }

        continuousTick.tick(elapsedTime);

        if (running) {
            run();
        }
    }


    @Override
    public void setContinuousTick(ContinuousTick tick) {
        continuousTick = tick;
    }

    @Override
    public void setFixedTick(FixedTick tick) {
        fixedTick = tick;
    }

    @Override
    public void start() {
        running = true;
        lastTickTime = System.currentTimeMillis();
        run();
    }

    @Override
    public void pause() {
        running = false;
    }
}
