package game;

import ui.mouse.ClickEvent;

import java.awt.event.KeyEvent;

public interface Game {
    void stopGameLoop();
    void startGameLoop();
    boolean isKeyDown(int keyCode);
    int getLastKey();
    void onKeyPressed(KeyEvent e);
    void onKeyReleased(KeyEvent e);
    void onKeyTyped(KeyEvent e);
    void onClick(ClickEvent e);
}
