package ui;

import javax.swing.*;
import java.awt.*;

public class AsciiUI implements UI{

    final static int SYMBOL_WIDTH = 20;
    final static int LINE_HEIGHT = 20;

    private SwingUI ui;

    public AsciiUI(String title, int xSymbols, int ySymbols) {
        Dimension dimensions = calcDimensions(xSymbols, ySymbols);
        ui = new SwingUI(title, dimensions);
        ui.setContent(getFilledScreen(xSymbols, ySymbols));
    }

    private Container getFilledScreen(int xSymbols, int ySymbols) {
        JLabel contentPane = new JLabel();
        contentPane.setLayout(new GridLayout(ySymbols, 1));

        for (int y = 0; y < ySymbols; y++) {
            JLabel line = new JLabel("@".repeat(xSymbols));
            contentPane.add(line);
        }

        return contentPane;
    }

    private Dimension calcDimensions(int xSymbols, int ySymbols) {
        int x = xSymbols * SYMBOL_WIDTH;
        int y = ySymbols * LINE_HEIGHT;
        return new Dimension(x, y);
    }

    @Override
    public void show() {
        ui.show();
    }
}
