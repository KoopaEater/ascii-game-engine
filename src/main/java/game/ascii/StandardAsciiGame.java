package game.ascii;

import game.ascii.actor.MutableAsciiActor;

import java.awt.*;

public class StandardAsciiGame extends AbstractAsciiGame {

    MutableAsciiActor player, blob, goalA, goalB, goalC;

    public StandardAsciiGame() {
        super("AsciiGame", 25, 13, 4);
    }

    @Override
    public void setup() {

        setBackgroundColor(Color.BLACK);

        player = createSymbolActor();
        player.setSymbol('■');
        player.setColor(Color.GREEN);
        player.moveTo(12, 12);
        player.show();

        blob = createSymbolActor();
        blob.setSymbol('●');
        blob.setColor(Color.RED);
        blob.moveTo(12, 6);
        blob.show();

        goalA = createSymbolActor();
        goalA.setBackground(Color.WHITE);
        goalA.moveTo(11, 0);
        goalA.show();

        goalB = createSymbolActor();
        goalB.setBackground(Color.WHITE);
        goalB.moveTo(12, 0);
        goalB.show();

        goalC = createSymbolActor();
        goalC.setBackground(Color.WHITE);
        goalC.moveTo(13, 0);
        goalC.show();

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
