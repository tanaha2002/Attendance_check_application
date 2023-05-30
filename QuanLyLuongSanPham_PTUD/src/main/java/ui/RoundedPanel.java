/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

/**
 *
 * @author ADMIN
 */
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.*;

public class RoundedPanel extends JPanel {

    private int cornerRadius = 20;
    private Color borderColor = Color.BLACK;
    private int borderWidth = 1;

    public RoundedPanel() {
        super();
        setOpaque(false);
        setBorder(new EmptyBorder(50, 50, 50, 50));
    }

    public RoundedPanel(int cornerRadius, Color backgroundColor, Color borderColor, int borderWidth) {
        super();
        this.cornerRadius = cornerRadius;
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
        setOpaque(false);
        setBorder(new EmptyBorder(50, 50, 50, 50));
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        repaint();
    }



    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        repaint();
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        Graphics2D g2d = (Graphics2D) g.create();

        // Set anti-aliasing for smoother edges
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw background
        g2d.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);

        // Draw border
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(borderWidth));
        g2d.drawRoundRect(0, 0, width - borderWidth, height - borderWidth, cornerRadius, cornerRadius);

        g2d.dispose();
    }
}

