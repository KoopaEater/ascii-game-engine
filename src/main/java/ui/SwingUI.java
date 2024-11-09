package ui;

import java.awt.*;
import java.awt.event.KeyListener;

public interface SwingUI extends UI {
    void setContent(Container content);
    void updateSizeToFit();
    void addKeyListener(KeyListener keyListener);
}
