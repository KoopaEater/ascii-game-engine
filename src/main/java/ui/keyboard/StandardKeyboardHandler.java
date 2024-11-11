package ui.keyboard;

import game.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class StandardKeyboardHandler implements KeyListener, KeyboardHandler {
    private Game game;
    private Set<Integer> keysPressed;
    private LinkedList<Integer> keyStack;
    private int lastKey;
    public StandardKeyboardHandler(Game game) {
        this.game = game;
        keysPressed = new HashSet<>();
        keyStack = new LinkedList<>();
        lastKey = KeyEvent.VK_UNDEFINED;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        game.onKeyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getExtendedKeyCode();
        if (isKeyDown(keyCode)) {
            return;
        }
        keysPressed.add(keyCode);
        keyStack.addFirst(keyCode);
        lastKey = keyCode;
        game.onKeyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getExtendedKeyCode();
        if (!isKeyDown(keyCode)) {
            return;
        }
        keysPressed.remove(keyCode);
        keyStack.remove((Integer) keyCode);

        if (keyStack.isEmpty()) {
            lastKey = keyCode;
        } else {
            lastKey = keyStack.getFirst();
        }
        game.onKeyReleased(e);
    }

    @Override
    public boolean isKeyDown(int keyCode) {
        return keysPressed.contains(keyCode);
    }

    @Override
    public int getSingleKeyDown() {
        if (keyStack.isEmpty()) {
            return KeyEvent.VK_UNDEFINED;
        }
        return keyStack.getFirst();
    }

    @Override
    public int getLastKey() {
        return lastKey;
    }
}
