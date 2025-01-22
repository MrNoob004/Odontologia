package org.ucuenca.Vista;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImagePanel extends JPanel {
    private BufferedImage image;
    private static final int WIDTH = 110; // Ancho fijo
    private static final int HEIGHT = 140; // Alto fijo

    public ImagePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public void cargarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                BufferedImage originalImage = ImageIO.read(file);

                // Redimensionar la imagen al tamaño fijo
                image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = image.createGraphics();
                g2d.drawImage(originalImage.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH), 0, 0, null);
                g2d.dispose();

                repaint(); // Actualizar el panel para mostrar la nueva imagen
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al cargar la imagen: " + e.getMessage());
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }
    }
}
