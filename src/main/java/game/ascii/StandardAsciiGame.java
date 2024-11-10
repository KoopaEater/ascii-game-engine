package game.ascii;

import actor.ActorConstants;
import actor.ascii.MutableAsciiActor;
import actor.ascii.text.MutableTextAsciiActor;
import ui.mouse.ClickEvent;

import java.awt.*;
import java.awt.event.KeyEvent;

public class StandardAsciiGame extends AbstractAsciiGame {

    MutableAsciiActor player, heart, goalA;
    MutableTextAsciiActor text;

    public StandardAsciiGame() {
        super("AsciiGame", 13, 13, 4);
    }

    @Override
    public void setup() {

        setBackgroundColor(Color.GRAY);

        player = createSymbolActor();
        player.setSymbol('☻');
        player.setColor(Color.YELLOW);
        player.moveTo(6, 12);
        player.setZ(ActorConstants.Z_FOREGROUND);
        player.show();

        heart = createSymbolActor();
        heart.setSymbol('♥');
        heart.setColor(Color.RED);
        heart.moveTo(6, 6);
        heart.setZ(ActorConstants.Z_DEFAULT);
        heart.show();

        goalA = createSymbolActor();
        goalA.setBackground(Color.WHITE);
        goalA.moveTo(6, 0);
        goalA.setZ(ActorConstants.Z_BACKGROUND);
        goalA.show();

        text = createSymbolTextActor(13);
        text.setText("Hej!");
        text.setZ(ActorConstants.Z_OVERLAY);
        text.show();

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
        if (getLastKey() == KeyEvent.VK_UP) {
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

    @Override
    public void onClick(ClickEvent e) {
        text.setOrigin(e.getX(), e.getY());
    }
}
