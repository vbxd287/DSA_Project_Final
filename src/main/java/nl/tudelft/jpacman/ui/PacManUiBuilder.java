package nl.tudelft.jpacman.ui;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.ui.ScorePanel.ScoreFormatter;


public class PacManUiBuilder {


    private static final String STOP_CAPTION = "Stop";

  
    private static final String START_CAPTION = "Start";

    private final Map<String, Action> buttons;

   
    private final Map<Integer, Action> keyMappings;

  
    private boolean defaultButtons;

    private ScoreFormatter scoreFormatter = null;

  
    public PacManUiBuilder() {
        this.defaultButtons = false;
        this.buttons = new LinkedHashMap<>();
        this.keyMappings = new HashMap<>();
    }

  
    public PacManUI build(final Game game) {
        assert game != null;

        if (defaultButtons) {
            addStartButton(game);
            addStopButton(game);
        }
        return new PacManUI(game, buttons, keyMappings, scoreFormatter);
    }

 
    private void addStopButton(final Game game) {
        assert game != null;

        buttons.put(STOP_CAPTION, game::stop);
    }

  
    private void addStartButton(final Game game) {
        assert game != null;

        buttons.put(START_CAPTION, game::start);
    }

   
    public PacManUiBuilder addKey(Integer keyCode, Action action) {
        assert keyCode != null;
        assert action != null;

        keyMappings.put(keyCode, action);
        return this;
    }

   
    public PacManUiBuilder addButton(String caption, Action action) {
        assert caption != null;
        assert !caption.isEmpty();
        assert action != null;

        buttons.put(caption, action);
        return this;
    }


    public PacManUiBuilder withDefaultButtons() {
        defaultButtons = true;
        buttons.put(START_CAPTION, null);
        buttons.put(STOP_CAPTION, null);
        return this;
    }

  
    public PacManUiBuilder withScoreFormatter(ScoreFormatter scoreFormatter) {
        this.scoreFormatter = scoreFormatter;
        return this;
    }
}
