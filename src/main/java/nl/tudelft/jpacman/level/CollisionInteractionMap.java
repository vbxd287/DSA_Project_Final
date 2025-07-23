package nl.tudelft.jpacman.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.tudelft.jpacman.board.Unit;

public class CollisionInteractionMap implements CollisionMap {

    private final Map<Class<? extends Unit>, Map<Class<? extends Unit>, CollisionHandler<?, ?>>> handlers;

    public CollisionInteractionMap() {
        this.handlers = new HashMap<>();
    }

    public <C1 extends Unit, C2 extends Unit> void onCollision(
        Class<C1> collider, Class<C2> collidee, CollisionHandler<C1, C2> handler) {
        onCollision(collider, collidee, true, handler);
    }

    public <C1 extends Unit, C2 extends Unit> void onCollision(
        Class<C1> collider, Class<C2> collidee, boolean symetric,
        CollisionHandler<C1, C2> handler) {
        addHandler(collider, collidee, handler);
        if (symetric) {
            addHandler(collidee, collider, new InverseCollisionHandler<>(handler));
        }
    }

    private void addHandler(Class<? extends Unit> collider,
                            Class<? extends Unit> collidee, CollisionHandler<?, ?> handler) {
        if (!handlers.containsKey(collider)) {
            handlers.put(collider, new HashMap<>());
        }
        Map<Class<? extends Unit>, CollisionHandler<?, ?>> map = handlers.get(collider);
        map.put(collidee, handler);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C1 extends Unit, C2 extends Unit> void collide(C1 collider, C2 collidee) {
        Class<? extends Unit> colliderKey = getMostSpecificClass(handlers, collider.getClass());
        if (colliderKey == null) {
            return;
        }

        Map<Class<? extends Unit>, CollisionHandler<?, ?>> map = handlers.get(colliderKey);
        Class<? extends Unit> collideeKey = getMostSpecificClass(map, collidee.getClass());
        if (collideeKey == null) {
            return;
        }

        CollisionHandler<C1, C2> collisionHandler = (CollisionHandler<C1, C2>) map.get(collideeKey);
        if (collisionHandler == null) {
            return;
        }

        collisionHandler.handleCollision(collider, collidee);
    }

    private Class<? extends Unit> getMostSpecificClass(
        Map<Class<? extends Unit>, ?> map, Class<? extends Unit> key) {
        List<Class<? extends Unit>> collideeInheritance = getInheritance(key);
        for (Class<? extends Unit> pointer : collideeInheritance) {
            if (map.containsKey(pointer)) {
                return pointer;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private List<Class<? extends Unit>> getInheritance(Class<? extends Unit> clazz) {
        List<Class<? extends Unit>> found = new ArrayList<>();
        found.add(clazz);

        int index = 0;
        while (found.size() > index) {
            Class<?> current = found.get(index);
            Class<?> superClass = current.getSuperclass();
            if (superClass != null && Unit.class.isAssignableFrom(superClass)) {
                found.add((Class<? extends Unit>) superClass);
            }
            for (Class<?> classInterface : current.getInterfaces()) {
                if (Unit.class.isAssignableFrom(classInterface)) {
                    found.add((Class<? extends Unit>) classInterface);
                }
            }
            index++;
        }

        return found;
    }

    public interface CollisionHandler<C1 extends Unit, C2 extends Unit> {
        void handleCollision(C1 collider, C2 collidee);
    }

    private static class InverseCollisionHandler<C1 extends Unit, C2 extends Unit>
        implements CollisionHandler<C1, C2> {

        private final CollisionHandler<C2, C1> handler;

        InverseCollisionHandler(CollisionHandler<C2, C1> handler) {
            this.handler = handler;
        }

        @Override
        public void handleCollision(C1 collider, C2 collidee) {
            handler.handleCollision(collidee, collider);
        }
    }
}
