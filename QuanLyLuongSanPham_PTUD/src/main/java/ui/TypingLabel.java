/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

/**
 *
 *
 */
import javax.swing.JLabel;

public class TypingLabel extends JLabel implements Runnable {
    private String text = "CHƯƠNG TRÌNH QUẢN LÝ LƯƠNG SẢN PHẨM..";
    private int index = 0;
    private boolean isTyping = true;
    private int delay = 100;

    public TypingLabel() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while (true) {
            if (isTyping) {
                setText(text.substring(0, index++) + "_");
            } else {
                setText(text.substring(0, index));
            }

            if (index > text.length()) {
                isTyping = false;
                index = text.length();
            } else if (index < 0) {
                isTyping = true;
                index = 0;
            }

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isTyping && index == text.length()) {
                try {
                    Thread.sleep(delay * 2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                isTyping = false;
                index = 0; // reset the index to 0
            } else if (!isTyping && index == 0) {
                try {
                    Thread.sleep(delay * 2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                isTyping = true;
            }
        }
    }
}

