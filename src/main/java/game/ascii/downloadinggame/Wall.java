package game.ascii.downloadinggame;

import actor.ascii.MutableAsciiActor;

import java.awt.*;

public class Wall implements Obstacle {

    private DownloadingGame game;
    private MutableAsciiActor actor;
    int x, y;

    public Wall(DownloadingGame game, int x, int y, char symbol) {

        this.game = game;
        this.x = x;
        this.y = y;

        actor = game.createSymbolActor();

        actor.moveTo(Constants.MID_SCREEN_X + x, Constants.MID_SCREEN_Y + y);
        actor.setSymbol(symbol);
        actor.setColor(Color.gray);
        actor.show();

        game.addToObstaclesAndEverything(this);
    }

    @Override
    public boolean isAt(int x, int y) {
        return this.x == x && this.y == y;
    }

    @Override
    public MutableAsciiActor getActor() {
        return actor;
    }
}
