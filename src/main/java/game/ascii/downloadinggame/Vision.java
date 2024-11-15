package game.ascii.downloadinggame;

import actor.ActorConstants;
import actor.ascii.MutableAsciiActor;

import java.awt.*;

public class Vision {
    private DownloadingGame game;
    private Vector2D pos;
    private MutableAsciiActor actor;
    public Vision(DownloadingGame game, int x, int y) {
        this.game = game;
        pos = new Vector2D(x, y);

        actor = game.createSymbolActor();
        Vector2D screenPosition = game.calcScreenPosition(x, y);
        actor.moveTo((int)screenPosition.x, (int)screenPosition.y);
        actor.setSymbol('█');
        actor.setColor(Color.yellow);
        actor.setZ(ActorConstants.Z_BACKGROUND);
        actor.show();

        game.addToVisionAndEverything(this);
    }

    public MutableAsciiActor getActor() {
        return actor;
    }

    public void move(int dx, int dy) {
        pos.x += dx;
        pos.y += dy;
        actor.move(dx, dy);
    }

    public boolean isAt(int x, int y) {
        return pos.x == x && pos.y == y;
    }
}
