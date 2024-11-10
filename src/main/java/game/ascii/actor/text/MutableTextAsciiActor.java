package game.ascii.actor.text;

import java.awt.*;

public interface MutableTextAsciiActor extends TextAsciiActor {
    void setText(String text);
    void setColor(Color color);
    void setBackground(Color color);
    void removeBackground();
    void setOrigin(int x, int y);
    void setZ(int z);
    void show();
    void hide();
    void toggleVisibility();
}
