package game;

import java.awt.*;

public class StandardAsciiGame extends AbstractAsciiGame {

    public StandardAsciiGame() {
        super("AsciiGame", 25, 13);
    }

    @Override
    public void setup() {

        setBackgroundColor(Color.BLACK);
        setSymbol('O', 12, 6);
        setColorOfSymbol(Color.RED, 12, 6);

    }

    @Override
    public void tick(long deltaTime) {

    }

    @Override
    public void fixedTick() {
    }
}
