package game;

import java.awt.*;

public class SymbolAsciiActor implements AsciiActor {
    private int x, y;
    private char symbol, symbolBefore;
    private Color color, colorBefore;
    private Color background, backgroundBefore;
    private AsciiGame game;
    public SymbolAsciiActor(AsciiGame game) {
        symbol = 'X';
        x = y = 0;
        color = Color.BLACK;
        this.game = game;

        symbolBefore = game.getSymbol(x, y);
        colorBefore = game.getColorOfSymbol(x, y);

    }

    private void undrawCurrentSymbol() {
        game.setSymbol(symbolBefore, x, y);
        game.setColorOfSymbol(colorBefore, x, y);
    }

    private void drawCurrentSymbol() {
        game.setSymbol(symbol, x, y);
        game.setColorOfSymbol(color, x, y);
    }

    @Override
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void move(int dx, int dy) {
        undrawCurrentSymbol();
        x += dx;
        y += dy;
        symbolBefore = game.getSymbol(x, y);
        colorBefore = game.getColorOfSymbol(x, y);
        drawCurrentSymbol();
    }

    @Override
    public void moveTo(int x, int y) {
        undrawCurrentSymbol();
        this.x = x;
        this.y = y;
        symbolBefore = game.getSymbol(x, y);
        drawCurrentSymbol();
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void show() {
        drawCurrentSymbol();
    }

    @Override
    public void hide() {
        undrawCurrentSymbol();
    }
}
