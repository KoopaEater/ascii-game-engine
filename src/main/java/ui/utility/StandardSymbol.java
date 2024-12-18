package ui.utility;

import javax.swing.*;
import java.awt.*;

public class StandardSymbol implements Symbol {

    private JLabel label;

    public StandardSymbol(JLabel label) {
        this.label = label;
    }

    @Override
    public void setSymbol(char symbol) {
        label.setText(String.valueOf(symbol));
    }

    @Override
    public void setColor(Color color) {
        label.setForeground(color);
    }

    @Override
    public void setBackground(Color color) {
        label.setBackground(color);
    }

    @Override
    public void setBackgroundOpaque(boolean opaque) {
        label.setOpaque(opaque);
    }

    @Override
    public void setFont(Font font) {
        label.setFont(font);
    }

    @Override
    public Color getColor() {
        return label.getForeground();
    }

    @Override
    public Color getBackground() {
        return label.getBackground();
    }

    @Override
    public char getSymbol() {
        return label.getText().charAt(0);
    }
}
