package game.ascii.actor;

import java.awt.*;

public interface AsciiActor {
    void setSymbol(char symbol);
    void setColor(Color color);
    void move(int dx, int dy);
    void moveTo(int x, int y);
    int getX();
    int getY();
    void show();
    void hide();
    void toggleVisibility();
}
