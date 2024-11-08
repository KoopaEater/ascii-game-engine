package game.ascii.actor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class SymbolAsciiActor implements MutableAsciiActor, AsciiActorObservable {
    private int x, y;
    private char symbol;
    private Color color;
    private Color background;
    private boolean hasBackground;
    private boolean visible;
    private List<AsciiActorObserver> observers;
    public SymbolAsciiActor() {
        symbol = ' ';
        x = y = 0;
        color = Color.BLACK;
        background = Color.WHITE;
        hasBackground = false;
        visible = false;
        observers = new ArrayList<>();
    }
    private void notifyChange() {
        for (AsciiActorObserver observer : observers) {
            observer.onChange(this);
        }
    }
    private void notifyMove(int xFrom, int yFrom, int xTo, int yTo) {
        for (AsciiActorObserver observer : observers) {
            observer.onMove(xFrom, yFrom, xTo, yTo, this);
        }
    }

    @Override
    public void setSymbol(char symbol) {
        this.symbol = symbol;
        notifyChange();
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
        notifyChange();
    }

    @Override
    public void move(int dx, int dy) {
        int xBefore = x;
        int yBefore = y;
        x += dx;
        y += dy;
        notifyMove(xBefore, yBefore, x, y);
    }

    @Override
    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
        notifyChange();
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
    public char getSymbol() {
        return symbol;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Color getBackground() {
        return background;
    }

    @Override
    public boolean hasBackground() {
        return hasBackground;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void show() {
        visible = true;
        notifyChange();
    }

    @Override
    public void hide() {
        visible = false;
        notifyChange();
    }

    @Override
    public void toggleVisibility() {
        visible = !visible;
        notifyChange();
    }

    @Override
    public void addObserver(AsciiActorObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(AsciiActorObserver observer) {
        observers.remove(observer);
    }
}
