package nl.tudelft.jpacman.sprite;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class SpriteStore {

    private final Map<String, Sprite> spriteMap;

    public SpriteStore() {
        spriteMap = new HashMap<>();
    }

    public Sprite loadSprite(String resource) throws IOException {
        Sprite result = spriteMap.get(resource);
        if (result == null) {
            result = loadSpriteFromResource(resource);
            spriteMap.put(resource, result);
        }
        return result;
    }

    private Sprite loadSpriteFromResource(String resource) throws IOException {
        try (InputStream input = SpriteStore.class.getResourceAsStream(resource)) {
            if (input == null) {
                throw new IOException("Unable to load " + resource + ", resource does not exist.");
            }
            BufferedImage image = ImageIO.read(input);
            return new ImageSprite(image);
        }
    }

    public AnimatedSprite createAnimatedSprite(Sprite baseImage, int frames,
                                               int delay, boolean loop) {
        assert baseImage != null;
        assert frames > 0;

        int frameWidth = baseImage.getWidth() / frames;

        Sprite[] animation = new Sprite[frames];
        for (int i = 0; i < frames; i++) {
            animation[i] = baseImage.split(i * frameWidth, 0, frameWidth,
                baseImage.getHeight());
        }

        return new AnimatedSprite(animation, delay, loop);
    }
}
