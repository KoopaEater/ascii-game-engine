package ui.mouse;

public class EmptyClickHandler implements ClickHandler {
    @Override
    public void onClick(ClickEvent e) {

    }

    @Override
    public ClickEvent getLastClick() {
        return null;
    }
}
