package engine;

public interface GameLoop {

    void setContinuousTick(ContinuousTick tick);
    void setFixedTick(FixedTick tick);
    void start();
    void pause();

}
