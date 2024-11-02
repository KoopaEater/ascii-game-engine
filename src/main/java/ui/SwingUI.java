package ui;

import java.awt.*;

public interface SwingUI extends UI {
    void setContent(Container content);
    void updateSizeToFit();
}
