package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyFile implements Serializable {

    private String filename;
    private int attr; //File or Directory
    private int begin;
    private int length;
    private int subDirNum;
    private List<MyFile> sub=new ArrayList<>();
    private MyFile myFile;
    private int readWrite;

    
    public MyFile(){

    }
    public MyFile(String filename){
        this.filename=filename;
    }
    
    
    public int getReadWrite() {

        return readWrite;
    }
    public void setReadWrite(int readWrite) {
        this.readWrite = readWrite;
    }


    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }

    
    public int getAttr() {
        return attr;
    }
    public void setAttr(int attr) {
        this.attr = attr;
    }

    
    public int getBegin() {
        return begin;
    }
    public void setBegin(int begin) {
        this.begin = begin;
    }

    
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }

    public int getSubDirNum() {
        return subDirNum;
    }
    public void setSubDirNum(int subDirNum) {
        this.subDirNum = subDirNum;
    }

    
    public List<MyFile> getSub() {
        return sub;
    }
    public void setSub(List<MyFile> sub) {
        this.sub = sub;
    }

    
    public MyFile getFile() {
        return myFile;
    }
    public void setFile(MyFile myFile) {
        this.myFile = myFile;
    }

}