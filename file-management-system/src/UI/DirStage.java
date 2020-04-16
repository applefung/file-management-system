package UI;



import application.Main;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.List;

import attr.FileAttr;
import attr.PathAttr;
import attr.SystemData;
import model.MyFile;
import service.FileService;
import service.UIService;




public class DirStage {
    private  Stage rootStage;
    private static BorderPane rootBorderPane;
    private static TilePane windowsPane;


    
    public  Stage getRootStage() {
        return rootStage;
    }


    public  void show(MyFile myFile) {
        rootStage = new Stage();
        rootStage.getIcons().add(new Image(PathAttr.TXT));
        rootBorderPane=new BorderPane();
        windowsPane=new TilePane();
        Scene scene = new Scene(rootBorderPane, 790, 550, Color.WHITE);
        
        windowsPane = new TilePane();
        windowsPane.getChildren().add(UIService.getItem(new MyFile("Disk"), PathAttr.DISK, 3, rootStage, windowsPane, rootBorderPane));
        rootBorderPane.setCenter(windowsPane);
        rootStage.setScene(scene);
        initWindow(windowsPane,myFile);
        setRightMenu(myFile);
        rootStage.show();
        SystemData.setActiveDirUi(this);
    }

    private  void setRightMenu(MyFile myFile) 
    {
        ContextMenu rootRightMenu = new ContextMenu();
        
        MenuItem newFileItem = new MenuItem("New File");
        rootRightMenu.getItems().add(newFileItem);
        newFileItem.setOnAction((a) -> {
            FileStage.show(FileAttr.FILE, myFile, windowsPane, rootStage);
        });
        
        MenuItem newFolderItem = new MenuItem("New Folder");
        rootRightMenu.getItems().add(newFolderItem);
        newFolderItem.setOnAction((e) -> {
            FileStage.show(FileAttr.DIRECTORY, myFile, windowsPane, rootStage);
        });
        
        windowsPane.addEventHandler(MouseEvent.MOUSE_CLICKED,  (MouseEvent  me) ->  {
            if(Main.getIfOpen())
            {
                if (me.getButton() == MouseButton.SECONDARY  || me.isControlDown())  
                {
                    rootRightMenu.show(rootBorderPane, me.getScreenX(), me.getScreenY());
                }  
                else  
                {
                    rootRightMenu.hide();
                }
            }
        });
    }


    private  void initWindow(TilePane windowsPane,MyFile myFile){
        List<Object> subs= FileService.getSub(myFile);
        UIService.initPane(subs, windowsPane, rootStage, rootBorderPane);
    }
}