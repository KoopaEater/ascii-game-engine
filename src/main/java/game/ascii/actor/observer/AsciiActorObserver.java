package game.ascii.actor.observer;

import game.ascii.actor.AsciiActor;

public interface AsciiActorObserver {
    void onChange(AsciiActor actor);
    void onMove(int xFrom, int yFrom, int xTo, int yTo, AsciiActor actor);
}
