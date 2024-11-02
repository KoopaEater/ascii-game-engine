package game;

import java.awt.*;

public class StandardAsciiGame extends AbstractAsciiGame {

    public StandardAsciiGame() {
        super();
    }

    @Override
    public void setup() {
        setColorOfSymbol(Color.RED, 5, 3);
    }

    @Override
    public void tick(long deltaTime) {

    }

    @Override
    public void fixedTick() {
    }
}
