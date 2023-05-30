package ui;

import java.awt.event.*;

public class ChuoiKeyListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (Character.isLetter(c)  ||  Character.isWhitespace(c)) {
            // allow letter keys
        } else {
            e.consume(); // ignore non-letter key presses
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