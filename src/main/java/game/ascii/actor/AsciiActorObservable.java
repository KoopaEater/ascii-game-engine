package game.ascii.actor;

public interface AsciiActorObservable {
    void addObserver(AsciiActorObserver observer);
    void removeObserver(AsciiActorObserver observer);
}
