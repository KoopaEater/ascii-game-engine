package game;

import engine.GameLoop;
import engine.StandardGameLoop;
import ui.AsciiUI;
import ui.UI;

public abstract class AbstractGame implements Game {
    private GameLoop gameLoop;
    private UI ui;
    public AbstractGame() {

        ui = new AsciiUI("AsciiUI", 20, 10);
        ui.show();

        gameLoop = new StandardGameLoop(60);
        gameLoop.tick(this::tick);
        gameLoop.fixedTick(this::fixedTick);
        gameLoop.start();
    }

    public void stopGameLoop() {
        gameLoop.stop();
    }
    public abstract void tick(long deltaTime);
    public abstract void fixedTick();
}
