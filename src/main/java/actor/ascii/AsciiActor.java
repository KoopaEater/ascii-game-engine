package actor.ascii;

import java.awt.*;

public interface AsciiActor {
    int getX();
    int getY();
    int getZ();
    char getSymbol();
    Color getColor();
    Color getBackground();
    boolean hasBackground();
    boolean isVisible();
}
