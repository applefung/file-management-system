package service;

import model.FAT;
import model.MyFile;

public class FATService {
    public static int queryFreeBlock(FAT FATtable){
        for(int i=3;i<128;i++){
            if(FATtable.getTable()[i]==0){
                return i;
            }
        }
        return -1;
    }

    public static void userBlock(int pre, int index, FAT FATtable){
          FATtable.getTable()[pre]=index;
          FATtable.freeCount--;
    }


    public static void freeBlock(int index,FAT Fat){
        Fat.getTable()[index]=0;
        Fat.freeCount++;
    }


    public static void setBlock(int index, FAT Fat, int value){
        Fat.getTable()[index]=value;
    }
}