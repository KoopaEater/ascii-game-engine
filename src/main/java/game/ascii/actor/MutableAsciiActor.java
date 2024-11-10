package game.ascii.actor;

import java.awt.*;

public interface MutableAsciiActor extends AsciiActor {
    void setSymbol(char symbol);
    void setColor(Color color);
    void setBackground(Color color);
    void removeBackground();
    void move(int dx, int dy);
    void moveTo(int x, int y);
    void setZ(int z);
    void show();
    void hide();
    void toggleVisibility();
}
