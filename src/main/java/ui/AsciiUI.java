package ui;

import java.awt.*;
import java.awt.event.KeyListener;

public interface AsciiUI extends UI {
    void addKeyListener(KeyListener keyListener);
    void setFontName(String fontName);
    void setFontStyle(int fontStyle);
    void setFontSize(int fontSize);
    void setFont(Font font);
    void setColorOfSymbol(Color color, int x, int y);
    void setBackgroundOfSymbol(Color color, int x, int y);
    void setBackgroundOpaqueOfSymbol(boolean opaque, int x, int y);
    void setBackgroundColor(Color color);
    void setSymbol(char symbol, int x, int y);
    Color getColorOfSymbol(int x, int y);
    Color getBackgroundOfSymbol(int x, int y);
    char getSymbol(int x, int y);
}
