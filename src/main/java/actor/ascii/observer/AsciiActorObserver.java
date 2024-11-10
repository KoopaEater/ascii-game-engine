package actor.ascii.observer;

import actor.ascii.AsciiActor;

public interface AsciiActorObserver {
    void onChange(AsciiActor actor);
    void onMove(int xFrom, int yFrom, int xTo, int yTo, AsciiActor actor);
    void onUpdateZ(AsciiActor actor);
}
