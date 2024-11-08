package game.ascii.actor;

import java.awt.*;

public interface AsciiActor {
    int getX();
    int getY();
    char getSymbol();
    Color getColor();
    Color getBackground();
    boolean hasBackground();
    boolean isVisible();
}
