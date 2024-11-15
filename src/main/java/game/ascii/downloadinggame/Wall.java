package game.ascii.downloadinggame;

import actor.ascii.MutableAsciiActor;

import java.awt.*;

public class Wall implements Obstacle {

    private MutableAsciiActor actor;
    int x, y;

    public Wall(MutableAsciiActor actor, int x, int y, char symbol) {
        this.actor = actor;
        this.x = x;
        this.y = y;

        actor.moveTo(Constants.MID_SCREEN_X + x, Constants.MID_SCREEN_Y + y);
        actor.setSymbol(symbol);
        actor.setColor(Color.gray);
        actor.show();
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
