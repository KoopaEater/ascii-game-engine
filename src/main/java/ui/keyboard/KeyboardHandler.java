package ui.keyboard;

public interface KeyboardHandler {
    boolean isKeyDown(int keyCode);
    int getSingleKeyDown();
    int getLastKey();
}
