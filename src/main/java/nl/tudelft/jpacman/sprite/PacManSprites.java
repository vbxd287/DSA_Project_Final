package nl.tudelft.jpacman.sprite;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.npc.ghost.GhostColor;

public class PacManSprites extends SpriteStore {

    private static final Direction[] DIRECTIONS = {
        Direction.NORTH,
        Direction.EAST,
        Direction.SOUTH,
        Direction.WEST
    };

    private static final int SPRITE_SIZE = 16;
    private static final int PACMAN_ANIMATION_FRAMES = 4;
    private static final int PACMAN_DEATH_FRAMES = 11;
    private static final int GHOST_ANIMATION_FRAMES = 2;
    private static final int ANIMATION_DELAY = 200;

    public Map<Direction, Sprite> getPacmanSprites() {
        return directionSprite("/sprite/pacman.png", PACMAN_ANIMATION_FRAMES);
    }

    public AnimatedSprite getPacManDeathAnimation() {
        String resource = "/sprite/dead.png";
        Sprite baseImage = loadSprite(resource);
        AnimatedSprite animation = createAnimatedSprite(baseImage, PACMAN_DEATH_FRAMES,
            ANIMATION_DELAY, false);
        animation.setAnimating(false);
        return animation;
    }

    private Map<Direction, Sprite> directionSprite(String resource, int frames) {
        Map<Direction, Sprite> sprite = new HashMap<>();
        Sprite baseImage = loadSprite(resource);
        for (int i = 0; i < DIRECTIONS.length; i++) {
            Sprite directionSprite = baseImage.split(0, i * SPRITE_SIZE, frames * SPRITE_SIZE, SPRITE_SIZE);
            AnimatedSprite animation = createAnimatedSprite(directionSprite, frames, ANIMATION_DELAY, true);
            animation.setAnimating(true);
            sprite.put(DIRECTIONS[i], animation);
        }
        return sprite;
    }

    public Map<Direction, Sprite> getGhostSprite(GhostColor color) {
        assert color != null;
        String resource = "/sprite/ghost_" + color.name().toLowerCase() + ".png";
        return directionSprite(resource, GHOST_ANIMATION_FRAMES);
    }

    public Sprite getWallSprite() {
        return loadSprite("/sprite/wall.png");
    }

    public Sprite getGroundSprite() {
        return loadSprite("/sprite/floor.png");
    }

    public Sprite getPelletSprite() {
        return loadSprite("/sprite/pellet.png");
    }

    @Override
    public Sprite loadSprite(String resource) {
        try {
            return super.loadSprite(resource);
        } catch (IOException e) {
            throw new PacmanConfigurationException("Unable to load sprite: " + resource, e);
        }
    }
}
