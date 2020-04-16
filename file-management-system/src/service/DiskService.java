package service;

import attr.SystemData;
import model.Disk;
import model.FAT;
import model.MyFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DiskService {
    public static void saveFile(MyFile newFile, Disk disk)
    {
        disk.getDisk().add(newFile.getBegin(), newFile);
    }
    

    public  static void saveContext(Object context, Disk disk, int index)
    {
        FATService.setBlock(index, SystemData.getFat(), 255);
        disk.getDisk().set(index, context);
    }
    

    public static int applyFreeBlock(FAT fatable)
    {
        for(int i=3;i<128;i++){
            if(fatable.getTable()[i]==0){
                return i;
            }
        }
        return -1;
    }

    
   public static void removeObject(Disk disk,int index)
   {
       disk.getDisk().set(index, null);
       FATService.freeBlock(index, SystemData.getFat());
   }



    public static Disk checkDisk(Disk disk)
    {
        if(disk==null)
        {
            disk=new Disk();
        }
        return  disk;
    }



    public static HashMap getFileAndDir(Disk disk)
    {
        HashMap hashMap=new HashMap();
        List<Object> files=new ArrayList<>();
        List<Object> dirs=new ArrayList<>();
        List<Object> allFiles=new ArrayList<>();
        List<Object> data=disk.getDisk();
        for(int i=0;i<128;i++){
            if(data.get(i) instanceof MyFile){
                MyFile myFile=(MyFile)data.get(i);
                allFiles.add(myFile);
                if(myFile.getAttr()==3){
                    files.add(myFile);
                }else{
                    dirs.add(myFile);
                }
            }
        }
        hashMap.put("files",files);
        hashMap.put("dirs",dirs);
        hashMap.put("allFiles",allFiles);
        return hashMap;
    }



    public static int getFreeBlocks(){
        return SystemData.getDisk().getFat().freeCount;
    }

  
    public static Object getDisk(String fileName){
        File file=new File(fileName);
        try{
            FileInputStream input=new FileInputStream(file);
            ObjectInputStream ois=new ObjectInputStream(input);
            Object obj=ois.readObject();
            ois.close();
            input.close();
            return obj;
        }catch (Exception e){
            
        }
      return null;
    }
}