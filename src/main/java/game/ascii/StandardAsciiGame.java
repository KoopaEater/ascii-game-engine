package game.ascii;

import game.ascii.actor.MutableAsciiActor;

import java.awt.*;
import java.awt.event.KeyEvent;

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
        if (isKeyDown(KeyEvent.VK_SPACE)) {
            setFontSize(50);
        } else {
            setFontSize(24);
        }
    }

    @Override
    public void fixedTick() {
        if (player.getY() > 0 && getLastKey() == KeyEvent.VK_UP) {
            player.move(0, -1);
        }
        if (player.getY() < 12 && getLastKey() == KeyEvent.VK_DOWN) {
            player.move(0, 1);
        }
        if (player.getX() > 0 && getLastKey() == KeyEvent.VK_LEFT) {
            player.move(-1, 0);
        }
        if (player.getX() < 24 && getLastKey() == KeyEvent.VK_RIGHT) {
            player.move(1, 0);
        }
    }

    @Override
    public void onKeyPressed(KeyEvent e) {

    }

    @Override
    public void onKeyReleased(KeyEvent e) {

    }

    @Override
    public void onKeyTyped(KeyEvent e) {

    }
}
