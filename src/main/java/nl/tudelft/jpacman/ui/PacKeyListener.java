package nl.tudelft.jpacman.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;


class PacKeyListener implements KeyListener {

 
    private final Map<Integer, Action> mappings;

  
    PacKeyListener(Map<Integer, Action> keyMappings) {
        assert keyMappings != null;
        this.mappings = keyMappings;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        assert event != null;
        Action action = mappings.get(event.getKeyCode());
        if (action != null) {
            action.doAction();
        }
    }

    @Override
    public void keyTyped(KeyEvent event) {
      
    }

    @Override
    public void keyReleased(KeyEvent event) {
       
    }
}
