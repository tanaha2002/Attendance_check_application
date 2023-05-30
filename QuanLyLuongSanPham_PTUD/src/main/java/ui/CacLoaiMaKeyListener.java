package ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CacLoaiMaKeyListener  implements KeyListener {
	
	 

	    @Override
	    public void keyTyped(KeyEvent e) {
	        char c = e.getKeyChar();
	        if (!Character.isLetterOrDigit(c) || Character.isWhitespace(c) || isAllowedSymbol(c) ) {
	            e.consume(); // ignore non-letter or non-digit key presses
	        }
	    }

	    @Override
	    public void keyPressed(KeyEvent e) {
	        // do nothing
	    }

	    @Override
	    public void keyReleased(KeyEvent e) {


}
	    
	    private boolean isAllowedSymbol(char c) {
	        String allowedSymbols = "@#$"; // Add any additional special symbols you want to allow
	        return allowedSymbols.indexOf(c) != -1;
	    }
}