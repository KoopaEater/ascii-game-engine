package ui;

import java.awt.*;

public interface AsciiUI extends UI {
    void setFontNameAndRerender(String fontName);
    void setFontStyleAndRerender(int fontStyle);
    void setFontSizeAndRerender(int fontSize);
    void setFontAndRerender(Font font);
}
