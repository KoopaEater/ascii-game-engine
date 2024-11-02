package engine;

public class StandardGameLoop implements GameLoop {
    private Tick tick;
    private FixedTick fixedTick;
    private boolean running;
    private long lastTickTime;
    private long lastFixedTickTime;
    private long millisPerFixedTick;

    public StandardGameLoop(int fixedTickRate) {
        tick = new NoTick();
        fixedTick = new NoFixedTick();
        millisPerFixedTick = Math.round(1000.0 / fixedTickRate);
    }

    private void runLoop() {
        lastTickTime = System.currentTimeMillis();
        lastFixedTickTime = System.currentTimeMillis();
        while (running) {
            long elapsedTime = System.currentTimeMillis() - lastTickTime;
            lastTickTime = System.currentTimeMillis();
            tick.tick(elapsedTime);

            long elapsedFixedTime = System.currentTimeMillis() - lastFixedTickTime;
            if (elapsedFixedTime >= millisPerFixedTick) {
                lastFixedTickTime = System.currentTimeMillis();
                long ticks = elapsedFixedTime / millisPerFixedTick;
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
