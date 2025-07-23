package nl.tudelft.jpacman.sprite;

import java.awt.Graphics;

public class EmptySprite implements Sprite {

    @Override
    public void draw(Graphics graphics, int x, int y, int width, int height) {
    }

    @Override
    public Sprite split(int x, int y, int width, int height) {
        return new EmptySprite();
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }
}
