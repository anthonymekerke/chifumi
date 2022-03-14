package client.graphics.widgets;

import client.graphics.ImageFactory;
import common.engine.Engine;
import javafx.animation.FadeTransition;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ActionTrigger extends AnchorPane {
    private ImageView view;
    private Label label;
    private Engine.ACTIONS action;

    private ColorAdjust effect;
    private Rectangle blackveil;

    private ObjectProperty<Engine.ACTIONS> actionPlayed;

    public ActionTrigger(ImageFactory.SPRITES_ID filename, Engine.ACTIONS action, ObjectProperty<Engine.ACTIONS>actionP){
        this.view = new ImageView();
        this.label = new Label(action.name);
        this.action = action;

        this.actionPlayed = actionP;

        Image[] images = ImageFactory.getSprites(filename);
        this.view.setImage(images[0]);

        this.effect = new ColorAdjust();
        this.effect.setSaturation(-1.0);
        this.effect.setBrightness(0.6);

        this.blackveil = new Rectangle(100, 100);

        buildUI();
        addListeners();
    }

    private void buildUI(){
        this.getStyleClass().addAll("action-trigger");

        this.label.setAlignment(Pos.BASELINE_CENTER);
        this.label.setTextFill(Color.WHITE);
        this.label.setFont(Font.font(null, FontWeight.BOLD, 15));

        this.view.setFitHeight(100);
        this.view.setFitWidth(100);

        this.blackveil.setOpacity(0.0);
        this.blackveil.setFill(Color.BLACK);

        this.getChildren().addAll(view, blackveil);

        AnchorPane.setLeftAnchor(view, 0.0);
        AnchorPane.setBottomAnchor(view, 0.0);
        AnchorPane.setRightAnchor(view, 0.0);
        AnchorPane.setTopAnchor(view, 0.0);

        AnchorPane.setBottomAnchor(label, 0.0);
        AnchorPane.setTopAnchor(label, 0.0);
        AnchorPane.setLeftAnchor(label, 0.0);
        AnchorPane.setRightAnchor(label, 0.0);

        AnchorPane.setBottomAnchor(blackveil, 0.0);
        AnchorPane.setTopAnchor(blackveil, 0.0);
        AnchorPane.setRightAnchor(blackveil, 0.0);
        AnchorPane.setLeftAnchor(blackveil, 0.0);
    }

    private void addListeners(){
        this.setOnMouseClicked(event -> trigger());
        this.setOnMouseEntered(event -> fadeToBlack());
        this.setOnMouseExited(event -> fadeToClear());
    }

    public void trigger(){
        this.actionPlayed.setValue(this.action);
    }

    private void fadeToBlack(){
        FadeTransition ft = new FadeTransition();
        ft.setToValue(0.8);
        ft.setNode(blackveil);

        ft.play();

        this.getChildren().add(label);
    }

    private void fadeToClear(){
        FadeTransition ft = new FadeTransition();
        ft.setToValue(0.0);
        ft.setNode(blackveil);

        ft.play();

        this.getChildren().remove(label);
    }
}
