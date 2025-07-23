package nl.tudelft.jpacman.sprite;

import java.awt.Graphics;

public interface Sprite {
    void draw(Graphics graphics, int x, int y, int width, int height);
    Sprite split(int x, int y, int width, int height);
    int getWidth();
    int getHeight();
}
