package game;

import java.awt.event.KeyEvent;

public interface Game {
    void stopGameLoop();
    void startGameLoop();
    boolean isKeyDown(int keyCode);
    int getLastKey();
    void onKeyPressed(KeyEvent e);
    void onKeyReleased(KeyEvent e);
    void onKeyTyped(KeyEvent e);
}
