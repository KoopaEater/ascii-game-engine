package game.ascii.downloadinggame;

import actor.ActorConstants;
import actor.ascii.MutableAsciiActor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Human implements Obstacle {
    private DownloadingGame game;
    private MutableAsciiActor human;
    private List<Vision> visions;
    private Vector2D pos;
    private Vector2D direction;
    private int moveDelay;
    private int moveCounter;
    private int coneSize;

    private List<Vector2D> path, dirs;
    private int pathIndex;

    public Human(DownloadingGame game, int x, int y, int moveDelay, int coneSize) {
        this.game = game;
        pos = new Vector2D(x, y);
        this.moveDelay = moveDelay;
        moveCounter = 0;
        this.coneSize = coneSize;

        visions = new ArrayList<>();
        path = new ArrayList<>();
        dirs = new ArrayList<>();
        pathIndex = 0;

        human = game.createSymbolActor();
        Vector2D screenPosition = game.calcScreenPosition(x, y);
        human.moveTo((int)screenPosition.x, (int)screenPosition.y);
        human.setSymbol('☺');
        human.setColor(Color.lightGray);
        human.setZ(ActorConstants.Z_DEFAULT);
        human.show();

        game.addToObstaclesAndEverything(this);
        game.addToHumans(this);
        setDirection(new Vector2D(0, 1));

    }

    private void step() {
        if (pos.equals(path.get(pathIndex % path.size()))) {
            pathIndex++;
            direction = dirs.get(pathIndex % path.size());
            setDirection(direction);
        } else {
            move((int)direction.x, (int)direction.y);
        }
        game.checkPlayerInVision();
    }

    public void tryStep() {
        if (moveCounter >= moveDelay) {
            step();
            moveCounter = 0;
        }
        moveCounter++;
    }

    public void setPath(List<Vector2D> path) {
        this.path = path;
    }

    public void setDirs(List<Vector2D> dirs) {
        this.dirs = dirs;
    }

    public void setDirection(Vector2D direction) {
        this.direction = direction;
        for (Vision vision : visions) {
            MutableAsciiActor actor = vision.getActor();
            actor.hide();
            game.removeFromVisionAndEverything(vision);
            game.removeActor(actor);
        }
        visions = VisionCalculator.calculateConeCells(pos, direction, 60, coneSize, game, Color.ORANGE);
    }

    public void move(int dx, int dy) {
        pos.x += dx;
        pos.y += dy;
        human.move(dx, dy);
        for (Vision vision : visions) {
            vision.move(dx, dy);
        }
    }

    @Override
    public boolean isAt(int x, int y) {
        return pos.x == x && pos.y == y;
    }

    @Override
    public MutableAsciiActor getActor() {
        return human;
    }
}
