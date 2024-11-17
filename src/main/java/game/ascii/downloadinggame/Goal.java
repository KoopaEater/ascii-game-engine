package game.ascii.downloadinggame;

import actor.ActorConstants;
import actor.ascii.MutableAsciiActor;

import java.awt.*;

public class Goal {

    private MutableAsciiActor actor;
    private Vector2D pos;

    public Goal(DownloadingGame game, int x, int y) {

        pos = new Vector2D(x, y);

        actor = game.createSymbolActor();
        actor.setSymbol('█');
        actor.setColor(Color.GREEN);
        Vector2D screenPosition = game.calcScreenPosition(x, y);
        actor.moveTo((int)screenPosition.x, (int)screenPosition.y);
        actor.setZ(ActorConstants.Z_BACKGROUND);
        actor.show();

        game.addToEverything(actor);
    }

    public boolean isAt(int x, int y) {
        return pos.x == x && pos.y == y;
    }

}
