package service;

import application.Main;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

import UI.*;
import attr.FileAttr;
import model.MyFile;


public class UIService {
    public  static VBox getItem(MyFile myFile, String itemIconPath, int number, Stage rootStage, TilePane windowsPane,BorderPane rootBorderPane) {
    	String itemName=myFile.getFilename();
        Label theItem = new Label(itemName, new ImageView(new Image(itemIconPath, 80, 80, false, false)));
        theItem.setContentDisplay(ContentDisplay.TOP);
        //double click
        theItem.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            	if(number==1)//Folder
            	{
                    new DirStage().show(myFile);
            	}
            	else if(number==2)//File
            	{
                    if(myFile.getReadWrite()== FileAttr.READWRITE){
                        EditFile.show(myFile);
                    }
                    else{
                        EditFile.showContent(myFile);
                        ErrorUI.alertDialog("Read Only","You cannot edit the file", EditFile.getStage());
                    }
            	}
            	else if(number==3) //Disk usage
            	{
            		DiskUsage.show();
            	}
            	else
            	{
                    rootStage.close();
                    System.exit(0);
            	}
            }

        });

        VBox theItemPane = new VBox(new Node[]{theItem});
        //Four functions: Edit, Delete, Accessibility, Attributes
        theItemPane.addEventHandler(MouseEvent.MOUSE_CLICKED,  (MouseEvent  me) ->  {
            ContextMenu rootRightMenu = new ContextMenu();
            if(myFile.getAttr()==FileAttr.FILE)
            {
                MenuItem openItem = new MenuItem("Edit");
                rootRightMenu.getItems().add(openItem);
                openItem.setOnAction((a) -> {
                    if(myFile.getReadWrite()==FileAttr.READWRITE)
                    {
                        EditFile.show(myFile);
                    }
                    else
                    {
                        EditFile.showContent(myFile);
                        ErrorUI.alertDialog("Cannot Edit","You are not allowed to edit", EditFile.getStage());

                    }
                });
                
                
                MenuItem deleteItem = new MenuItem("Delete");
                rootRightMenu.getItems().add(deleteItem);
                deleteItem.setOnAction((e) -> {
                    FileService.delFile(myFile);
                    windowsPane.getChildren().remove(theItemPane);
                    Main.setIfOpen(true);
                });
                
                
                MenuItem updateItem=new MenuItem("Access");
                rootRightMenu.getItems().add(updateItem);
                updateItem.setOnAction(e->{
                    AttrEdit.show(myFile);
                    Main.setIfOpen(true);
                });
                
                
                MenuItem fileAttr=new MenuItem("Attributes");
                rootRightMenu.getItems().add(fileAttr);
                fileAttr.setOnAction(e->{
                    UI.FileAttr.show(myFile);
                    Main.setIfOpen(true);
                });

            }
            else if(myFile.getAttr()==FileAttr.DIRECTORY)
            {
                MenuItem openItem = new MenuItem("Open");
                rootRightMenu.getItems().add(openItem);
                openItem.setOnAction((a) -> {
                    new DirStage().show(myFile);
                });
                
                
                MenuItem deleteItem = new MenuItem("Delete");
                rootRightMenu.getItems().add(deleteItem);
                deleteItem.setOnAction((e) -> {
                    DirService.delDirContent(myFile);
                    windowsPane.getChildren().remove(theItemPane);
                    Main.setIfOpen(true);
                });

            }
            
           
            //right click to the menu: open, delete
            if (me.getButton() == MouseButton.SECONDARY)  {
                Main.setIfOpen(false);
                rootRightMenu.show(windowsPane, me.getScreenX(), me.getScreenY());
            }  
            else  {
              Main.setIfOpen(true);
              rootRightMenu.hide();
            }
        });
        
        return theItemPane;
    }



    public static void initPane(List subs,TilePane windowsPane,Stage rootStage,BorderPane rootBorderPane){
        for(int i=0; i<subs.size(); i++)
        {
            if(subs.get(i) instanceof MyFile)
            {
                MyFile myFile=(MyFile)subs.get(i);
                if(myFile.getAttr()==FileAttr.DIRECTORY)
                {
                    windowsPane.getChildren().add(UIService.getItem(myFile, "/resource/icon/folder.png", 1,rootStage,windowsPane,rootBorderPane));
                }
                else{
                    windowsPane.getChildren().add(UIService.getItem(myFile, "/resource/icon/txt.png", 2,rootStage,windowsPane,rootBorderPane));
                }
            }
        }
    }
}
