package engine;

public interface GameLoop {

    void setupTick(SetupTick tick);
    void tick(Tick tick);
    void fixedTick(FixedTick tick);
    void start();
    void stop();

}
