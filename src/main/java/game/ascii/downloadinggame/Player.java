package game.ascii.downloadinggame;

import actor.ActorConstants;
import actor.ascii.MutableAsciiActor;

import java.awt.*;

public class Player {
    private MutableAsciiActor player, pirate;
    private DownloadingGame game;
    private int x, y;

    private boolean inDanger;

    public Player(DownloadingGame game) {
        this.game = game;

        inDanger = false;
        x = y = 0;

        this.player = game.createSymbolActor();
        this.pirate = game.createSymbolActor();

        player.moveTo(12, 12);
        player.setSymbol('☻');
        player.setColor(Color.lightGray);
        player.setZ(ActorConstants.Z_FOREGROUND);
        player.show();

        pirate.moveTo(12, 12);
        pirate.setSymbol('•');
        pirate.setZ(ActorConstants.Z_FOREGROUND);
    }

    public void moveToWorldPos(int x, int y) {
        int dx = x - this.x;
        int dy = y - this.y;

        this.x = x;
        this.y = y;

        game.moveEverythingExceptPlayerOpposite(dx, dy);
    }

    public void move(int dx, int dy) {

        if (game.obstacleAt(x+dx, y+dy)) {
            standStill();
            return;
        }

        x += dx;
        y += dy;

        game.moveEverythingExceptPlayerOpposite(dx, dy);
        pirate.moveTo(Constants.MID_SCREEN_X-dx, Constants.MID_SCREEN_Y-dy);
        if (!inDanger) {
            pirate.show();
            inDanger = true;
        }
    }

    public void standStill() {
        inDanger = false;
        pirate.hide();
    }

    public Vector2D getPosition() {
        return new Vector2D(x, y);
    }

    public boolean isInDanger() {
        return inDanger;
    }
}
