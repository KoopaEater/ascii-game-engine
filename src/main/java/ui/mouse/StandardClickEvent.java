package ui.mouse;

public class StandardClickEvent implements ClickEvent {
    private int x, y;
    public StandardClickEvent(int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
