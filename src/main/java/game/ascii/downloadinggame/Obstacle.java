package game.ascii.downloadinggame;

import actor.ascii.MutableAsciiActor;

public interface Obstacle {
    boolean isAt(int x, int y);

    MutableAsciiActor getActor();
}
