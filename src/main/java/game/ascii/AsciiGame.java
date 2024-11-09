package game.ascii;

import game.Game;
import game.ascii.actor.MutableAsciiActor;

import java.awt.*;

public interface AsciiGame extends Game {
    void setFontSize(int fontSize);
    void setColorOfSymbol(Color color, int x, int y);
    void setBackgroundOfSymbol(Color color, int x, int y);
    void setBackgroundOpaqueOfSymbol(boolean opaque, int x, int y);
    void setBackgroundColor(Color color);
    void setSymbol(char symbol, int x, int y);
    Color getColorOfSymbol(int x, int y);
    Color getBackgroundOfSymbol(int x, int y);
    char getSymbol(int x, int y);
    int getXSymbols();
    int getYSymbols();
    MutableAsciiActor createSymbolActor();
}
