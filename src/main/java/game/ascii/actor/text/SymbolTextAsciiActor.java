package game.ascii.actor.text;

import game.ascii.actor.AsciiActor;
import game.ascii.actor.SymbolAsciiActor;
import game.ascii.actor.observer.AsciiActorObserver;
import game.ascii.actor.observer.TextAsciiActorObservable;

import java.awt.*;

public class SymbolTextAsciiActor implements MutableTextAsciiActor, TextAsciiActorObservable {
    private int length;
    private String text;
    private int x, y;
    SymbolAsciiActor[] actors;
    public SymbolTextAsciiActor(int length) {
        this.length = length;
        text = "";
        x = y = 0;
        actors = new SymbolAsciiActor[length];
        fillActors();
    }

    private void fillActors() {
        for (int i = 0; i < length; i++) {
            actors[i] = new SymbolAsciiActor();
        }
        moveActorsToCorrectLocation();
    }
    private void moveActorsToCorrectLocation() {
        for (int i = 0; i < length; i++) {
            actors[i].moveTo(x + i, y);
        }
    }
    private void clearSymbols() {
        for (SymbolAsciiActor actor : actors) {
            actor.hide();
        }
    }

    @Override
    public void setText(String text) {
        clearSymbols();
        this.text = text;
        char[] chars = text.toCharArray();
        int maxIndex = Math.min(chars.length, length) - 1;
        for (int i = 0; i <= maxIndex; i++) {
            SymbolAsciiActor actor = actors[i];
            actor.setSymbol(chars[i]);
            actor.show();
        }
    }

    @Override
    public void setColor(Color color) {
        for (SymbolAsciiActor actor : actors) {
            actor.setColor(color);
        }
    }

    @Override
    public void setBackground(Color color) {
        for (SymbolAsciiActor actor : actors) {
            actor.setBackground(color);
        }
    }

    @Override
    public void removeBackground() {
        for (SymbolAsciiActor actor : actors) {
            actor.removeBackground();
        }
    }

    @Override
    public void setOrigin(int x, int y) {
        this.x = x;
        this.y = y;
        moveActorsToCorrectLocation();
    }

    @Override
    public void show() {
        for (int i = 0; i < length; i++) {
            actors[i].show();
        }

    }

    @Override
    public void hide() {
        for (SymbolAsciiActor actor : actors) {
            actor.hide();
        }
    }

    @Override
    public void toggleVisibility() {
        for (SymbolAsciiActor actor : actors) {
            actor.toggleVisibility();
        }
    }

    @Override
    public void addObserver(AsciiActorObserver observer) {
        for (SymbolAsciiActor actor : actors) {
            actor.addObserver(observer);
        }
    }

    @Override
    public void removeObserver(AsciiActorObserver observer) {
        for (SymbolAsciiActor actor : actors) {
            actor.removeObserver(observer);
        }
    }

    @Override
    public AsciiActor[] getActors() {
        return actors;
    }
}
