package game.ascii.actor;

import game.ascii.AsciiGame;

import java.util.LinkedList;
import java.util.List;

public class StandardAsciiActorMap implements AsciiActorMap, AsciiActorObserver {
    private List<AsciiActor>[][] actorMap;
    private AsciiGame game;
    public StandardAsciiActorMap(AsciiGame game) {
        int xSymbols = game.getXSymbols();
        int ySymbols = game.getYSymbols();
        actorMap = new List[xSymbols][ySymbols];
        for (int y = 0; y < ySymbols; y++) {
            for (int x = 0; x < xSymbols; x++) {
                actorMap[y][x] = new LinkedList<AsciiActor>();
            }
        }
    }

    @Override
    public void addActor(AsciiActor actor, int x, int y) {
        actorMap[y][x].addLast(actor);
    }

    @Override
    public void onChange(AsciiActor actor) {
        int x = actor.getX();
        int y = actor.getY();
        AsciiActor top = actorMap[y][x].getLast();
        if (top != actor) {
            return;
        }
        // DO CHANGE!
    }

    @Override
    public void onMove(int xFrom, int yFrom, int xTo, int yTo, AsciiActor actor) {

    }
}
