package ui;

import java.awt.*;

public interface AsciiUI extends UI {
    void setFontName(String fontName);
    void setFontStyle(int fontStyle);
    void setFontSize(int fontSize);
    void setFont(Font font);
    void setColorOfSymbol(Color color, int x, int y);
}
