package engine;

public class StandardGameLoop implements GameLoop {
    private Tick tick;
    private FixedTick fixedTick;
    private boolean running;
    private long nanosPerFixedTick;

    public StandardGameLoop(int fixedTickRate) {
        tick = new NoTick();
        fixedTick = new NoFixedTick();
        nanosPerFixedTick = Math.round(1000000000.0 / fixedTickRate);
    }

    private void runLoop() {
        long lastTickTime = System.nanoTime();
        long lastFixedTickTime = System.nanoTime();
        while (running) {
            long elapsedTime = System.nanoTime() - lastTickTime;
            lastTickTime = System.nanoTime();
            tick.tick(elapsedTime);

            long elapsedFixedTime = System.nanoTime() - lastFixedTickTime;
            if (elapsedFixedTime >= nanosPerFixedTick) {
                lastFixedTickTime = System.nanoTime();
                long ticks = elapsedFixedTime / nanosPerFixedTick;
                for (int i = 0; i < ticks; i++) {
                    if (running) {
                        fixedTick.tick();
                    }
                }
            }
        }
    }


    @Override
    public void tick(Tick tick) {
        this.tick = tick;
    }

    @Override
    public void fixedTick(FixedTick tick) {
        fixedTick = tick;
    }

    @Override
    public void start() {
        running = true;
        runLoop();
    }

    @Override
    public void stop() {
        running = false;
    }
}
