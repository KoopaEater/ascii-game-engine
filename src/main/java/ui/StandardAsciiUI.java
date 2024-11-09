package ui;

import ui.utility.StandardSymbol;
import ui.utility.Symbol;
import ui.utility.SymbolFunction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class StandardAsciiUI implements AsciiUI {

    private SwingUI ui;
    private Font font;
    private String fontName;
    private int fontStyle;
    private int fontSize;
    private int xSymbols, ySymbols;
    private JPanel contentPane;
    private Symbol[][] symbols;

    public StandardAsciiUI(String title, int xSymbols, int ySymbols) {

        this.xSymbols = xSymbols;
        this.ySymbols = ySymbols;

        symbols = new Symbol[ySymbols][xSymbols];

        ui = new StandardSwingUI(title);
        ui.setResizable(false);
        fillScreen(xSymbols, ySymbols);
        setFont(new Font("Monospaced", Font.PLAIN, 14));
        ui.setContent(contentPane);
        ui.updateSizeToFit();
    }

    private void fillScreen(int xSymbols, int ySymbols) {
        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        for (int y = 0; y < ySymbols; y++) {
            JPanel line = new JPanel();
            line.setOpaque(false);
            line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));

            for (int x = 0; x < xSymbols; x++) {
                JLabel symbolLabel = new JLabel(" ");
                line.add(symbolLabel);
                symbols[y][x] = new StandardSymbol(symbolLabel);
            }
            contentPane.add(line);
        }
    }

    private void redrawFont() {
        applyToAllSymbols(symbol -> symbol.setFont(font));
        ui.updateSizeToFit();
    }

    private void applyToAllSymbols(SymbolFunction fun) {
        for (int y = 0; y < ySymbols; y++) {
            for (int x = 0; x < xSymbols; x++) {
                Symbol symbol = symbols[y][x];
                fun.apply(symbol);
            }
        }
    }

    @Override
    public void show() {
        ui.show();
    }

    @Override
    public void setResizable(boolean resizable) {
        ui.setResizable(resizable);
    }

    @Override
    public void addKeyListener(KeyListener keyListener) {
        ui.addKeyListener(keyListener);
    }

    @Override
    public void setFontName(String fontName) {
        if (this.fontName != null && this.fontName.equals(fontName)) {
            return;
        }
        this.fontName = fontName;
        font = new Font(fontName, fontStyle, fontSize);
        redrawFont();
    }

    @Override
    public void setFontStyle(int fontStyle) {
        if (this.fontStyle == fontStyle) {
            return;
        }
        this.fontStyle = fontStyle;
        font = new Font(fontName, fontStyle, fontSize);
        redrawFont();
    }

    @Override
    public void setFontSize(int fontSize) {
        if (this.fontSize == fontSize) {
            return;
        }
        this.fontSize = fontSize;
        font = new Font(fontName, fontStyle, fontSize);
        redrawFont();
    }

    @Override
    public void setFont(Font font) {
        if (this.font != null && this.font.equals(font)) {
            return;
        }
        fontName = font.getFontName();
        fontStyle = font.getStyle();
        fontSize = font.getSize();
        this.font = font;
        redrawFont();
    }

    @Override
    public void setColorOfSymbol(Color color, int x, int y) {
        symbols[y][x].setColor(color);
    }

    @Override
    public void setBackgroundOfSymbol(Color color, int x, int y) {
        symbols[y][x].setBackground(color);
    }

    @Override
    public void setBackgroundOpaqueOfSymbol(boolean opaque, int x, int y) {
        symbols[y][x].setBackgroundOpaque(opaque);
    }

    @Override
    public void setBackgroundColor(Color color) {
        contentPane.setBackground(color);
    }

    @Override
    public void setSymbol(char symbol, int x, int y) {
        symbols[y][x].setSymbol(symbol);
    }

    @Override
    public Color getColorOfSymbol(int x, int y) {
        return symbols[y][x].getColor();
    }

    @Override
    public Color getBackgroundOfSymbol(int x, int y) {
        return symbols[y][x].getBackground();
    }

    @Override
    public char getSymbol(int x, int y) {
        return symbols[y][x].getSymbol();
    }

}
