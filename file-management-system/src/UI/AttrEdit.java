package UI;

import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import model.MyFile;
import attr.FileAttr;
import attr.PathAttr;

public class AttrEdit {
    private static Stage rootStage;

    public static void show(MyFile myFile) {
        rootStage = new Stage();
        rootStage.getIcons().add(new Image(PathAttr.TXT));
        VBox newFileNamePane = new VBox();
        newFileNamePane.setPadding(new Insets(5.0D, 20.0D, 75.0D, 75.0D));
        
        Button yesButton = new Button("Finish");
        Button noButton = new Button("Cancel");
        HBox btnBox = new HBox(new Node[]{yesButton, noButton});
        btnBox.setSpacing(25.0D);
        btnBox.setPadding(new Insets(20.0D, 20.0D, 5.0D, 20.0D));
        final ToggleGroup group = new ToggleGroup();

        RadioButton rb1 = new RadioButton("Read Only");
        rb1.setToggleGroup(group);
        rb1.setSelected(true);
        

        RadioButton rb2 = new RadioButton("Read Write");
        rb2.setToggleGroup(group);

        group.selectedToggleProperty().addListener(
                (ObservableValue<? extends Toggle> ov, Toggle old_Toggle,
                 Toggle new_Toggle) -> {
                    if (group.getSelectedToggle() != null) {
                       String icon=group.getSelectedToggle().getUserData().toString();
                    }
                });

        newFileNamePane.getChildren().add(rb1);newFileNamePane.getChildren().add(rb2);
        newFileNamePane.getChildren().addAll(new Node[]{btnBox, });
        yesButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                if (e.getButton() == MouseButton.PRIMARY) {
                    int attr=0;
                    if(rb1.isSelected()){
                        attr=FileAttr.READONLY;
                    }else{
                        attr=FileAttr.READWRITE;
                    }
                    myFile.setReadWrite(attr);
                    rootStage.close();
                }

            }
        });
        noButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent arg0) {
                rootStage.close();
            }
        });
        Scene scene = new Scene(newFileNamePane, 300, 250, Color.WHITE);
        rootStage.setScene(scene);
        rootStage.show();
    }
}