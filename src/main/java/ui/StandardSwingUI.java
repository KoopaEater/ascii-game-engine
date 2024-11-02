package ui;

import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;

public class StandardSwingUI implements SwingUI {

    private JFrame mainFrame;

    public StandardSwingUI(String title) {
        mainFrame = new JFrame(title);
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

    @Override
    public void setContent(Container content) {
        mainFrame.setContentPane(content);
    }

    @Override
    public void updateSizeToFit() {
        mainFrame.pack();
    }

    @Override
    public void setResizable(boolean resizable) {
        mainFrame.setResizable(resizable);
    }

    @Override
    public void show() {
        mainFrame.setVisible(true);
    }


}
