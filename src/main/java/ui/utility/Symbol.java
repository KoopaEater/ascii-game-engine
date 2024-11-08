package ui.utility;

import java.awt.*;

public interface Symbol {
    void setSymbol(char symbol);
    void setColor(Color color);
    void setBackground(Color color);
    void setBackgroundOpaque(boolean opaque);
    void setFont(Font font);
    Color getColor();
    Color getBackground();
    char getSymbol();
}
