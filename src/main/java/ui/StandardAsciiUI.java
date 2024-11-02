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

    public StandardAsciiUI(String title, int xSymbols, int ySymbols) {

        this.xSymbols = xSymbols;
        this.ySymbols = ySymbols;

        ui = new StandardSwingUI(title);
        ui.setResizable(false);
        setFontAndRerender(new Font("Monospaced", Font.PLAIN, 14));
        renderUI();
    }

    private void renderUI() {
        Container content = getFilledScreen(xSymbols, ySymbols);
        ui.setContent(content);
    }

    private Container getFilledScreen(int xSymbols, int ySymbols) {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        for (int y = 0; y < ySymbols; y++) {
            JPanel line = new JPanel();
            line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));

            for (int x = 0; x < xSymbols; x++) {
                JLabel symbol = new JLabel("@");
                Font font = new Font(fontName, fontStyle, fontSize);
                symbol.setFont(font);
                line.add(symbol);
            }
            contentPane.add(line);
        }

        contentPane.doLayout();

        return contentPane;
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
    public void setFontNameAndRerender(String fontName) {
        this.fontName = fontName;
        font = new Font(fontName, fontStyle, fontSize);
        renderUI();
    }

    @Override
    public void setFontStyleAndRerender(int fontStyle) {
        this.fontStyle = fontStyle;
        font = new Font(fontName, fontStyle, fontSize);
        renderUI();
    }

    @Override
    public void setFontSizeAndRerender(int fontSize) {
        this.fontSize = fontSize;
        font = new Font(fontName, fontStyle, fontSize);
        renderUI();
    }

    @Override
    public void setFontAndRerender(Font font) {
        fontName = font.getFontName();
        fontStyle = font.getStyle();
        fontSize = font.getSize();
        this.font = font;
        renderUI();
    }

}
