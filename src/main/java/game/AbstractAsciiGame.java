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
    public AbstractAsciiGame() {

        ui = new StandardAsciiUI("AsciiUI", 20, 10);
        trySetMonospacedFont();
        ui.show();

        gameLoop = new StandardGameLoop(60);
        gameLoop.tick(this::tick);
        gameLoop.fixedTick(this::fixedTick);
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
        System.out.println("Abstract");
        ui.setColorOfSymbol(color, x, y);
    }

    public abstract void tick(long deltaTime);
    public abstract void fixedTick();
}
