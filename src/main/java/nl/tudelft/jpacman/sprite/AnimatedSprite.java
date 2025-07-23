package nl.tudelft.jpacman.sprite;

import java.awt.Graphics;

public class AnimatedSprite implements Sprite {

    private static final Sprite END_OF_LOOP = new EmptySprite();
    private final Sprite[] animationFrames;
    private final int animationDelay;
    private final boolean looping;
    private int current;
    private boolean animating;
    private long lastUpdate;

    public AnimatedSprite(Sprite[] frames, int delay, boolean loop) {
        this(frames, delay, loop, false);
    }

    public AnimatedSprite(Sprite[] frames, int delay, boolean loop, boolean isAnimating) {
        assert frames.length > 0;
        this.animationFrames = frames.clone();
        this.animationDelay = delay;
        this.looping = loop;
        this.animating = isAnimating;
        this.current = 0;
        this.lastUpdate = System.currentTimeMillis();
    }

    private Sprite currentSprite() {
        Sprite result = END_OF_LOOP;
        if (current < animationFrames.length) {
            result = animationFrames[current];
        }
        assert result != null;
        return result;
    }

    public void setAnimating(boolean isAnimating) {
        this.animating = isAnimating;
    }

    public void restart() {
        this.current = 0;
        this.lastUpdate = System.currentTimeMillis();
        setAnimating(true);
    }

    @Override
    public void draw(Graphics graphics, int x, int y, int width, int height) {
        update();
        currentSprite().draw(graphics, x, y, width, height);
    }

    @Override
    public Sprite split(int x, int y, int width, int height) {
        update();
        return currentSprite().split(x, y, width, height);
    }

    private void update() {
        long now = System.currentTimeMillis();
        if (animating) {
            while (lastUpdate < now) {
                lastUpdate += animationDelay;
                current++;
                if (looping) {
                    current %= animationFrames.length;
                } else if (current == animationFrames.length) {
                    animating = false;
                }
            }
        } else {
            lastUpdate = now;
        }
    }

    @Override
    public int getWidth() {
        assert currentSprite() != null;
        return currentSprite().getWidth();
    }

    @Override
    public int getHeight() {
        assert currentSprite() != null;
        return currentSprite().getHeight();
    }
}
