package actor.ascii.observer;

public interface AsciiActorObservable {
    void addObserver(AsciiActorObserver observer);
    void removeObserver(AsciiActorObserver observer);
}
