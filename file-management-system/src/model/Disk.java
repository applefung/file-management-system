package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Disk implements Serializable {
    private FAT fat;
    private List<Object> disk;
    
    public Disk(){
        disk=new ArrayList<>(128);
        fat=new FAT();
        for(int i=1;i<128;i++)
        {
            disk.add(null);
        }
        
        MyFile myFile=new MyFile();
        myFile.setFile(null);
        myFile.setAttr(8);
        myFile.setBegin(2);
        myFile.setFilename("Disk");
        disk.add(2,myFile);
    }
    
    
    public FAT getFat() {
        return fat;
    }
    public void setFat(FAT fat) {
        this.fat = fat;
    }


    public List<Object> getDisk() {
        return disk;
    }
    public void setDisk(List<Object> disk) {
        this.disk = disk;
    }
}