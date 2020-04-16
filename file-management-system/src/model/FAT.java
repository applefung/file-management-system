package model;

import java.io.Serializable;

public class FAT implements Serializable {
    public  int freeCount = 125;
    public  int[] table = new int[128];
    
    public FAT(){
        table[0]=-1;
        table[1]=-1;
        table[2]=-1;
    }
  
    
    public int getFreeCount() {
        return freeCount;
    }
    public void setFreeCount(int freeCount) {
        this.freeCount = freeCount;
    }

    
    public int[] getTable() {
        return table;
    }
    public void setTable(int[] table) {
        this.table = table;
    }
}
