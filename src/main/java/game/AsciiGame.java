package game;

import java.awt.*;

public interface AsciiGame extends Game {
    void setColorOfSymbol(Color color, int x, int y);
    void setBackgroundColor(Color color);
    void setSymbol(char symbol, int x, int y);
}
