package game.ascii.actor;

import java.awt.*;

public class NoAsciiActor implements AsciiActor {
    public NoAsciiActor() {}
    @Override
    public int getX() {
        return -1;
    }

    @Override
    public int getY() {
        return -1;
    }

    @Override
    public int getZ() {
        return Integer.MIN_VALUE;
    }

    @Override
    public char getSymbol() {
        return '!';
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
        return false;
    }
}
