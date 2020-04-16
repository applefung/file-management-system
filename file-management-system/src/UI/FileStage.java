package UI;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.List;

import UI.ErrorUI;
import attr.FileAttr;
import attr.PathAttr;
import attr.SystemData;
import model.MyFile;
import model.Result;
import service.FileService;
import service.UIService;



public class FileStage {
    private static Stage rootStage;

    public static void show(int fileType, MyFile myFile, TilePane windowsPane, Stage preStage) {
        try{
            BorderPane borderPane=new BorderPane();
            List<Object> sub=FileService.getSub(myFile);
            //maximum is 8
            if(sub.size()> FileAttr.SUB_DIR_LIMIT-1){
                throw new Exception("exception case");
            }
            
            rootStage = new Stage();
            rootStage.getIcons().add(new Image(PathAttr.TXT));
            VBox newFileNamePane = new VBox();
            
            Button yesButton = new Button("Finish");
            Button noButton = new Button("Cancel");
            HBox btnBox = new HBox(new Node[]{yesButton, noButton});
            btnBox.setSpacing(25.0D);
            btnBox.setPadding(new Insets(20.0D, 20.0D, 5.0D, 20.0D));

            Label title=new Label("   Name:   ");
            TextField field = new TextField();
            HBox name=new HBox(new Node[] {title,field});

            newFileNamePane.getChildren().addAll(new Node[]{name, btnBox, });
            
            yesButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent e) {
                	//left click
                    if (e.getButton() == MouseButton.PRIMARY) {
                        MyFile newFile=new MyFile();
                        newFile.setAttr(fileType);
                        if(field.getText()==null||field.getText().equals(""))
                        {
                            ErrorUI.alertDialog("File name cannot be empty"," ",rootStage);
                        }
                        else if(field.getText().toCharArray().length>3){
                            ErrorUI.alertDialog("Lengh of the file name is too long", "Please enter a file name which the length is smaller than 3", rootStage);
                        }
                        else if(field.getText().contains("$")||field.getText().contains("/")||field.getText().contains(".")){
                            ErrorUI.alertDialog("Invalid character", "The file name contains $ or / or. ", rootStage);
                        }
                        else{
                            newFile.setFilename(field.getText());
                            newFile.setFile(myFile);

                            int num=0;
                            String iconpath;
                            if(fileType== FileAttr.DIRECTORY)
                            {
                                iconpath= PathAttr.FOLDER;
                                num=1;
                            }
                            else{
                                iconpath=PathAttr.TXT;
                                num=2;
                            }
                            newFile.setReadWrite(FileAttr.READWRITE);
                            
                            Result result;
                            result=FileService.addFile(newFile, SystemData.getDisk(), SystemData.getFat());

                            if(result.getStatus()==200){
                                windowsPane.getChildren().add(UIService.getItem(newFile, iconpath, num, rootStage, windowsPane, borderPane));
                                rootStage.close();
                            }
                            else {
                                ErrorUI.alertDialog("Wrong", result.getMessage(),rootStage);
                            }

                        }
                    }

                }
            });
            
            noButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent arg0) {
                    rootStage.close();
                }
            });
            
            //name and textfield
            borderPane.setCenter(name);
            borderPane.setBottom(btnBox);
            Scene scene = new Scene(borderPane, 300, 100, Color.WHITE);
            rootStage.setScene(scene);
            rootStage.show();
        }
        catch (Exception e){
                ErrorUI.alertDialog("ERROR", e.getMessage(),preStage);
        }

//
    }

}