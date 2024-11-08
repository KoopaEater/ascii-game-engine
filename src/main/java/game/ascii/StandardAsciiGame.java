package game.ascii;

import game.ascii.actor.MutableAsciiActor;

import java.awt.*;

public class StandardAsciiGame extends AbstractAsciiGame {

    MutableAsciiActor player;

    public StandardAsciiGame() {
        super("AsciiGame", 25, 13, 4);
    }

    @Override
    public void setup() {

        setBackgroundColor(Color.BLACK);

        player = createSymbolActor();
        player.setSymbol('■');
        player.setColor(Color.RED);
        player.moveTo(12, 12);
        player.show();

        setBackgroundOfSymbol(Color.WHITE, 12, 0);
        setSymbol('X', 12, 6);
        setColorOfSymbol(Color.GREEN, 12, 6);

    }

    @Override
    public void tick(long deltaTime) {

    }

    @Override
    public void fixedTick() {
        if (player.getY() > 0) {
            player.move(0, -1);
        }
    }
}
