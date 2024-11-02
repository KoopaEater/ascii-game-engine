package engine;

public interface GameLoop {

    void tick(Tick tick);
    void fixedTick(FixedTick tick);
    void start();
    void stop();

}
