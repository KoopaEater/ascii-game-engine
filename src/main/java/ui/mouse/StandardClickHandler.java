package ui.mouse;

import game.ascii.AsciiGame;

public class StandardClickHandler implements ClickHandler {
    private AsciiGame game;
    public StandardClickHandler(AsciiGame game) {
        this.game = game;
    }

    @Override
    public void onClick(ClickEvent e) {
        game.onClick(e);
    }
}
