package client.graphics.pane;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HUDPane extends VBox {
    private TextField status;
    private Label statusLabel;
    private Label score;
    private Label scoreLabel;

    private IntegerProperty scoreProperty = new SimpleIntegerProperty();

    public HUDPane(){
        buildUI();
        addListeners();
    }

    private void buildUI(){
        HBox box1 = new HBox();

        this.status = new TextField();
        status.setDisable(true);

        this.statusLabel = new Label("Status ");

        box1.getChildren().addAll(statusLabel, status);
        box1.setAlignment(Pos.CENTER);

        HBox box2 = new HBox();

        this.score = new Label("0");
        this.scoreLabel = new Label("Score: ");

        box2.getChildren().addAll(scoreLabel, score);
        box2.setAlignment(Pos.CENTER);

        this.getChildren().addAll(box1, box2);
        this.setAlignment(Pos.CENTER);
    }

    private void addListeners(){
        scoreProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                score.setText(t1.toString());
            }
        });
    }

    public IntegerProperty scoreProperty(){
        return this.scoreProperty;
    }

}
