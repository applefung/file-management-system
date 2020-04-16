package UI;



import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.image.ImageView;
import UI.ErrorUI;
import attr.PathAttr;
import model.MyFile;
import model.Result;
import service.FileService;



public class EditFile {

    private static Stage stage;
    static TextArea textField ;
    static VBox root;
    static  MenuBar menuBar;
    static Scene scene;
    
    public static Stage getStage() {
        return stage;
    }
   
    
    public static void init(MyFile myFile)
    {
        stage=new Stage();
        stage.getIcons().add(new Image(PathAttr.TXT));
        stage.setTitle(myFile.getFilename());
        stage.setWidth(650);
        stage.setHeight(500);
        textField=new TextArea();
        root=new VBox();
        menuBar= new MenuBar();
        scene= new Scene(new Group());
    }
    
    //read write
    public static void show(MyFile myFile) {
         init(myFile);
        //Menu File
        Menu menuFile = new Menu("File");
        MenuItem add = new MenuItem("Save", new ImageView(new Image(PathAttr.TXT, 30, 30, false, false)));
        //shortcut
        add.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        add.setOnAction((ActionEvent t) -> {
            Result result=FileService.updateFile(myFile, textField.getText());
            if(result.getStatus()!=200){
                ErrorUI.alertDialog("Cannot save the file",result.getMessage(),stage);
            }
        });
        

        //add save to menu
        menuFile.getItems().addAll(add);
 
        menuBar.getMenus().addAll(menuFile);

        textField.setPrefHeight(750);

        
        String fileContext=FileService.getFileContext(myFile);

        textField.setText(fileContext);

        root.getChildren().addAll(menuBar,textField);
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
    }

    //read only
    public static void showContent(MyFile myFile){

        init(myFile);
        root.setAlignment(Pos.BOTTOM_LEFT);
        textField.setPrefHeight(750);
        textField.setEditable(false);
        String fileContext=FileService.getFileContext(myFile);
        textField.setText(fileContext);

        root.getChildren().addAll(menuBar,textField);
        scene.setRoot(root);
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                windowEvent.consume();
                stage.close();
            }
        });
        stage.show();
    }

}
