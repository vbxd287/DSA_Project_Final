package nl.tudelft.jpacman.sprite;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

public class ImageSprite implements Sprite {

    private final Image image;

    public ImageSprite(Image img) {
        this.image = img;
    }

    @Override
    public void draw(Graphics graphics, int x, int y, int width, int height) {
        graphics.drawImage(image, x, y, x + width, y + height, 0, 0,
            image.getWidth(null), image.getHeight(null), null);
    }

    @Override
    public Sprite split(int x, int y, int width, int height) {
        if (withinImage(x, y) && withinImage(x + width - 1, y + height - 1)) {
            BufferedImage newImage = newImage(width, height);
            newImage.createGraphics().drawImage(image, 0, 0, width, height, x,
                y, x + width, y + height, null);
            return new ImageSprite(newImage);
        }
        return new EmptySprite();
    }

    private boolean withinImage(int x, int y) {
        return x < image.getWidth(null) && x >= 0 && y < image.getHeight(null)
            && y >= 0;
    }

    private BufferedImage newImage(int width, int height) {
        GraphicsConfiguration gc = GraphicsEnvironment
            .getLocalGraphicsEnvironment().getDefaultScreenDevice()
            .getDefaultConfiguration();
        return gc.createCompatibleImage(width, height, Transparency.BITMASK);
    }

    @Override
    public int getWidth() {
        return image.getWidth(null);
    }

    @Override
    public int getHeight() {
        return image.getHeight(null);
    }
}
