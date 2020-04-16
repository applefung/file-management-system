package UI;

import attr.PathAttr;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import model.MyFile;



public class FileAttr {
    private static Stage rootStage;
    private static BorderPane borderPane;
    private static Label fileName;
    
    public static void show(MyFile myFile){
        rootStage=new Stage();
        rootStage.getIcons().add(new Image(PathAttr.TXT));
        borderPane=new BorderPane();
        Scene scene = new Scene(borderPane);
        rootStage.setScene(scene);
        
        fileName=new Label(myFile.getFilename());
        Label nameTitle=new Label("Name: ");
        HBox name=new HBox(new Node[]{nameTitle,fileName});

        Label sizeTitle=new Label("Size: ");
        Label fileSize=new Label(myFile.getLength()+" bytes");
        HBox size=new HBox(new Node[]{sizeTitle,fileSize});


        Label attr=new Label("Read/Write: ");
        Label attrName=new Label(myFile.getReadWrite()==0?"Read Only":"Read Write");
        HBox attrBox=new HBox(new Node[]{attr,attrName});
        VBox vBox=new VBox(new Node[]{name,size,attrBox});
        vBox.setPadding(new Insets(20D, 20D, 20D, 20D));
        borderPane.setCenter(vBox);
        rootStage.setResizable(false);
        rootStage.setMinWidth(200);
        rootStage.setMinHeight(300);

        rootStage.show();


    }
}