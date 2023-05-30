package ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SoKeyListener implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!(Character.isDigit(c) || c == '.')) {
            e.consume(); // ignore non-number and non-dot key presses
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // do nothing
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // do nothing
    }
}
