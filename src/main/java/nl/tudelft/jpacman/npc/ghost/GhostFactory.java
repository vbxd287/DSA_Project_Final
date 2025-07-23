package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.sprite.PacManSprites;

public class GhostFactory {

    private final PacManSprites sprites;

    public GhostFactory(PacManSprites spriteStore) {
        this.sprites = spriteStore;
    }

    public Ghost createBlinky() {
        return new Blinky(sprites.getGhostSprite(GhostColor.RED));
    }

    public Ghost createPinky() {
        return new Pinky(sprites.getGhostSprite(GhostColor.PINK));
    }

    public Ghost createInky() {
        return new Inky(sprites.getGhostSprite(GhostColor.CYAN));
    }

    public Ghost createClyde() {
        return new Clyde(sprites.getGhostSprite(GhostColor.ORANGE));
    }
}
