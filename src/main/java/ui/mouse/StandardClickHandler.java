package ui.mouse;

import game.ascii.AsciiGame;

public class StandardClickHandler implements ClickHandler {
    private AsciiGame game;
    private ClickEvent lastClick;
    public StandardClickHandler(AsciiGame game) {
        this.game = game;
        lastClick = new EmptyClickEvent();
    }

    @Override
    public void onClick(ClickEvent e) {
        game.onClick(e);
        lastClick = e;
    }

    @Override
    public ClickEvent getLastClick() {
        return lastClick;
    }
}
