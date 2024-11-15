package game.ascii.downloadinggame;

import actor.ActorConstants;
import actor.ascii.MutableAsciiActor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Looker implements Obstacle{

    private DownloadingGame game;
    private MutableAsciiActor looker;
    private Vector2D pos;
    private Vector2D direction;
    private List<Vision> visions;
    private int lookDelay;
    private int lookCounter;
    private boolean looking;

    public Looker(DownloadingGame game, int x, int y, Vector2D direction, int lookDelay) {
        this.game = game;
        pos = new Vector2D(x, y);
        this.lookDelay = lookDelay;
        looking = true;

        visions = new ArrayList<>();

        looker = game.createSymbolActor();
        Vector2D screenPos = game.calcScreenPosition((int)pos.x, (int)pos.y);
        looker.moveTo((int)screenPos.x, (int)screenPos.y);
        looker.setSymbol('♠');
        looker.setColor(Color.lightGray);
        looker.setZ(ActorConstants.Z_DEFAULT);
        looker.show();

        game.addToObstaclesAndEverything(this);
        setCone(direction, 8);
    }

    private void toggleLook() {
        looking = !looking;
        if (looking) {
            setCone(direction, 8);
            game.checkPlayerInVision();
        } else {
            setCone(direction, 0);
        }
    }

    public void tryLook() {
        if (lookCounter >= lookDelay) {
            toggleLook();
            lookCounter = 0;
        }
        lookCounter++;
    }

    public void setCone(Vector2D direction, int coneSize) {
        this.direction = direction;
        for (Vision vision : visions) {
            MutableAsciiActor actor = vision.getActor();
            actor.hide();
            game.removeFromVisionAndEverything(vision);
            game.removeActor(actor);
        }
        visions = VisionCalculator.calculateConeCells(pos, direction, 60, coneSize, game);
    }

    @Override
    public boolean isAt(int x, int y) {
        return pos.x == x && pos.y == y;
    }

    @Override
    public MutableAsciiActor getActor() {
        return looker;
    }
}
