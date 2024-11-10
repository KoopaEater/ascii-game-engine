package ui.mouse;

public class NoClickEvent implements ClickEvent {
    @Override
    public int getX() {
        return -1;
    }

    @Override
    public int getY() {
        return -1;
    }
}
