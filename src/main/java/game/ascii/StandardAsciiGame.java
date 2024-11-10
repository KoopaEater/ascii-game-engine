package game.ascii;

import game.ascii.actor.MutableAsciiActor;

import java.awt.*;
import java.awt.event.KeyEvent;

public class StandardAsciiGame extends AbstractAsciiGame {

    MutableAsciiActor player, blob, goalA, goalB, goalC;

    public StandardAsciiGame() {
        super("AsciiGame", 13, 13, 4);
    }

    @Override
    public void setup() {

        setBackgroundColor(Color.BLACK);

        player = createSymbolActor();
        player.setSymbol('■');
        player.setColor(Color.GREEN);
        player.moveTo(6, 12);
        player.show();

        blob = createSymbolActor();
        blob.setSymbol('♥');
        blob.setColor(Color.RED);
        blob.moveTo(6, 6);
        blob.show();

        goalB = createSymbolActor();
        goalB.setBackground(Color.WHITE);
        goalB.moveTo(6, 0);
        goalB.show();

    }

    @Override
    public void tick(long deltaTime) {
        if (isKeyDown(KeyEvent.VK_SPACE)) {
            setFontSize(24);
        } else {
            setFontSize(50);
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
        if (player.getX() < 12 && getLastKey() == KeyEvent.VK_RIGHT) {
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
