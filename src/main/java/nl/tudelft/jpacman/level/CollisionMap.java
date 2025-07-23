package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;

public interface CollisionMap {
    <C1 extends Unit, C2 extends Unit> void collide(C1 collider, C2 collidee);
}
