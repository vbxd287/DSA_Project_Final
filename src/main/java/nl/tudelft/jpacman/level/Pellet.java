package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.sprite.Sprite;

public class Pellet extends Unit {

    private final Sprite image;
    private final int value;

    public Pellet(int points, Sprite sprite) {
        this.image = sprite;
        this.value = points;
    }

    public int getValue() {
        return value;
    }

    @Override
    public Sprite getSprite() {
        return image;
    }
}
