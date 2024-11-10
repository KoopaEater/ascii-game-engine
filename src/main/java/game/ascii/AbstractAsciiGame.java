package game.ascii;

import engine.GameLoop;
import engine.StandardGameLoop;
import game.ascii.actor.AsciiActor;
import game.ascii.actor.MutableAsciiActor;
import game.ascii.actor.actormap.StandardAsciiActorMap;
import game.ascii.actor.SymbolAsciiActor;
import game.ascii.actor.text.MutableTextAsciiActor;
import game.ascii.actor.text.SymbolTextAsciiActor;
import ui.AsciiUI;
import ui.StandardAsciiUI;
import ui.keyboard.StandardKeyboardHandler;
import ui.mouse.ClickEvent;
import ui.mouse.StandardClickHandler;

import java.awt.*;

public abstract class AbstractAsciiGame implements AsciiGame {
    private GameLoop gameLoop;
    private AsciiUI ui;
    private StandardAsciiActorMap actorMap;
    private StandardKeyboardHandler keyboardHandler;
    private StandardClickHandler clickHandler;
    private int xSymbols, ySymbols;
    public AbstractAsciiGame(String title, int xSymbols, int ySymbols, int frameRate) {

        this.xSymbols = xSymbols;
        this.ySymbols = ySymbols;

        actorMap = new StandardAsciiActorMap(this);
        keyboardHandler = new StandardKeyboardHandler(this);
        clickHandler = new StandardClickHandler(this);

        ui = new StandardAsciiUI(title, xSymbols, ySymbols);
        ui.setFontSize(50);
        ui.addKeyListener(keyboardHandler);
        ui.addClickHandler(clickHandler);
        ui.show();

        gameLoop = new StandardGameLoop(frameRate);
        gameLoop.tick(this::tick);
        gameLoop.fixedTick(this::fixedTick);
        gameLoop.setupTick(this::setup);
        gameLoop.start();
    }

    public void stopGameLoop() {
        gameLoop.stop();
    }
    public void startGameLoop() {
        gameLoop.start();
    }

    @Override
    public void setFontSize(int fontSize) {
        ui.setFontSize(fontSize);
    }
    @Override
    public void setColorOfSymbol(Color color, int x, int y) {
        ui.setColorOfSymbol(color, x, y);
    }
    @Override
    public void setBackgroundOfSymbol(Color color, int x, int y) {
        ui.setBackgroundOfSymbol(color, x, y);
    }
    @Override
    public void setBackgroundOpaqueOfSymbol(boolean opaque, int x, int y) {
        ui.setBackgroundOpaqueOfSymbol(opaque, x, y);
    }
    @Override
    public void setBackgroundColor(Color color) {
        ui.setBackgroundColor(color);
    }
    @Override
    public void setSymbol(char symbol, int x, int y) {
        ui.setSymbol(symbol, x, y);
    }
    @Override
    public Color getColorOfSymbol(int x, int y) {
        return ui.getColorOfSymbol(x, y);
    }
    @Override
    public Color getBackgroundOfSymbol(int x, int y) {
        return ui.getColorOfSymbol(x, y);
    }
    @Override
    public char getSymbol(int x, int y) {
        return ui.getSymbol(x, y);
    }
    @Override
    public int getXSymbols() {
        return xSymbols;
    }
    @Override
    public int getYSymbols() {
        return ySymbols;
    }
    @Override
    public MutableAsciiActor createSymbolActor() {
        SymbolAsciiActor actor = new SymbolAsciiActor();
        actor.addObserver(actorMap);
        actorMap.addActor(actor);
        return actor;
    }
    @Override
    public MutableTextAsciiActor createSymbolTextActor(int length) {
        SymbolTextAsciiActor actor = new SymbolTextAsciiActor(length);
        actor.addObserver(actorMap);
        AsciiActor[] symbolActors = actor.getActors();
        for (AsciiActor symbolActor : symbolActors) {
            actorMap.addActor(symbolActor);
        }
        return actor;
    }

    @Override
    public boolean isKeyDown(int keyCode) {
        return keyboardHandler.isKeyDown(keyCode);
    }
    @Override
    public int getLastKey() {
        return keyboardHandler.getLastKey();
    }
    @Override
    public ClickEvent getLastClick() {
        return clickHandler.getLastClick();
    }

    public abstract void setup();
    public abstract void tick(long deltaTime);
    public abstract void fixedTick();
}
