package game;

public interface Game {
    void stopGameLoop();
    void startGameLoop();
    boolean isKeyDown(int keyCode);
    int getLastKey();
}
