package client.graphics.pane;

import client.graphics.ImageFactory;
import client.graphics.widgets.ActionTrigger;
import common.engine.Engine;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

import java.util.LinkedHashMap;

public class ActionPane extends VBox {
    private static LinkedHashMap<Engine.ACTIONS, ImageFactory.SPRITES_ID> IMAGES_FILENAME = new LinkedHashMap<>();
    static{
        IMAGES_FILENAME.put(Engine.ACTIONS.ROCK, ImageFactory.SPRITES_ID.STARTER_ROCK);
        IMAGES_FILENAME.put(Engine.ACTIONS.PAPER, ImageFactory.SPRITES_ID.STARTER_PAPER);
        IMAGES_FILENAME.put(Engine.ACTIONS.SCISSORS, ImageFactory.SPRITES_ID.STARTER_SCISSORS);
    }

    private ActionTrigger[] triggers;

    private ObjectProperty<Engine.ACTIONS> actionPlayed;

    public ActionPane(ObjectProperty<Engine.ACTIONS> actionP){
        this.actionPlayed = actionP;

        buildUI();
        init();
    }

    private void init(){
        triggers = new ActionTrigger[IMAGES_FILENAME.size()];

        int i = 0;
        for(Engine.ACTIONS key : IMAGES_FILENAME.keySet()){
            ActionTrigger trigger = new ActionTrigger(IMAGES_FILENAME.get(key), key, actionPlayed);
            triggers[i] = trigger;
            this.getChildren().addAll(triggers[i]);
            i++;
        }
    }

    private void buildUI(){
        this.setSpacing(20);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(0,20,0,20));
        this.getStyleClass().addAll("action-pane");
    }

    public ActionTrigger getTrigger(int slot){
        return this.triggers[slot];
    }
}
