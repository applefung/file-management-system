package service;

import model.MyFile;

import java.util.List;

public class DirService {

    public static void delDirContent(MyFile myDir){
        List<Object> subs=(List<Object>)FileService.getSub(myDir);
        for(int i=0;i<subs.size();i++){
            MyFile myFile=(MyFile)subs.get(i);
            List<Object> fileSubs=(List<Object>)FileService.getSub(myFile);
            if(fileSubs.size()>0){
                delDirContent(myFile);
            }else{
                FileService.delFile(myFile);
            }
        }
        FileService.delFile(myDir);
    }

}
