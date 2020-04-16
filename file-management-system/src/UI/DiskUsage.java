package UI;

import attr.PathAttr;
import attr.SystemData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class DiskUsage {
    private static Stage rootStage;
    static BorderPane rootBossPane;

    static HBox diskPane;
    static BorderPane centerVBox;
    static List<StackPane> disk;
    static GridPane myPane=new GridPane();
    public static ContextMenu contextMenu;


    public static int using = 3;
    public static int noUsing;
    public static ObservableList<PieChart.Data> pieChartData;
    public static PieChart chart;
    static PieChart.Data usingData;
    static PieChart.Data noUsingData;

    public static void main(String[] args) {

    }

    public static void  show(){
        rootBossPane = new BorderPane();
        diskPane = new HBox(new Node[]{makePieChart()});
        diskPane.setPadding(new Insets(5.0D, 5.0D, 20.0D, 5.0D));
        Text diskTipText = new Text("Disk Usage");
        diskTipText.setFont(Font.font(15.0D));
        VBox rootDiskVBox = new VBox(new Node[]{new Separator(), new StackPane(new Node[]{diskTipText}), diskPane});
        centerVBox = new BorderPane();
        centerVBox.setCenter(rootDiskVBox);
        rootBossPane.setBottom(setDisk());
        HBox fatPane = new HBox();
        fatPane.getChildren().add(rootBossPane);
        fatPane.getChildren().add(centerVBox);
        rootStage = newBlankStage(PathAttr.DISK,PathAttr.DISKNAME , fatPane);
        rootStage.setResizable(false);
        ImageView bossIcon = new ImageView(new Image(PathAttr.DISK));
        MenuItem bossItem = new MenuItem(PathAttr.DISKNAME, bossIcon);
        rootStage.show();
        changDiskUsing(SystemData.getFat().getTable());
    }




    public static GridPane setDisk() {
        myPane = new GridPane();
        myPane.setStyle("-fx-background-color: #2cfffc");//lightblue left bgcolor
        myPane.setVgap(30.0D);
        myPane.setHgap(30.0D);
        myPane.setPadding(new Insets(3.0D, 3.0D, 3.0D, 3.0D));
        disk = new ArrayList();

        for(int i = 0; i < 128; ++i) {
            Text numberLabel = new Text(String.valueOf(i));
            StackPane stackPane = new StackPane();
            stackPane.getChildren().add(numberLabel);
            stackPane.setStyle("-fx-background-color: #c8c8c8");//gray right bgcolor
            disk.add(i, stackPane);
            myPane.add(stackPane, i % 10, i / 10);
        }

        return myPane;
    }

    public static VBox makePieChart() {
        usingData = new PieChart.Data("Used: ", (double)using);
        noUsingData = new PieChart.Data("Unused: ", (double)noUsing);
        pieChartData = FXCollections.observableArrayList(new PieChart.Data[]{usingData, noUsingData});
        chart = new PieChart();
        chart.setStartAngle(0.0D);
        chart.setData(pieChartData);
        chart.setLabelsVisible(false);
        chart.setTitle("Disk Usage Percentage");
        VBox chartPane = new VBox();
        chartPane.setMaxWidth(250.0D);
        chartPane.getChildren().add(chart);
        chart.setMaxHeight(280.0D);
        return chartPane;
    }


    public static void changDiskUsing(int[] fatTable) {
        int using = 0;

        for(int i = 0; i < fatTable.length; ++i) {
            if(fatTable[i]==0){
                ((StackPane)disk.get(i)).setStyle("-fx-background-color: #18ff0e");//green free block color

            }else {
                using++;
                ((StackPane)disk.get(i)).setStyle("-fx-background-color: #ff0043");//red occupied block color
            }
        }

        changPieChart(using);
    }
    
    public static void changPieChart(int using) {
        float usingNum = (float)((double)((float)using * 100.0F) / 128.0D);
        usingNum = (float)((double)Math.round(usingNum * 100.0F) / 100.0D);
        float noUsingNum = 100.0F - usingNum;
        noUsingNum = (float)((double)Math.round(noUsingNum * 100.0F) / 100.0D);
        String usingTip = "Used: " + usingNum + "%";
        String noUsingTip = "Unused: " + noUsingNum + "%";
        usingData.setName(usingTip);
        noUsingData.setName(noUsingTip);
        usingData.setPieValue((double)using);
        noUsingData.setPieValue((double)(128 - using));
    }

    public static Stage newBlankStage(String stageIconPath, String stageTitle, Pane rootPane) {
        Stage stage = new Stage();
        stage.setTitle(stageTitle);
        stage.getIcons().add(new Image(stageIconPath));
        Scene scene = new Scene(rootPane);
        stage.setScene(scene);
        return stage;
    }
}
