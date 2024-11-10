package ui;

import game.ascii.AbstractAsciiGame;
import ui.mouse.StandardClickEvent;
import ui.mouse.ClickEvent;
import ui.mouse.ClickHandler;
import ui.mouse.EmptyClickHandler;
import ui.utility.StandardSymbol;
import ui.utility.Symbol;
import ui.utility.SymbolFunction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;

public class StandardAsciiUI implements AsciiUI {

    private SwingUI ui;
    private Font font;
    private String fontName;
    private int fontStyle;
    private int fontSize;
    private int xSymbols, ySymbols;
    private JPanel contentPane;
    private Symbol[][] symbols;
    private ClickHandler clickHandler;

    public StandardAsciiUI(String title, int xSymbols, int ySymbols) {

        this.xSymbols = xSymbols;
        this.ySymbols = ySymbols;

        symbols = new Symbol[ySymbols][xSymbols];

        clickHandler = new EmptyClickHandler();

        ui = new StandardSwingUI(title);
        ui.setResizable(false);
        fillScreen(xSymbols, ySymbols);
        setFont(new Font("Monospaced", Font.PLAIN, 14));
        trySetSquareFont();
        ui.setContent(contentPane);
        ui.updateSizeToFit();
    }

    // https://int10h.org/oldschool-pc-fonts/fontlist/font?hp_100lx_8x8
    private void trySetSquareFont() {
        try {
            // Courtesy of Copilot
            InputStream resourceStream = AbstractAsciiGame.class.getResourceAsStream("/fonts/Px437_HP_100LX_8x8.ttf");
            if (resourceStream == null) {
                throw new IOException("Font file not found");
            }
            Font jetBrainsMonoFont = Font.createFont(Font.TRUETYPE_FONT, resourceStream);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(jetBrainsMonoFont);
            setFont(jetBrainsMonoFont);
        } catch (FontFormatException e) {
            System.out.println("Could not format font. Uses default font.");
        } catch (IOException e) {
            System.out.println("Could not load font. Uses default font.");
        }
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
                symbolLabel.addMouseListener(getMouseListenerFor(x, y));
            }
            contentPane.add(line);
        }
    }

    private MouseListener getMouseListenerFor(int x, int y) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ClickEvent clickEvent = new StandardClickEvent(x, y);
                clickHandler.onClick(clickEvent);
            }
        };
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
    public void addClickHandler(ClickHandler clickHandler) {
        this.clickHandler = clickHandler;
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
