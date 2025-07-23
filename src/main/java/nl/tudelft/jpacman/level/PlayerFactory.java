package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.sprite.PacManSprites;

public class PlayerFactory {

    private final PacManSprites sprites;

    public PlayerFactory(PacManSprites spriteStore) {
        this.sprites = spriteStore;
    }

    public Player createPacMan() {
        return new Player(getSprites().getPacmanSprites(), getSprites().getPacManDeathAnimation());
    }

    protected PacManSprites getSprites() {
        return sprites;
    }
}
