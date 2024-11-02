package ui;

import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;

public class SwingUI implements UI {

    private JFrame mainFrame;

    public SwingUI(String title, Dimension dimensions) {
        mainFrame = new JFrame(title);
        mainFrame.setSize(dimensions);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        tryUseFlatLaF();
    }

    private void tryUseFlatLaF() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
    }

    protected void setContent(Container content) {
        mainFrame.setContentPane(content);
    }

    @Override
    public void show() {
        mainFrame.setVisible(true);
    }


}
