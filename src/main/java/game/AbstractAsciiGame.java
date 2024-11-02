package game;

import engine.GameLoop;
import engine.StandardGameLoop;
import ui.AsciiUI;
import ui.StandardAsciiUI;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractAsciiGame implements AsciiGame {
    private GameLoop gameLoop;
    private AsciiUI ui;
    public AbstractAsciiGame(String title, int xSymbols, int ySymbols, int frameRate) {

        ui = new StandardAsciiUI(title, xSymbols, ySymbols);
        trySetMonospacedFont();
        ui.show();

        gameLoop = new StandardGameLoop(frameRate);
        gameLoop.tick(this::tick);
        gameLoop.fixedTick(this::fixedTick);
        gameLoop.setupTick(this::setup);
        gameLoop.start();
    }

    private void trySetMonospacedFont() {
        try {
            // Courtesy of Copilot
            InputStream resourceStream = AbstractAsciiGame.class.getResourceAsStream("/fonts/JetBrainsMono-Regular.ttf");
            if (resourceStream == null) {
                throw new IOException("Font file not found");
            }
            Font jetBrainsMonoFont = Font.createFont(Font.TRUETYPE_FONT, resourceStream).deriveFont(50f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(jetBrainsMonoFont);
            ui.setFont(jetBrainsMonoFont);
        } catch (FontFormatException e) {
            System.out.println("Could not format font. Uses default font.");
        } catch (IOException e) {
            System.out.println("Could not load font. Uses default font.");
        }
    }

    public void stopGameLoop() {
        gameLoop.stop();
    }

    @Override
    public void setColorOfSymbol(Color color, int x, int y) {
        ui.setColorOfSymbol(color, x, y);
    }
    @Override
    public void setBackgroundColor(Color color) {
        ui.setBackgroundColor(color);
    }
    @Override
    public void setSymbol(char symbol, int x, int y) {
        ui.setSymbol(symbol, x, y);
    }

    public abstract void setup();
    public abstract void tick(long deltaTime);
    public abstract void fixedTick();
}
