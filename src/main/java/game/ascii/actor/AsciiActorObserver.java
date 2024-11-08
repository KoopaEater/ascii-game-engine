package game.ascii.actor;

public interface AsciiActorObserver {
    void onChange(AsciiActor actor);
    void onMove(int xFrom, int yFrom, int xTo, int yTo, AsciiActor actor);
}
