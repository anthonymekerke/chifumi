package client;

import client.graphics.CSSFactory;
import client.graphics.pane.ActionPane;
import client.graphics.pane.DisplayPane;
import client.graphics.pane.HUDPane;
import common.engine.Engine;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.rmi.RemoteException;

public class ShifumiClientApplication extends Application {

    private BorderPane root;
    private Scene scene;

    private ActionPane actionPane;
    private DisplayPane displayPane;
    private HUDPane hudPane;

    private ObjectProperty<Engine.ACTIONS> playerAction = new SimpleObjectProperty<>(Engine.ACTIONS.DEFAULT);
    private ObjectProperty<Engine.ACTIONS> opponentAction = new SimpleObjectProperty<>(Engine.ACTIONS.DEFAULT);
    private IntegerProperty score = new SimpleIntegerProperty(0);

    private RMIClientImpl client;
    private String username;

    @Override
    public void start(Stage stage){
        stage.setTitle("Shifumi");

        root = new BorderPane();
        scene = new Scene(root,800, 460);

        stage.setScene(scene);
        stage.setResizable(false);

        buildUI();

        username = "client1";
        client = new RMIClientImpl(this, username);
        try {
            stage.setTitle(client.getUserName());
        }catch(RemoteException e){
            e.printStackTrace();
        }

        addListeners();
        //disableRoot(client.canPlayProperty().get());

        stage.show();

        stage.setOnCloseRequest(windowEvent -> System.exit(0));
    }

    private void buildUI(){
        scene.getStylesheets().addAll(
                CSSFactory.getStyleSheet("Shifumi.css")
        );

        displayPane = new DisplayPane();
        displayPane.playerActionProperty().bind(this.playerAction);
        displayPane.opponentActionProperty().bind(this.opponentAction);
        root.setCenter(displayPane);

        actionPane = new ActionPane(this.playerAction);
        root.setLeft(actionPane);

        hudPane = new HUDPane();
        hudPane.scoreProperty().bind(score);
        root.setTop(hudPane);
    }

    private void addListeners(){
        playerAction.addListener(new ChangeListener<Engine.ACTIONS>() {
            @Override
            public void changed(ObservableValue<? extends Engine.ACTIONS> observableValue, Engine.ACTIONS actions, Engine.ACTIONS t1) {
                if(t1 != Engine.ACTIONS.DEFAULT){
                    client.submitActionToServer(t1);
                }
            }
        });

        client.canPlayProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                disableRoot(t1);
            }
        });

        client.toEvaluateProperty().addListener(new ChangeListener<Engine.ACTIONS>() {
            @Override
            public void changed(ObservableValue<? extends Engine.ACTIONS> observableValue, Engine.ACTIONS actions, Engine.ACTIONS t1) {
                if(t1 != Engine.ACTIONS.DEFAULT) {
                    opponentAction.setValue(t1);
                    Engine.ACTIONS winningAction = Engine.computeWinner(playerAction.getValue(), opponentAction.getValue());
                    displayPane.animateWinnerDisplay(winningAction, event -> {
                        updateScore(winningAction);
                        reset();
                    });
                }
            }
        });
    }

    private void disableRoot(boolean canPlay){
        if(canPlay){ root.setDisable(false);}
        else {root.setDisable(true);}
    }

    private void updateScore(Engine.ACTIONS winningAction){
        if(playerAction.get() == opponentAction.get()){ return;}
        else if(playerAction.get() == winningAction){
            score.setValue(score.getValue() + 1);
        }
    }

    private void reset(){
        playerAction.setValue(Engine.ACTIONS.DEFAULT);
        opponentAction.setValue(Engine.ACTIONS.DEFAULT);
        client.resetActionOnServer();
    }

    public static void main(String[] args){
        launch(args);
    }
}
