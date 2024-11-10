package game.ascii.actor;

import java.awt.*;

public class EmptyAsciiActor implements AsciiActor {
    private int x, y;
    public EmptyAsciiActor(int x, int y) {
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

    @Override
    public int getZ() {
        return Integer.MIN_VALUE;
    }

    @Override
    public char getSymbol() {
        return ' ';
    }

    @Override
    public Color getColor() {
        return Color.BLACK;
    }

    @Override
    public Color getBackground() {
        return Color.WHITE;
    }

    @Override
    public boolean hasBackground() {
        return false;
    }

    @Override
    public boolean isVisible() {
        return true;
    }
}
