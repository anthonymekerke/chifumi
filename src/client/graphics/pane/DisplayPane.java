package client.graphics.pane;

import client.graphics.ImageFactory;
import common.engine.Engine;
import javafx.animation.ScaleTransition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class DisplayPane extends HBox {
    private ImageView player;
    private ImageView opponent;

    private ObjectProperty<Engine.ACTIONS> playerAction = new SimpleObjectProperty<>(Engine.ACTIONS.DEFAULT);
    private ObjectProperty<Engine.ACTIONS> opponentAction = new SimpleObjectProperty<>(Engine.ACTIONS.DEFAULT);

    private static final Duration ANIMATION_DURATION = Duration.millis(2000);
    private static final double ZOOM_SCALE = 1.2;

    public DisplayPane(){
        this.player = new ImageView();
        this.opponent = new ImageView();

        buildUI();
        addListeners();
    }

    private void buildUI(){
        Image img = ImageFactory.getSprites(ImageFactory.SPRITES_ID.DEFAULT)[0];

        //BorderPane displayer1 = new BorderPane();
        player.setImage(img);
        player.setFitWidth(200);
        player.setFitHeight(200);
        //player.getStyleClass().addAll("image-view");
        //displayer1.setCenter(player);
        //displayer1.getStyleClass().addAll("image-view");

        //BorderPane displayer2 = new BorderPane();
        opponent.setImage(img);
        opponent.setFitWidth(200);
        opponent.setFitHeight(200);
        //opponent.getStyleClass().addAll("image-view");
        //displayer2.setCenter(opponent);
        //displayer2.getStyleClass().addAll("image-view");


        this.setSpacing(50);
        this.setAlignment(Pos.CENTER);
        //this.setHgap(50);

        //this.getChildren().addAll(displayer1, displayer2);
        this.getChildren().addAll(player, opponent);
    }

    private void addListeners(){
        this.playerAction.addListener(new ChangeListener<Engine.ACTIONS>() {
            @Override
            public void changed(ObservableValue<? extends Engine.ACTIONS> observableValue, Engine.ACTIONS actions, Engine.ACTIONS t1) {
                if(t1 == Engine.ACTIONS.ROCK) {
                    Image[] img = ImageFactory.getSprites(ImageFactory.SPRITES_ID.STARTER_ROCK);
                    player.setImage(img[0]);
                }
                if(t1 == Engine.ACTIONS.PAPER) {
                    Image[] img = ImageFactory.getSprites(ImageFactory.SPRITES_ID.STARTER_PAPER);
                    player.setImage(img[0]);
                }
                if(t1 == Engine.ACTIONS.SCISSORS) {
                    Image[] img = ImageFactory.getSprites(ImageFactory.SPRITES_ID.STARTER_SCISSORS);
                    player.setImage(img[0]);
                }
                if(t1 == Engine.ACTIONS.DEFAULT) {
                    Image[] img = ImageFactory.getSprites(ImageFactory.SPRITES_ID.DEFAULT);
                    player.setImage(img[0]);
                }
            }
        });

        this.opponentAction.addListener(new ChangeListener<Engine.ACTIONS>() {
            @Override
            public void changed(ObservableValue<? extends Engine.ACTIONS> observableValue, Engine.ACTIONS actions, Engine.ACTIONS t1) {
                if(t1 == Engine.ACTIONS.ROCK) {
                    Image[] img = ImageFactory.getSprites(ImageFactory.SPRITES_ID.STARTER_ROCK);
                    opponent.setImage(img[0]);
                }
                if(t1 == Engine.ACTIONS.PAPER) {
                    Image[] img = ImageFactory.getSprites(ImageFactory.SPRITES_ID.STARTER_PAPER);
                    opponent.setImage(img[0]);
                }
                if(t1 == Engine.ACTIONS.SCISSORS) {
                    Image[] img = ImageFactory.getSprites(ImageFactory.SPRITES_ID.STARTER_SCISSORS);
                    opponent.setImage(img[0]);
                }
                if(t1 == Engine.ACTIONS.DEFAULT) {
                    Image[] img = ImageFactory.getSprites(ImageFactory.SPRITES_ID.DEFAULT);
                    opponent.setImage(img[0]);
                }
            }
        });
    }

    public void animateWinnerDisplay(Engine.ACTIONS action, EventHandler<ActionEvent> finishedHandler){
        if(playerAction.get() == action){
            animate(player, finishedHandler);
        }
        if(opponentAction.get() == action){
            animate(opponent, finishedHandler);
        }
    }

    private void animate(ImageView view, EventHandler<ActionEvent> finishedHandler) {
        ScaleTransition st = new ScaleTransition(ANIMATION_DURATION);
        st.setToX(ZOOM_SCALE);
        st.setToY(ZOOM_SCALE);
        st.setCycleCount(2);
        st.setNode(view);
        st.setAutoReverse(true);
        st.setOnFinished(finishedHandler);

        st.play();
    }

    public ObjectProperty<Engine.ACTIONS> playerActionProperty() {
        return playerAction;
    }

    public ObjectProperty<Engine.ACTIONS> opponentActionProperty() {
        return opponentAction;
    }
}
