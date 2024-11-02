package game;

import java.awt.*;

public class StandardAsciiGame extends AbstractAsciiGame {

    private int y;

    public StandardAsciiGame() {
        super("AsciiGame", 25, 13, 1);
    }

    @Override
    public void setup() {

        y = 12;

        setBackgroundColor(Color.BLACK);
        setSymbol('O', 12, y);
        setColorOfSymbol(Color.RED, 12, y);

    }

    @Override
    public void tick(long deltaTime) {

    }

    @Override
    public void fixedTick() {
        setSymbol(' ', 12, y);
        setColorOfSymbol(Color.RED, 12, y);

        if (y>0) {
            y--;
        }

        setSymbol('O', 12, y);
        setColorOfSymbol(Color.RED, 12, y);


    }
}
