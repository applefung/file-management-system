package application;
	
import java.util.HashMap;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;

import model.Disk;
import model.MyFile;
import service.DiskService;
import service.FileService;
import service.UIService;
import UI.FileStage;
import attr.FileAttr;
import attr.PathAttr;
import attr.SystemData;


public class Main extends Application {
    private static BorderPane rootBorderPane;
    private static Stage rootStage;
    private static TilePane windowsPane;
    
    private static Boolean ifOpen=true;
    
    public static void setIfOpen(Boolean ifOpen) {
        Main.ifOpen = ifOpen;
    }
    public static Boolean getIfOpen() {
        return ifOpen;
    }

    

	@Override
	public void start(Stage primaryStage) throws Exception{
        rootStage=primaryStage;
        rootStage.setMaximized(true);
        rootStage.setTitle("File Management System");
        rootStage.getIcons().add(new Image(PathAttr.START_BUTTON));
        rootBorderPane = new BorderPane();
        Scene scene = new Scene(rootBorderPane);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        rootStage.setScene(scene);
        
        
        rootBorderPane.setBackground(new Background(new BackgroundImage[]{new BackgroundImage(new Image(PathAttr.BACKGROUND), (BackgroundRepeat)null, (BackgroundRepeat)null, (BackgroundPosition)null, new BackgroundSize(1.0, 1.0, true, true, false, false))}));
        
        windowsPane = new TilePane();

        rootBorderPane.setCenter(windowsPane);
        //3 means disk usage
        windowsPane.getChildren().add(UIService.getItem(new MyFile("Disk C"), PathAttr.DISK, 3, rootStage, windowsPane, rootBorderPane));
        Disk disk=(Disk)DiskService.getDisk("Disk C");
        SystemData.setDisk(DiskService.checkDisk(disk));
        SystemData.setFat(SystemData.getDisk().getFat());
        HashMap fileDir=DiskService.getFileAndDir(SystemData.getDisk());
        SystemData.setActiveFile((List<Object>)fileDir.get("files"));
        SystemData.setActiveDir((List<Object>)fileDir.get("dirs"));
        SystemData.setFileAndDir((List<Object>)fileDir.get("allFiles"));

        initWindow(SystemData.getDisk(),windowsPane);
        setRightMenu();
        rootStage.show();
	}
	
	
	//right click to get menu (create file or folder)
	   private void setRightMenu() {
	        ContextMenu rootRightMenu = new ContextMenu();
	        
	        //new file
	        MenuItem newFileItem = new MenuItem("New File");
	        rootRightMenu.getItems().add(newFileItem);
	        newFileItem.setOnAction((a) -> {
	            FileStage.show(FileAttr.FILE,(MyFile)SystemData.getDisk().getDisk().get(2),windowsPane,rootStage);
	        });
	        
	        //new folder
	        MenuItem newFolderItem = new MenuItem("New Folder");
	        rootRightMenu.getItems().add(newFolderItem);
	        newFolderItem.setOnAction((e) -> {
	            FileStage.show(FileAttr.DIRECTORY,(MyFile)SystemData.getDisk().getDisk().get(2),windowsPane,rootStage);
	        });
	        
	        windowsPane.addEventHandler(MouseEvent.MOUSE_CLICKED,  (MouseEvent  me) ->  {
	            if(ifOpen)
	            {
	            	//right click
	                if (me.getButton() == MouseButton.SECONDARY)  {
	                    rootRightMenu.show(rootBorderPane, me.getScreenX(), me.getScreenY());
	                }  
	                else  
	                {
	                    rootRightMenu.hide();
	                }
	            }
	        });
	    }

	    private static void initWindow(Disk disk,TilePane windowsPane){
	        List<Object> diskContent=disk.getDisk();
	        List<Object> subs= FileService.getSub((MyFile)diskContent.get(2));
	        UIService.initPane(subs, windowsPane, rootStage, rootBorderPane);
	    }
	    
	public static void main(String[] args) {
		launch(args);
	}
}
