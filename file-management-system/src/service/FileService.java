package service;

import attr.SystemData;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Disk;
import model.FAT;
import model.MyFile;
import model.Result;
import util.DiskUtils;

import java.util.ArrayList;
import java.util.List;

public class FileService {
	
	
    public static Result addFile(MyFile newFile, Disk disk, FAT fat){
        Result result=new Result();
        int block=FATService.queryFreeBlock(fat);
        if(block!=-1){
            newFile.setBegin(block);
            FATService.userBlock(block,255,fat);
            newFile.setLength(1);
            if(newFile.getFilename()==null||newFile.getFilename().trim().equals("")){
                result.setMessage("null");
                result.setStatus(300);
            }
            else if(checkMulti(newFile)){
                result.setStatus(400);
                result.setMessage("Multiple copies");
            }
            else{
                DiskService.saveFile(newFile,disk);
                SystemData.getActiveFile().add(newFile);
                SystemData.getFileAndDir().add(newFile);
                result.setStatus(200);
                result.setMessage("333");
            }
        }
        else{
            result.setStatus(500);
            result.setMessage("Cannot do anything");
        }
        return result;
    }


    public static void delFile(MyFile myFile){
        delFileContent(myFile);

        SystemData.getActiveFile().remove(myFile);
        SystemData.getFileAndDir().remove(myFile);
        DiskService.removeObject(SystemData.getDisk(), myFile.getBegin());

    }



    public static void delFileContent(MyFile myFile){
        try{
            int[] FATtable=SystemData.getFat().getTable();
            int begin=myFile.getBegin();
            while(FATtable[begin]!=255)
            {
                SystemData.getDisk().getDisk().set(FATtable[begin], null);
                int temp=FATtable[begin];
                FATService.freeBlock(begin, SystemData.getFat());
                begin=temp;
                int length=myFile.getLength()-1;
                myFile.setLength(length);
            }
            if(myFile.getBegin()!=begin)
            {
                FATService.setBlock(begin, SystemData.getFat(), 0);
            }
            FATService.setBlock(myFile.getBegin(), SystemData.getFat(), 255);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }



    public static String getFileContext(MyFile myFile){
        String fileContext="";
        int[] FATtable=SystemData.getFat().getTable();
        int begin=myFile.getBegin();
        while(FATtable[begin]!=255){
            fileContext+=SystemData.getDisk().getDisk().get(FATtable[begin]).toString();
            begin=FATtable[begin];
        }
        return fileContext;
    }



    public static Result updateFile(MyFile myFile, String txt){
        Result result=new Result();
        String test="";
        delFileContent(myFile);
        int blocks=DiskUtils.calNeedBlock(txt); //the amount of the blocks needed
        int freeBlocks=DiskService.getFreeBlocks();
        int index=myFile.getBegin();
        if(blocks>freeBlocks)
        {
            result.setStatus(400);
            result.setMessage("Not enough blocks");
        }
        else{
            char[] chars=txt.toCharArray();
            char[] temps=new char[80];

            while(blocks>0)
            {
            	 int pre=index;
                 index=DiskService.applyFreeBlock(SystemData.getFat());
                 for(int i=0; i<64 && i<chars.length; i++)
                 {
                	 temps[i]= chars[i];
                 }
	             DiskService.saveContext(String.valueOf(temps), SystemData.getDisk(), index);
	             FATService.userBlock(pre, index, SystemData.getFat());
	             test+=String.valueOf(temps);
	             blocks--;


	             myFile.setLength(myFile.getLength()+1);
	        }
            
            FATService.setBlock(index, SystemData.getFat(),255);
            result.setStatus(200);
            result.setMessage("Assigned blocks");
        	}
        
        return result;
    }

    
    public static List<Object> getSub(MyFile file){
        List<Object> subs=new ArrayList<>();
        List<Object> filesAndDirs= SystemData.getFileAndDir();
        for(int i=0; i<filesAndDirs.size(); i++){
            MyFile myFile=(MyFile)filesAndDirs.get(i);
            if(file.equals(myFile.getFile())){
                subs.add(myFile);
            }
        }
        return subs;
    }



    public static boolean checkMulti(MyFile myFile){
        Boolean flag=false;
        MyFile File=myFile.getFile();
        List<Object> subs=FileService.getSub(File);
        for(int i=0;i<subs.size();i++){
            MyFile subFile=(MyFile)subs.get(i);

            if(subFile.getFilename().equals(myFile.getFilename())&&subFile.getAttr()==myFile.getAttr()){
               flag=true;
            }
        }
        return flag;
    }


}