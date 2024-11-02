package ui;

import javax.swing.*;
import java.awt.*;

public class StandardAsciiUI implements AsciiUI {

    private SwingUI ui;
    private Font font;
    private String fontName;
    private int fontStyle;
    private int fontSize;
    private int xSymbols, ySymbols;
    private JPanel contentPane;
    private JLabel[][] symbols;

    public StandardAsciiUI(String title, int xSymbols, int ySymbols) {

        this.xSymbols = xSymbols;
        this.ySymbols = ySymbols;

        symbols = new JLabel[ySymbols][xSymbols];

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
            line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));

            for (int x = 0; x < xSymbols; x++) {
                JLabel symbol = new JLabel("@");
                line.add(symbol);
                symbols[y][x] = symbol;
            }
            contentPane.add(line);
        }
    }

    private void redrawFont() {
        for (int y = 0; y < ySymbols; y++) {
            for (int x = 0; x < xSymbols; x++) {
                JLabel symbol = symbols[y][x];
                symbol.setFont(font);
            }
        }
        ui.updateSizeToFit();
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
    public void setFontName(String fontName) {
        this.fontName = fontName;
        font = new Font(fontName, fontStyle, fontSize);
        redrawFont();
    }

    @Override
    public void setFontStyle(int fontStyle) {
        this.fontStyle = fontStyle;
        font = new Font(fontName, fontStyle, fontSize);
        redrawFont();
    }

    @Override
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        font = new Font(fontName, fontStyle, fontSize);
        redrawFont();
    }

    @Override
    public void setFont(Font font) {
        fontName = font.getFontName();
        fontStyle = font.getStyle();
        fontSize = font.getSize();
        this.font = font;
        redrawFont();
    }

    @Override
    public void setColorOfSymbol(Color color, int x, int y) {
        JLabel symbol = symbols[y][x];
        symbol.setForeground(color);
    }

}
