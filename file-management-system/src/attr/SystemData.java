package attr;

import UI.DirStage;
import model.Disk;
import model.FAT;

import java.util.List;


public class SystemData {
    private static Disk disk;
    private static List<Object> activeFile;
    private static List<Object> activeDir;
    private static List<Object> fileAndDir;
    private static DirStage activeDirUi;
    public static FAT Fat;

    public static DirStage getActiveDirUi() {
        return activeDirUi;
    }
    public static void setActiveDirUi(DirStage activeDirUi) {
        SystemData.activeDirUi = activeDirUi;
    }

    
    public static List<Object> getFileAndDir() {
        return fileAndDir;
    }
    public static void setFileAndDir(List<Object> fileAndDir) {
        SystemData.fileAndDir = fileAndDir;
    }

    
    public static List<Object> getActiveFile() {
        return activeFile;
    }
    public static void setActiveFile(List<Object> activeFile) {
        SystemData.activeFile = activeFile;
    }

    
    public static List<Object> getActiveDir() {
        return activeDir;
    }
    public static void setActiveDir(List<Object> activeDir) {
        SystemData.activeDir = activeDir;
    }

    
    public static FAT  getFat() {
        return Fat;
    }
    public static void setFat(FAT fat) {
        Fat = fat;
    }

    

    public static Disk getDisk() {
        return disk;
    }
    public static void setDisk(Disk disk) {
            SystemData.disk = disk;
            setFat(disk.getFat());
    }
}