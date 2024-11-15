package actor.ascii.actormap;

import actor.ascii.AsciiActor;
import actor.ascii.EmptyAsciiActor;
import actor.ascii.NoAsciiActor;
import actor.ascii.observer.AsciiActorObserver;
import game.ascii.AsciiGame;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class StandardAsciiActorMap implements AsciiActorMap, AsciiActorObserver {
    private List<AsciiActor>[][] actorMap;
    private AsciiGame game;
    int xSymbols, ySymbols;
    public StandardAsciiActorMap(AsciiGame game) {
        this.game = game;
        xSymbols = game.getXSymbols();
        ySymbols = game.getYSymbols();
        actorMap = new List[ySymbols][xSymbols];
        for (int y = 0; y < ySymbols; y++) {
            for (int x = 0; x < xSymbols; x++) {
                actorMap[y][x] = new LinkedList<>();
            }
        }
    }

    private void removeActorFrom(AsciiActor actor, int x, int y) {
        if (!isValidSpot(x, y)) return;
        actorMap[y][x].remove(actor);
    }

    // PRECONDITION: actor is the top actor
    private void update(AsciiActor actor) {
        updateSymbol(actor);
        updateColor(actor);
        updateBackground(actor);
    }

    private void update(int x, int y) {
        AsciiActor topActor = getVisibleTop(x, y);
        update(topActor);
    }
    private void updateSymbol(AsciiActor actor) {
        int x = actor.getX();
        int y = actor.getY();
        if (!isValidSpot(x, y)) return;
        char symbol = actor.getSymbol();
        game.setSymbol(symbol, x, y);
    }
    private void updateColor(AsciiActor actor) {
        Color color = actor.getColor();
        int x = actor.getX();
        int y = actor.getY();
        if (!isValidSpot(x, y)) return;
        game.setColorOfSymbol(color, x, y);
    }
    private void updateBackground(AsciiActor actor) {
        // TODO: Fix this
        int x = actor.getX();
        int y = actor.getY();
        if (!isValidSpot(x, y)) return;
        boolean opaque = actor.hasBackground();
        if (!opaque) {
            game.setBackgroundOpaqueOfSymbol(false, x, y);
            return;
        }
        Color color = actor.getBackground();
        game.setBackgroundOfSymbol(color, x, y);
        game.setBackgroundOpaqueOfSymbol(true, x, y);
    }
    private AsciiActor getVisibleTop(int x, int y) {
        if (!isValidSpot(x, y)) return new NoAsciiActor();
        List<AsciiActor> actorsInThisCell = actorMap[y][x];
        for (AsciiActor actor : actorsInThisCell) {
            if (actor.isVisible()) {
                return actor;
            }
        }
        return new EmptyAsciiActor(x, y);
    }

    private boolean isValidSpot(int x, int y) {
        if (x < 0 || x >= xSymbols) return false;
        if (y < 0 || y >= ySymbols) return false;
        return true;
    }

    private void addActorOnCorrectZ(AsciiActor actor) {
        int x = actor.getX();
        int y = actor.getY();
        if (!isValidSpot(x, y)) return;
        List<AsciiActor> thisCell = actorMap[y][x];
        int i = 0;
        while (i < thisCell.size()) {
            AsciiActor otherActor = thisCell.get(i);
            if (otherActor.getZ() <= actor.getZ()) break;
            i++;
        }
        thisCell.add(i, actor);
    }

    @Override
    public void addActor(AsciiActor actor) {
        addActorOnCorrectZ(actor);
    }

    @Override
    public void removeActor(AsciiActor actor) {
        int x = actor.getX();
        int y = actor.getY();
        if (!isValidSpot(x, y)) return;
        removeActorFrom(actor, x, y);
    }

    @Override
    public void onChange(AsciiActor actor) {

        int x = actor.getX();
        int y = actor.getY();

        AsciiActor top = getVisibleTop(x, y);
        update(top);

    }

    @Override
    public void onMove(int xFrom, int yFrom, int xTo, int yTo, AsciiActor actor) {
        removeActorFrom(actor, xFrom, yFrom);
        addActorOnCorrectZ(actor);
        update(xFrom, yFrom);
        update(xTo, yTo);
    }

    @Override
    public void onUpdateZ(AsciiActor actor) {
        removeActor(actor);
        addActorOnCorrectZ(actor);
        onChange(actor);
    }
}
