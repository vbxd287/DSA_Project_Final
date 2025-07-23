package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.Ghost;

public class DefaultPlayerInteractionMap implements CollisionMap {

    private final CollisionMap collisions = defaultCollisions();

    @Override
    public void collide(Unit mover, Unit movedInto) {
        collisions.collide(mover, movedInto);
    }

    private static CollisionInteractionMap defaultCollisions() {
        CollisionInteractionMap collisionMap = new CollisionInteractionMap();

        collisionMap.onCollision(Player.class, Ghost.class,
            (player, ghost) -> player.setAlive(false));

        collisionMap.onCollision(Player.class, Pellet.class,
            (player, pellet) -> {
                pellet.leaveSquare();
                player.addPoints(pellet.getValue());
            });
        return collisionMap;
    }
}
