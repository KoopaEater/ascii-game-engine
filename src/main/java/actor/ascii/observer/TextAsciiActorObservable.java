package actor.ascii.observer;

public interface TextAsciiActorObservable {
    void addObserver(AsciiActorObserver observer);
    void removeObserver(AsciiActorObserver observer);
}
