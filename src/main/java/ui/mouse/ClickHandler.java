package ui.mouse;

public interface ClickHandler {
    void onClick(ClickEvent e);
    ClickEvent  getLastClick();

}
